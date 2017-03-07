package jp.co.tukiyo.yoruha.ui.fragment

import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.*
import com.hannesdorfmann.fragmentargs.annotation.Arg
import com.hannesdorfmann.fragmentargs.annotation.FragmentWithArgs
import jp.co.tukiyo.yoruha.R
import jp.co.tukiyo.yoruha.api.googlebooks.GoogleBooksAPIClient
import jp.co.tukiyo.yoruha.databinding.FragmentSearchResultListBinding
import jp.co.tukiyo.yoruha.ui.adapter.BookSearchResultListAdapter
import jp.co.tukiyo.yoruha.viewmodel.SearchResultListFragmentViewModel

@FragmentWithArgs
class SearchResultListFragment : BaseFragment<FragmentSearchResultListBinding>() {

    @Arg(key = "queryString")
    var query: String = ""

    override val layoutResourceId: Int = R.layout.fragment_search_result_list
    val searchViewModel: SearchResultListFragmentViewModel by lazy {
        SearchResultListFragmentViewModel(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindViewModel(searchViewModel)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return binding?.apply {
            toolbar.inflateMenu(R.menu.menu_search)
        }?.root
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.run {
            searchResultList.run {
                layoutManager = LinearLayoutManager(activity)
                adapter = searchViewModel.listAdapter
                addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
            }
            searchResultRefresh.run {
                setOnRefreshListener {
                    searchViewModel.refresh()
                    if (isRefreshing) {
                        isRefreshing = false
                    }
                }
            }

            toolbar.setOnMenuItemClickListener { item ->
                if (item != null && item.groupId == R.id.menu_search_sort) {
                    item.isChecked = true
                    when (item.itemId) {
                        R.id.menu_search_sort_relevance -> {
                            searchViewModel.orderBy = GoogleBooksAPIClient.OrderBy.RELEVANCE
                        }
                        R.id.menu_search_sort_newest -> {
                            searchViewModel.orderBy = GoogleBooksAPIClient.OrderBy.NEWEST
                        }
                    }
                }
                return@setOnMenuItemClickListener true
            }
            toolbar.menu.findItem(R.id.menu_search_sort_relevance).isChecked = true
            searchViewModel.orderBy = GoogleBooksAPIClient.OrderBy.RELEVANCE

            viewModel = searchViewModel
        }

        searchViewModel.search(query)
    }
}