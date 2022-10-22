package com.gmail.jiangyang5157.transaction_data.repo

import com.gmail.jiangyang5157.common.network.ApiResponse
import com.gmail.jiangyang5157.common.data.Resource
import com.gmail.jiangyang5157.transaction_data.local.ReportDb
import com.gmail.jiangyang5157.transaction_data.remote.ReportService
import com.gmail.jiangyang5157.transaction_data.remote.dto.asEntity
import com.gmail.jiangyang5157.transaction_domain.entity.ReportEntity
import com.gmail.jiangyang5157.transaction_domain.entity.StatementEntity
import com.gmail.jiangyang5157.transaction_domain.entity.TransactionEntity
import com.gmail.jiangyang5157.transaction_domain.repo.ReportRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.util.Date
import javax.inject.Inject

class DefaultReportRepository @Inject constructor(
    private val db: ReportDb,
    private val service: ReportService
) : ReportRepository<ReportEntity, StatementEntity, TransactionEntity> {

    /**
     * Save into database, no service call support
     */
    override suspend fun addReport(
        statement: StatementEntity,
        transactions: List<TransactionEntity>
    ) {
        db.reportDao().insertReport(statement, transactions)
    }

    /**
     * Delete from database, no service call support
     */
    override suspend fun deleteReports(statements: List<StatementEntity>) {
        db.reportDao().deleteStatements(statements)
    }

    /**
     * Find from database, no service call support
     */
    override fun findTransactionBy(
        id: String,
        importedDate: Date
    ): Flow<Resource<TransactionEntity, String>> {
        return flow {
            emit(Resource.Completed(db.reportDao().findTransactionBy(id, importedDate)))
        }
    }

    /**
     * Get from database, if not exists, make a service call to fetch new data
     */
    override fun getReports(): Flow<Resource<List<ReportEntity>, String>> {
        return flow {
            val local = db.reportDao().findReports()
            if (local.isNullOrEmpty()) {
                emit(Resource.Loading())

                when (val remote = service.fetchTransactions()) {
                    is ApiResponse.Success -> {
                        val statement = StatementEntity(Date())
                        val transactions: List<TransactionEntity> = remote.body.mapNotNull { dto ->
                            dto.asEntity()?.let { entity ->
                                TransactionEntity(
                                    id = entity.id,
                                    date = entity.date,
                                    money = entity.money,
                                    description = entity.description,
                                    importedDate = statement.importedDate
                                )
                            }
                        }
                        // save into database once new data received
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
