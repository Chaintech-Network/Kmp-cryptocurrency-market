package com.example.binancepriceticker.core

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight

object Fonts {
    @Composable
    fun barlowBold() = FontFamily(
        font(
            "barlow_bold",
            "barlow_bold",
            FontWeight.W400,
            FontStyle.Normal
        ),
    )

    @Composable
    fun ibmplexsansBold() = FontFamily(
        font(
            "ibmplexsans_bold",
            "ibmplexsans_bold",
            FontWeight.W400,
            FontStyle.Normal
        ),
    )

    @Composable
    fun dinBold() = FontFamily(
        font(
            "din_bold",
            "din_bold",
            FontWeight.W400,
            FontStyle.Normal
        ),
    )

    @Composable
    fun barlowRegular() = FontFamily(
        font(
            "barlow_regular",
            "barlow_regular",
            FontWeight.Normal,
            FontStyle.Normal
        ),
    )

    @Composable
    fun ibmplexsansRegular() = FontFamily(
        font(
            "ibmplexsans_regular",
            "ibmplexsans_regular",
            FontWeight.Normal,
            FontStyle.Normal
        ),
    )

    @Composable
    fun dinRegular() = FontFamily(
        font(
            "din",
            "din",
            FontWeight.Normal,
            FontStyle.Normal
        ),
    )

    @Composable
    fun barlowMedium() = FontFamily(
        font(
            "barlow_medium",
            "barlow_medium",
            FontWeight.Medium,
            FontStyle.Normal
        ),
    )

    @Composable
    fun ibmplexsansMedium() = FontFamily(
        font(
            "ibmplexsans_medium",
            "ibmplexsans_medium",
            FontWeight.Medium,
            FontStyle.Normal
        ),
    )

    @Composable
    fun dinMedium() = FontFamily(
        font(
            "din_medium",
            "din_medium",
            FontWeight.Medium,
            FontStyle.Normal
        ),
    )

    @Composable
    fun barlowSemiBold() = FontFamily(
        font(
            "barlow_semibold",
            "barlow_semibold",
            FontWeight.SemiBold,
            FontStyle.Normal
        ),
    )

    @Composable
    fun ibmplexsansSemibold() = FontFamily(
        font(
            "ibmplexsans_semibold",
            "ibmplexsans_semibold",
            FontWeight.SemiBold,
            FontStyle.Normal
        ),
    )
}

@Composable
expect fun font(name: String, res: String, weight: FontWeight, style: FontStyle): Font

