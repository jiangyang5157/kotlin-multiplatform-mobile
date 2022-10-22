package com.gmail.jiangyang5157.kit.data.finance

import org.junit.Test
import java.math.RoundingMode
import java.util.*
import kotlin.test.*

/**
 * Created by Yang Jiang on January 23, 2019
 */
class MoneyTest {

    @Test
    fun test_constructor_default() {
        assertEquals(
            "0.00",
            Money(
                0
            ).amount.toString()
        )
//        assertEquals("NZD", Money(0).currency.currencyCode.toString())
        assertEquals(
            RoundingMode.HALF_UP,
            Money(
                0
            ).roundingMode
        )
    }

    @Test
    fun test_constructor_fraction_digits_2() {
        val countryCode = "USD"
        val countryCurrency = Currency.getInstance(Locale.US)

        assertEquals(
            "0.00",
            Money(
                0,
                countryCode
            ).amount.toString()
        )
        assertEquals(
            "0.00",
            Money(
                0,
                countryCurrency
            ).amount.toString()
        )
        assertEquals(
            "0.00",
            Money(
                -0,
                countryCode
            ).amount.toString()
        )
        assertEquals(
            "0.00",
            Money(
                -0,
                countryCurrency
            ).amount.toString()
        )

        assertEquals(
            "1234567.89",
            Money(
                123456789,
                countryCode
            ).amount.toString()
        )
        assertEquals(
            "1234567.89",
            Money(
                123456789,
                countryCurrency
            ).amount.toString()
        )
        assertEquals(
            "-1234567.89",
            Money(
                -123456789,
                countryCode
            ).amount.toString()
        )
        assertEquals(
            "-1234567.89",
            Money(
                -123456789,
                countryCurrency
            ).amount.toString()
        )

        assertEquals(
            "123.00",
            Money(
                123.0,
                countryCode
            ).amount.toString()
        )
        assertEquals(
            "123.00",
            Money(
                123.0,
                countryCurrency
            ).amount.toString()
        )
        assertEquals(
            "-123.00",
            Money(
                -123.0,
                countryCode
            ).amount.toString()
        )
        assertEquals(
            "-123.00",
            Money(
                -123.0,
                countryCurrency
            ).amount.toString()
        )

        assertEquals(
            "123.45",
            Money(
                123.451,
                countryCode
            ).amount.toString()
        )
        assertEquals(
            "123.45",
            Money(
                123.451,
                countryCurrency
            ).amount.toString()
        )
        assertEquals(
            "-123.45",
            Money(
                -123.451,
                countryCode
            ).amount.toString()
        )
        assertEquals(
            "-123.45",
            Money(
                -123.451,
                countryCurrency
            ).amount.toString()
        )
        assertEquals(
            "123.46",
            Money(
                123.455,
                countryCode
            ).amount.toString()
        )
        assertEquals(
            "123.46",
            Money(
                123.455,
                countryCurrency
            ).amount.toString()
        )
        assertEquals(
            "-123.46",
            Money(
                -123.455,
                countryCode
            ).amount.toString()
        )
        assertEquals(
            "-123.46",
            Money(
                -123.455,
                countryCurrency
            ).amount.toString()
        )
    }

    @Test
    fun test_constructor_fraction_digits_0() {
        val countryCode = "JPY"
        val countryCurrency = Currency.getInstance(Locale.JAPAN)

        assertEquals(
            "0",
            Money(
                0,
                countryCode
            ).amount.toString()
        )
        assertEquals(
            "0",
            Money(
                0,
                countryCurrency
            ).amount.toString()
        )
        assertEquals(
            "0",
            Money(
                -0,
                countryCode
            ).amount.toString()
        )
        assertEquals(
            "0",
            Money(
                -0,
                countryCurrency
            ).amount.toString()
        )

        assertEquals(
            "123456789",
            Money(
                123456789,
                countryCode
            ).amount.toString()
        )
        assertEquals(
            "123456789",
            Money(
                123456789,
                countryCurrency
            ).amount.toString()
        )
        assertEquals(
            "-123456789",
            Money(
                -123456789,
                countryCode
            ).amount.toString()
        )
        assertEquals(
            "-123456789",
            Money(
                -123456789,
                countryCurrency
            ).amount.toString()
        )

        assertEquals(
            "123",
            Money(
                123.0,
                countryCode
            ).amount.toString()
        )
        assertEquals(
            "123",
            Money(
                123.0,
                countryCurrency
            ).amount.toString()
        )
        assertEquals(
            "-123",
            Money(
                -123.0,
                countryCode
            ).amount.toString()
        )
        assertEquals(
            "-123",
            Money(
                -123.0,
                countryCurrency
            ).amount.toString()
        )

        assertEquals(
            "123",
            Money(
                123.451,
                countryCode
            ).amount.toString()
        )
        assertEquals(
            "123",
            Money(
                123.451,
                countryCurrency
            ).amount.toString()
        )
        assertEquals(
            "-123",
            Money(
                -123.451,
                countryCode
            ).amount.toString()
        )
        assertEquals(
            "-123",
            Money(
                -123.451,
                countryCurrency
            ).amount.toString()
        )
        assertEquals(
            "124",
            Money(
                123.551,
                countryCode
            ).amount.toString()
        )
        assertEquals(
            "124",
            Money(
                123.551,
                countryCurrency
            ).amount.toString()
        )
        assertEquals(
            "-124",
            Money(
                -123.551,
                countryCode
            ).amount.toString()
        )
        assertEquals(
            "-124",
            Money(
                -123.551,
                countryCurrency
            ).amount.toString()
        )
    }

    @Test
    fun test_equality() {
        assertTrue {
            Money(
                0,
                "USD"
            ) == Money(
                0,
                "USD"
            )
        }
        assertTrue {
            Money(
                12345678900,
                "USD"
            ) == Money(
                123456789.00,
                "USD"
            )
        }
        assertTrue {
            Money(
                123456789,
                "JPY"
            ) == Money(
                123456789.00,
                "JPY"
            )
        }
        assertTrue {
            Money(
                1,
                "JPY"
            ) == Money(
                1.1,
                "JPY"
            )
        }
        assertTrue {
            Money(
                123.45,
                "USD"
            ) == Money(
                123.451,
                "USD"
            )
        }
        assertTrue {
            Money(
                123.46,
                "USD"
            ) == Money(
                123.456,
                "USD"
            )
        }
        assertTrue {
            Money(
                1,
                "JPY"
            ) == Money(
                1.1,
                "JPY"
            )
        }
        assertTrue {
            Money(
                1,
                "JPY"
            ) == Money(
                1.4,
                "JPY"
            )
        }
        assertTrue {
            Money(
                2,
                "JPY"
            ) == Money(
                1.5,
                "JPY"
            )
        }
        assertFalse {
            Money(
                0,
                "JPY"
            ) == Money(
                0,
                "USD"
            )
        }

        assertEquals(
            Money(
                0,
                "USD"
            ),
            Money(0, "USD")
        )
        assertEquals(
            Money(
                12345678900,
                "USD"
            ),
            Money(
                123456789.00,
                "USD"
            )
        )
        assertEquals(
            Money(
                123456789,
                "JPY"
            ),
            Money(
                123456789.00,
                "JPY"
            )
        )
        assertEquals(
            Money(
                1,
                "JPY"
            ),
            Money(
                1.1,
                "JPY"
            )
        )
        assertEquals(
            Money(
                123.45,
                "USD"
            ),
            Money(
                123.451,
                "USD"
            )
        )
        assertEquals(
            Money(
                123.46,
                "USD"
            ),
            Money(
                123.456,
                "USD"
            )
        )
        assertEquals(
            Money(
                1,
                "JPY"
            ),
            Money(
                1.1,
                "JPY"
            )
        )
        assertEquals(
            Money(
                1,
                "JPY"
            ),
            Money(
                1.4,
                "JPY"
            )
        )
        assertEquals(
            Money(
                2,
                "JPY"
            ),
            Money(
                1.5,
                "JPY"
            )
        )
        assertNotEquals(
            Money(
                0,
                "JPY"
            ),
            Money(0, "USD")
        )
    }

    @Test
    fun test_operator() {
        val JYP__3 = Money(
            -3,
            "JPY"
        )
        val JYP_0 =
            Money(0, "JPY")
        val JYP_1 =
            Money(1, "JPY")
        val JYP_2 =
            Money(2, "JPY")
        val JYP_3 =
            Money(3, "JPY")
        val JYP_4 =
            Money(4, "JPY")
        val JYP_5 =
            Money(5, "JPY")
        val JYP_6 =
            Money(6, "JPY")
        val JYP_7 =
            Money(7, "JPY")
        val JYP_8 =
            Money(8, "JPY")
        val JYP_9 =
            Money(9, "JPY")
        val JYP_10 = Money(
            10,
            "JPY"
        )

        val USD__3 = Money(
            -300,
            "USD"
        )
        val USD_0 =
            Money(0, "USD")
        val USD_1 = Money(
            100,
            "USD"
        )
        val USD_1_1 =
            Money(
                110,
                "USD"
            )
        val USD_1_11 =
            Money(
                111,
                "USD"
            )
        val USD_1_12 =
            Money(
                112,
                "USD"
            )
        val USD_2 = Money(
            200,
            "USD"
        )
        val USD_3 = Money(
            300,
            "USD"
        )
        val USD_3_3 =
            Money(
                330,
                "USD"
            )
        val USD_3_33 =
            Money(
                333,
                "USD"
            )
        val USD_3_34 =
            Money(
                334,
                "USD"
            )
        val USD_4 = Money(
            400,
            "USD"
        )
        val USD_5 = Money(
            500,
            "USD"
        )
        val USD_6 = Money(
            600,
            "USD"
        )
        val USD_6_6 =
            Money(
                660,
                "USD"
            )
        val USD_6_66 =
            Money(
                666,
                "USD"
            )
        val USD_7 = Money(
            700,
            "USD"
        )
        val USD_8 = Money(
            800,
            "USD"
        )
        val USD_9 = Money(
            900,
            "USD"
        )
        val USD_9_99 =
            Money(
                999,
                "USD"
            )
        val USD_10 = Money(
            1000,
            "USD"
        )

        assertTrue { JYP_3 == -JYP__3 }
        assertTrue { USD_3 == -USD__3 }

        assertEquals(JYP_1 + JYP_2, JYP_3)
        assertEquals(USD_1 + USD_2, USD_3)
        assertEquals(USD_3_3 + USD_3_3, USD_6_6)

        assertEquals(USD_3_3 - USD_3_3, USD_0)
        assertEquals(USD_3 - USD_6, USD__3)
        assertEquals(USD_3_3 - USD_3_3, USD_0)

        assertEquals(USD_0 * -3, USD_0)
        assertEquals(USD_0 * 5, USD_0)
        assertEquals(USD_1 * 5, USD_5)
        assertEquals(USD_2 * 5, USD_10)
        assertEquals(USD__3 * -3, USD_9)
        assertEquals(USD__3 * 1, USD__3)
        assertEquals(USD_3 * 3.33, USD_9_99)
        assertEquals(USD_3 * 3.333, USD_10)
        assertEquals(USD_1 * 1, USD_1)
        assertEquals(USD_1 * 1.1, USD_1_1)
        assertEquals(USD_1 * 1.11, USD_1_11)
        assertEquals(USD_1 * 1.111, USD_1_11)
        assertEquals(JYP_1 * 1, JYP_1)
        assertEquals(JYP_1 * 1.1, JYP_1)

        assertEquals(USD_0 / -3, USD_0)
        assertEquals(USD_0 / 5, USD_0)
        assertEquals(USD_9 / -3, USD__3)
        assertEquals(USD__3 / 1, USD__3)
        assertEquals(USD_9_99 / 3.33, USD_3)
        assertEquals(USD_10 / 3, USD_3_33)
        assertEquals(USD_1 / 1, USD_1)
        assertEquals(JYP_10 / 2, JYP_5)
        assertEquals(JYP_10 / 3, JYP_3)
    }

    @Test
    fun test_string2Double() {
        val countryCode = "USD"

        assertEquals(
            "0.00",
            Money(
                "0.".toDouble(),
                countryCode
            ).amount.toString()
        )
        assertEquals(
            "1.00",
            Money(
                "0001.".toDouble(),
                countryCode
            ).amount.toString()
        )
        assertEquals(
            "0.10",
            Money(
                ".10".toDouble(),
                countryCode
            ).amount.toString()
        )
        assertEquals(
            "-0.10",
            Money(
                "-.1".toDouble(),
                countryCode
            ).amount.toString()
        )
        assertEquals(
            "-0.10",
            Money(
                "-0.1".toDouble(),
                countryCode
            ).amount.toString()
        )
        assertEquals(
            "1.00",
            Money(
                "+1.".toDouble(),
                countryCode
            ).amount.toString()
        )

        try {
            Money(
                "".toDouble(),
                countryCode
            ).amount.toString()
            fail()
        } catch (e: NumberFormatException) {
        }

        try {
            Money(
                "+".toDouble(),
                countryCode
            ).amount.toString()
            fail()
        } catch (e: NumberFormatException) {
        }

        try {
            Money(
                "-".toDouble(),
                countryCode
            ).amount.toString()
            fail()
        } catch (e: NumberFormatException) {
        }

        try {
            Money(
                ".".toDouble(),
                countryCode
            ).amount.toString()
            fail()
        } catch (e: NumberFormatException) {
        }
    }
}
