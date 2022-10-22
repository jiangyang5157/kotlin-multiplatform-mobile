package com.gmail.jiangyang5157.common.network

import com.gmail.jiangyang5157.kit.network.ApiResponse
import okhttp3.Request
import okhttp3.ResponseBody
import okio.Timeout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Converter
import retrofit2.Response
import java.io.IOException

class ApiResponseCall<T : Any, E : Any>(
    private val delegate: Call<T>,
    private val errorConverter: Converter<ResponseBody, E>,
) : Call<ApiResponse<T, E>> {

    override fun enqueue(callback: Callback<ApiResponse<T, E>>) {
        return delegate.enqueue(object : Callback<T> {

            override fun onResponse(call: Call<T>, response: Response<T>) {
                if (response.isSuccessful) {
                    val body = response.body()
                    if (body != null) {
                        callback.onResponse(
                            this@ApiResponseCall,
                            Response.success(ApiResponse.Success(body))
                        )
                    } else {
                        callback.onResponse(
                            this@ApiResponseCall,
                            Response.success(ApiResponse.UnknownError(Throwable("Response is successful but the body is null")))
                        )
                    }
                } else {
                    val errorBody = response.errorBody()
                    val error = when {
                        errorBody == null -> null
                        errorBody.contentLength() == 0L -> null
                        else -> try {
                            errorConverter.convert(errorBody)
                        } catch (ex: Exception) {
                            null
                        }
                    }
                    if (error != null) {
                        callback.onResponse(
                            this@ApiResponseCall,
                            Response.success(ApiResponse.Failure(error, response.code()))
                        )
                    } else {
                        callback.onResponse(
                            this@ApiResponseCall,
                            Response.success(ApiResponse.UnknownError(Throwable("Response is unsuccessful but the errorBody is null")))
                        )
                    }
                }
            }

            override fun onFailure(call: Call<T>, throwable: Throwable) {
                val apiResponse = when (throwable) {
                    is IOException -> ApiResponse.NetworkError(throwable)
                    else -> ApiResponse.UnknownError(throwable)
                }
                callback.onResponse(this@ApiResponseCall, Response.success(apiResponse))
            }
        })
    }

    override fun isExecuted(): Boolean = delegate.isExecuted

    override fun cancel() = delegate.cancel()

    override fun isCanceled(): Boolean = delegate.isCanceled

    override fun request(): Request = delegate.request()

    override fun timeout(): Timeout = delegate.timeout()

    override fun clone(): Call<ApiResponse<T, E>> = ApiResponseCall(delegate.clone(), errorConverter)

    override fun execute(): Response<ApiResponse<T, E>> {
        throw UnsupportedOperationException("ApiResponseCall doesn't support execute")
    }
}
