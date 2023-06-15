package com.example.carbonapp.data

/**
 * A generic class that holds a value with its loading status.
 * @param <T>
 */
sealed class HttpResult<out T : Any> {

    data class Success<out T : Any>(
        val data: T,
    ) : HttpResult<T>()

    data class Error(
        val exception: Exception,
    ) : HttpResult<Nothing>()

    override fun toString(): String {
        return when (this) {
            is Success<*> -> "Success[data=$data]"
            is Error -> "Error[exception=$exception]"
        }
    }
}