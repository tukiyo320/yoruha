package jp.co.tukiyo.yoruha.api.googlebooks.model

import java.io.Serializable

data class VolumeInfo(
        val title: String,
        val authors: List<String>,
        val publisher: String,
        val publishedDate: String,
        val description: String,
        val industryIdentifiers: List<IndustryIdentifier>,
        val readingModes: ReadingMode,
        val pageCount: Int?,
        val printType: String,
        val categories: List<String>,
        val maturityRating: String,
        val allowAnonLogging: Boolean,
        val contentVersion: String,
        val imageLinks: ImageLink,
        val language: String,
        val previewLink: String,
        val infoLink: String,
        val canonicalVolumeLink: String,
        val seriesInfo: SeriesInfo
) : Serializable

data class IndustryIdentifier(
        val type: String,
        val identifier: String
) : Serializable

data class ReadingMode(
        val text: Boolean,
        val image: Boolean
) : Serializable

data class ImageLink(
        val smallThumbnail: String,
        val thumbnail: String
) : Serializable

data class SeriesInfo(
        val kind: String,
        val bookDisplayNumber: String,
        val volumeSeries: List<VolumeSeries>
) : Serializable

data class VolumeSeries(
        val seriesId: String,
        val seriesBookType: String,
        val orderNumber: Int
) : Serializable