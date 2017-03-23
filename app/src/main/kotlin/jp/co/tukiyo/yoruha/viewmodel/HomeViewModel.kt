package jp.co.tukiyo.yoruha.viewmodel

import android.content.Context
import android.widget.Toast
import io.reactivex.CompletableTransformer
import jp.co.tukiyo.yoruha.extensions.onCompleted
import jp.co.tukiyo.yoruha.usecase.BookShelfManageUseCase

class HomeViewModel(context: Context) : FragmentViewModel(context) {
    private val useCase = BookShelfManageUseCase(context)

    fun syncBooks() {
        useCase.syncBooksForAllShelves()
                .compose(bindToLifecycle<CompletableTransformer>())
                .onCompleted {
                    Toast.makeText(context, "本棚データを同期しました", Toast.LENGTH_SHORT).show()
                }
                .onError {
                    Toast.makeText(context, "本棚データの同期に失敗しました", Toast.LENGTH_SHORT).show()
                }
                .subscribe()
    }
}