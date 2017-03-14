package jp.co.tukiyo.yoruha.viewmodel

import android.content.Context
import android.widget.Toast
import jp.co.tukiyo.yoruha.api.googlebooks.GoogleBooksAPIClient
import jp.co.tukiyo.yoruha.api.googlebooks.model.VolumeItem
import jp.co.tukiyo.yoruha.extensions.onSuccess
import jp.co.tukiyo.yoruha.ui.adapter.BookSearchResultListAdapter
import jp.co.tukiyo.yoruha.ui.fragment.BaseFragment
import jp.co.tukiyo.yoruha.ui.screen.BookInfoScreen
import jp.co.tukiyo.yoruha.usecase.BookShelfManageUseCase

class SearchResultListFragmentViewModel(context: Context) : FragmentViewModel(context) {
    lateinit var adapter: BookSearchResultListAdapter
    var orderBy: GoogleBooksAPIClient.OrderBy = GoogleBooksAPIClient.OrderBy.RELEVANCE
    val useCase = BookShelfManageUseCase(context)

    fun search(query: String) {
        search(query, 0)
    }

    fun search(query: String, startIndex: Int) {
        useCase.search(query, orderBy, startIndex)
                .compose(bindToLifecycle())
                .onSuccess {
                    adapter.run {
                        clear()
                        addAll(it)
                        notifyDataSetChanged()
                    }
                }
                .onError {
                    Toast.makeText(context, "search failed", Toast.LENGTH_SHORT).show()
                }
                .subscribe()
    }

    fun refresh(query: String) {
        search(query)
    }

    fun onItemClick(item: VolumeItem, fragment: BaseFragment<*>) {
        fragment.screenActivity.pushScreen(BookInfoScreen(item.id))
    }
}