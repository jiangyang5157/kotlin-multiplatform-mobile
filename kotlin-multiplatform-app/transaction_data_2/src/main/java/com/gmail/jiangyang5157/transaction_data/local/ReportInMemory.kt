package com.gmail.jiangyang5157.transaction_data.local

import android.os.Build
import androidx.annotation.RequiresApi
import com.gmail.jiangyang5157.transaction_domain.entity.ReportEntity
import com.gmail.jiangyang5157.transaction_domain.entity.StatementEntity
import com.gmail.jiangyang5157.transaction_domain.entity.TransactionEntity
import java.util.Date
import javax.inject.Inject

class ReportInMemory @Inject constructor() {

    private val reports = arrayListOf<ReportEntity>()

    fun getReports() = reports

    @RequiresApi(Build.VERSION_CODES.N)
    fun deleteStatements(statements: List<StatementEntity>) {
        statements.forEach { statement ->
            reports.removeIf { report ->
                report.statement.importedDate == statement.importedDate
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun addReport(statement: StatementEntity, transactions: List<TransactionEntity>) {
        val existingReport = reports.find { it.statement.importedDate == statement.importedDate }
        if (existingReport != null) {
            deleteStatements(listOf(existingReport.statement))
        }
        reports.add(ReportEntity(statement = statement, transactions))
    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun addReport(report: ReportEntity) {
        addReport(report.statement, report.transactions)
    }

    fun getTransactionBy(id: String, importedDate: Date): TransactionEntity? {
        val existingReport = reports.find { it.statement.importedDate == importedDate }
        return existingReport?.transactions?.find { it.id == id && it.importedDate == importedDate }
    }
}
