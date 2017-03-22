package jp.co.tukiyo.yoruha.data.api.googlebooks.model

import java.io.Serializable

data class VolumeItem(
        val kind: String,
        val id: String,
        val etag: String,
        val selfLink: String,
        val volumeInfo: VolumeInfo,
        val saleInfo: SaleInfo,
        val accessInfo: AccessInfo,
        val searchInfo: SearchInfo
) : Serializable