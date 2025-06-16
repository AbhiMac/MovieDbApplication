package com.example.moviesapp.utils

import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException

object ErrorHandler {
    fun getErrorMessage(exception: Throwable): String {
        return when (exception) {
            is SocketTimeoutException -> "Request timed out."
            is IOException -> "Please check your internet connection."
            is HttpException -> "Server error: ${exception.code()}"
            else -> exception.localizedMessage ?: "Something went wrong."
        }
    }
}
