package com.example.timber

actual object Timber {
    actual fun d(message: String) {
        println("\u001B[34m[DEBUG] $message\u001B[0m") // Blue color for debug
    }

    actual fun i(message: String) {
        println("\u001B[32m[INFO] $message\u001B[0m") // Green color for info
    }

    actual fun e(message: String, exception: Throwable?) {
        if (exception != null)
            println("\u001B[31m[ERROR] $message ${exception.message}\u001B[0m") // Red color for error
        else
            println("\u001B[31m[ERROR] $message\u001B[0m") // Red color for error
    }

    actual fun w(message: String) {
        println("\u001B[33m[WARN] $message\u001B[0m") // Yellow color for warn
    }
}
