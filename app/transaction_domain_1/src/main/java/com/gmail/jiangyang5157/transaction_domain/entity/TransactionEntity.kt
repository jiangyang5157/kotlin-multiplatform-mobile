package com.gmail.jiangyang5157.transaction_domain.entity

import androidx.room.*
import androidx.room.ForeignKey.CASCADE
import com.gmail.jiangyang5157.kit.data.finance.Money
import com.google.gson.*
import com.google.gson.annotations.JsonAdapter
import com.google.gson.annotations.SerializedName
import java.lang.reflect.Type
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

/**
 * This is Room supported entity.
 *
 * A Transaction belongs to a statement by it's [importedDate].
 * Deleting a statement will delete all transactions that have same [importedDate]
 * Set [id] and [importedDate] as primaryKeys, due to they cannot be same at the same time.
 */
@Entity(
    tableName = "transactions",
    primaryKeys = ["id", "importedDate"],
    foreignKeys = [
        ForeignKey(
            entity = StatementEntity::class,
            parentColumns = ["importedDate"],
            childColumns = ["importedDate"],
            onDelete = CASCADE,
            onUpdate = CASCADE
        )
    ],
    indices = [Index("importedDate")]
)
data class TransactionEntity(

    @ColumnInfo(name = "id")
    @SerializedName("id")
    override val id: String,

    @ColumnInfo(name = "date")
    @SerializedName("date")
    override val date: Date,

    @ColumnInfo(name = "money")
    @SerializedName("money")
    @JsonAdapter(value = MoneyDoubleJsonSerializer::class)
    override val money: Money,

    @ColumnInfo(name = "description")
    @SerializedName("description")
    override val description: String,

    @ColumnInfo(name = "importedDate")
    @SerializedName("importedDate")
    override val importedDate: Date,
) : com.gmail.jiangyang5157.transaction_domain_base.entity.TransactionEntity {

    class MoneyDoubleJsonSerializer : JsonSerializer<Money>, JsonDeserializer<Money> {

        @Throws(IllegalArgumentException::class)
        override fun serialize(
            money: Money?,
            typeOfSrc: Type?,
            context: JsonSerializationContext?
        ): JsonElement {
            return MoneyDoubleConverter().forward(money)?.let { JsonPrimitive(it) }
                ?: throw IllegalArgumentException("Cannot serialize $money to [JsonElement]")
        }

        @Throws(IllegalArgumentException::class)
        override fun deserialize(
            json: JsonElement?,
            typeOfT: Type?,
            context: JsonDeserializationContext?
        ): Money {
            return json?.asDouble?.let { MoneyDoubleConverter().backward(it) }
                ?: throw IllegalArgumentException("Cannot deserialize $json to [Money]")
        }
    }
}

class MoneyDoubleConverter : com.gmail.jiangyang5157.kit.data.Converter<Money, Double> {

    @TypeConverter
    override fun backward(b: Double?): Money? = b?.let { Money(it) }

    @TypeConverter
    override fun forward(a: Money?): Double? = a?.amount?.toDouble()
}

class DateLongConverter : com.gmail.jiangyang5157.kit.data.Converter<Date, Long> {

    @TypeConverter
    override fun backward(b: Long?): Date? = b?.let { Date(it) }

    @TypeConverter
    override fun forward(a: Date?): Long? = a?.time
}

fun Date.asString(pattern: String) = DateStringConverter(pattern).forward(this)

fun String.asDate(pattern: String) = DateStringConverter(pattern).backward(this)

class DateStringConverter(private val pattern: String) :
    com.gmail.jiangyang5157.kit.data.Converter<Date, String> {

    override fun backward(b: String?): Date? =
        b?.let {
            try {
                SimpleDateFormat(pattern, Locale.getDefault()).parse(it.trim())
            } catch (e: ParseException) {
                null
            }
        }

    override fun forward(a: Date?): String? =
        a?.let { SimpleDateFormat(pattern, Locale.getDefault()).format(it) }
}
