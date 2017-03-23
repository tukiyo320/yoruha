package jp.co.tukiyo.yoruha.repository

import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import jp.co.tukiyo.yoruha.data.db.Book
import jp.co.tukiyo.yoruha.data.db.OrmaHolder
import jp.co.tukiyo.yoruha.extensions.async

object BooksRepository {
    val orma = OrmaHolder.ORMA

    fun upsertBooks(book: Book): Observable<Long> {
        return Observable.just(book)
                .filter {
                    orma.selectFromBook()
                            .volumeIdAndShelfIdEq(it.volumeId, it.shelfId)
                            .getOrNull(0) == null
                }
                .map { orma.insertIntoBook(it) }
                .async(Schedulers.newThread())
    }

    fun findByVolumeId(volumeId: String): Observable<Book> {
        return orma.selectFromBook()
                .volumeIdEq(volumeId)
                .executeAsObservable()
                .async(Schedulers.newThread())
    }
}