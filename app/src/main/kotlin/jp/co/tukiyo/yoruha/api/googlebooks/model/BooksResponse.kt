package jp.co.tukiyo.yoruha.api.googlebooks.model

import java.io.Serializable

data class BooksResponse(
        val kind: String,
        val totalItems: Long,
        val items: List<SearchResultInfo>
) : Serializable