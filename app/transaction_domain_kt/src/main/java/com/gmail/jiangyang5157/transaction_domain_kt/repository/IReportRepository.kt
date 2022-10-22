package com.gmail.jiangyang5157.transaction_domain_kt.repository

import com.gmail.jiangyang5157.kit.data.Resource
import com.gmail.jiangyang5157.transaction_domain_kt.entity.IReportEntity
import com.gmail.jiangyang5157.transaction_domain_kt.entity.IStatementEntity
import com.gmail.jiangyang5157.transaction_domain_kt.entity.ITransactionEntity
import kotlinx.coroutines.flow.Flow
import java.util.Date

interface IReportRepository<ReportEntity : IReportEntity, StatementEntity : IStatementEntity, TransactionEntity : ITransactionEntity> {

    suspend fun addReport(statement: StatementEntity, transactions: List<TransactionEntity>)

    suspend fun deleteReports(statements: List<StatementEntity>)

    fun getReports(): Flow<Resource<List<ReportEntity>, String>>

    fun findTransactionBy(
        id: String,
        importedDate: Date
    ): Flow<Resource<TransactionEntity, String>>
}