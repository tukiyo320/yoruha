package jp.co.tukiyo.yoruha.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import com.github.chuross.recyclerviewadapters.ItemAdapter
import jp.co.tukiyo.yoruha.R
import jp.co.tukiyo.yoruha.api.googlebooks.model.SearchResultInfo

class BookSearchResultListAdapter(context: Context) : ItemAdapter<SearchResultInfo, BookSearchResultViewHolder>(context){
    override fun getAdapterId(): Int = R.layout.search_result_item

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): BookSearchResultViewHolder {
        return BookSearchResultViewHolder(LayoutInflater.from(context).inflate(adapterId, parent, false))
    }

    override fun onBindViewHolder(holder: BookSearchResultViewHolder?, position: Int) {
        holder?.binding?.book = get(position)
    }
}