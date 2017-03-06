package jp.co.tukiyo.yoruha.api.googlebooks.model

import java.io.Serializable

data class SearchResultInfo(
        val kind: String,
        val id: String,
        val etag: String,
        val selfLink: String,
        val volumeInfo: VolumeInfo,
        val saleInfo: SaleInfo,
        val accessInfo: AccessInfo,
        val searchInfo: SearchInfo
) : Serializable