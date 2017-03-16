package jp.co.tukiyo.yoruha.api.googlebooks.model

import java.io.Serializable

data class BookShelfVolumesResponse(
        val kind: String,
        val totalItems: Int,
        val items: List<VolumeItem>?
) : Serializable