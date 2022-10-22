package com.gmail.jiangyang5157.transaction_domain.repo

import com.gmail.jiangyang5157.kit.data.Resource
import com.gmail.jiangyang5157.transaction_domain.entity.ReportEntity
import com.gmail.jiangyang5157.transaction_domain.entity.StatementEntity
import com.gmail.jiangyang5157.transaction_domain.entity.TransactionEntity
import kotlinx.coroutines.flow.Flow
import java.util.Date

interface ReportRepository :
    com.gmail.jiangyang5157.transaction_domain_base.repo.ReportRepository<ReportEntity, StatementEntity, TransactionEntity> {

    override suspend fun addReport(
        statement: StatementEntity,
        transactions: List<TransactionEntity>
    )

    override suspend fun deleteReports(statements: List<StatementEntity>)

    override fun getReports(): Flow<Resource<List<ReportEntity>, String>>

    override fun findTransactionBy(
        id: String,
        importedDate: Date
    ): Flow<Resource<TransactionEntity, String>>
}