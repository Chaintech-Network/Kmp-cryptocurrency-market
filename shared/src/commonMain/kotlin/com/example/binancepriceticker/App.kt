package com.example.binancepriceticker

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.example.binancepriceticker.di.appModule
import com.example.binancepriceticker.theme.MyApplicationTheme
import com.example.binancepriceticker.ui.market.TickerListCompose
import com.example.binancepriceticker.ui.viewmodel.MainViewModel
import kotlinx.coroutines.launch
import org.koin.compose.KoinApplication
import org.koin.compose.rememberKoinInject

@Composable
fun MainView() {
    MyApplicationTheme {
        KoinApplication(application = { modules(appModule()) }) {
            val mainViewModel =
                rememberKoinInject<MainViewModel>()

            LaunchedEffect(Unit) {
                launch {
                    mainViewModel.getSymbols()
                }
            }
            TickerListCompose(mainViewModel)
        }
    }
}




