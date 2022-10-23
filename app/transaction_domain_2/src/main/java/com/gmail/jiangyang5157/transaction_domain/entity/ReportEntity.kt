package com.gmail.jiangyang5157.transaction_domain.entity

data class ReportEntity(
    override val statement: StatementEntity,
    override val transactions: List<TransactionEntity>,
) : com.gmail.jiangyang5157.transaction_domain_base.entity.ReportEntity
