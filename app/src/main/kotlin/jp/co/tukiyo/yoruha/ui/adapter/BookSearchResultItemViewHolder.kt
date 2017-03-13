package jp.co.tukiyo.yoruha.ui.adapter

import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.View
import jp.co.tukiyo.yoruha.api.googlebooks.model.VolumeItem
import jp.co.tukiyo.yoruha.databinding.SearchResultItemBinding
import jp.co.tukiyo.yoruha.ui.listener.OnListsBookItemListener

class BookSearchResultItemViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    val binding: SearchResultItemBinding = DataBindingUtil.bind(itemView)

    fun bind(item: VolumeItem, listener: OnListsBookItemListener) {
        binding.apply {
            book = item
            itemView.setOnClickListener {
                listener.onBookClick(item)
            }
        }
    }
}