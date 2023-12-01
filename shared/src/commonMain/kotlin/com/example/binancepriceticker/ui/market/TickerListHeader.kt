package com.example.binancepriceticker.ui.market

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.binancepriceticker.core.Fonts
import com.example.binancepriceticker.ui.util.SortParams
import com.example.binancepriceticker.ui.viewmodel.MainViewModel

@Composable
fun TickerListHeader(mainViewModel: MainViewModel) {
    Row(
        Modifier
            .background(MaterialTheme.colors.background)
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            Modifier.weight(1f), horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                Modifier
                    .clickable {
                        mainViewModel.updateSortKey(SortParams.Pair)
                    },
            ) {
                TickListHeaderItem("Name", SortParams.Pair, mainViewModel)
            }
            Text(
                text = "/ ",
                color = MaterialTheme.colors.onSecondary,
                fontSize = 11.sp,
                fontWeight = FontWeight.W300,
                fontFamily = Fonts.dinMedium()
            )
            Row(
                Modifier
                    .clickable {
                        mainViewModel.updateSortKey(SortParams.Vol)

                    },
            ) {
                TickListHeaderItem("Vol", SortParams.Vol, mainViewModel)
            }
        }
        Row(
            Modifier
                .clickable {
                    mainViewModel.updateSortKey(SortParams.Price)
                },
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            TickListHeaderItem("Last Price", SortParams.Price, mainViewModel)
        }
        Row(
            Modifier
                .padding(start = 12.dp)
                .width(86.dp)
                .clickable {
                    mainViewModel.updateSortKey(SortParams.Change)
                },
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ) {
            TickListHeaderItem("24h Chg%", SortParams.Change, mainViewModel)
        }
    }
}
