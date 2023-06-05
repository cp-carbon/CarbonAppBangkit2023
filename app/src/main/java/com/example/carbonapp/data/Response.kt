package com.example.carbonapp.data

/**
 * A generic class that holds a value with its loading status.
 * @param <T>
 */
sealed class Response<out T : Any> {

    abstract val code: Int
    abstract val status: String

    data class Success<out T : Any>(
        override val code: Int,
        override val status: String,
        val data: T,
    ) : Response<T>()

    data class Error(
        override val code: Int,
        override val status: String,
        val exception: Exception,
    ) : Response<Nothing>()

    override fun toString(): String {
        return when (this) {
            is Success<*> -> "Success[data=$data]"
            is Error -> "Error[exception=$exception]"
        }
    }
}