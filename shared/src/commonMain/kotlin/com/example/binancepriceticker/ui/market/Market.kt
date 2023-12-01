package com.example.binancepriceticker.ui.market

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.binancepriceticker.ui.viewmodel.MainViewModel

@Composable
fun TickerListCompose(
    mainViewModel: MainViewModel,
) {
    val isLoading = mainViewModel.tickerResponse.value.isLoading
    LaunchedEffect(Unit) {
        mainViewModel.searchDebounce()
    }

    Column(Modifier.fillMaxSize().background(Color.White)) {
        SearchInput(mainViewModel)
        if (isLoading) {
            TickerShimmer(
                Modifier.fillMaxSize().background(Color.White)
                    .padding(top = 12.dp),
            )
        } else {
            QuoteCoinListTab(mainViewModel)
            TickerListHeader(mainViewModel)
            Crossfade(targetState = mainViewModel.selectedTabIndex.value) {
                TickerList(mainViewModel)
            }
        }
    }
}



