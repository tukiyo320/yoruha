package jp.co.tukiyo.yoruha.ui.fragment

import android.os.Bundle
import android.view.View
import com.hannesdorfmann.fragmentargs.FragmentArgs
import com.hannesdorfmann.fragmentargs.annotation.Arg
import com.hannesdorfmann.fragmentargs.annotation.FragmentWithArgs
import jp.co.tukiyo.yoruha.R
import jp.co.tukiyo.yoruha.databinding.FragmentBookInfoBinding
import jp.co.tukiyo.yoruha.viewmodel.BookInfoFragmentViewModel

@FragmentWithArgs
class BookInfoFragment: BaseFragment<FragmentBookInfoBinding>() {
    override val layoutResourceId: Int = R.layout.fragment_book_info
    val infoViewModel : BookInfoFragmentViewModel by lazy {
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

        binding?.viewModel = infoViewModel

        infoViewModel.fetchInfo()
    }
}