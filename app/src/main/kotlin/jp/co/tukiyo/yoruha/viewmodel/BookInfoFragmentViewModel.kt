package jp.co.tukiyo.yoruha.viewmodel

import android.content.Context
import android.databinding.ObservableField
import android.text.Html
import android.text.Spanned
import android.widget.ArrayAdapter
import android.widget.Toast
import jp.co.tukiyo.yoruha.data.api.googlebooks.model.VolumeItem
import jp.co.tukiyo.yoruha.extensions.onSuccess
import jp.co.tukiyo.yoruha.usecase.BookShelfManageUseCase

class BookInfoFragmentViewModel(context: Context, val volumeId: String) : FragmentViewModel(context) {
    val book: ObservableField<VolumeItem> = ObservableField()
    val description: ObservableField<Spanned> = ObservableField()
    val useCase = BookShelfManageUseCase(context)
    val adapter = ArrayAdapter<String>(context, android.R.layout.simple_list_item_1)

    fun fetchInfo() {
        useCase.getBookInfo(volumeId)
                .compose(bindToLifecycle())
                .onSuccess {
                    book.set(it)
                    description.set(Html.fromHtml(it.volumeInfo.description))
                }
                .onError { }
                .subscribe()
    }

    fun fetchInWhichShelf() {
        useCase.getShelfIdBookIn(volumeId)
                .compose(bindToLifecycle())
                .collectInto(mutableListOf<String>(), { list, (_, title) -> list.add(title) })
                .onSuccess {
                    adapter.addAll(it)
                }
                .onError {
                    Toast.makeText(context, "failed", Toast.LENGTH_SHORT).show()
                }
                .subscribe()
    }
}