package com.gmail.jiangyang5157.common.network

import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Retrofit
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

class ApiResponseCallAdapterFactory : CallAdapter.Factory() {

    override fun get(
        returnType: Type,
        annotations: Array<out Annotation>,
        retrofit: Retrofit
    ): CallAdapter<*, *>? {

        // suspend functions wrap the response type in `Call`
        if (Call::class.java != getRawType(returnType)) {
            return null
        }

        check(returnType is ParameterizedType) {
            "Return type must be parameterized as Call<*>"
        }
        val responseType = getParameterUpperBound(0, returnType)

        // if the response type is not ApiResponse then we cannot handle this type
        if (ApiResponse::class.java != getRawType(responseType)) {
            return null
        }

        check(responseType is ParameterizedType) {
            "Response must be parameterized as ApiResponse<*, *>"
        }
        val successType = getParameterUpperBound(0, responseType)
        val errorType = getParameterUpperBound(1, responseType)

        return ApiResponseCallAdapter<Any, Any>(
            successType,
            retrofit.nextResponseBodyConverter(null, errorType, annotations)
        )
    }
}
