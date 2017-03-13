package jp.co.tukiyo.yoruha.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import com.github.chuross.recyclerviewadapters.ItemAdapter
import jp.co.tukiyo.yoruha.R
import jp.co.tukiyo.yoruha.api.googlebooks.model.VolumeItem

class BookShelfItemListAdapter(
        context: Context,
        val listener: BookListItemViewHolder.OnBookShelfItemListener
): ItemAdapter<VolumeItem, BookListItemViewHolder>(context) {
    override fun getAdapterId(): Int = R.layout.book_list_item

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): BookListItemViewHolder {
        return BookListItemViewHolder(LayoutInflater.from(context).inflate(adapterId, parent, false))
    }

    override fun onBindViewHolder(holder: BookListItemViewHolder?, position: Int) {
        holder?.bind(get(position), listener)
    }
}