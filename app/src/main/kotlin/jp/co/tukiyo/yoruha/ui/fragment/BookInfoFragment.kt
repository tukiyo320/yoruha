package jp.co.tukiyo.yoruha.ui.fragment

import android.os.Bundle
import android.view.View
import com.hannesdorfmann.fragmentargs.FragmentArgs
import com.hannesdorfmann.fragmentargs.annotation.Arg
import com.hannesdorfmann.fragmentargs.annotation.FragmentWithArgs
import jp.co.tukiyo.yoruha.R
import jp.co.tukiyo.yoruha.databinding.FragmentBookInfoBinding
import jp.co.tukiyo.yoruha.ui.listener.OnAddBookToShelfListener
import jp.co.tukiyo.yoruha.viewmodel.BookInfoFragmentViewModel

@FragmentWithArgs
class BookInfoFragment : BaseFragment<FragmentBookInfoBinding>(), OnAddBookToShelfListener {
    override val layoutResourceId: Int = R.layout.fragment_book_info
    private val RC_ADD_TO_SHELF = 10030

    val infoViewModel: BookInfoFragmentViewModel by lazy {
        BookInfoFragmentViewModel(context, volumeId)
    }
    @Arg
    var volumeId: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FragmentArgs.inject(this)
        bindViewModel(infoViewModel)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.run {
            viewModel = infoViewModel

            bookInfoShelfList.adapter = viewModel.adapter

            bookInfoAddToBookshelfButton.setOnClickListener {
                AddBookToBookshelfDialogFragmentBuilder(volumeId).build().apply {
                    setTargetFragment(this@BookInfoFragment, RC_ADD_TO_SHELF)
                }
                        .show(fragmentManager, "add_book")
            }
        }

        infoViewModel.fetchInfo()
        infoViewModel.fetchInWhichShelf()
    }

    override fun onAdd(shelfId: Int) {
        infoViewModel.fetchInWhichShelf()
    }
}
