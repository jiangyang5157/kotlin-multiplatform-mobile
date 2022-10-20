package com.gmail.jiangyang5157.transaction_data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.gmail.jiangyang5157.transaction_domain.entity.DateLongConverter
import com.gmail.jiangyang5157.transaction_domain.entity.MoneyDoubleConverter
import com.gmail.jiangyang5157.transaction_domain.entity.StatementEntity
import com.gmail.jiangyang5157.transaction_domain.entity.TransactionEntity

@Database(
    entities = [
        StatementEntity::class,
        TransactionEntity::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(
    value = [
        MoneyDoubleConverter::class,
        DateLongConverter::class
    ]
)
abstract class ReportDb : RoomDatabase() {

    abstract fun reportDao(): ReportDao
}
