package jp.co.tukiyo.yoruha.ui.adapter

import android.content.Context
import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.chuross.recyclerviewadapters.ItemAdapter
import jp.co.tukiyo.yoruha.R
import jp.co.tukiyo.yoruha.api.googlebooks.model.VolumeItem
import jp.co.tukiyo.yoruha.databinding.SearchResultItemBinding

class BookSearchResultListAdapter(context: Context) : ItemAdapter<VolumeItem, BookSearchResultListAdapter.BookSearchResultViewHolder>(context){
    override fun getAdapterId(): Int = R.layout.search_result_item

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): BookSearchResultViewHolder {
        return BookSearchResultViewHolder(LayoutInflater.from(context).inflate(adapterId, parent, false))
    }

    override fun onBindViewHolder(holder: BookSearchResultViewHolder?, position: Int) {
        holder?.binding?.book = get(position)
    }

    class BookSearchResultViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val binding: SearchResultItemBinding = DataBindingUtil.bind(itemView)
    }
}