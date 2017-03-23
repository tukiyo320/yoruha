package jp.co.tukiyo.yoruha.usecase

import android.content.Context
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import jp.co.tukiyo.yoruha.data.api.googlebooks.GoogleBooksAPIClient
import jp.co.tukiyo.yoruha.data.api.googlebooks.model.VolumeItem
import jp.co.tukiyo.yoruha.data.db.Book
import jp.co.tukiyo.yoruha.element.BookShelf
import jp.co.tukiyo.yoruha.extensions.async
import jp.co.tukiyo.yoruha.repository.BooksRepository
import jp.co.tukiyo.yoruha.repository.GoogleBooksRepository

class BookShelfManageUseCase(context: Context) {
    private val googleBooksRepository = GoogleBooksRepository(context)
    private val sortedPreShelves by lazy {
        preShelves.sortedBy { it.no }
    }

    companion object {
        val haveReadShelf: BookShelf = BookShelf(4, "読んだ本", false)
        val readingShelf: BookShelf = BookShelf(3, "読んでる本", false)
        val wantReadShelf: BookShelf = BookShelf(2, "読みたい本", false)

        val preShelves: List<BookShelf> = listOf(
                readingShelf,
                wantReadShelf,
                haveReadShelf
        )
    }

    fun getMyShelfBooks(shelfId: Int): Single<List<VolumeItem>> {
        return googleBooksRepository.getMyShelfVolumes(shelfId)
                .map { it.items }
    }

    fun removeBook(shelfId: Int, volumeId: String): Completable {
        return googleBooksRepository.removeVolume(shelfId, volumeId)
                .andThen(BooksRepository.deleteBook(Book(volumeId, shelfId)))
    }

    fun addBook(shelfId: Int, volumeId: String): Completable {
        return googleBooksRepository.addVolume(shelfId, volumeId)
                .andThen(BooksRepository.insertBook(Book(volumeId, shelfId)))
    }

    fun getBookInfo(volumeId: String): Single<VolumeItem> {
        return googleBooksRepository.getVolumeInfo(volumeId)
    }

    fun search(q: String, orderBy: GoogleBooksAPIClient.OrderBy, startIndex: Int): Single<List<VolumeItem>> {
        return googleBooksRepository.search(q, orderBy, startIndex)
                .map { it.items }
    }

    fun getPageOfAllHaveReadBooks(): Single<Int> {
        return googleBooksRepository.getMyShelfVolumesInfoAll(haveReadShelf.no)
                .map { it.volumeInfo.pageCount ?: 0 }
                .reduce(0) { t1: Int, t2: Int -> t1 + t2 }
    }

    fun syncBooksForAllShelves(): Completable {
        return Observable.fromIterable(preShelves)
                .flatMap { syncBooks(it.no) }
                .flatMapCompletable {
                    Completable.complete()
                }
                .async(Schedulers.newThread())
    }

    private fun syncBooks(shelfId: Int): Observable<Long> {
        return googleBooksRepository.getMyShelfVolumesAll(shelfId)
                .map { Book(it.id, shelfId) }
                .flatMap { BooksRepository.upsertBooks(it) }
    }

    fun getShelfIdBookIn(volumeId: String): Observable<BookShelf> {
        return BooksRepository.findByVolumeId(volumeId)
                .map {
                    sortedPreShelves.binarySearchBy(it.shelfId, selector = { it.no }).let {
                        sortedPreShelves[it]
                    }
                }
    }
}