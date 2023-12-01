package com.example.timber

import android.util.Log

actual object Timber {
    actual fun d(message: String) {
        Log.d("Logger", message)
    }

    actual fun i(message: String) {
        Log.i("Logger", message)
    }

    actual fun e(message: String, exception: Throwable?) {
        Log.e("Logger", message, exception)
    }

    actual fun w(message: String) {
        Log.w("Logger", message)
    }
}