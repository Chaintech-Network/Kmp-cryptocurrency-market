package com.example.binancepriceticker.model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CoinData(
    @SerialName("code")
    val code: String? = null, // 000000
    @SerialName("data")
    val `data`: List<Data?>? = null,
    @SerialName("message")
    val message: String? = null, // null
    @SerialName("messageDetail")
    val messageDetail: String? = null, // null
    @SerialName("success")
    val success: Boolean? = null // true
) {

    @Serializable
    data class Data(
        @SerialName("base")
        val base: String? = null, // BNB
        @SerialName("delistedTime")
        val delistedTime: String? = null, // null
        @SerialName("id")
        val id: String? = null, // 351637150141315861
        @SerialName("isBuyAllowed")
        val isBuyAllowed: Boolean? = null, // true
        @SerialName("isMarginTrade")
        val isMarginTrade: Boolean? = null, // true
        @SerialName("isSellAllowed")
        val isSellAllowed: Boolean? = null, // true
        @SerialName("quote")
        val quote: String? = null, // BTC
        @SerialName("status")
        val status: String? = null, // NORMAL
        @SerialName("symbol")
        val symbol: String? = null, // BNBBTC
    )
}