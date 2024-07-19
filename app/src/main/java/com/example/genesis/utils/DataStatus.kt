package com.example.genesis.utils

sealed class DataStatus<out T> {
    data class Loading<out T>(val timestamp: Long = System.currentTimeMillis()) : DataStatus<T>()
    data class Success<out T>(val data: T?, val timestamp: Long = System.currentTimeMillis()) :
        DataStatus<T>()

    data class Error<out T>(
        val error: String,
        val code: Int? = null,
        val timestamp: Long = System.currentTimeMillis()
    ) : DataStatus<T>()

    companion object {
        fun <T> loading(): DataStatus<T> {
            return Loading()
        }

        fun <T> success(data: T?): DataStatus<T> {
            return Success(data)
        }

        fun <T> error(error: String, code: Int? = null): DataStatus<T> {
            return Error(error, code)
        }
    }
}