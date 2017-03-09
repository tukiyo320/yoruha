package jp.co.tukiyo.yoruha.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import com.github.chuross.recyclerviewadapters.ItemAdapter
import jp.co.tukiyo.yoruha.R
import jp.co.tukiyo.yoruha.api.googlebooks.model.VolumeItem

class BookShelfItemListAdapter(context: Context): ItemAdapter<VolumeItem, BookShelfItemViewHolder>(context) {
    override fun getAdapterId(): Int = R.layout.book_shelf_item

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): BookShelfItemViewHolder {
        return BookShelfItemViewHolder(LayoutInflater.from(context).inflate(adapterId, parent, false))
    }

    override fun onBindViewHolder(holder: BookShelfItemViewHolder?, position: Int) {
        holder?.binding?.book = get(position)
    }
}