package com.example.binancepriceticker.ui.util

import androidx.compose.runtime.snapshots.SnapshotStateList


fun String.removeTrailingZeros(): String {
    val decimalPointIndex = this.indexOf('.')
    if (decimalPointIndex != -1) {
        var lastIndex = this.length - 1
        while (lastIndex > decimalPointIndex && this[lastIndex] == '0') {
            lastIndex--
        }
        if (lastIndex == decimalPointIndex) {
            lastIndex-- // If only one digit after decimal point, keep it
        }
        return this.substring(0, lastIndex + 1)
    }
    return this
}

fun Double.round(decimals: Int): Double {
    var multiplier = 1.0
    repeat(decimals) { multiplier *= 10 }
    return kotlin.math.round(this * multiplier) / multiplier
}

fun formatVolume(value: Double): String {
    return when {
        value >= 1_000_000_000 -> (value / 1_000_000_000).round(2).toString() + "B"
        value >= 1_000_000 -> (value / 1_000_000).round(2).toString() + "M"
        else -> value.round(2).toString()
    }
}

fun <T> SnapshotStateList<T>.swapList(newList: List<T>) {
    clear()
    addAll(newList)
}