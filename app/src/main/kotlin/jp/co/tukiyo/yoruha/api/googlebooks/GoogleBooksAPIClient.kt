package jp.co.tukiyo.yoruha.api.googlebooks

import io.reactivex.Completable
import io.reactivex.Single
import jp.co.tukiyo.yoruha.api.googlebooks.model.BookShelfVolumesResponse
import jp.co.tukiyo.yoruha.api.googlebooks.model.BooksResponse
import jp.co.tukiyo.yoruha.api.googlebooks.model.VolumeItem
import retrofit2.http.*

interface GoogleBooksAPIClient {

    enum class OrderBy(var command: String) {
        RELEVANCE("relevance"),
        NEWEST("newest");
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