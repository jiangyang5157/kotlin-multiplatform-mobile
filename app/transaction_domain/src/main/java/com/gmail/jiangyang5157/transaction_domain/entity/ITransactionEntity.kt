package com.gmail.jiangyang5157.transaction_domain.entity

import com.gmail.jiangyang5157.kit.data.finance.Money
import java.util.Date

interface ITransactionEntity {
    val id: String
    val date: Date
    val money: Money
    val description: String
    val importedDate: Date
}

// Tue, 15 Nov 1994
const val REGEX_DATE_EEE_dd_MMM_yyyy: String = "EEE, dd MMM yyyy"

// Tue, 15 Nov 1994 12:45:26 GMT
const val REGEX_DATE_EEE_dd_MMM_yyyy_HH_mm_ss_zzz: String = "EEE, dd MMM yyyy HH:mm:ss zzz"

// 1994/11/15 12:45:26
const val REGEX_DATE_yyyy_MM_dd_HH_mm_ss: String = "yyyy/MM/dd HH:mm:ss"

// 2021-02-02T08:11:16+13:00
const val REGEX_DATE_yyyy_MM_dd_T_HH_mm_ss_XXX = "yyyy-MM-dd'T'HH:mm:ssXXX"
