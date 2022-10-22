package com.gmail.jiangyang5157.common.network

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Converter
import java.lang.reflect.Type

class ApiResponseCallAdapter<T : Any, E : Any>(
    private val responseType: Type,
    private val errorConverter: Converter<ResponseBody, E>,
) : CallAdapter<T, Call<ApiResponse<T, E>>> {

    override fun responseType(): Type = responseType

    override fun adapt(call: Call<T>): Call<ApiResponse<T, E>> {
        return ApiResponseCall(call, errorConverter)
    }
}
