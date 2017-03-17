package jp.co.tukiyo.yoruha.usecase

import android.content.Context
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import jp.co.tukiyo.yoruha.api.googlebooks.GoogleBooksAPIClient
import jp.co.tukiyo.yoruha.api.googlebooks.model.VolumeItem
import jp.co.tukiyo.yoruha.element.BookShelf
import jp.co.tukiyo.yoruha.repository.GoogleBooksRepository

class BookShelfManageUseCase(context: Context) {
    private val repository = GoogleBooksRepository(context)

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
        return repository.getMyShelfVolumes(shelfId)
                .map { it.items }
    }

    fun removeBook(shelfId: Int, volumeId: String): Completable {
        return repository.removeVolume(shelfId, volumeId)
    }

    fun addBook(shelfId: Int, volumeId: String): Completable {
        return repository.addVolume(shelfId, volumeId)
    }

    fun getBookInfo(volumeId: String): Single<VolumeItem> {
        return repository.getVolumeInfo(volumeId)
    }

    fun search(q: String, orderBy: GoogleBooksAPIClient.OrderBy, startIndex: Int): Single<List<VolumeItem>> {
        return repository.search(q, orderBy, startIndex)
                .map { it.items }
    }

    fun getPageOfAllHaveReadBooks(): Single<Int> {
        return repository.getMyShelfVolumesAll(haveReadShelf.no)
                .map { it.volumeInfo.pageCount ?: 0 }
                .reduce(0) { t1: Int, t2: Int -> t1 + t2 }
    }
}