package jp.co.tukiyo.yoruha.repository

import android.accounts.Account
import android.content.Context
import android.content.SharedPreferences
import com.google.android.gms.auth.GoogleAuthUtil
import com.google.android.gms.auth.UserRecoverableAuthException
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import jp.co.tukiyo.yoruha.api.googlebooks.GoogleBooksAPIClient
import jp.co.tukiyo.yoruha.api.googlebooks.GoogleBooksAPIClientBuilder
import jp.co.tukiyo.yoruha.api.googlebooks.model.BookShelfVolumesResponse
import jp.co.tukiyo.yoruha.api.googlebooks.model.BooksResponse
import jp.co.tukiyo.yoruha.api.googlebooks.model.VolumeItem
import jp.co.tukiyo.yoruha.extensions.*
import retrofit2.HttpException

class GoogleBooksRepository(val context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreference()
    private val client: GoogleBooksAPIClient = GoogleBooksAPIClientBuilder(context).build()
    private val ACCOUNT_TYPE = "com.google"
    private val SCOPE = "https://www.googleapis.com/auth/books"
    private val authorizationHeader: String
        get() = "Bearer $token"
    private var token: String = ""
        get() {
            return prefs.getGoogleOAuthToken().let {
                context.crypto.decrypt(it)
            }
        }
        set(value) {
            field = value
            context.crypto.encrypt(value).run {
                prefs.edit().putGoogleOAuthToken(this).apply()
            }
        }

    fun revertToken() {
        token = ""
    }

    private fun authorization(email: String): Completable {
        return Completable.create({ subscriber ->
            try {
                token = GoogleAuthUtil.getToken(context, Account(email, ACCOUNT_TYPE), "oauth2:$SCOPE")
                subscriber.onComplete()
            } catch (e: UserRecoverableAuthException) {
                token = ""
                subscriber.onError(e)
            } catch (e: Exception) {
                token = ""
                subscriber.onError(e)
            }
        })
    }

    fun authorize(email: String): Completable {
        return authorization(email)
                .async(Schedulers.newThread())
    }

    private fun getRefreshedToken(): String {
        return authorization(prefs.getUserEmail())
                .async(Schedulers.newThread())
                .andThen(Single.just(token))
                .blockingGet()
    }

    fun getMyShelfVolumes(shelfId: Int): Single<BookShelfVolumesResponse> {
        return getMyShelfVolumes(shelfId, 0)
                .async(Schedulers.newThread())
    }

    private fun getMyShelfVolumes(shelfId: Int, startIndex: Int): Single<BookShelfVolumesResponse> {
        return client.myShelfVolumes(authorizationHeader, shelfId, startIndex)
                .onErrorResumeNext {
                    if (it is HttpException && it.code() == 401) {
                        token = getRefreshedToken()
                    }
                    client.myShelfVolumes(authorizationHeader, shelfId)
                }
    }

    fun getMyShelfVolumesAll(shelfId: Int): Observable<VolumeItem> {
        return Observable.create<List<VolumeItem>> { subscriber ->
            try {
                var startIndex = 0
                while (true) {
                    val res = getMyShelfVolumes(shelfId, startIndex).blockingGet()

                    if (res.items != null) {
                        startIndex += res.items.size
                        subscriber.onNext(res.items)
                    } else {
                        subscriber.onComplete()
                        break
                    }
                }
            } catch (e: Exception) {
                subscriber.onError(e)
            }
        }
                .flatMap { Observable.fromIterable(it) }
                .map { getVolumeInfo(it.id).blockingGet() }
                .async(Schedulers.newThread())
    }

    fun getVolumeInfo(volumeId: String): Single<VolumeItem> {
        return client.volumeInfo(volumeId)
                .async(Schedulers.newThread())
    }

    fun removeVolume(shelfId: Int, volumeId: String): Completable {
        return client.removeVolume(authorizationHeader, shelfId, volumeId)
                .onErrorResumeNext {
                    if (it is HttpException && it.code() == 401) {
                        token = getRefreshedToken()
                    }
                    client.removeVolume(authorizationHeader, shelfId, volumeId)
                }
                .async(Schedulers.newThread())
    }

    fun addVolume(shelfId: Int, volumeId: String): Completable {
        return client.addVolumeToShelf(authorizationHeader, shelfId, volumeId)
                .onErrorResumeNext {
                    if (it is HttpException && it.code() == 401) {
                        token = getRefreshedToken()
                    }
                    client.addVolumeToShelf(authorizationHeader, shelfId, volumeId)
                }
                .async(Schedulers.newThread())
    }

    fun search(q: String, orderBy: GoogleBooksAPIClient.OrderBy, startIndex: Int): Single<BooksResponse> {
        return client.search(q, orderBy.name, startIndex)
                .async(Schedulers.newThread())
    }
}