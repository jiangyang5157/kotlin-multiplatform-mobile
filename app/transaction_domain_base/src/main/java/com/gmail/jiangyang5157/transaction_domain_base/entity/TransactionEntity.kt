package com.gmail.jiangyang5157.transaction_domain_base.entity

import com.gmail.jiangyang5157.kit.data.finance.Money
import java.util.*

interface TransactionEntity {
    val id: String
    val date: Date
    val money: Money
    val description: String
    val importedDate: Date
}
