package com.example.binancepriceticker.core

import androidx.lifecycle.viewModelScope as androidViewModelScope
import androidx.lifecycle.ViewModel as LifecycleViewModel

actual abstract class ViewModel : LifecycleViewModel() {
    actual val viewModelScope = androidViewModelScope

    actual override fun onCleared() {
        super.onCleared()
    }

    actual fun clear() {
        onCleared()
    }
}