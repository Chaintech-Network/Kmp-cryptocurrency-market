package com.example.binancepriceticker.repository

import com.example.binancepriceticker.model.CoinData
import com.example.binancepriceticker.model.SpotTicker
import com.example.binancepriceticker.network.ApiService
import kotlinx.coroutines.flow.flow

class MainRepository constructor(private val apiService: ApiService) {

    suspend fun getTickers() = flow<List<SpotTicker>> {
        emit(apiService.getTickers())
    }

    suspend fun getSymbols() = flow<CoinData> {
        emit(apiService.getSymbols())
    }

    fun initTicker() = apiService.tickerWebSocket()

}
