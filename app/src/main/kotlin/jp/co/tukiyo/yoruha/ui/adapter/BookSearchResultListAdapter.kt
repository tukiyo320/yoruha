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
import jp.co.tukiyo.yoruha.databinding.BookListItemBinding
import jp.co.tukiyo.yoruha.databinding.SearchResultItemBinding

class BookSearchResultListAdapter(
        context: Context,
        val listener: BookListItemViewHolder.OnBookShelfItemClickListener
) : ItemAdapter<VolumeItem, BookListItemViewHolder>(context) {
    override fun getAdapterId(): Int = R.layout.book_list_item

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): BookListItemViewHolder {
        return BookListItemViewHolder(LayoutInflater.from(context).inflate(adapterId, parent, false))
    }

    override fun onBindViewHolder(holder: BookListItemViewHolder?, position: Int) {
        holder?.bind(get(position), listener)
    }
}