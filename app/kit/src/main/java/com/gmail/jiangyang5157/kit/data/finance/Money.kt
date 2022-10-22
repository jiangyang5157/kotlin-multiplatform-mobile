package com.gmail.jiangyang5157.kit.data.finance

import java.math.BigDecimal
import java.math.RoundingMode
import java.util.*

/**
 * Created by Yang Jiang on January 22, 2019
 *
 * Apply ISO 4217 code
 * Apply default fraction digits associated with the currency
 * Apply java.math.RoundingMode.HALF_UP as default
 */
data class Money(
    val amount: BigDecimal,
    val currency: Currency = Currency.getInstance(Locale.getDefault()),
    val roundingMode: RoundingMode = RoundingMode.HALF_UP,
) {

    constructor(
        amount: Long,
        currency: Currency = Currency.getInstance(Locale.getDefault()),
        roundingMode: RoundingMode = RoundingMode.HALF_UP
    ) :
            this(BigDecimal.valueOf(amount, currency.defaultFractionDigits), currency, roundingMode)

    constructor(
        amount: Long,
        currencyCode: String,
        roundingMode: RoundingMode = RoundingMode.HALF_UP
    ) :
            this(amount, Currency.getInstance(currencyCode), roundingMode)

    constructor(
        amount: Double,
        currency: Currency = Currency.getInstance(Locale.getDefault()),
        roundingMode: RoundingMode = RoundingMode.HALF_UP
    ) :
            this(
                BigDecimal.valueOf(amount).setScale(currency.defaultFractionDigits, roundingMode),
                currency,
                roundingMode
            )

    constructor(
        amount: Double,
        currencyCode: String,
        roundingMode: RoundingMode = RoundingMode.HALF_UP
    ) :
            this(amount, Currency.getInstance(currencyCode), roundingMode)

    operator fun unaryMinus(): Money =
        Money(
            -amount,
            currency,
            roundingMode
        )

    @Throws(IllegalArgumentException::class)
    operator fun plus(other: Money): Money {
        if (currency != other.currency) {
            throw IllegalArgumentException("Same currency is required.")
        }
        return Money(
            amount.add(
                other.amount
            ),
            currency, roundingMode
        )
    }

    @Throws(IllegalArgumentException::class)
    operator fun minus(other: Money): Money {
        if (currency != other.currency) {
            throw IllegalArgumentException("Same currency is required.")
        }
        return Money(
            amount.subtract(
                other.amount
            ),
            currency, roundingMode
        )
    }

    operator fun times(n: Long): Money {
        return Money(
            amount.multiply(BigDecimal.valueOf(n)).setScale(
                currency.defaultFractionDigits,
                RoundingMode.HALF_UP
            ),
            currency, roundingMode
        )
    }

    operator fun times(n: Double): Money {
        return Money(
            amount.multiply(BigDecimal.valueOf(n)).setScale(
                currency.defaultFractionDigits,
                RoundingMode.HALF_UP
            ),
            currency, roundingMode
        )
    }

    operator fun div(n: Long): Money {
        return Money(
            amount.divide(
                BigDecimal.valueOf(n),
                currency.defaultFractionDigits,
                RoundingMode.HALF_UP
            ),
            currency, roundingMode
        )
    }

    operator fun div(n: Double): Money {
        return Money(
            amount.divide(
                BigDecimal.valueOf(n),
                currency.defaultFractionDigits,
                RoundingMode.HALF_UP
            ),
            currency, roundingMode
        )
    }
}
