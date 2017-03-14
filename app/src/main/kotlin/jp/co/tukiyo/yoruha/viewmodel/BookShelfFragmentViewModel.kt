package jp.co.tukiyo.yoruha.viewmodel

import android.content.Context
import android.widget.Toast
import jp.co.tukiyo.yoruha.api.googlebooks.model.VolumeItem
import jp.co.tukiyo.yoruha.extensions.onCompleted
import jp.co.tukiyo.yoruha.extensions.onSuccess
import jp.co.tukiyo.yoruha.ui.adapter.BookShelfItemListAdapter
import jp.co.tukiyo.yoruha.ui.fragment.BaseFragment
import jp.co.tukiyo.yoruha.ui.screen.BookInfoScreen
import jp.co.tukiyo.yoruha.usecase.BookShelfManageUseCase

class BookShelfFragmentViewModel(context: Context, val shelfId: Int)
    : FragmentViewModel(context) {
    lateinit var adapter: BookShelfItemListAdapter
    val useCase = BookShelfManageUseCase(context)

    fun fetchBooks() {
        useCase.getMyShelfBooks(shelfId)
                .compose(bindToLifecycle())
                .onSuccess {
                    adapter.clear()
                    adapter.addAll(it)
                    adapter.notifyDataSetChanged()
                }
                .onError {
                    Toast.makeText(context, "failed fetching books", Toast.LENGTH_SHORT).show()
                }
                .subscribe()
    }

    fun onItemClick(item: VolumeItem, fragment: BaseFragment<*>) {
        fragment.screenActivity.pushScreen(BookInfoScreen(item.id))
    }

    fun onItemRemove(item: VolumeItem, fragment: BaseFragment<*>) {
        useCase.removeBook(shelfId, item.id)
                .onCompleted {
                    adapter.remove(item)
                    Toast.makeText(context, "本棚から削除しました", Toast.LENGTH_SHORT).show()
                }
                .onError {
                    Toast.makeText(context, "本棚から削除できませんでした", Toast.LENGTH_SHORT).show()
                }
                .subscribe()
    }
}
