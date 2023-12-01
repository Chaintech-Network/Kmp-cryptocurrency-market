package com.example.binancepriceticker.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Ticker(
    @SerialName("E") var eventTime: String,
    @SerialName("s") var symbol: String,
    @SerialName("p") var priceChange: String,
    @SerialName("P") var priceChangePercent: String,
    @SerialName("c") var lastPrice: String,
    @SerialName("q") var quoteVolume: String,
    @SerialName("o") var openPrice: String,
    @SerialName("h") var highPrice: String,
    @SerialName("l") var lowPrice: String,
    @SerialName("v") var volume: String,
    var isFavPair: Boolean = false,
)

@Serializable
data class SpotTicker(
    @SerialName("symbol") var symbol: String,
    @SerialName("priceChangePercent") var priceChangePercent: String,
    @SerialName("priceChange") var priceChange: String,
    @SerialName("lastPrice") var lastPrice: String,
    @SerialName("volume") var volume: String,
    @SerialName("quoteVolume") var quoteVolume: String,
    var base: String = "",
    var quote: String = "",
)