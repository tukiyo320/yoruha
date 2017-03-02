package jp.co.tukiyo.yoruha.ui.fragment

import android.os.Bundle
import android.support.v7.widget.SearchView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hannesdorfmann.fragmentargs.annotation.FragmentWithArgs
import jp.co.tukiyo.yoruha.R
import jp.co.tukiyo.yoruha.databinding.FragmentHomeBinding

@FragmentWithArgs
class HomeFragment : BaseFragment<FragmentHomeBinding>() {
    override val layoutResourceId: Int = R.layout.fragment_home

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return binding?.apply {
            toolbar.inflateMenu(R.menu.menu_home)
        }?.root
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.run {
            val searchView = toolbar.menu.findItem(R.id.menu_search).actionView as SearchView
            searchView.setBackgroundColor(android.R.color.white)
            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    return false
                }
            })
        }
    }
}