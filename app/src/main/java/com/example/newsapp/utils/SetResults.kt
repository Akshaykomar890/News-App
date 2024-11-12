package com.example.newsapp.utils

sealed class SetResults<T>(
    val data:T? = null,
    val errorType:ErrorType? = null
) {

    class Success<T>(data: T):SetResults<T>(data=data)

    class Error<T>(errorType: ErrorType?):SetResults<T>(data = null,errorType = errorType)

}

enum class ErrorType{
    NETWORK_ERROR,
    AUTHENTICATION_ERROR,
    UNKNOWN_ERROR
}