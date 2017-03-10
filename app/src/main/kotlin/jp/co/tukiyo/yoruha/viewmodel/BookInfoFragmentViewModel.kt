package jp.co.tukiyo.yoruha.viewmodel

import android.content.Context
import android.databinding.ObservableField
import android.text.Html
import android.text.Spanned
import io.reactivex.schedulers.Schedulers
import jp.co.tukiyo.yoruha.api.googlebooks.GoogleBooksAPIClient
import jp.co.tukiyo.yoruha.api.googlebooks.GoogleBooksAPIClientBuilder
import jp.co.tukiyo.yoruha.api.googlebooks.model.VolumeItem
import jp.co.tukiyo.yoruha.extensions.async
import jp.co.tukiyo.yoruha.extensions.onNext

class BookInfoFragmentViewModel(context: Context, val volumeId: String): FragmentViewModel(context) {
    val book: ObservableField<VolumeItem> = ObservableField()
    val description: ObservableField<Spanned> = ObservableField()
    val client: GoogleBooksAPIClient = GoogleBooksAPIClientBuilder().build()

    fun fetchInfo() {
        client.bookInfo(volumeId)
                .async(Schedulers.newThread())
                .onNext {
                    book.set(it)
                    description.set(Html.fromHtml(it.volumeInfo.description))
                }
                .onError {  }
                .subscribe()
    }
}