package jp.co.tukiyo.yoruha.api.googlebooks

import android.accounts.Account
import android.content.Context
import android.widget.Toast
import com.google.android.gms.auth.GoogleAuthUtil
import com.google.android.gms.auth.UserRecoverableAuthException
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import jp.co.tukiyo.yoruha.api.googlebooks.model.BookShelfVolumesResponse
import jp.co.tukiyo.yoruha.api.googlebooks.model.BooksResponse
import jp.co.tukiyo.yoruha.api.googlebooks.model.VolumeItem
import jp.co.tukiyo.yoruha.extensions.getSharedPreference
import jp.co.tukiyo.yoruha.extensions.getUserEmail
import okhttp3.ResponseBody
import retrofit2.http.*

interface GoogleBooksAPIClient {

    enum class OrderBy(var command: String) {
        RELEVANCE("relevance"),
        NEWEST("newest");
    }

    fun getToken(context: Context): Single<String> {
        val email = context.getSharedPreference().getUserEmail()
        val scope = "https://www.googleapis.com/auth/books"

        return Single.create { subscriber ->
            try {
                val token = GoogleAuthUtil.getToken(context, Account(email, "com.google"), "oauth2:$scope")
                subscriber.onSuccess(token)
            } catch (e: UserRecoverableAuthException) {
                Toast.makeText(context, "authorization failed", Toast.LENGTH_SHORT).show()
                subscriber.onError(e)
            } catch (e: Exception) {
                subscriber.onError(e)
            }
        }
    }

    @GET("/books/v1/volumes")
    fun search(@Query("q") q: String, @Query("orderBy") orderBy: String, @Query("startIndex") startIndex: Int): Single<BooksResponse>

    @Headers("Content-Type: application/json")
    @GET("/books/v1/mylibrary/bookshelves/{shelfId}/volumes")
    fun myShelfVolumes(@Header("Authorization") token: String, @Path("shelfId") shelfId: Int): Single<BookShelfVolumesResponse>

    @GET("/books/v1/volumes/{volumeId}")
    fun volumeInfo(@Path("volumeId") volumeId: String): Single<VolumeItem>

    @POST("/books/v1/mylibrary/bookshelves/{shelfId}/addVolume")
    fun addVolumeToShelf(
            @Header("Authorization") token: String,
            @Path("shelfId") shelfId: Int,
            @Query("volumeId") volumeId: String
    ): Completable

    @POST("/books/v1/mylibrary/bookshelves/{shelfId}/removeVolume")
    fun removeVolume(
            @Header("Authorization") token: String,
            @Path("shelfId") shelfId: Int,
            @Query("volumeId") volumeId: String
    ): Completable
}