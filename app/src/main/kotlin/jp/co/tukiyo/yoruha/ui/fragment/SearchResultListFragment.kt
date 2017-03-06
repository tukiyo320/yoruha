package jp.co.tukiyo.yoruha.ui.fragment

import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.hannesdorfmann.fragmentargs.annotation.Arg
import com.hannesdorfmann.fragmentargs.annotation.FragmentWithArgs
import jp.co.tukiyo.yoruha.R
import jp.co.tukiyo.yoruha.databinding.FragmentSearchResultListBinding
import jp.co.tukiyo.yoruha.ui.adapter.BookSearchResultListAdapter
import jp.co.tukiyo.yoruha.viewmodel.SearchResultListFragmentViewModel

@FragmentWithArgs
class SearchResultListFragment : BaseFragment<FragmentSearchResultListBinding>() {

    @Arg(key = "queryString")
    var query : String = ""

    override val layoutResourceId: Int = R.layout.fragment_search_result_list
    val searchViewModel: SearchResultListFragmentViewModel by lazy {
        SearchResultListFragmentViewModel(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindViewModel(searchViewModel)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.run {
            searchResultList.run {
                layoutManager = LinearLayoutManager(activity)
                adapter = searchViewModel.listAdapter
                addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
            }

            viewModel = searchViewModel
        }

        searchViewModel.search(query)
    }
}