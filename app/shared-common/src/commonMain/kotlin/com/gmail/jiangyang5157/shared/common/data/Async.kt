package com.gmail.jiangyang5157.shared.common.data

sealed class Async<out T> {

    data class Success<out T>(val data: T) : Async<T>()

    data class Loading<out T>(val data: T?) : Async<T>()

    data class Error(val code: Int) : Async<Nothing>()
}