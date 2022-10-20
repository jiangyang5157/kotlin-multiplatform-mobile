package com.gmail.jiangyang5157.transaction_presentation.ui.binding

import android.view.View
import com.gmail.jiangyang5157.transaction_domain.entity.TransactionEntity

data class TransactionItem(
    val transaction: TransactionEntity,
    val onClickListener: View.OnClickListener? = null
)
