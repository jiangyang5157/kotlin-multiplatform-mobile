package com.gmail.jiangyang5157.transaction_data.remote.dto

import com.gmail.jiangyang5157.kit.data.finance.Money
import com.gmail.jiangyang5157.transaction_domain.entity.TransactionEntity
import com.gmail.jiangyang5157.transaction_domain.entity.asDate
import com.gmail.jiangyang5157.transaction_domain.entity.asString
import com.gmail.jiangyang5157.transaction_domain.entity.REGEX_DATE_yyyy_MM_dd_T_HH_mm_ss_XXX
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonPrimitive
import com.google.gson.JsonSerializationContext
import com.google.gson.JsonSerializer
import com.google.gson.annotations.JsonAdapter
import com.google.gson.annotations.SerializedName
import java.lang.reflect.Type
import java.util.Date

fun TransactionDto.asEntity() = TransactionDto.Converter().forward(this)

fun TransactionEntity.asDto() = TransactionDto.Converter().backward(this)

/**
 * Transaction service model
 */
data class TransactionDto(

    @SerializedName("id")
    val id: String,

    @SerializedName("transactionDate")
    @JsonAdapter(value = DateStringJsonSerializer::class)
    val date: Date,

    @SerializedName("summary")
    val description: String,

    @SerializedName("debit")
    @JsonAdapter(value = TransactionEntity.MoneyDoubleJsonSerializer::class)
    val debit: Money,

    @SerializedName("credit")
    @JsonAdapter(value = TransactionEntity.MoneyDoubleJsonSerializer::class)
    val credit: Money
) {

    /**
     * Convert between Transaction service model and Transaction transaction_feature model
     */
    class Converter :
        com.gmail.jiangyang5157.kit.data.Converter<TransactionDto, TransactionEntity> {

        override fun backward(b: TransactionEntity?): TransactionDto? =
            b?.let {
                TransactionDto(
                    id = it.id,
                    date = it.date,
                    description = it.description,
                    debit = if (it.money.amount < Money(0).amount) it.money else Money(0),
                    credit = if (it.money.amount >= Money(0).amount) it.money else Money(0)
                )
            }

        override fun forward(a: TransactionDto?): TransactionEntity? =
            a?.let {
                TransactionEntity(
                    id = it.id,
                    date = it.date,
                    money = if (it.debit.amount > Money(0).amount) -it.debit else it.credit,
                    description = it.description,
                    importedDate = Date()
                )
            }
    }

    class DateStringJsonSerializer : JsonSerializer<Date>, JsonDeserializer<Date> {

        private val pattern: String = REGEX_DATE_yyyy_MM_dd_T_HH_mm_ss_XXX

        @Throws(IllegalArgumentException::class)
        override fun serialize(
            date: Date?,
            typeOfSrc: Type?,
            context: JsonSerializationContext?
        ): JsonElement {
            return date?.asString(pattern)?.let { JsonPrimitive(it) }
                ?: throw IllegalArgumentException("Cannot serialize $date to [JsonElement]")
        }

        @Throws(IllegalArgumentException::class)
        override fun deserialize(
            json: JsonElement?,
            typeOfT: Type?,
            context: JsonDeserializationContext?
        ): Date {
            return json?.asString?.asDate(pattern)
                ?: throw IllegalArgumentException("Cannot deserialize $json to [Date]")
        }
    }
}
