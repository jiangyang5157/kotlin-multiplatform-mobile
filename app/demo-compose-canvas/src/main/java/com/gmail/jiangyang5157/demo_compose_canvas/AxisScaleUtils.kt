package com.gmail.jiangyang5157.demo_compose_canvas

import androidx.annotation.IntRange
import kotlin.math.ceil

class AxisScaleUtils {

    enum class RoundUpScale {
        Integer, Ten, Hundred, Thousand, Million, Billion;

        /**
         * Return roundup scale
         */
        fun apply(value: Double): Long {
            if (value < 0.0) throw IllegalArgumentException("Value $value should be not less than 0")

            return when (this) {
                Integer -> ceil(value)
                Ten -> ceil(value / 10) * 10
                Hundred -> ceil(value / 100) * 100
                Thousand -> ceil(value / 1000) * 1000
                Million -> ceil(value / 1000000) * 1000000
                Billion -> ceil(value / 1000000000) * 1000000000
            }.toLong()
        }

        companion object {

            /**
             * Choice expected [RoundUpScale] to [apply] the [value], return next value
             */
            fun roundUp(value: Double): Long {
                return when {
                    value == 0.0 -> 0
                    value > 0 && value <= 10 -> Integer.apply(value)
                    value > 10 && value <= 100 -> Ten.apply(value)
                    value > 100 && value <= 1000 -> Hundred.apply(value)
                    value > 1000 && value <= 1000000.0 -> Thousand.apply(value)
                    value > 1000000 && value <= 1000000000.0 -> Million.apply(value)
                    value > 1000000000 -> Billion.apply(value)
                    else -> throw IllegalArgumentException("Unexpected value $value")
                }
            }
        }
    }

    /**
     * Return Long divisor >= 1
     */
    fun factorToDivisor(value: Long, @IntRange(from = 1, to = 9) factor: Int): Long {
        if (value < 0) throw IllegalArgumentException("Value $value should be not less than 0")
        if (factor < 1 || factor > 9) throw IllegalArgumentException("Factor $factor should be in Int range [1, 9]")

        return when {
            value >= 0 && value <= 10 -> factor
            value > 10 && value <= 100 -> factor * 10
            value > 100 && value <= 1000 -> factor * 100
            value > 1000 && value <= 1000000 -> factor * 1000
            value > 1000000 && value <= 1000000000 -> factor * 1000000
            else -> throw IllegalArgumentException("Unexpected value $value")
        }.toLong()
    }

    /**
     * Use [divisor] from [factorToDivisor], not useful for others
     * Return Int factor in range [1, 9]
     */
    fun divisorToFactor(value: Long, divisor: Long): Int {
        if (value < 0) throw IllegalArgumentException("Value $value should be not less than 0")
        if (divisor < 1) throw IllegalArgumentException("Divisor $divisor should be not less than 1")

        return when {
            // expecting divisor in range [1, 9] with step 1
            value >= 0 && value <= 10 -> divisor
            // expecting divisor in range [10, 90] with step 10
            value > 10 && value <= 100 -> if (divisor > 10) {
                divisor / 10
            } else {
                divisor
            }
            // expecting divisor in range [100, 900] with step 100
            value > 100 && value <= 1000 -> if (divisor > 100) {
                divisor / 100
            } else {
                divisor / 10
            }
            // expecting divisor in range [1000, 900000] with step 1000
            value > 1000 && value <= 1000000 -> if (divisor > 1000) {
                divisor / 1000
            } else {
                divisor / 100
            }
            // expecting divisor in range [1000000, 900000000] with step 1000000
            value > 1000000 && value <= 1000000000 -> if (divisor > 1000000) {
                divisor / 1000000
            } else {
                divisor / 1000
            }
            // expecting divisor large than 1000000000 with step 1000000000
            value > 1000000000 -> if (divisor > 1000000000) {
                divisor / 1000000000
            } else {
                divisor / 1000000
            }
            else -> throw IllegalArgumentException("Unexpected value $value")
        }.toInt()
    }

    /**
     * Round up value and make it divisible by the divisor with no remainders.
     * Return the new value.
     */
    fun roundUpForDivision(value: Long, divisor: Long): Long {
        if (value < 0) throw IllegalArgumentException("Value $value should be not less than 0")
        if (divisor < 1) throw IllegalArgumentException("Divisor $divisor should be not less than 1")

        if (value == 0L) return divisor

        val remainders = value % divisor
        return if (remainders == 0L) {
            value
        } else {
            value + (divisor - remainders)
        }
    }

    /**
     * Round up value and make it divisible by either [primaryDivisor] or [secondaryDivisor] with no remainders.
     * Return the smaller new value and the divisor was used.
     */
    fun roundUpForDivision(
        value: Long, primaryDivisor: Long, secondaryDivisor: Long
    ): Pair<Long, Long> {
        if (value < 0) throw IllegalArgumentException("Value $value should be not less than 0")
        if (primaryDivisor < 1) throw IllegalArgumentException("Divisor $primaryDivisor should be not less than 1")
        if (secondaryDivisor < 1) throw IllegalArgumentException("Divisor $secondaryDivisor should be not less than 1")

        if (value == 0L) return if (primaryDivisor <= secondaryDivisor) {
            Pair(primaryDivisor, primaryDivisor)
        } else {
            Pair(secondaryDivisor, secondaryDivisor)
        }

        val primaryMoney = roundUpForDivision(value, primaryDivisor)
        val secondaryMoney = roundUpForDivision(value, secondaryDivisor)
        return if (primaryMoney <= secondaryMoney) {
            Pair(primaryMoney, primaryDivisor)
        } else {
            Pair(secondaryMoney, secondaryDivisor)
        }
    }

    fun buildScaleList(value: Long, @IntRange(from = 1, to = 9) factor: Int): List<Long> {
        if (value < 0) throw IllegalArgumentException("Value $value should be not less than 0")
        if (factor < 1 || factor > 9) throw IllegalArgumentException("Factor $factor should be in Int range [1, 9]")

        return if (value == 0L) {
            List(factor + 1) { it.toLong() }
        } else {
            val step = value / factor
            LongRange(0, value).step(step).toList()
        }
    }
}