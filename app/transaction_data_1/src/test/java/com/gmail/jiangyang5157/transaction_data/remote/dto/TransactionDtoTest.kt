package com.gmail.jiangyang5157.transaction_data.remote.dto

import com.gmail.jiangyang5157.common.ext.fromJson
import com.gmail.jiangyang5157.kit.data.finance.Money
import com.gmail.jiangyang5157.transaction_domain.entity.TransactionEntity
import com.google.gson.GsonBuilder
import org.junit.Assert
import org.junit.Test
import java.util.Date

class TransactionDtoTest {

    @Test
    fun test_json_serializer() {
        val dto =
            TransactionDto(
                id = "id",
                date = Date(1612206676000), // "2021-02-02T08:11:16+13:00"
                description = "desc",
                debit = Money(100.0),
                credit = Money(0)
            )
        val json = """
{
  "id": "id",
  "transactionDate": "2021-02-02T08:11:16+13:00",
  "summary": "desc",
  "debit": 100.0,
  "credit": 0.0
}
        """.trimIndent()
        Assert.assertEquals(json, GsonBuilder().setPrettyPrinting().create().toJson(dto))
    }

    @Test
    fun test_json_deserialize() {
        val dto =
            TransactionDto(
                id = "id",
                date = Date(1612206676000), // "2021-02-02T08:11:16+13:00"
                description = "desc",
                debit = Money(100.0),
                credit = Money(0)
            )
        val json = """
{
  "id": "id",
  "transactionDate": "2021-02-02T08:11:16+13:00",
  "summary": "desc",
  "debit": 100.0,
  "credit": 0.0
}
        """.trimIndent()
        Assert.assertEquals(
            dto,
            GsonBuilder().setPrettyPrinting().create().fromJson<TransactionDto>(json)
        )
    }

    @Test
    fun test_converter_forward_with_debit() {
        val entity = TransactionDto(
            id = "id",
            date = Date(1592391430000), // "Jun 17, 2020 10:57:10 PM"
            description = "desc",
            debit = Money(100.0),
            credit = Money(0)
        ).asEntity()
        Assert.assertEquals("id", entity?.id)
        Assert.assertEquals(Date(1592391430000), entity?.date)
        Assert.assertEquals("desc", entity?.description)
        Assert.assertEquals(Money(-100.0), entity?.money)
    }

    @Test
    fun test_converter_forward_with_credit() {
        val entity = TransactionDto(
            id = "id",
            date = Date(1592391430000), // "Jun 17, 2020 10:57:10 PM"
            description = "desc",
            debit = Money(0),
            credit = Money(100.0)
        ).asEntity()
        Assert.assertEquals("id", entity?.id)
        Assert.assertEquals(Date(1592391430000), entity?.date)
        Assert.assertEquals("desc", entity?.description)
        Assert.assertEquals(Money(100.0), entity?.money)
    }

    @Test
    fun test_converter_backward_with_positive_money() {
        val dto = TransactionEntity(
            id = "id",
            date = Date(1592391430000),
            money = Money(100.0),
            description = "desc",
            importedDate = Date(1592391430000)
        ).asDto()
        Assert.assertEquals("id", dto?.id)
        Assert.assertEquals(Date(1592391430000), dto?.date)
        Assert.assertEquals("desc", dto?.description)
        Assert.assertEquals(Money(0), dto?.debit)
        Assert.assertEquals(Money(100.0), dto?.credit)
    }

    @Test
    fun test_converter_backward_with_negative_money() {
        val dto = TransactionEntity(
            id = "id",
            date = Date(1592391430000),
            money = Money(-100.0),
            description = "desc",
            importedDate = Date(1592391430000)
        ).asDto()
        Assert.assertEquals("id", dto?.id)
        Assert.assertEquals(Date(1592391430000), dto?.date)
        Assert.assertEquals("desc", dto?.description)
        Assert.assertEquals(Money(-100.0), dto?.debit)
        Assert.assertEquals(Money(0), dto?.credit)
    }
}
