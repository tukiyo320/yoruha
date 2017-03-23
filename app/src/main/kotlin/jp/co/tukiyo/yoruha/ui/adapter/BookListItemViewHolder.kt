package jp.co.tukiyo.yoruha.ui.adapter

import android.databinding.DataBindingUtil
import android.support.v7.widget.PopupMenu
import android.support.v7.widget.RecyclerView
import android.view.View
import jp.co.tukiyo.yoruha.R
import jp.co.tukiyo.yoruha.data.api.googlebooks.model.VolumeItem
import jp.co.tukiyo.yoruha.databinding.BookListItemBinding
import jp.co.tukiyo.yoruha.ui.listener.OnListsBookItemListener

class BookListItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val binding: BookListItemBinding = DataBindingUtil.bind(itemView)

    fun bind(item: VolumeItem, listener: OnBookShelfItemListener) {
        binding.apply {
            book = item
            itemView.setOnClickListener {
                listener.onBookClick(item)
            }
            bookItemMenu.setOnClickListener {
                PopupMenu(itemView.context, it).run {
                    menuInflater.inflate(R.menu.menu_shelves_book, menu)
                    setOnMenuItemClickListener { menuItem ->
                        when (menuItem.itemId) {
                            R.id.menu_shelves_book_remove -> {
                                listener.onItemRemove(item)
                            }
                        }
                        return@setOnMenuItemClickListener true
                    }
                    show()
                }
            }
        }
    }

    interface OnBookShelfItemListener : OnListsBookItemListener {
        fun onItemRemove(item: VolumeItem)
    }
}