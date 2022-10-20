package com.gmail.jiangyang5157.transaction_data.local

import android.database.sqlite.SQLiteConstraintException
import androidx.arch.core.executor.testing.CountingTaskExecutorRule
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.gmail.jiangyang5157.kit.data.finance.Money
import com.gmail.jiangyang5157.transaction_domain.entity.StatementEntity
import com.gmail.jiangyang5157.transaction_domain.entity.TransactionEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Assert
import org.junit.Assert.assertTrue
import org.junit.Assert.fail
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.util.Date
import java.util.concurrent.TimeUnit

/**
 * Test Report Database with in-memory database instance
 */
@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class ReportDaoTest {

    @get:Rule
    val countingTaskExecutorRule = CountingTaskExecutorRule()

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var reportDb: ReportDb

    @Before
    fun setup() {
        reportDb = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            ReportDb::class.java
        ).build()
    }

    @After
    fun cleanUp() {
        countingTaskExecutorRule.drainTasks(500, TimeUnit.MILLISECONDS)
        assertTrue(countingTaskExecutorRule.isIdle)
        reportDb.close()
    }

    @Test
    fun test_trans_without_statement_expect_failed() = runBlockingTest {
        val job = launch(Dispatchers.IO) {
            val transactions = listOf(
                TransactionEntity(
                    id = "1",
                    date = Date(),
                    money = Money(-10.99),
                    description = "desc",
                    importedDate = Date()
                )
            )

            try {
                reportDb.reportDao().insertTransactions(transactions)
                fail()
            } catch (e: SQLiteConstraintException) {
            }
        }
        job.cancel()
    }

    @Test
    fun test_1statement_with_0trans() = runBlockingTest {
        val job = launch(Dispatchers.IO) {
            val statement = StatementEntity(Date())
            val transactions = emptyList<TransactionEntity>()
            reportDb.reportDao().insertReport(statement, transactions)

            val reports = reportDb.reportDao().findReports()
            Assert.assertEquals(1, reports.size)
            Assert.assertEquals(statement, reports[0].statement)
            Assert.assertEquals(transactions, reports[0].transactions)
        }
        job.cancel()
    }

    @Test
    fun test_trans_with_diff_importedDate_expect_failed() = runBlockingTest {
        val job = launch(Dispatchers.IO) {
            val importedDate = Date(1)
            val diffImportedDate = Date(2)
            val statement = StatementEntity(importedDate)
            val transactions = listOf(
                TransactionEntity(
                    id = "1",
                    date = Date(),
                    money = Money(-10.99),
                    description = "desc",
                    importedDate = importedDate
                ),
                TransactionEntity(
                    id = "2",
                    date = Date(),
                    money = Money(-10.99),
                    description = "desc",
                    importedDate = diffImportedDate
                )
            )
            reportDb.reportDao().insertStatement(statement)
            try {
                reportDb.reportDao().insertTransactions(transactions)
                fail()
            } catch (e: SQLiteConstraintException) {
            }

            val reports = reportDb.reportDao().findReports()
            Assert.assertEquals(1, reports.size)
            Assert.assertEquals(statement, reports[0].statement)
            Assert.assertEquals(emptyList<TransactionEntity>(), reports[0].transactions)
        }
        job.cancel()
    }

    @Test
    fun test_trans_with_same_id_expect_override() = runBlockingTest {
        val job = launch(Dispatchers.IO) {
            val statement = StatementEntity(Date())
            val transactions = listOf(
                TransactionEntity(
                    id = "1",
                    date = Date(),
                    money = Money(-10.99),
                    description = "desc",
                    importedDate = statement.importedDate
                ),
                TransactionEntity(
                    id = "1",
                    date = Date(),
                    money = Money(-12.99),
                    description = "desc",
                    importedDate = statement.importedDate
                )
            )
            reportDb.reportDao().insertReport(statement, transactions)

            val reports = reportDb.reportDao().findReports()
            Assert.assertEquals(1, reports.size)
            Assert.assertEquals(statement, reports[0].statement)
            Assert.assertEquals(listOf(transactions[1]), reports[0].transactions)
        }
        job.cancel()
    }

    @Test
    fun test_delete_statement_expect_delete_associated_trans() =
        runBlockingTest {
            val job = launch(Dispatchers.IO) {
                val statement = StatementEntity(Date())
                val transactions = listOf(
                    TransactionEntity(
                        id = "1",
                        date = Date(),
                        money = Money(-10.99),
                        description = "desc",
                        importedDate = statement.importedDate
                    ),
                    TransactionEntity(
                        id = "2",
                        date = Date(),
                        money = Money(-10.99),
                        description = "desc",
                        importedDate = statement.importedDate
                    )
                )
                reportDb.reportDao().insertReport(statement, transactions)

                val reports = reportDb.reportDao().findReports()
                Assert.assertEquals(1, reports.size)
                Assert.assertEquals(statement, reports[0].statement)
                Assert.assertEquals(transactions, reports[0].transactions)

                reportDb.reportDao().deleteStatements(listOf(statement))
                Assert.assertEquals(0, reportDb.reportDao().findReports().size)
                Assert.assertEquals(
                    null,
                    reportDb.reportDao().findTransactionBy("1", statement.importedDate)
                )
                Assert.assertEquals(
                    null,
                    reportDb.reportDao().findTransactionBy("2", statement.importedDate)
                )
            }
            job.cancel()
        }

    @Test
    fun test_delete_transactions_wont_delete_associated_statement() =
        runBlockingTest {
            val job = launch(Dispatchers.IO) {
                val statement = StatementEntity(Date())
                val transactions = listOf(
                    TransactionEntity(
                        id = "1",
                        date = Date(),
                        money = Money(-10.99),
                        description = "desc",
                        importedDate = statement.importedDate
                    ),
                    TransactionEntity(
                        id = "2",
                        date = Date(),
                        money = Money(-10.99),
                        description = "desc",
                        importedDate = statement.importedDate
                    )
                )
                reportDb.reportDao().insertReport(statement, transactions)

                val reports = reportDb.reportDao().findReports()
                Assert.assertEquals(1, reports.size)
                Assert.assertEquals(statement, reports[0].statement)
                Assert.assertEquals(transactions, reports[0].transactions)

                reportDb.reportDao().deleteTransactions(transactions)
                Assert.assertEquals(1, reportDb.reportDao().findReports().size)
                Assert.assertEquals(
                    null,
                    reportDb.reportDao().findTransactionBy("1", statement.importedDate)
                )
                Assert.assertEquals(
                    null,
                    reportDb.reportDao().findTransactionBy("2", statement.importedDate)
                )
            }
            job.cancel()
        }

    @Test
    fun test_findTransactionByIdAndDate() = runBlockingTest {
        val job = launch(Dispatchers.IO) {
            val statement = StatementEntity(Date())
            val transactions = listOf(
                TransactionEntity(
                    id = "1",
                    date = Date(),
                    money = Money(-10.99),
                    description = "desc",
                    importedDate = statement.importedDate
                ),
                TransactionEntity(
                    id = "2",
                    date = Date(),
                    money = Money(-10.99),
                    description = "desc",
                    importedDate = statement.importedDate
                )
            )
            reportDb.reportDao().insertReport(statement, transactions)

            val reports = reportDb.reportDao().findReports()
            Assert.assertEquals(1, reports.size)
            Assert.assertEquals(statement, reports[0].statement)
            Assert.assertEquals(transactions, reports[0].transactions)
            Assert.assertEquals(
                transactions[0],
                reportDb.reportDao().findTransactionBy("1", statement.importedDate)
            )
            Assert.assertEquals(
                transactions[1],
                reportDb.reportDao().findTransactionBy("2", statement.importedDate)
            )
        }
        job.cancel()
    }
}
