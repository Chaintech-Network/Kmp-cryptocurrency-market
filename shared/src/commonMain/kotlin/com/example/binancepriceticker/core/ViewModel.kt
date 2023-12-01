package com.example.binancepriceticker.core

import kotlinx.coroutines.CoroutineScope

expect abstract class ViewModel() {
    val viewModelScope: CoroutineScope

    protected open fun onCleared()
    fun clear()
}