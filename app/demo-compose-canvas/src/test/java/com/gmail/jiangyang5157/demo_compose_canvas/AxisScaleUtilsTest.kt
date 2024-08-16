package com.gmail.jiangyang5157.demo_compose_canvas

import com.gmail.jiangyang5157.demo_compose_canvas.AxisScaleUtils.RoundUpScale
import org.junit.Assert
import org.junit.Test

class AxisScaleUtilsTest {

    @Test
    fun RoundUpScale_apply_unexpected_value_throw_exception() {
        val value = -0.1
        try {
            RoundUpScale.Integer.apply(value)
            Assert.fail()
        } catch (_: Exception) {
        }
    }

    @Test
    fun RoundUpScale_Integer_apply_0_999_999_999_999_9() {
        val valueAndResult = listOf<Pair<Double, Long>>(
            Pair(0.0, 0),
            Pair(0.1, 1),
            Pair(1.1, 2),
            Pair(4.1, 5),
            Pair(9.0, 9),
            Pair(9.1, 10),
            Pair(555555555.5, 555555556),
            Pair(999999999999.9, 1000000000000),
        )
        valueAndResult.forEach {
            Assert.assertEquals(it.second, RoundUpScale.Integer.apply(it.first))
        }
    }

    @Test
    fun RoundUpScale_Ten_apply_0_999_999_999_999_9() {
        val valueAndResult = listOf<Pair<Double, Long>>(
            Pair(0.0, 0),
            Pair(0.1, 10),
            Pair(10.1, 20),
            Pair(40.1, 50),
            Pair(90.0, 90),
            Pair(90.1, 100),
            Pair(555555555.5, 555555560),
            Pair(999999999999.9, 1000000000000),
        )
        valueAndResult.forEach {
            Assert.assertEquals(it.second, RoundUpScale.Ten.apply(it.first))
        }
    }

    @Test
    fun RoundUpScale_Hundred_apply_0_999_999_999_999_9() {
        val valueAndResult = listOf<Pair<Double, Long>>(
            Pair(0.0, 0),
            Pair(0.1, 100),
            Pair(100.1, 200),
            Pair(400.1, 500),
            Pair(900.0, 900),
            Pair(900.1, 1000),
            Pair(555555555.5, 555555600),
            Pair(999999999999.9, 1000000000000),
        )
        valueAndResult.forEach {
            Assert.assertEquals(it.second, RoundUpScale.Hundred.apply(it.first))
        }
    }

    @Test
    fun RoundUpScale_Thousand_apply_0_999_999_999_999_9() {
        val valueAndResult = listOf<Pair<Double, Long>>(
            Pair(0.0, 0),
            Pair(0.1, 1000),
            Pair(1000.1, 2000),
            Pair(4000.1, 5000),
            Pair(9000.0, 9000),
            Pair(9000.1, 10000),
            Pair(555555555.5, 555556000),
            Pair(999999999999.9, 1000000000000),
        )
        valueAndResult.forEach {
            Assert.assertEquals(it.second, RoundUpScale.Thousand.apply(it.first))
        }
    }

    @Test
    fun RoundUpScale_Million_apply_0_999_999_999_999_9() {
        val valueAndResult = listOf<Pair<Double, Long>>(
            Pair(0.0, 0),
            Pair(0.1, 1000000),
            Pair(1000000.1, 2000000),
            Pair(4000000.1, 5000000),
            Pair(9000000.0, 9000000),
            Pair(9000000.1, 10000000),
            Pair(555555555.5, 556000000),
            Pair(999999999999.9, 1000000000000),
        )
        valueAndResult.forEach {
            Assert.assertEquals(it.second, RoundUpScale.Million.apply(it.first))
        }
    }

    @Test
    fun RoundUpScale_Billion_apply_0_999_999_999_999_9() {
        val valueAndResult = listOf<Pair<Double, Long>>(
            Pair(0.0, 0),
            Pair(0.1, 1000000000),
            Pair(1000000000.1, 2000000000),
            Pair(4000000000.1, 5000000000),
            Pair(9000000000.0, 9000000000),
            Pair(9000000000.1, 10000000000),
            Pair(555555555.5, 1000000000),
            Pair(999999999999.9, 1000000000000),
        )
        valueAndResult.forEach {
            Assert.assertEquals(it.second, RoundUpScale.Billion.apply(it.first))
        }
    }

    @Test
    fun AxisScaleUtils_roundUp_0_999_999_999_999_9() {
        val valueAndResult = listOf<Pair<Double, Long>>(
            Pair(0.0, 0),
            Pair(0.1, 1),
            Pair(1.1, 2),
            Pair(4.1, 5),
            Pair(9.0, 9),
            Pair(9.1, 10),
            Pair(10.0, 10),

            Pair(10.1, 20),
            Pair(40.1, 50),
            Pair(90.0, 90),
            Pair(90.1, 100),
            Pair(100.0, 100),

            Pair(100.1, 200),
            Pair(400.1, 500),
            Pair(900.0, 900),
            Pair(900.1, 1000),
            Pair(1000.0, 1000),

            Pair(1000.1, 2000),
            Pair(4000.1, 5000),
            Pair(9000.0, 9000),
            Pair(9000.1, 10000),
            Pair(100000.0, 100000),

            Pair(1000000.0, 1000000),
            Pair(1000000.1, 2000000),
            Pair(4000000.1, 5000000),
            Pair(9000000.0, 9000000),
            Pair(9000000.1, 10000000),
            Pair(555555555.5, 556000000),

            Pair(1000000000.0, 1000000000),
            Pair(1000000000.1, 2000000000),
            Pair(999999999999.9, 1000000000000),
        )
        valueAndResult.forEach {
            Assert.assertEquals(it.second, RoundUpScale.roundUp(it.first))
        }
    }

    @Test
    fun factorToDivisor_unexpected_value_throw_exception() {
        val value = -1L
        try {
            AxisScaleUtils().factorToDivisor(value, 1)
            Assert.fail()
        } catch (_: Exception) {
        }
    }

    @Test
    fun factorToDivisor_unexpected_factor_throw_exception() {
        val factor1 = 0
        val factor2 = 10
        try {
            AxisScaleUtils().factorToDivisor(1, factor1)
            Assert.fail()
        } catch (_: Exception) {
        }
        try {
            AxisScaleUtils().factorToDivisor(1, factor2)
            Assert.fail()
        } catch (_: Exception) {
        }
    }

    @Test
    fun factorToDivisor_with_factor_1_value_0_1000000000() {
        Assert.assertEquals(1, AxisScaleUtils().factorToDivisor(0, 1))
        Assert.assertEquals(1, AxisScaleUtils().factorToDivisor(10, 1))
        Assert.assertEquals(10, AxisScaleUtils().factorToDivisor(100, 1))
        Assert.assertEquals(100, AxisScaleUtils().factorToDivisor(1000, 1))
        Assert.assertEquals(1000, AxisScaleUtils().factorToDivisor(10000, 1))
        Assert.assertEquals(10000, AxisScaleUtils().factorToDivisor(100000, 1))
        Assert.assertEquals(100000, AxisScaleUtils().factorToDivisor(1000000, 1))
        Assert.assertEquals(1000000, AxisScaleUtils().factorToDivisor(10000000, 1))
        Assert.assertEquals(10000000, AxisScaleUtils().factorToDivisor(100000000, 1))
        Assert.assertEquals(100000000, AxisScaleUtils().factorToDivisor(1000000000, 1))

        Assert.assertEquals(1, AxisScaleUtils().factorToDivisor(1, 1))
        Assert.assertEquals(10, AxisScaleUtils().factorToDivisor(12, 1))
        Assert.assertEquals(100, AxisScaleUtils().factorToDivisor(123, 1))
        Assert.assertEquals(1000, AxisScaleUtils().factorToDivisor(1234, 1))
        Assert.assertEquals(10000, AxisScaleUtils().factorToDivisor(12345, 1))
        Assert.assertEquals(100000, AxisScaleUtils().factorToDivisor(123456, 1))
        Assert.assertEquals(1000000, AxisScaleUtils().factorToDivisor(1234567, 1))
        Assert.assertEquals(10000000, AxisScaleUtils().factorToDivisor(12345678, 1))
        Assert.assertEquals(100000000, AxisScaleUtils().factorToDivisor(123456789, 1))
    }

    @Test
    fun factorToDivisor_with_factor_2_value_0_1000000000() {
        Assert.assertEquals(2, AxisScaleUtils().factorToDivisor(0, 2))
        Assert.assertEquals(2, AxisScaleUtils().factorToDivisor(10, 2))
        Assert.assertEquals(20, AxisScaleUtils().factorToDivisor(100, 2))
        Assert.assertEquals(200, AxisScaleUtils().factorToDivisor(1000, 2))
        Assert.assertEquals(2000, AxisScaleUtils().factorToDivisor(10000, 2))
        Assert.assertEquals(20000, AxisScaleUtils().factorToDivisor(100000, 2))
        Assert.assertEquals(200000, AxisScaleUtils().factorToDivisor(1000000, 2))
        Assert.assertEquals(2000000, AxisScaleUtils().factorToDivisor(10000000, 2))
        Assert.assertEquals(20000000, AxisScaleUtils().factorToDivisor(100000000, 2))
        Assert.assertEquals(200000000, AxisScaleUtils().factorToDivisor(1000000000, 2))

        Assert.assertEquals(2, AxisScaleUtils().factorToDivisor(1, 2))
        Assert.assertEquals(20, AxisScaleUtils().factorToDivisor(12, 2))
        Assert.assertEquals(200, AxisScaleUtils().factorToDivisor(123, 2))
        Assert.assertEquals(2000, AxisScaleUtils().factorToDivisor(1234, 2))
        Assert.assertEquals(20000, AxisScaleUtils().factorToDivisor(12345, 2))
        Assert.assertEquals(200000, AxisScaleUtils().factorToDivisor(123456, 2))
        Assert.assertEquals(2000000, AxisScaleUtils().factorToDivisor(1234567, 2))
        Assert.assertEquals(20000000, AxisScaleUtils().factorToDivisor(12345678, 2))
        Assert.assertEquals(200000000, AxisScaleUtils().factorToDivisor(123456789, 2))
    }

    @Test
    fun factorToDivisor_with_factor_9_value_0_1000000000() {
        Assert.assertEquals(9, AxisScaleUtils().factorToDivisor(0, 9))
        Assert.assertEquals(9, AxisScaleUtils().factorToDivisor(10, 9))
        Assert.assertEquals(90, AxisScaleUtils().factorToDivisor(100, 9))
        Assert.assertEquals(900, AxisScaleUtils().factorToDivisor(1000, 9))
        Assert.assertEquals(9000, AxisScaleUtils().factorToDivisor(10000, 9))
        Assert.assertEquals(90000, AxisScaleUtils().factorToDivisor(100000, 9))
        Assert.assertEquals(900000, AxisScaleUtils().factorToDivisor(1000000, 9))
        Assert.assertEquals(9000000, AxisScaleUtils().factorToDivisor(10000000, 9))
        Assert.assertEquals(90000000, AxisScaleUtils().factorToDivisor(100000000, 9))
        Assert.assertEquals(900000000, AxisScaleUtils().factorToDivisor(1000000000, 9))

        Assert.assertEquals(9, AxisScaleUtils().factorToDivisor(1, 9))
        Assert.assertEquals(90, AxisScaleUtils().factorToDivisor(12, 9))
        Assert.assertEquals(900, AxisScaleUtils().factorToDivisor(123, 9))
        Assert.assertEquals(9000, AxisScaleUtils().factorToDivisor(1234, 9))
        Assert.assertEquals(90000, AxisScaleUtils().factorToDivisor(12345, 9))
        Assert.assertEquals(900000, AxisScaleUtils().factorToDivisor(123456, 9))
        Assert.assertEquals(9000000, AxisScaleUtils().factorToDivisor(1234567, 9))
        Assert.assertEquals(90000000, AxisScaleUtils().factorToDivisor(12345678, 9))
        Assert.assertEquals(900000000, AxisScaleUtils().factorToDivisor(123456789, 9))
    }

    @Test
    fun roundUpForDivision_unexpected_value_throw_exception() {
        val value = -1L
        try {
            AxisScaleUtils().roundUpForDivision(value, 1)
            Assert.fail()
        } catch (_: Exception) {
        }
    }

    @Test
    fun roundUpForDivision_unexpected_divisor_throw_exception() {
        val divisor = 0L
        try {
            AxisScaleUtils().roundUpForDivision(1, divisor)
            Assert.fail()
        } catch (_: Exception) {
        }
    }

    @Test
    fun roundUpForDivision_with_divisor_3_30_3000_value_0_60000() {
        Assert.assertEquals(3, AxisScaleUtils().roundUpForDivision(0, 3))
        Assert.assertEquals(3, AxisScaleUtils().roundUpForDivision(1, 3))
        Assert.assertEquals(3, AxisScaleUtils().roundUpForDivision(2, 3))
        Assert.assertEquals(3, AxisScaleUtils().roundUpForDivision(3, 3))
        Assert.assertEquals(6, AxisScaleUtils().roundUpForDivision(4, 3))
        Assert.assertEquals(6, AxisScaleUtils().roundUpForDivision(5, 3))
        Assert.assertEquals(6, AxisScaleUtils().roundUpForDivision(6, 3))
        Assert.assertEquals(9, AxisScaleUtils().roundUpForDivision(7, 3))
        Assert.assertEquals(9, AxisScaleUtils().roundUpForDivision(8, 3))
        Assert.assertEquals(9, AxisScaleUtils().roundUpForDivision(9, 3))
        Assert.assertEquals(12, AxisScaleUtils().roundUpForDivision(10, 3))

        Assert.assertEquals(30, AxisScaleUtils().roundUpForDivision(10, 30))
        Assert.assertEquals(30, AxisScaleUtils().roundUpForDivision(11, 30))
        Assert.assertEquals(30, AxisScaleUtils().roundUpForDivision(29, 30))
        Assert.assertEquals(30, AxisScaleUtils().roundUpForDivision(30, 30))
        Assert.assertEquals(60, AxisScaleUtils().roundUpForDivision(31, 30))
        Assert.assertEquals(60, AxisScaleUtils().roundUpForDivision(60, 30))

        Assert.assertEquals(12000, AxisScaleUtils().roundUpForDivision(10000, 3000))
        Assert.assertEquals(12000, AxisScaleUtils().roundUpForDivision(11000, 3000))
        Assert.assertEquals(30000, AxisScaleUtils().roundUpForDivision(29000, 3000))
        Assert.assertEquals(30000, AxisScaleUtils().roundUpForDivision(30000, 3000))
        Assert.assertEquals(33000, AxisScaleUtils().roundUpForDivision(30001, 3000))
        Assert.assertEquals(33000, AxisScaleUtils().roundUpForDivision(32999, 3000))
        Assert.assertEquals(60000, AxisScaleUtils().roundUpForDivision(60000, 3000))
    }

    @Test
    fun roundUpForDivision_with_factor_3_2_30_20() {
        Assert.assertEquals(Pair<Long, Long>(2, 2), AxisScaleUtils().roundUpForDivision(0, 3, 2))
        Assert.assertEquals(Pair<Long, Long>(2, 2), AxisScaleUtils().roundUpForDivision(1, 3, 2))
        Assert.assertEquals(Pair<Long, Long>(2, 2), AxisScaleUtils().roundUpForDivision(2, 3, 2))
        Assert.assertEquals(Pair<Long, Long>(3, 3), AxisScaleUtils().roundUpForDivision(3, 3, 2))
        Assert.assertEquals(Pair<Long, Long>(4, 2), AxisScaleUtils().roundUpForDivision(4, 3, 2))
        Assert.assertEquals(Pair<Long, Long>(6, 3), AxisScaleUtils().roundUpForDivision(5, 3, 2))
        Assert.assertEquals(Pair<Long, Long>(6, 3), AxisScaleUtils().roundUpForDivision(6, 3, 2))
        Assert.assertEquals(Pair<Long, Long>(8, 2), AxisScaleUtils().roundUpForDivision(7, 3, 2))
        Assert.assertEquals(Pair<Long, Long>(8, 2), AxisScaleUtils().roundUpForDivision(8, 3, 2))
        Assert.assertEquals(Pair<Long, Long>(9, 3), AxisScaleUtils().roundUpForDivision(9, 3, 2))
        Assert.assertEquals(
            Pair<Long, Long>(10, 2),
            AxisScaleUtils().roundUpForDivision(10, 3, 2)
        )

        Assert.assertEquals(
            Pair<Long, Long>(20, 20),
            AxisScaleUtils().roundUpForDivision(10, 30, 20)
        )
        Assert.assertEquals(
            Pair<Long, Long>(20, 20),
            AxisScaleUtils().roundUpForDivision(11, 30, 20)
        )
        Assert.assertEquals(
            Pair<Long, Long>(30, 30),
            AxisScaleUtils().roundUpForDivision(29, 30, 20)
        )
        Assert.assertEquals(
            Pair<Long, Long>(30, 30),
            AxisScaleUtils().roundUpForDivision(30, 30, 20)
        )
        Assert.assertEquals(
            Pair<Long, Long>(40, 20),
            AxisScaleUtils().roundUpForDivision(31, 30, 20)
        )
        Assert.assertEquals(
            Pair<Long, Long>(60, 30),
            AxisScaleUtils().roundUpForDivision(60, 30, 20)
        )

        Assert.assertEquals(
            Pair<Long, Long>(10000, 2000),
            AxisScaleUtils().roundUpForDivision(10000, 3000, 2000)
        )
        Assert.assertEquals(
            Pair<Long, Long>(12000, 3000),
            AxisScaleUtils().roundUpForDivision(11000, 3000, 2000)
        )
        Assert.assertEquals(
            Pair<Long, Long>(30000, 3000),
            AxisScaleUtils().roundUpForDivision(29000, 3000, 2000)
        )
        Assert.assertEquals(
            Pair<Long, Long>(30000, 3000),
            AxisScaleUtils().roundUpForDivision(30000, 3000, 2000)
        )
        Assert.assertEquals(
            Pair<Long, Long>(32000, 2000),
            AxisScaleUtils().roundUpForDivision(30001, 3000, 2000)
        )
        Assert.assertEquals(
            Pair<Long, Long>(33000, 3000),
            AxisScaleUtils().roundUpForDivision(32999, 3000, 2000)
        )
        Assert.assertEquals(
            Pair<Long, Long>(60000, 3000),
            AxisScaleUtils().roundUpForDivision(60000, 3000, 2000)
        )
    }

    @Test
    fun buildScaleList_unexpected_value_throw_exception() {
        val value = -1L
        try {
            AxisScaleUtils().buildScaleList(value, 1)
            Assert.fail()
        } catch (_: Exception) {
        }
    }

    @Test
    fun buildScaleList_unexpected_factor_throw_exception() {
        val factor1 = 0
        val factor2 = 10
        try {
            AxisScaleUtils().buildScaleList(1, factor1)
            Assert.fail()
        } catch (_: Exception) {
        }
        try {
            AxisScaleUtils().buildScaleList(1, factor2)
            Assert.fail()
        } catch (_: Exception) {
        }
    }

    @Test
    fun buildScaleList_with_factor_3_value_0_9000000() {
        Assert.assertEquals(listOf<Long>(0, 1, 2, 3), AxisScaleUtils().buildScaleList(0, 3))
        Assert.assertEquals(listOf<Long>(0, 2, 4, 6), AxisScaleUtils().buildScaleList(6, 3))
        Assert.assertEquals(listOf<Long>(0, 20, 40, 60), AxisScaleUtils().buildScaleList(60, 3))
        Assert.assertEquals(
            listOf<Long>(0, 1000, 2000, 3000),
            AxisScaleUtils().buildScaleList(3000, 3)
        )
        Assert.assertEquals(
            listOf<Long>(0, 3000000, 6000000, 9000000),
            AxisScaleUtils().buildScaleList(9000000, 3)
        )
    }
}