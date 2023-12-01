package com.example.binancepriceticker.ui.market

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.binancepriceticker.core.Fonts
import com.example.binancepriceticker.ui.util.SortParams
import com.example.binancepriceticker.ui.viewmodel.MainViewModel

@Composable
fun TickListHeaderItem(
    text: String,
    sortKey: SortParams,
    mainViewModel: MainViewModel
) {
    val currentSortKey = mainViewModel.currentSortKey
    val isSortDesc = mainViewModel.isSortDesc

    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(
            text,
            fontSize = 12.sp,
            fontWeight = FontWeight.W400,
            color = if (currentSortKey.value == sortKey) Color.Black else Color.Gray,
            textAlign = TextAlign.End,
            fontFamily = Fonts.dinMedium()
        )
        Box {
            Icon(
                imageVector = Icons.Filled.ArrowDropDown,
                contentDescription = "Change Percentage",
                tint = if (currentSortKey.value == sortKey && isSortDesc.value) Color.Black
                else Color.Gray,
                modifier = Modifier
                    .padding(bottom = 6.dp)
                    .size(18.dp)
                    .rotate(180f)
            )
            Icon(
                imageVector = Icons.Filled.ArrowDropDown,
                contentDescription = "Change Percentage",
                tint = if (mainViewModel.currentSortKey.value == sortKey && !isSortDesc.value) Color.Black
                else Color.Gray,
                modifier = Modifier
                    .padding(top = 6.dp)
                    .size(18.dp)
            )
        }
    }
}
