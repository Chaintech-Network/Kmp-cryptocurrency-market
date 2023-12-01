package com.example.binancepriceticker.ui.market

import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.binancepriceticker.core.Fonts
import com.example.binancepriceticker.ui.viewmodel.MainViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SearchInput(mainViewModel: MainViewModel) {
    val interactionSource = remember { MutableInteractionSource() }
    val coroutineScope = rememberCoroutineScope()
    val searchText = mainViewModel.searchText.collectAsState().value

    BasicTextField(
        value = searchText,
        onValueChange = {
            coroutineScope.launch {
                mainViewModel.searchText.emit(it)
            }
        },
        modifier = Modifier
            .padding(start = 16.dp, end = 16.dp, top = 14.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(Color(0xFFF5F5F5), RoundedCornerShape(12.dp))
            .height(38.dp),
        textStyle = TextStyle(
            color = Color.Black,
            fontSize = 18.sp,
            fontFamily = Fonts.dinMedium()
        ),
        singleLine = true,
        decorationBox = {
            TextFieldDefaults.OutlinedTextFieldDecorationBox(
                value = searchText,
                visualTransformation = VisualTransformation.None,
                innerTextField = it,
                singleLine = true,
                enabled = true,
                interactionSource = interactionSource,
                contentPadding = TextFieldDefaults.textFieldWithoutLabelPadding(
                    start = 8.dp, end = 8.dp, top = 0.dp, bottom = 0.dp
                ),
                placeholder = {
                    Text(
                        "Search Coin Pairs",
                        fontSize = 15.sp,
                        fontFamily = Fonts.ibmplexsansMedium(),
                        color = Color.Gray
                    )
                },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Filled.Search,
                        contentDescription = "Search",
                        modifier = Modifier
                            .size(20.dp)
                    )
                },
                colors = TextFieldDefaults.textFieldColors(
                    textColor = Color.Black,
                    disabledTextColor = Color.Transparent,
                    backgroundColor = Color(0xFFF5F5F5),
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent
                ),
            )
        }
    )
}
