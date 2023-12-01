package com.example.binancepriceticker.ui.market

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.binancepriceticker.core.Fonts
import com.example.binancepriceticker.core.FromLocalDrawable
import com.example.binancepriceticker.model.SpotTicker
import com.example.binancepriceticker.ui.util.formatVolume
import com.example.binancepriceticker.ui.util.removeTrailingZeros
import com.example.binancepriceticker.ui.util.round
import com.example.binancepriceticker.ui.viewmodel.MainViewModel

@Composable
fun TickerList(mainViewModel: MainViewModel) {
    val state = rememberLazyListState()
    LaunchedEffect(Unit) {
        snapshotFlow { state.isScrollInProgress }.collect {
            mainViewModel.isScrolling = it
        }
    }

    if (mainViewModel.showNoData.value) {
        Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
            FromLocalDrawable(
                painterResource = "no_data.jpg",
                contentScale = ContentScale.FillWidth,
                modifier = Modifier.padding(top = 100.dp, start = 16.dp, end = 16.dp)
                    .width(300.dp),
            )
        }
    } else {
        LazyColumn(
            state = state,
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
        ) {
            items(mainViewModel.tickerSortedList) { ticker ->
                TickerListItem(ticker, mainViewModel)
            }
        }
    }
}

@Composable
fun TickerListItem(
    ticker: SpotTicker,
    mainViewModel: MainViewModel,
) {
    var lastTicker by remember { mutableStateOf(ticker) }
    val basePrice = mainViewModel.basePrice.value

    val priceColor by remember(ticker) {
        derivedStateOf {
            val color =
                if (ticker.lastPrice != lastTicker.lastPrice)
                    if (lastTicker.lastPrice <= ticker.lastPrice) Color(0xFF5FBB89)
                    else Color(0xFFE35461)
                else Color.Unspecified
            lastTicker = ticker
            color
        }
    }

    Column {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                Modifier
                    .weight(1f)
                    .fillMaxHeight(), horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Center
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        ticker.base,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.W400,
                        fontFamily = Fonts.barlowSemiBold()
                    )
                    Text(
                        (" /" + ticker.quote),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.W400,
                        modifier = Modifier.padding(bottom = (0.5).dp).align(Alignment.Bottom),
                        color = Color.Gray, fontFamily = Fonts.barlowMedium()
                    )
                }
                Text(
                    formatVolume(ticker.quoteVolume.toDouble()),
                    color = Color.Gray,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.W400,
                    modifier = Modifier.padding(top = 4.dp), fontFamily = Fonts.dinMedium()
                )
            }
            Column(
                Modifier
                    .padding(start = 12.dp),
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    if (ticker.lastPrice.toDouble() > 1.0)
                        ticker.lastPrice.toDouble().round(2).toString()
                    else
                        ticker.lastPrice.removeTrailingZeros(),
                    fontSize = 16.sp,
                    color = priceColor,
                    fontWeight = FontWeight.W400, fontFamily = Fonts.dinMedium()
                )
                Text(
                    "$${(ticker.lastPrice.toDouble() * basePrice).round(2)}",
                    color = Color.Gray,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.W400,
                    modifier = Modifier.padding(top = 4.dp), fontFamily = Fonts.dinMedium()
                )
            }

            Column(
                Modifier
                    .padding(start = 22.dp)
                    .clip(RoundedCornerShape(6.dp))
                    .background(
                        if (ticker.priceChange.toDouble() >= 0)
                            Color(0xFF5FBB89)
                        else
                            Color(0xFFE35461)
                    )
                    .width(82.dp)
                    .padding(vertical = 7.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                Text(
                    ticker.priceChangePercent.toDouble().round(2).toString() + "%",
                    color = Color.White,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.W500, fontFamily = Fonts.dinMedium()
                )
            }
        }
    }
}