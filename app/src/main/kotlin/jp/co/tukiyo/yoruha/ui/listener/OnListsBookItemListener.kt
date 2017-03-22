package jp.co.tukiyo.yoruha.ui.listener

import jp.co.tukiyo.yoruha.data.api.googlebooks.model.VolumeItem

interface OnListsBookItemListener {
    fun onBookClick(item: VolumeItem)
}