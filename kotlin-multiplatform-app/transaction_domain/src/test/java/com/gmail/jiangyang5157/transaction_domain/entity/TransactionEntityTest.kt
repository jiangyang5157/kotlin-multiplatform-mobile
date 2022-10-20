package com.gmail.jiangyang5157.transaction_domain.entity

import com.gmail.jiangyang5157.common.ext.fromJson
import com.gmail.jiangyang5157.kit.data.finance.Money
import com.google.gson.GsonBuilder
import org.junit.Assert
import org.junit.Test
import java.util.Date

class TransactionEntityTest {

    @Test
    fun test_json_serializer() {
        val transaction = TransactionEntity(
            id = "id",
            date = Date(1592391430000), // "Jun 17, 2020 10:57:10 PM"
            money = Money(-10.99),
            description = "desc",
            importedDate = Date(1592391430000)
        )
        val json = """
{
  "id": "id",
  "date": "Jun 17, 2020, 10:57:10 PM",
  "money": -10.99,
  "description": "desc",
  "importedDate": "Jun 17, 2020, 10:57:10 PM"
}
        """.trimIndent()
        Assert.assertEquals(
            json,
            GsonBuilder().setPrettyPrinting().create().toJson(transaction)
        )
    }

    @Test
    fun test_json_deserialize() {
        val transaction = TransactionEntity(
            id = "id",
            date = Date(1592391430000),
            money = Money(-10.99),
            description = "desc",
            importedDate = Date(1592391430000)
        )
        val json = """
{
  "id": "id",
  "date": "Jun 17, 2020, 10:57:10 PM",
  "money": -10.99,
  "description": "desc",
  "importedDate": "Jun 17, 2020, 10:57:10 PM"
}
        """.trimIndent()
        Assert.assertEquals(
            transaction,
            GsonBuilder().setPrettyPrinting().create().fromJson<TransactionEntity>(json)
        )
    }
}
