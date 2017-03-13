package jp.co.tukiyo.yoruha.ui.fragment

import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.MenuItem
import android.view.View
import com.hannesdorfmann.fragmentargs.FragmentArgs
import com.hannesdorfmann.fragmentargs.annotation.Arg
import com.hannesdorfmann.fragmentargs.annotation.FragmentWithArgs
import jp.co.tukiyo.yoruha.R
import jp.co.tukiyo.yoruha.api.googlebooks.model.VolumeItem
import jp.co.tukiyo.yoruha.databinding.FragmentBookShelfBinding
import jp.co.tukiyo.yoruha.element.BookShelf
import jp.co.tukiyo.yoruha.ui.adapter.BookListItemViewHolder
import jp.co.tukiyo.yoruha.ui.adapter.BookShelfItemListAdapter
import jp.co.tukiyo.yoruha.viewmodel.BookShelfFragmentViewModel

@FragmentWithArgs
class BookShelfFragment : BaseFragment<FragmentBookShelfBinding>(),
        BookListItemViewHolder.OnBookShelfItemListener {
    override val layoutResourceId: Int = R.layout.fragment_book_shelf

    val shelfViewModel: BookShelfFragmentViewModel by lazy {
        BookShelfFragmentViewModel(context, info.no)
    }

    @Arg
    lateinit var info: BookShelf

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FragmentArgs.inject(this)
        bindViewModel(shelfViewModel)
        shelfViewModel.adapter = BookShelfItemListAdapter(context, this)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.run {
            bookShelfBooksList.apply {
                layoutManager = LinearLayoutManager(activity)
                adapter = shelfViewModel.adapter
                addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
            }
        }

        shelfViewModel.fetchBooks()
    }

    override fun onItemClick(item: VolumeItem) {
        shelfViewModel.onItemClick(item, this)
    }

    override fun onItemRemove(item: VolumeItem) {
        shelfViewModel.onItemRemove(item, this)
    }
}
