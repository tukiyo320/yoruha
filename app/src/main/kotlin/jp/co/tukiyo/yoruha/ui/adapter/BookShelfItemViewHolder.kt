package jp.co.tukiyo.yoruha.ui.adapter

import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.View
import jp.co.tukiyo.yoruha.api.googlebooks.model.VolumeItem
import jp.co.tukiyo.yoruha.databinding.BookShelfItemBinding

class BookShelfItemViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    val binding: BookShelfItemBinding = DataBindingUtil.bind(itemView)

    fun bind(item: VolumeItem, listener: OnBookShelfItemClickListener) {
        binding.apply {
            book = item
            itemView.setOnClickListener {
                listener.onItemClick(item)
            }
        }
    }

    interface OnBookShelfItemClickListener {
        fun onItemClick(item: VolumeItem)
    }
}