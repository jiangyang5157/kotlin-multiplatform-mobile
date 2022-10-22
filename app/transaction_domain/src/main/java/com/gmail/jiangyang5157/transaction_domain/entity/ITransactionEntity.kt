package com.gmail.jiangyang5157.transaction_domain.entity

import com.gmail.jiangyang5157.common.data.finance.Money
import java.util.Date

interface ITransactionEntity {
    val id: String
    val date: Date
    val money: Money
    val description: String
    val importedDate: Date
}
