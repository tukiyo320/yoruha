package jp.co.tukiyo.yoruha.ui.fragment

import android.os.Bundle
import android.support.v7.widget.SearchView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.hannesdorfmann.fragmentargs.annotation.FragmentWithArgs
import jp.co.tukiyo.yoruha.R
import jp.co.tukiyo.yoruha.databinding.FragmentHomeBinding
import jp.co.tukiyo.yoruha.ui.adapter.BookShelfPagerAdapter
import jp.co.tukiyo.yoruha.ui.screen.SearchResultListScreen

@FragmentWithArgs
class HomeFragment : BaseFragment<FragmentHomeBinding>(), SearchView.OnQueryTextListener {
    override val layoutResourceId: Int = R.layout.fragment_home
    lateinit var adapter: BookShelfPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adapter = BookShelfPagerAdapter(fragmentManager)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return binding?.apply {
            adapter.view = bookShelfPager
            toolbar.inflateMenu(R.menu.menu_home)
        }?.root
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.run {
            val searchView = toolbar.menu.findItem(R.id.menu_search).actionView as SearchView
            searchView.setBackgroundColor(android.R.color.white)
            searchView.setOnQueryTextListener(this@HomeFragment)
            bookShelf.adapter = adapter
            bookShelfPager.setViewPager(bookShelf)
        }
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        query?.run {
            screenActivity.pushScreen(SearchResultListScreen(this))
        }
        return false
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        return false
    }
}
