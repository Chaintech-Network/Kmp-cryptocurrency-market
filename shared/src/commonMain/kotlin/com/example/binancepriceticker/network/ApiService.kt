package com.example.binancepriceticker.network

import com.example.binancepriceticker.model.CoinData
import com.example.binancepriceticker.model.SpotTicker
import com.example.binancepriceticker.model.Ticker
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.websocket.DefaultClientWebSocketSession
import io.ktor.client.plugins.websocket.webSocket
import io.ktor.client.request.get
import io.ktor.client.request.url
import io.ktor.websocket.Frame
import io.ktor.websocket.readText
import kotlinx.coroutines.flow.flow
import kotlinx.serialization.json.Json

const val WS_TICKER_URL = "wss://stream.binance.com:9443/ws/!ticker@arr"

class ApiService(
    private val json: Json,
    private val client: HttpClient,
) {
    private lateinit var session: DefaultClientWebSocketSession

    suspend fun getTickers(): List<SpotTicker> {
        return client.get {
            url("https://api.binance.com/api/v3/ticker/24hr")
        }.body()
    }

    suspend fun getSymbols(): CoinData {
        return client.get {
            url("https://www.binance.com/bapi/margin/v1/public/margin/symbols")
        }.body()
    }

    fun tickerWebSocket() = flow {
        val url = WS_TICKER_URL
        client.webSocket(
            urlString = url,
        ) {
            session = this
            for (frame in incoming) {
                if (frame is Frame.Text) {
                    val messageJson = frame.readText()
                    val item = json.decodeFromString<List<Ticker>>(messageJson)
//                    println(messageJson)
                    emit(item)
                }
            }
        }
    }
}