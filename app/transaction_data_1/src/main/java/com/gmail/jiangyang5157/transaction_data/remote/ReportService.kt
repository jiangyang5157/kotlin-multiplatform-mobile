package com.gmail.jiangyang5157.transaction_data.remote

import com.gmail.jiangyang5157.common.network.ApiResponse
import com.gmail.jiangyang5157.transaction_data.remote.dto.TransactionDto
import retrofit2.http.GET

interface ReportService {

    /*
Example of https://60df9ae9abbdd9001722d437.mockapi.io/api/v1/transactions1
[
    {
        "id":"1",
        "transactionDate":"2021-02-02T08:11:16+13:00",
        "summary":"Fancy Food Market Auckland",
        "debit":197.9,
        "credit":0
    },
    {
        "id":"2",
        "transactionDate":"2021-02-03T00:06:37+13:00",
        "summary":"Parking Auckland",
        "debit":8.25,
        "credit":0
    }
]
    */
    @GET("api/v1/transactions1")
    suspend fun fetchTransactions(): ApiResponse<List<TransactionDto>, String>
}
