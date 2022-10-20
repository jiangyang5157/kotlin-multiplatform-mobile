package com.gmail.jiangyang5157.transaction_data.repo

import android.os.Build
import androidx.annotation.RequiresApi
import com.gmail.jiangyang5157.common.network.ApiResponse
import com.gmail.jiangyang5157.kit.data.Resource
import com.gmail.jiangyang5157.transaction_data.local.ReportInMemory
import com.gmail.jiangyang5157.transaction_data.remote.ReportService
import com.gmail.jiangyang5157.transaction_domain.entity.ReportEntity
import com.gmail.jiangyang5157.transaction_domain.entity.StatementEntity
import com.gmail.jiangyang5157.transaction_domain.entity.TransactionEntity
import com.gmail.jiangyang5157.transaction_domain.repository.ReportRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.util.Date
import javax.inject.Inject

class DefaultReportRepository @Inject constructor(
    private val memory: ReportInMemory,
    private val service: ReportService
) : ReportRepository {

    /**
     * Save into memory cache, no service call support
     */
    @RequiresApi(Build.VERSION_CODES.N)
    override suspend fun addReport(
        statement: StatementEntity,
        transactions: List<TransactionEntity>
    ) {
        memory.addReport(statement, transactions)
    }

    /**
     * Delete from memory cache, no service call support
     */
    @RequiresApi(Build.VERSION_CODES.N)
    override suspend fun deleteReports(statements: List<StatementEntity>) {
        memory.deleteStatements(statements)
    }

    /**
     * Find from memory cache, no service call support
     */
    override fun findTransactionBy(
        id: String,
        importedDate: Date
    ): Flow<Resource<TransactionEntity, String>> {
        return flow {
            emit(Resource.Completed(memory.getTransactionBy(id, importedDate)))
        }
    }

    /**
     * Get from memory cache, if not exists, make a service call to fetch new data
     */
    @RequiresApi(Build.VERSION_CODES.N)
    override fun getReports(): Flow<Resource<List<ReportEntity>, String>> {
        return flow {
            val local = memory.getReports()
            if (local.isNullOrEmpty()) {
                emit(Resource.Loading())

                when (val remote = service.fetchTransactions()) {
                    is ApiResponse.Success -> {
                        val statement = StatementEntity(Date())
                        val transactions: List<TransactionEntity> = remote.body.map { entity ->
                            TransactionEntity(
                                id = entity.id,
                                date = entity.date,
                                money = entity.money,
                                description = entity.description,
                                importedDate = statement.importedDate
                            )
                        }
                        // save into memory once new data received
                        addReport(statement, transactions)
                        emit(Resource.Completed(listOf(ReportEntity(statement, transactions))))
                    }
                    is ApiResponse.Failure -> {
                        emit(Resource.Failed(remote.error))
                    }
                    is ApiResponse.NetworkError -> {
                        emit(Resource.Failed(remote.exception.message))
                    }
                    is ApiResponse.UnknownError -> {
                        emit(Resource.Failed(remote.throwable.message))
                    }
                }
            } else {
                emit(Resource.Completed(local))
            }
        }
    }
}
