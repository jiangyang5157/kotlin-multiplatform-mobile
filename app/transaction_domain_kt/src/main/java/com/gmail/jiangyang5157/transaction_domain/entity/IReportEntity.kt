package com.gmail.jiangyang5157.transaction_domain.entity

interface IReportEntity {
    val statement: IStatementEntity
    val transactions: List<ITransactionEntity>
}
