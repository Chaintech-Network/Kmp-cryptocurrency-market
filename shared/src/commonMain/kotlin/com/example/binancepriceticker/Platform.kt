package com.example.binancepriceticker

import com.example.binancepriceticker.core.ViewModel
import io.ktor.client.HttpClient
import io.ktor.client.HttpClientConfig
import org.koin.core.definition.Definition
import org.koin.core.definition.KoinDefinition
import org.koin.core.module.Module
import org.koin.core.qualifier.Qualifier

interface Platform {
    val name: String
    val absoluteName: String
}

expect fun getPlatform(): Platform


expect inline fun <reified T : ViewModel> Module.viewModelDefinition(
    qualifier: Qualifier? = null,
    noinline definition: Definition<T>
): KoinDefinition<T>

expect fun httpClient(config: HttpClientConfig<*>.() -> Unit = {}): HttpClient
