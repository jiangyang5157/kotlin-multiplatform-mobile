package com.gmail.jiangyang5157.transaction_domain_kt.entity

interface IReportEntity {
    val statement: IStatementEntity
    val transactions: List<ITransactionEntity>
}
