package com.gmail.jiangyang5157.transaction_data.local

import com.gmail.jiangyang5157.kit.data.finance.Money
import com.gmail.jiangyang5157.transaction_domain.entity.StatementEntity
import com.gmail.jiangyang5157.transaction_domain.entity.TransactionEntity
import org.junit.Assert.assertEquals
import org.junit.Test
import java.util.*

class ReportInMemoryTest {

    @Test
    fun test_get_trans() {
        val importedDate = Date()
        val statement = StatementEntity(importedDate)
        val transaction = TransactionEntity(
            id = "1",
            date = Date(),
            money = Money(-11.99),
            description = "desc",
            importedDate = importedDate
        )
        val report = ReportInMemory()
        report.addReport(statement, listOf(transaction))
        assertEquals(1, report.getReports().size)
        assertEquals(null, report.getTransactionBy("0", importedDate))
        assertEquals(transaction, report.getTransactionBy("1", importedDate))
        assertEquals(
            TransactionEntity(
                transaction.id,
                transaction.date,
                transaction.money,
                transaction.description,
                transaction.importedDate
            ),
            report.getTransactionBy("1", importedDate)
        )
    }

    @Test
    fun test_add_same_report_with_diff_trans_expected_override() {
        val importedDate = Date()
        val statement0 = StatementEntity(importedDate)
        val transactions0 = emptyList<TransactionEntity>()
        val statement1 = StatementEntity(importedDate)
        val transactions1 = listOf(
            TransactionEntity(
                id = "1",
                date = Date(),
                money = Money(-11.99),
                description = "desc",
                importedDate = importedDate
            ),
            TransactionEntity(
                id = "2",
                date = Date(),
                money = Money(-12.99),
                description = "desc",
                importedDate = importedDate
            )
        )

        val statement2 = StatementEntity(importedDate)
        val transactions2 = listOf(
            TransactionEntity(
                id = "3",
                date = Date(),
                money = Money(-13.99),
                description = "desc",
                importedDate = importedDate
            )
        )

        val report = ReportInMemory()
        report.addReport(statement0, transactions0)
        assertEquals(1, report.getReports().size)

        report.addReport(statement1, transactions1)
        assertEquals(1, report.getReports().size)
        assertEquals(transactions1[0], report.getTransactionBy("1", importedDate))
        assertEquals(transactions1[1], report.getTransactionBy("2", importedDate))

        report.addReport(statement2, transactions2)
        assertEquals(1, report.getReports().size)
        assertEquals(transactions2[0], report.getTransactionBy("3", importedDate))
    }
}
