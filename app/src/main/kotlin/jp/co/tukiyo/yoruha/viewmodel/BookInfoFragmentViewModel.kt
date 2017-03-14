package jp.co.tukiyo.yoruha.viewmodel

import android.content.Context
import android.databinding.ObservableField
import android.text.Html
import android.text.Spanned
import jp.co.tukiyo.yoruha.api.googlebooks.model.VolumeItem
import jp.co.tukiyo.yoruha.extensions.onSuccess
import jp.co.tukiyo.yoruha.usecase.BookShelfManageUseCase

class BookInfoFragmentViewModel(context: Context, val volumeId: String) : FragmentViewModel(context) {
    val book: ObservableField<VolumeItem> = ObservableField()
    val description: ObservableField<Spanned> = ObservableField()
    val useCase = BookShelfManageUseCase(context)

    fun fetchInfo() {
        useCase.getBookInfo(volumeId)
                .onSuccess {
                    book.set(it)
                    description.set(Html.fromHtml(it.volumeInfo.description))
                }
                .onError { }
                .subscribe()
    }
}