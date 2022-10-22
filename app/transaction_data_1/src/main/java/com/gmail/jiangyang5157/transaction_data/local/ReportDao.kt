package com.gmail.jiangyang5157.transaction_data.local

import androidx.room.*
import com.gmail.jiangyang5157.transaction_domain.entity.ReportEntity
import com.gmail.jiangyang5157.transaction_domain.entity.StatementEntity
import com.gmail.jiangyang5157.transaction_domain.entity.TransactionEntity
import java.util.*

@Dao
interface ReportDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStatement(statement: StatementEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTransactions(transactions: List<TransactionEntity>)

    @Transaction
    suspend fun insertReport(statement: StatementEntity, transactions: List<TransactionEntity>) {
        insertStatement(statement)
        insertTransactions(transactions)
    }

    suspend fun insertReport(report: ReportEntity) {
        insertReport(report.statement, report.transactions)
    }

    @Delete
    suspend fun deleteStatements(statements: List<StatementEntity>)

    @Delete
    suspend fun deleteTransactions(transactions: List<TransactionEntity>)

    @Query("SELECT * from transactions WHERE id = :id AND importedDate = :importedDate")
    suspend fun findTransactionBy(id: String, importedDate: Date): TransactionEntity

    @Transaction
    @Query("SELECT * from statements")
    suspend fun findReports(): List<ReportEntity>
}
