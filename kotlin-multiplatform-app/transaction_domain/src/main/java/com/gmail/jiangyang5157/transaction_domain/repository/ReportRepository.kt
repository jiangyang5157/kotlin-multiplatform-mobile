package com.gmail.jiangyang5157.transaction_domain.repository

import com.gmail.jiangyang5157.kit.data.Resource
import com.gmail.jiangyang5157.transaction_domain.entity.ReportEntity
import com.gmail.jiangyang5157.transaction_domain.entity.StatementEntity
import com.gmail.jiangyang5157.transaction_domain.entity.TransactionEntity
import kotlinx.coroutines.flow.Flow
import java.util.Date

interface ReportRepository {

    suspend fun addReport(statement: StatementEntity, transactions: List<TransactionEntity>)

    suspend fun deleteReports(statements: List<StatementEntity>)

    fun getReports(): Flow<Resource<List<ReportEntity>, String>>

    fun findTransactionBy(id: String, importedDate: Date): Flow<Resource<TransactionEntity, String>>
}
