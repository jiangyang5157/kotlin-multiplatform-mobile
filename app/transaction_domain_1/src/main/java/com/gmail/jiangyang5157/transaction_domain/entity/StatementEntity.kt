package com.gmail.jiangyang5157.transaction_domain.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.gmail.jiangyang5157.transaction_domain.entity.IStatementEntity
import com.google.gson.annotations.SerializedName
import java.util.Date

/**
 * This is Room supported entity.
 */
@Entity(tableName = "statements")
data class StatementEntity(

    /**
     * The time this statement get imported or downloaded.
     * A statement has a bunch of associated transactions.
     */
    @PrimaryKey
    @ColumnInfo(name = "importedDate")
    @SerializedName("importedDate")
    override val importedDate: Date
): IStatementEntity
