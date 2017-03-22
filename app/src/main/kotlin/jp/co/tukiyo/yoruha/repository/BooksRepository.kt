package jp.co.tukiyo.yoruha.repository

import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import jp.co.tukiyo.yoruha.data.db.Book
import jp.co.tukiyo.yoruha.data.db.OrmaHolder
import jp.co.tukiyo.yoruha.extensions.async

object BooksRepository {
    val orma = OrmaHolder.ORMA

    fun upsertBooks(books: List<Book>): Observable<Book> {
        return orma.relationOfBook()
                .upsertAsObservable(books)
                .async(Schedulers.newThread())
    }

    fun findByVolumeId(volumeId: String): Observable<Book> {
        return orma.selectFromBook()
                .volumeIdEq(volumeId)
                .executeAsObservable()
                .async(Schedulers.newThread())
    }
}