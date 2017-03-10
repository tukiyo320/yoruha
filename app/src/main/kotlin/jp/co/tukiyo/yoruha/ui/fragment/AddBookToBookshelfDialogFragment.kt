package jp.co.tukiyo.yoruha.ui.fragment

import android.app.Dialog
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.app.AlertDialog
import com.hannesdorfmann.fragmentargs.FragmentArgs
import com.hannesdorfmann.fragmentargs.annotation.Arg
import com.hannesdorfmann.fragmentargs.annotation.FragmentWithArgs
import jp.co.tukiyo.yoruha.element.BookShelf
import jp.co.tukiyo.yoruha.usecase.BookShelfManageUseCase
import jp.co.tukiyo.yoruha.viewmodel.AddBookToBookShelfDialogFragmentViewModel

@FragmentWithArgs
class AddBookToBookshelfDialogFragment: DialogFragment() {

    val bookshelves: List<BookShelf> = BookShelfManageUseCase.preShelves

    @Arg
    var volumeId: String = ""

    val addViewModel: AddBookToBookShelfDialogFragmentViewModel by lazy {
        AddBookToBookShelfDialogFragmentViewModel(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FragmentArgs.inject(this)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(activity)
                .setTitle("本棚に追加")
                .setItems(bookshelves.map { it.title }.toTypedArray(), { dialog, which ->
                    addViewModel.add(this@AddBookToBookshelfDialogFragment, bookshelves[which].no, volumeId)
                })
                .create()
    }
}