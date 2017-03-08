package jp.co.tukiyo.yoruha.ui.fragment

import android.os.Bundle
import android.view.View
import com.hannesdorfmann.fragmentargs.annotation.Arg
import com.hannesdorfmann.fragmentargs.annotation.FragmentWithArgs
import jp.co.tukiyo.yoruha.R
import jp.co.tukiyo.yoruha.databinding.FragmentBookShelfBinding
import jp.co.tukiyo.yoruha.ui.adapter.BookShelfPagerAdapter

@FragmentWithArgs
class BookShelfFragment: BaseFragment<FragmentBookShelfBinding>() {
    override val layoutResourceId: Int = R.layout.fragment_book_shelf

    @Arg
    var info: BookShelfPagerAdapter.BookShelf? = null

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}