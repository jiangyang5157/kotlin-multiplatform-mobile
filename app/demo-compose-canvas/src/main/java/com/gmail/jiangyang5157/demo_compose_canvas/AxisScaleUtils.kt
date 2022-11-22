package com.gmail.jiangyang5157.demo_compose_canvas

import kotlin.math.ceil

/**
 * Build expected scale list on the value for column visualization.
 * Example:
 * 567.89 --> [0, 200, 400, 600]
 * 1234.56789 --> [0, 1000, 2000, 3000]
 *
 * https://pl.kotl.in/MvKoH1wIQ
 */
class AxisScaleUtils {

    enum class RoundUpScale {
        Integer, Ten, Hundred, Thousand, Million, Billion;

        /**
         * Apply scale roundup on [value]
         *
         * Examples of Integer: value --> next value
         * 0.0 --> 0
         * 0.1 --> 1
         * 1.1 --> 2
         * 4.1 --> 5
         * 9.0 --> 9
         * 9.1 --> 10
         * 555555555.5 --> 555555556
         * 999999999999.9 --> 1000000000000
         *
         * Examples of Ten: value --> next value
         * 0.0 --> 0
         * 0.1 --> 10
         * 10.1 --> 20
         * 40.1 --> 50
         * 90.0 --> 90
         * 90.1 --> 100
         * 555555555.5 --> 555555560
         * 999999999999.9 --> 1000000000000
         *
         * Examples of Hundred: value --> next value
         * 0.0 --> 0
         * 0.1 --> 100
         * 100.1 --> 200
         * 400.1 --> 500
         * 900.0 --> 900
         * 900.1 --> 1000
         * 555555555.5 --> 555555600
         * 999999999999.9 --> 1000000000000
         *
         * Examples of Thousand: value --> next value
         * 0.0 --> 0
         * 0.1 --> 1000
         * 1000.1 --> 2000
         * 4000.1 --> 5000
         * 9000.0 --> 9000
         * 9000.1 --> 10000
         * 555555555.5 --> 555556000
         * 999999999999.9 --> 1000000000000
         *
         * Examples of Million: value --> next value
         * 0.0 --> 0
         * 0.1 --> 1000000
         * 1000000.1 --> 2000000
         * 4000000.1 --> 5000000
         * 9000000.0 --> 9000000
         * 9000000.1 --> 10000000
         * 555555555.5 --> 556000000
         * 999999999999.9 --> 1000000000000
         *
         * Examples of Billion: value --> next value
         * 0.0 --> 0
         * 0.1 --> 1000000000
         * 1000000000.1 --> 2000000000
         * 4000000000.1 --> 5000000000
         * 9000000000.0 --> 9000000000
         * 9000000000.1 --> 10000000000
         * 555555555.5 --> 1000000000
         * 999999999999.9 --> 1000000000000
         *
         * @throws IllegalArgumentException
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
             * Apply expected [RoundUpScale], return next value
             * @throws IllegalArgumentException
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
     * Return Long divisor greater than 0
     *
     * Examples: value, factor --> divisor
     * 1, 2 --> 2
     * 12, 2 --> 20,
     * 123, 2 --> 200
     * 1234, 2 --> 2000
     * 12345, 2 --> 20000
     * 123456, 2 --> 200000
     * 1234567, 2 --> 2000000
     * 12345678, 2 --> 20000000
     * 123456789, 2 --> 200000000
     *
     * @param factor in Int range [1, 9]
     * @throws IllegalArgumentException
     */
    fun factorToDivisor(value: Long, factor: Int): Long {
        if (value < 0) throw IllegalArgumentException("Value $value should be not less than 0")
        if (factor < 1 || factor > 9) throw IllegalArgumentException("Factor $factor should be in Int range [1, 9]")

        return when {
            value >= 0 && value <= 10 -> factor
            value > 10 && value <= 100 -> factor * 10
            value > 100 && value <= 1000 -> factor * 100
            value > 1000 && value <= 10000 -> factor * 1000
            value > 10000 && value <= 100000 -> factor * 10000
            value > 100000 && value <= 1000000 -> factor * 100000
            value > 1000000 && value <= 10000000 -> factor * 1000000
            value > 10000000 && value <= 100000000 -> factor * 10000000
            value > 100000000 && value <= 1000000000 -> factor * 100000000
            else -> throw IllegalArgumentException("Unexpected value $value")
        }.toLong()
    }

    /**
     * Return Int factor in range [1, 9]
     *
     * Examples: value, divisor --> factor
     * 1, 2 --> 2
     * 12, 20 --> 2,
     * 123, 200 --> 2
     * 1234, 2000 --> 2
     * 12345, 20000 --> 2
     * 123456, 200000 --> 2
     * 1234567, 2000000 --> 2
     * 12345678, 20000000 --> 2
     * 123456789, 200000000 --> 2
     *
     * @param divisor greater than 0. Expecting it came from [factorToDivisor], not quite useful for others.
     * @throws IllegalArgumentException
     */
    fun divisorToFactor(value: Long, divisor: Long): Int {
        if (value < 0) throw IllegalArgumentException("Value $value should be not less than 0")
        if (divisor < 1) throw IllegalArgumentException("Divisor $divisor should be not less than 1")

        return when {
            value >= 0 && value <= 10 -> divisor
            value > 10 && value <= 100 -> if (divisor > 10) divisor / 10 else divisor
            value > 100 && value <= 1000 -> if (divisor > 100) divisor / 100 else divisor / 10
            value > 1000 && value <= 10000 -> if (divisor > 1000) divisor / 1000 else divisor / 100
            value > 10000 && value <= 100000 -> if (divisor > 10000) divisor / 10000 else divisor / 1000
            value > 100000 && value <= 1000000 -> if (divisor > 100000) divisor / 100000 else divisor / 10000
            value > 1000000 && value <= 10000000 -> if (divisor > 1000000) divisor / 1000000 else divisor / 100000
            value > 10000000 && value <= 100000000 -> if (divisor > 10000000) divisor / 10000000 else divisor / 1000000
            value > 100000000 && value <= 1000000000 -> if (divisor > 100000000) divisor / 100000000 else divisor / 10000000
            value > 1000000000 -> if (divisor > 1000000000) divisor / 1000000000 else divisor / 100000000
            else -> throw IllegalArgumentException("Unexpected value $value")
        }.toInt()
    }

    /**
     * Round up to next value and make it divisible by the divisor with no remainders.
     *
     * Examples: value, divisor --> next value
     * 0, 3 --> 3
     * 1, 3 --> 3
     * 2, 3 --> 3
     * 3, 3 --> 3
     * 4, 3 --> 6
     * 5, 3 --> 6
     * 6, 3 --> 6
     * 7, 3 --> 9
     * 8, 3 --> 9
     * 9, 3 --> 9
     * 10, 3 --> 12
     *
     * @throws IllegalArgumentException
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
     * Round up to next value and make it divisible by either [primaryDivisor] or [secondaryDivisor] with no remainders.
     * Return the smaller new value and the divisor was used.
     *
     * Examples: value, primaryDivisor, secondaryDivisor --> (next value, chosen divisor)
     * 0, 3, 2 --> (2, 2)
     * 1, 3, 2 --> (2, 2)
     * 2, 3, 2 --> (2, 2)
     * 3, 3, 2 --> (3, 3)
     * 4, 3, 2 --> (4, 2)
     * 5, 3, 2 --> (6, 3)
     * 6, 3, 2 --> (6, 3)
     * 7, 3, 2 --> (8, 2)
     * 8, 3, 2 --> (8, 2)
     * 9, 3, 2 --> (9, 3)
     *
     * @throws IllegalArgumentException
     */
    fun roundUpForDivision(
        value: Long,
        primaryDivisor: Long,
        secondaryDivisor: Long,
    ): Pair<Long, Long> {
        if (value < 0) throw IllegalArgumentException("Value $value should be not less than 0")
        if (primaryDivisor < 1) throw IllegalArgumentException("Divisor $primaryDivisor should be not less than 1")
        if (secondaryDivisor < 1) throw IllegalArgumentException("Divisor $secondaryDivisor should be not less than 1")

        if (value == 0L) return if (primaryDivisor <= secondaryDivisor) {
            Pair(primaryDivisor, primaryDivisor)
        } else {
            Pair(secondaryDivisor, secondaryDivisor)
        }

        val primaryDivision = roundUpForDivision(value, primaryDivisor)
        val secondaryDivision = roundUpForDivision(value, secondaryDivisor)
        return if (primaryDivision <= secondaryDivision) {
            Pair(primaryDivision, primaryDivisor)
        } else {
            Pair(secondaryDivision, secondaryDivisor)
        }
    }

    /**
     * Build expected scale list on the [value]
     *
     * Examples: value, factor --> scale list
     * 0, 3 --> [0, 1, 2, 3]
     * 5, 2 --> [0, 3, 6]
     * 5, 3 --> [0, 2, 4, 6]
     * 60, 3 --> [0, 20, 40, 60]
     *
     * @throws IllegalArgumentException
     */
    fun buildScaleList(value: Long, factor: Int): List<Long> {
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