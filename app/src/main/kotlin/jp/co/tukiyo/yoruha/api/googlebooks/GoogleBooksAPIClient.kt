package jp.co.tukiyo.yoruha.api.googlebooks

import io.reactivex.Observable
import jp.co.tukiyo.yoruha.api.googlebooks.model.BooksResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface GoogleBooksAPIClient {

    enum class OrderBy(var command : String) {
        RELEVANCE("relevance"),
        NEWEST("newest");
    }

    @GET("/books/v1/volumes")
    fun search(@Query("q") q: String): Observable<BooksResponse>

    @GET("/books/v1/volumes")
    fun search(@Query("q") q: String, @Query("orderBy") orderBy: String, @Query("startIndex") startIndex: Int) : Observable<BooksResponse>
}