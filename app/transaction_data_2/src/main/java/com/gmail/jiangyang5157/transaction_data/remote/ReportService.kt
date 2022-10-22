package com.gmail.jiangyang5157.transaction_data.remote

import com.gmail.jiangyang5157.kit.network.ApiResponse
import com.gmail.jiangyang5157.transaction_domain.entity.TransactionEntity
import retrofit2.http.GET

interface ReportService {

    /*
Example of https://60df9ae9abbdd9001722d437.mockapi.io/api/v1/transactions2
[
    {
        "id":"1",
        "date":"2021-02-02T08:11:16+13:00",
        "description":"Trans with positive money",
        "money":150.99
    },
    {
        "id":"2",
        "date":"2021-02-03T00:06:37+13:00",
        "description":"Trans with negative money",
        "money":-101.99
    }
]
     */
    @GET("api/v1/transactions2")
    suspend fun fetchTransactions(): ApiResponse<List<TransactionEntity>, String>
}
