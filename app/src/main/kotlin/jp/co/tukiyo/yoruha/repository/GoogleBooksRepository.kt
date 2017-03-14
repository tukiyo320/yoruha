package jp.co.tukiyo.yoruha.repository

import android.content.Context
import android.content.SharedPreferences
import io.reactivex.Completable
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
    private val client: GoogleBooksAPIClient = GoogleBooksAPIClientBuilder().build()
    private val authorizationHeader: String = "Bearer $token"
    private var token: String
        get() {
            return prefs.getGoogleOAuthToken().let {
                context.crypto.decrypt(it)
            }
        }
        set(value) {
            context.crypto.encrypt(value).run {
                prefs.edit().putGoogleOAuthToken(this).apply()
            }
        }

    private fun refreshedToken(): String {
        return client.getToken(context)
                .onErrorReturn { token }
                .blockingGet()
    }

    fun getMyShelfVolumes(shelfId: Int): Single<BookShelfVolumesResponse> {
        return client.myShelfVolumes(authorizationHeader, shelfId)
                .onErrorResumeNext {
                    if (it is HttpException && it.code() == 401) {
                        token = refreshedToken()
                    }
                    client.myShelfVolumes(authorizationHeader, shelfId)
                }
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
                        token = refreshedToken()
                    }
                    client.removeVolume(authorizationHeader, shelfId, volumeId)
                }
                .async(Schedulers.newThread())
    }

    fun addVolume(shelfId: Int, volumeId: String): Completable {
        return client.addVolumeToShelf(authorizationHeader, shelfId, volumeId)
                .onErrorResumeNext {
                    if (it is HttpException && it.code() == 401) {
                        token = refreshedToken()
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