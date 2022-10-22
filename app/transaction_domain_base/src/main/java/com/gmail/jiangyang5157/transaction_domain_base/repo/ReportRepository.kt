package com.gmail.jiangyang5157.transaction_domain_base.repo

import com.gmail.jiangyang5157.kit.data.Resource
import com.gmail.jiangyang5157.transaction_domain_base.entity.ReportEntity
import com.gmail.jiangyang5157.transaction_domain_base.entity.StatementEntity
import com.gmail.jiangyang5157.transaction_domain_base.entity.TransactionEntity
import kotlinx.coroutines.flow.Flow
import java.util.*

interface ReportRepository<Report : ReportEntity, Statement : StatementEntity, Transaction : TransactionEntity> {

    suspend fun addReport(statement: Statement, transactions: List<Transaction>)

    suspend fun deleteReports(statements: List<Statement>)

    fun getReports(): Flow<Resource<List<Report>, String>>

    fun findTransactionBy(
        id: String,
        importedDate: Date
    ): Flow<Resource<Transaction, String>>
}