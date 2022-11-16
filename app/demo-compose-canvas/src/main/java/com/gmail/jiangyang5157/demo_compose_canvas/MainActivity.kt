package com.gmail.jiangyang5157.demo_compose_canvas

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.toRect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.text.*
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.ceil

class MainActivity : ComponentActivity() {

    @ExperimentalTextApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        findViewById<ComposeView>(R.id.composeView).setContent {
            MaterialTheme {
                Column {
                    Row {
                        Button(onClick = {
                            // TODO YangJ
                        }) {
                            Text(text = "Prev")
                        }
                        Button(onClick = {
                            // TODO YangJ
                        }) {
                            Text(text = "Next")
                        }
                    }
                    Graph()
                }
            }
        }
    }

    @OptIn(ExperimentalTextApi::class)
    @Composable
    private fun Graph() {
        val textMeasurer = rememberTextMeasurer()

        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color.LightGray),
        ) {

            val graphRect = this.size.toRect()
            val contentHorizontalPadding = 8.dp
            val contentVerticalPadding = 4.dp

            val indicatorTextStyle = TextStyle(fontSize = 16.sp)
            val androidIndicatorColor = Color.Red
            val androidIndicatorText = textMeasurer.measure(
                text = AnnotatedString("Android"),
                style = indicatorTextStyle
            )
            val iosIndicatorColor = Color.Blue
            val iosIndicatorText = textMeasurer.measure(
                text = AnnotatedString("iOS"),
                style = indicatorTextStyle
            )
            val indicatorRect = Rect(
                topLeft = Offset(
                    graphRect.left,
                    graphRect.bottom - androidIndicatorText.size.height
                ),
                bottomRight = Offset(graphRect.right, graphRect.bottom),
            )
            val contentRect = Rect(
                topLeft = Offset(
                    graphRect.left + contentHorizontalPadding.toPx(),
                    graphRect.top + contentVerticalPadding.toPx(),
                ),
                bottomRight = Offset(
                    graphRect.right - contentHorizontalPadding.toPx(),
                    indicatorRect.top - contentVerticalPadding.toPx(),
                ),
            )

            // debug
            drawLine(
                color = Color.Black,
                start = Offset(graphRect.center.x, graphRect.top),
                end = Offset(graphRect.center.x, graphRect.size.height),
            )
            // debug
            drawLine(
                color = Color.Black,
                start = Offset(graphRect.left, graphRect.center.y),
                end = Offset(graphRect.size.width, graphRect.center.y),
            )

            drawIndicators(
                this,
                indicatorRect,
                androidIndicatorText,
                androidIndicatorColor,
                iosIndicatorText,
                iosIndicatorColor,
            )

            drawContent(
                this,
                contentRect,
                androidIndicatorColor,
                iosIndicatorColor,
                listOf(
                    Item("APR", 1000.0, 1500.0),
                    Item("MAY", 100.0, 150.0),
                    Item("JUN", 2000.0, 2499.9),
                    Item("JUL", 500.0, 500.0),
                    Item("AUG", 4999.0, 200.0),
                    Item("SEP", 200.0, 200.0),
                ),
                textMeasurer,
            )
        }
    }

    @OptIn(ExperimentalTextApi::class)
    private fun drawIndicators(
        drawScope: DrawScope,
        rect: Rect,
        androidIndicatorText: TextLayoutResult,
        androidIndicatorColor: Color,
        iosIndicatorText: TextLayoutResult,
        iosIndicatorColor: Color,
    ) {
        drawScope.run {
            // debug
            drawRect(
                color = Color.Yellow,
                alpha = 0.2f,
                topLeft = rect.topLeft,
                size = rect.size,
            )

            val iconTextPadding = 8.dp
            val indicatorBetweenPadding = 32.dp
            val textWidth = maxOf(androidIndicatorText.size.width, iosIndicatorText.size.width)
            val textHeight = androidIndicatorText.size.height
            val iconRadius = textHeight * 0.4f
            val indicatorWidth = iconRadius * 2 + iconTextPadding.toPx() + textWidth

            val androidIndicatorOffset = Offset(
                x = rect.center.x - indicatorBetweenPadding.toPx() / 2 - indicatorWidth,
                y = rect.top
            )
            val iosIndicatorOffset = Offset(
                x = rect.center.x + indicatorBetweenPadding.toPx() / 2,
                y = rect.top
            )

            drawCircle(
                color = androidIndicatorColor,
                radius = iconRadius,
                center = Offset(androidIndicatorOffset.x + iconRadius, rect.center.y),
            )
            drawText(
                textLayoutResult = androidIndicatorText,
                color = Color.Black,
                topLeft = Offset(
                    x = androidIndicatorOffset.x + iconRadius * 2 + iconTextPadding.toPx(),
                    y = rect.center.y - textHeight / 2
                ),
            )
            drawCircle(
                color = iosIndicatorColor,
                radius = iconRadius,
                center = Offset(iosIndicatorOffset.x + iconRadius, rect.center.y),
            )
            drawText(
                textLayoutResult = iosIndicatorText,
                color = Color.Black,
                topLeft = Offset(
                    x = iosIndicatorOffset.x + iconRadius * 2 + iconTextPadding.toPx(),
                    y = rect.center.y - textHeight / 2,
                ),
            )
        }
    }

    @OptIn(ExperimentalTextApi::class)
    private fun drawContent(
        drawScope: DrawScope,
        rect: Rect,
        androidIndicatorColor: Color,
        iosIndicatorColor: Color,
        items: List<Item>,
        textMeasurer: TextMeasurer,
        labelStyle: TextStyle = TextStyle(fontSize = 16.sp),
        valueStyle: TextStyle = TextStyle(fontSize = 24.sp),
    ) {


        val maxMoney = items.maxByOrNull { it.maxValue }?.maxValue ?: 0.0
        val roundUpMaxMoney = roundUpMoney(maxMoney)
        Log.d("####", "Round up $maxMoney to $roundUpMaxMoney")

        val primaryFactor = 3
        val secondaryFactor = 4
        val primaryDivisor = factor2Divisor(roundUpMaxMoney, primaryFactor)
        val secondaryDivisor = factor2Divisor(roundUpMaxMoney, secondaryFactor)
        Log.d(
            "####",
            "Prepare divisors $primaryDivisor (primary) and $secondaryDivisor from factors $primaryFactor (primary) and $secondaryFactor"
        )

        val moneyToDivision = roundUpToDivision(roundUpMaxMoney, primaryDivisor, secondaryDivisor)
        val highest = moneyToDivision.first
        val factory = divisor2Factor(highest, moneyToDivision.second)
        Log.d("####", "Round up $roundUpMaxMoney to $highest by chosen factory $factory")

        val scaleList = buildScaleList(highest, factory)
        val scaleMoneyList = scaleList.map { buildMoneyAbbr(it) }
        Log.d("####", "Build scale list $scaleList")
        Log.d("####", "Build scale money list $scaleMoneyList")

        val yTexts = scaleMoneyList.map {
            textMeasurer.measure(
                text = AnnotatedString(it),
                style = labelStyle
            )
        }
        val xTexts = items.map {
            textMeasurer.measure(
                text = AnnotatedString(it.name),
                style = labelStyle
            )
        }

        val yAxisItemTextHeight = yTexts.first().size.height
        val xAxisItemTextHeight = xTexts.first().size.height

        val yAxisItemTextMaxWidth = yTexts.maxByOrNull { it.size.width }?.size?.width ?: 0

        val yAxisLeft = rect.left
        val yAxisRight = yAxisLeft + yAxisItemTextMaxWidth
        val yAxisTop = rect.top
        val yAxisBottom = rect.bottom - xAxisItemTextHeight - yAxisItemTextHeight

        val xAxisLeft = yAxisRight
        val xAxisRight = rect.right
        val xAxisBottom = rect.bottom
        val xAxisTop = xAxisBottom - xAxisItemTextHeight

        val yAxisHeight = yAxisTop - yAxisBottom
        val yAxisItemHeight = yAxisHeight / yTexts.size

        drawScope.run {
            // debug
            drawRect(
                color = Color.Yellow,
                alpha = 0.2f,
                topLeft = rect.topLeft,
                size = rect.size,
            )

            drawAxisX(
                this,
                Rect(
                    topLeft = Offset(xAxisLeft, xAxisTop),
                    bottomRight = Offset(xAxisRight, xAxisBottom),
                ),
                xTexts,
            )

            drawAxisY(
                this,
                Rect(
                    topLeft = Offset(yAxisLeft, yAxisTop),
                    bottomRight = Offset(yAxisRight, yAxisBottom),
                ),
                yTexts,
            )

            drawAxis(
                this,
                Rect(
                    topLeft = Offset(
                        yAxisRight + xAxisItemTextHeight / 2,
                        yAxisTop - yAxisItemHeight + xAxisItemTextHeight / 2,
                    ),
                    bottomRight = Offset(
                        xAxisRight,
                        xAxisTop - xAxisItemTextHeight / 2,
                    ),
                )
            )

            drawValue(
                this,
                Rect(
                    topLeft = Offset(
                        yAxisRight + xAxisItemTextHeight / 2,
                        yAxisTop,
                    ),
                    bottomRight = Offset(
                        xAxisRight,
                        yAxisTop - yAxisItemHeight + xAxisItemTextHeight / 2,
                    ),
                )
            )
        }
    }

    private fun drawAxis(
        drawScope: DrawScope,
        rect: Rect,
    ) {
        drawScope.run {
            // debug
            drawRect(
                color = Color.Red,
                alpha = 0.2f,
                topLeft = rect.topLeft,
                size = rect.size,
            )
        }
    }

    private fun drawValue(
        drawScope: DrawScope,
        rect: Rect,
    ) {
        drawScope.run {
            // debug
            drawRect(
                color = Color.Green,
                alpha = 0.2f,
                topLeft = rect.topLeft,
                size = rect.size,
            )
        }
    }

    @OptIn(ExperimentalTextApi::class)
    private fun drawAxisX(
        drawScope: DrawScope,
        rect: Rect,
        items: List<TextLayoutResult>,
    ) {
        val width = rect.right - rect.left
        val itemWidth = width / items.size

        drawScope.run {
            items.forEachIndexed { index, textLayoutResult ->
                drawText(
                    textLayoutResult = textLayoutResult,
                    color = Color.Black,
                    topLeft = Offset(
                        rect.left + index * itemWidth + (itemWidth - textLayoutResult.size.width) / 2,
                        rect.top
                    ),
                )
            }
        }
    }

    @OptIn(ExperimentalTextApi::class)
    private fun drawAxisY(
        drawScope: DrawScope,
        rect: Rect,
        items: List<TextLayoutResult>,
    ) {
        val height = rect.top - rect.bottom
        val itemHeight = height / items.size
        val itemWidth = items.maxByOrNull { it.size.width }?.size?.width ?: throw RuntimeException()

        drawScope.run {
            items.forEachIndexed { index, textLayoutResult ->
                drawText(
                    textLayoutResult = textLayoutResult,
                    color = Color.Black,
                    topLeft = Offset(
                        rect.left + (itemWidth - textLayoutResult.size.width),
                        rect.bottom + index * itemHeight,
                    ),
                )
            }
        }
    }

    // https://pl.kotl.in/isin149pF

    /*
    0.00 0
    0.01 1
    1.01 2
    4.01 5
    8.01 9
    9.01 10
    999999999.99 1000000000
     */
    fun roundUpToNextInteger(money: Double): Int {
        if (money < 0.0) throw IllegalArgumentException("Money should be not less than 0")
        return ceil(money).toInt()
    }

    /*
    11.0 20
    41.0 50
    89.0 90
    100.0 110
    100.1 110
    888.0 890
    8888.0 8890
     */
    fun roundUpToNextTens(money: Double): Int {
        if (money < 0.0) throw IllegalArgumentException("Money should be not less than 0")
        return (ceil(money / 10) * 10).toInt()
    }

    /*
    101.0 200
    401.0 500
    1000.0 1000
    1000.1 1100
    888888888.0 888888900
     */
    fun roundUpToNextHundreds(money: Double): Int {
        if (money < 0.0) throw IllegalArgumentException("Money should be not less than 0")
        return (ceil(money / 100) * 100).toInt()
    }

    /*
    3000.0 3000
    3000.1 4000
    3001.0 4000
    4100.0 5000
    99001.0 100000
    888888888.0 888889000
     */
    fun roundUpToNextThousands(money: Double): Int {
        if (money < 0.0) throw IllegalArgumentException("Money should be not less than 0")
        return (ceil(money / 1000) * 1000).toInt()
    }

    /*
    48300998.0 49000000
    888888888.0 889000000
    999000000.1 1000000000
    999000001.0 1000000000
     */
    fun roundUpToNextMillions(money: Double): Int {
        if (money < 0.0) throw IllegalArgumentException("Money should be not less than 0")
        return (ceil(money / 1000000) * 1000000).toInt()
    }

    fun roundUpMoney(money: Double): Int {
        return when {
            money == 0.0 -> 0
            money > 0.0 && money <= 10.0 -> roundUpToNextInteger(money)
            money > 10.0 && money <= 100.0 -> roundUpToNextTens(money)
            money > 100.0 && money <= 1000.0 -> roundUpToNextHundreds(money)
            money > 1000.0 && money <= 1000000.0 -> roundUpToNextThousands(money)
            money > 1000000.0 && money <= 1000000000.0 -> roundUpToNextMillions(money)
            else -> throw IllegalArgumentException("Money should be in range [0, 1,000,000,000]")
        }
    }

    /*
    0 $0
    9 $9
    99 $99
    999 $999
    9999 $9k
    99999 $99k
    999999 $999k
    9999999 $9m
    99999999 $99m
    999999999 $999m
     */
    /**
     * Build money abbr string, and ignore remainders when it comes to thousands and millions.
     */
    fun buildMoneyAbbr(money: Int): String {
        return when {
            money >= 0 && money < 1000 -> "$$money"
            money >= 1000 && money < 1000000 -> "$${money / 1000}k"
            money >= 1000000 && money <= 1000000000 -> "$${money / 1000000}m"
            else -> throw IllegalArgumentException("Money should be in range [0, 1,000,000,000]")
        }
    }

    /*
    1,2 2
    10,2 2
    100,2 20
    1000,2 200
    10000,2 2000
    100000,2 2000
    1000000,2 2000
    10000000,2 2000000
    100000000,2 2000000
     */
    fun factor2Divisor(money: Int, factor: Int): Int {
        if (money < 0) throw IllegalArgumentException("Money should be not less than 0")
        if (factor <= 0) throw IllegalArgumentException("Factor should be greater than 0")

        return when {
            money >= 0 && money <= 10 -> factor
            money > 10 && money <= 100 -> factor * 10
            money > 100 && money <= 1000 -> factor * 100
            money > 1000 && money <= 1000000 -> factor * 1000
            money > 1000000 && money <= 1000000000 -> factor * 1000000
            else -> throw IllegalArgumentException("Money should be in range [0, 1,000,000,000]")
        }
    }

    /*
    1,2 2
    10,2 2
    100,20 2
    1000,200 2
    10000,2000 2
    100000,2000 2
    1000000,2000 2
    10000000,2000000 2
    100000000,2000000 2
     */
    fun divisor2Factor(money: Int, divisor: Int): Int {
        if (money < 0) throw IllegalArgumentException("Money should be not less than 0")
        if (divisor <= 0) throw IllegalArgumentException("divisor should be greater than 0")

        return when {
            money >= 0 && money <= 10 -> divisor
            money > 10 && money <= 100 -> divisor / 10
            money > 100 && money <= 1000 -> divisor / 100
            money > 1000 && money <= 1000000 -> divisor / 1000
            money > 1000000 && money <= 1000000000 -> divisor / 1000000
            else -> throw IllegalArgumentException("Money should be in range [0, 1,000,000,000]")
        }
    }

    /*
    0,1 0
    0,2 0
    5,1 5
    5,2 6
    5,3 6
    5,4 8
    5,5 5
    4999,2 5000
    5000,2 5000
    5001,2 5002
    4999,2000 6000
    5000,2000 6000
    5001,2000 6000
     */
    /**
     * Round up number and make it divisible by the divisor.
     * Return the new number.
     */
    fun roundUpToDivision(money: Int, divisor: Int): Int {
        if (money < 0) throw IllegalArgumentException("Money should be not less than 0")
        if (divisor <= 0) throw IllegalArgumentException("Divisor should be greater than 0")

        val remainders = money % divisor
        return if (remainders == 0) {
            money
        } else {
            money + (divisor - remainders)
        }
    }

    /*
    5,2,3 (6, 2)
    5,3,2 (6, 3)
    5,3,4 (6, 3)
    5,4,3 (6, 3)
    5000,2,3 (5000, 2)
    5000,3,2 (5000, 2)
    5000,2000,3000 (6000, 2000)
    5000,3000,2000 (6000, 3000)
     */
    /**
     * Round up number and make it divisible by either primary or secondary.
     * Return the smaller new number and the divisor.
     */
    fun roundUpToDivision(money: Int, primary: Int, secondary: Int): Pair<Int, Int> {
        if (money < 0) throw IllegalArgumentException("Money should be not less than 0")
        if (primary <= 0) throw IllegalArgumentException("primary should be greater than 0")
        if (secondary <= 0) throw IllegalArgumentException("secondary should be greater than 0")

        return if (money == 0) {
            Pair(primary, primary)
        } else {
            val primaryMoney = roundUpToDivision(money, primary)
            val secondaryMoney = roundUpToDivision(money, secondary)
            if (primaryMoney <= secondaryMoney) {
                Pair(primaryMoney, primary)
            } else {
                Pair(secondaryMoney, secondary)
            }
        }
    }

    /*
   	6,2 [0, 3, 6]
    6,3 [0, 2, 4, 6]
    60,2 [0, 30, 60]
    60,3 [0, 20, 40, 60]
    2000,2 [0, 1000, 2000]
    3000,2 [0, 1500, 3000]
    3000,3 [0, 1000, 2000, 3000]
     */
    fun buildScaleList(money: Int, factor: Int): List<Int> {
        if (money < 0) throw IllegalArgumentException("Money should be not less than 0")

        return if (money == 0) {
            listOf(0, 1, 2)
        } else {
            val step = money / factor
            IntRange(0, money).step(step).toList()
        }
    }

    data class Item(
        val name: String,
        val androidValue: Double,
        val iosValue: Double,
    ) {
        val maxValue = maxOf(androidValue, iosValue)
        val diff = androidValue - iosValue
    }
}
