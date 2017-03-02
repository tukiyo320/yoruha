package jp.co.tukiyo.yoruha.api.googlebooks.model

import java.io.Serializable

data class SaleInfo(
        val country: String,
        val saleability: String,
        val isEbook: Boolean,
        val listPrice: PriceInfo,
        val retailPrice: PriceInfo,
        val buyLink: String,
        val offers: List<Offer>
) : Serializable

data class Offer(
        val finskyOfferType: Int,
        val listPrice: MicroPriceInfo,
        val retailPrice: MicroPriceInfo
) : Serializable

data class PriceInfo(
        val amount: Float,
        val currencyCode: String
) : Serializable

data class MicroPriceInfo(
        val amountInMicros: Float,
        val currencyCode: String
) : Serializable