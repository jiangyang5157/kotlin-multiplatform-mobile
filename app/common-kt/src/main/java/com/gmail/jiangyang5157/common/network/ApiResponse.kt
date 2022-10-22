package com.gmail.jiangyang5157.common.network

import java.io.IOException

sealed class ApiResponse<out T : Any, out E : Any> {

    data class Success<T : Any>(val body: T) : ApiResponse<T, Nothing>()

    data class Failure<E : Any>(val error: E, val code: Int) : ApiResponse<Nothing, E>()

    data class NetworkError(val exception: IOException) : ApiResponse<Nothing, Nothing>()

    data class UnknownError(val throwable: Throwable) : ApiResponse<Nothing, Nothing>()
}
