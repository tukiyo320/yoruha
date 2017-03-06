package jp.co.tukiyo.yoruha.ui.adapter

import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.View
import jp.co.tukiyo.yoruha.databinding.SearchResultItemBinding

class BookSearchResultViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
    val binding: SearchResultItemBinding = DataBindingUtil.bind(itemView)
}