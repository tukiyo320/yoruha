package jp.co.tukiyo.yoruha.viewmodel

import android.content.Context
import android.databinding.BaseObservable
import android.databinding.ObservableField
import android.widget.Toast
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers
import jp.co.tukiyo.yoruha.api.googlebooks.GoogleBooksAPIClientBuilder
import jp.co.tukiyo.yoruha.extensions.async
import jp.co.tukiyo.yoruha.extensions.onNext
import jp.co.tukiyo.yoruha.extensions.sync
import jp.co.tukiyo.yoruha.ui.adapter.BookSearchResultListAdapter

class SearchResultListFragmentViewModel(context: Context) : FragmentViewModel(context) {
    val listAdapter: BookSearchResultListAdapter = BookSearchResultListAdapter(context)
    val queryString: ObservableField<String> = ObservableField("")

    fun search(query: String) {
        queryString.set(query)
        GoogleBooksAPIClientBuilder().build()
                .search(query)
                .async(Schedulers.newThread())
                .compose(bindToLifecycle())
                .map { it.items }
                .onNext {
                    listAdapter.run {
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
}