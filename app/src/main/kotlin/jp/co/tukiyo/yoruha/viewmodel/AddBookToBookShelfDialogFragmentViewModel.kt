package jp.co.tukiyo.yoruha.viewmodel

import android.content.Context
import android.content.SharedPreferences
import android.support.v4.app.DialogFragment
import android.widget.Toast
import io.reactivex.schedulers.Schedulers
import jp.co.tukiyo.yoruha.api.googlebooks.GoogleBooksAPIClient
import jp.co.tukiyo.yoruha.api.googlebooks.GoogleBooksAPIClientBuilder
import jp.co.tukiyo.yoruha.extensions.*
import retrofit2.HttpException

class AddBookToBookShelfDialogFragmentViewModel(context: Context): FragmentViewModel(context) {
    val prefs: SharedPreferences = context.getSharedPreference()
    val client: GoogleBooksAPIClient = GoogleBooksAPIClientBuilder().build()

    fun add(fragment: DialogFragment, shelfId: Int, volumeId: String) {
        client.addBookToShelf(getStoredToken(), shelfId, volumeId)
                .onErrorResumeNext { t: Throwable ->
                    val token = if(t is HttpException && t.code() == 401) {
                        client.getToken(context).blockingFirst()
                    } else getStoredToken()
                    client.addBookToShelf(token, shelfId, volumeId)
                }
                .async(Schedulers.newThread())
                .doOnEvent {
                    fragment.dismiss()
                }
                .onCompleted {
                    Toast.makeText(context, "本が追加されました", Toast.LENGTH_SHORT).show()
                }
                .onError {
                    Toast.makeText(context, "本の追加に失敗しました", Toast.LENGTH_SHORT).show()
                }
                .subscribe()
    }

    private fun getStoredToken(): String {
        return prefs.getGoogleOAuthToken().let {
            "Bearer " + context.crypto.decrypt(it)
        }
    }
}