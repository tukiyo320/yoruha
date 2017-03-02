package jp.co.tukiyo.yoruha.api.googlebooks.model

import java.io.Serializable

data class AccessInfo(
        val country: String,
        val viewablity: String,
        val embeddable: Boolean,
        val publicDomain: Boolean,
        val textToSpeechPermission: String,
        val epub: FormatInfo,
        val pdf: FormatInfo,
        val webReaderLink: String,
        val accessViewStatus: String,
        val quoteSharingAllowed: Boolean
) : Serializable

data class FormatInfo(
        val isAvailable: Boolean,
        val acsTokenLink: String
) : Serializable