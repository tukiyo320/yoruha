package jp.co.tukiyo.yoruha.viewmodel

import android.content.Context
import android.support.v4.app.DialogFragment
import android.widget.Toast
import jp.co.tukiyo.yoruha.extensions.onCompleted
import jp.co.tukiyo.yoruha.usecase.BookShelfManageUseCase

class AddBookToBookShelfDialogFragmentViewModel(context: Context) : FragmentViewModel(context) {
    val useCase = BookShelfManageUseCase(context)

    fun add(fragment: DialogFragment, shelfId: Int, volumeId: String) {
        useCase.addBook(shelfId, volumeId)
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
}