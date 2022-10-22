package com.gmail.jiangyang5157.transaction_domain_base.entity

interface ReportEntity {
    val statement: StatementEntity
    val transactions: List<TransactionEntity>
}
