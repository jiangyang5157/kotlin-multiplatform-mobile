package com.gmail.jiangyang5157.transaction_domain.entity

import com.gmail.jiangyang5157.kit.data.finance.Money
import java.util.*

data class TransactionEntity(
    override val id: String,
    override val date: Date,
    override val money: Money,
    override val description: String,
    override val importedDate: Date,
) : com.gmail.jiangyang5157.transaction_domain_base.entity.TransactionEntity