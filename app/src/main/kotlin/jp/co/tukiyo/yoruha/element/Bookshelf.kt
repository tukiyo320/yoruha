package jp.co.tukiyo.yoruha.element

import java.io.Serializable

data class BookShelf(
        val no: Int,
        val title: String,
        val removable: Boolean
) : Serializable