package com.gmail.jiangyang5157.transaction_domain.entity

import androidx.room.Embedded
import androidx.room.Relation
import com.google.gson.annotations.SerializedName

/**
 * This is Room supported entity.
 *
 * A report associated with a statement and it's transactions.
 */
data class ReportEntity(

    @Embedded
    @SerializedName("statement")
    override val statement: StatementEntity,

    @Relation(
        parentColumn = "importedDate",
        entityColumn = "importedDate",
        entity = TransactionEntity::class
    )
    @SerializedName("transactions")
    override val transactions: List<TransactionEntity>,
) : com.gmail.jiangyang5157.transaction_domain_base.entity.ReportEntity
