package com.example.timber

expect object Timber {
    fun d(message: String)
    fun i(message: String)
    fun e(message: String, exception: Throwable? = null)
    fun w(message: String)
}