package jp.co.tukiyo.yoruha.viewmodel

import android.content.Context
import android.content.SharedPreferences
import android.widget.Toast
import io.reactivex.schedulers.Schedulers
import jp.co.tukiyo.yoruha.api.googlebooks.GoogleBooksAPIClient
import jp.co.tukiyo.yoruha.api.googlebooks.GoogleBooksAPIClientBuilder
import jp.co.tukiyo.yoruha.extensions.*
import jp.co.tukiyo.yoruha.ui.adapter.BookShelfItemListAdapter
import retrofit2.HttpException

class BookShelfFragmentViewModel(context: Context, val shelfId: Int) : FragmentViewModel(context) {
    val prefs: SharedPreferences = context.getSharedPreference()
    val adapter: BookShelfItemListAdapter = BookShelfItemListAdapter(context)
    val client: GoogleBooksAPIClient = GoogleBooksAPIClientBuilder().build()

    fun fetchBooks() {
        client.myShelfBooks(getStoredToken(), shelfId)
                .onErrorResumeNext({ t: Throwable ->
                    val token = if (t is HttpException && t.code() == 401) {
                        client.getToken(context).blockingFirst()
                    } else {
                        getStoredToken()
                    }
                    client.myShelfBooks(token, shelfId)
                })
                .compose(bindToLifecycle())
                .async(Schedulers.newThread())
                .map { it.items }
                .onNext {
                    adapter.clear()
                    adapter.addAll(it)
                    adapter.notifyDataSetChanged()
                }
                .onError {
                    Toast.makeText(context, "failed fetching books", Toast.LENGTH_SHORT).show()
                }
                .subscribe()
    }

    private fun getStoredToken(): String {
        return prefs.getGoogleOAuthToken().let {
            "Bearer " + context.crypto.decrypt(it)
        }
    }
}