package com.gmail.jiangyang5157.transaction_domain.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.Index
import androidx.room.TypeConverter
import com.gmail.jiangyang5157.kit.data.Converter
import com.gmail.jiangyang5157.kit.data.finance.Money
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonPrimitive
import com.google.gson.JsonSerializationContext
import com.google.gson.JsonSerializer
import com.google.gson.annotations.JsonAdapter
import com.google.gson.annotations.SerializedName
import java.lang.reflect.Type
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

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
    val id: String,

    @ColumnInfo(name = "date")
    @SerializedName("date")
    val date: Date,

    @ColumnInfo(name = "money")
    @SerializedName("money")
    @JsonAdapter(value = MoneyDoubleJsonSerializer::class)
    val money: Money,

    @ColumnInfo(name = "description")
    @SerializedName("description")
    val description: String,

    @ColumnInfo(name = "importedDate")
    @SerializedName("importedDate")
    val importedDate: Date
) {

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

class MoneyDoubleConverter : Converter<Money, Double> {

    @TypeConverter
    override fun backward(b: Double?): Money? = b?.let { Money(it) }

    @TypeConverter
    override fun forward(a: Money?): Double? = a?.amount?.toDouble()
}

class DateLongConverter : Converter<Date, Long> {

    @TypeConverter
    override fun backward(b: Long?): Date? = b?.let { Date(it) }

    @TypeConverter
    override fun forward(a: Date?): Long? = a?.time
}

fun Date.asString(pattern: String) = DateStringConverter(pattern).forward(this)

fun String.asDate(pattern: String) = DateStringConverter(pattern).backward(this)

class DateStringConverter(private val pattern: String) : Converter<Date, String> {

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

    companion object {

        // Tue, 15 Nov 1994
        const val REGEX_DATE_EEE_dd_MMM_yyyy: String = "EEE, dd MMM yyyy"

        // Tue, 15 Nov 1994 12:45:26 GMT
        const val REGEX_DATE_EEE_dd_MMM_yyyy_HH_mm_ss_zzz: String = "EEE, dd MMM yyyy HH:mm:ss zzz"

        // 1994/11/15 12:45:26
        const val REGEX_DATE_yyyy_MM_dd_HH_mm_ss: String = "yyyy/MM/dd HH:mm:ss"

        // 2021-02-02T08:11:16+13:00
        const val REGEX_DATE_yyyy_MM_dd_T_HH_mm_ss_XXX = "yyyy-MM-dd'T'HH:mm:ssXXX"
    }
}
