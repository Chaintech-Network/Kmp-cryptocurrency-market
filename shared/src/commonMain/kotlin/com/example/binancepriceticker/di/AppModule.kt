package com.example.binancepriceticker.di

import com.example.binancepriceticker.httpClient
import com.example.binancepriceticker.network.ApiService
import com.example.binancepriceticker.repository.MainRepository
import com.example.binancepriceticker.ui.viewmodel.MainViewModel
import com.example.binancepriceticker.viewModelDefinition
import com.example.timber.Timber
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.logging.SIMPLE
import io.ktor.client.plugins.websocket.WebSockets
import io.ktor.serialization.kotlinx.KotlinxWebsocketSerializationConverter
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.core.context.startKoin
import org.koin.core.qualifier.named
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module

fun init(appDeclaration: KoinAppDeclaration = {}) = startKoin {
    appDeclaration()
    modules(appModule())

}

fun initKoin() = init {}

fun appModule() = module {
    single { createJson() }
    single(named("webSocketClient")) { createWebSocketHttpClient(get()) }
    single { ApiService(get(), get(named("webSocketClient"))) }
    factory { MainRepository(get()) }

    viewModelDefinition { MainViewModel(get()) }
}


fun createJson(): Json = Json { isLenient = true; ignoreUnknownKeys = true }

fun createWebSocketHttpClient(
    json: Json
) = httpClient {

    install(ContentNegotiation) {
        json(json)
    }
    install(WebSockets) {
        contentConverter = KotlinxWebsocketSerializationConverter(json)
//        pingInterval = 20_000
    }

    install(Logging) {
        logger = Logger.SIMPLE
        level = LogLevel.ALL
    }

    install(HttpTimeout) {
        requestTimeoutMillis = 100000
        connectTimeoutMillis = 100000
        socketTimeoutMillis = 100000
    }

}

class DebugKtorLogger : Logger {
    override fun log(message: String) {
        Timber.i("Ktor OkHttp\n$message")
    }
}

