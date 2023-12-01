package com.example.binancepriceticker.ui.market

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.binancepriceticker.core.Fonts
import com.example.binancepriceticker.ui.viewmodel.MainViewModel

@Composable
fun QuoteCoinListTab(
    mainViewModel: MainViewModel,
) {
    LazyRow(
        modifier = Modifier.padding(top = 2.dp).fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(vertical = 12.dp, horizontal = 16.dp),
    ) {
        itemsIndexed(mainViewModel.quoteCoinList) { index, item ->
            if (index == mainViewModel.selectedTabIndex.value)
                Text(
                    item,
                    color = Color.Unspecified,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.W500,
                    modifier = Modifier
                        .clip(RoundedCornerShape(6.dp))
                        .background(Color(0xFFF5F5F5), RoundedCornerShape(6.dp))
                        .padding(vertical = 4.dp, horizontal = 10.dp)
                        .clickable {
                            mainViewModel.onClickTab(index)
                        }, fontFamily = Fonts.dinMedium()
                )
            else
                Text(
                    item,
                    color = Color.Gray,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.W500,
                    modifier = Modifier
                        .padding(vertical = 4.dp, horizontal = 14.dp)
                        .clickable {
                            mainViewModel.onClickTab(index)
                        }, fontFamily = Fonts.dinMedium()
                )
        }

    }
}