package jp.co.tukiyo.yoruha.api.googlebooks

import android.accounts.Account
import android.content.Context
import android.widget.Toast
import com.google.android.gms.auth.GoogleAuthUtil
import com.google.android.gms.auth.UserRecoverableAuthException
import io.reactivex.Observable
import jp.co.tukiyo.yoruha.api.googlebooks.model.BookShelfVolumesResponse
import jp.co.tukiyo.yoruha.api.googlebooks.model.BooksResponse
import jp.co.tukiyo.yoruha.api.googlebooks.model.VolumeItem
import jp.co.tukiyo.yoruha.extensions.getSharedPreference
import jp.co.tukiyo.yoruha.extensions.getUserEmail
import retrofit2.http.*

interface GoogleBooksAPIClient {

    enum class OrderBy(var command : String) {
        RELEVANCE("relevance"),
        NEWEST("newest");
    }

    fun getToken(context: Context): Observable<String> {
        val email = context.getSharedPreference().getUserEmail()
        val scope = "https://www.googleapis.com/auth/books"

        return Observable.create { subscriber ->
            try {
                val token = GoogleAuthUtil.getToken(context, Account(email, "com.google"), "oauth2:$scope")
                subscriber.onNext(token)
            } catch (e: UserRecoverableAuthException) {
                Toast.makeText(context, "authorization failed", Toast.LENGTH_SHORT).show()
                subscriber.onError(e)
            } catch (e: Exception) {
                subscriber.onError(e)
            }
        }
    }

    @GET("/books/v1/volumes")
    fun search(@Query("q") q: String): Observable<BooksResponse>

    @GET("/books/v1/volumes")
    fun search(@Query("q") q: String, @Query("orderBy") orderBy: String, @Query("startIndex") startIndex: Int) : Observable<BooksResponse>

    @Headers("Content-Type: application/json")
    @GET("/books/v1/mylibrary/bookshelves/{shelfId}/volumes")
    fun myShelfBooks(@Header("Authorization") token: String, @Path("shelfId") shelfId: Int) : Observable<BookShelfVolumesResponse>

    @GET("/books/v1/volumes/{volumeId}")
    fun bookInfo(@Path("volumeId") volumeId: String): Observable<VolumeItem>
}