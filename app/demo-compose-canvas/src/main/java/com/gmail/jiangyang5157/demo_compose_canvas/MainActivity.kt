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
import androidx.compose.ui.geometry.Size
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
            val padding = 8.dp
            val indicatorHeight = 24.dp

            val androidIndicatorText = "Android"
            val androidIndicatorColor = Color.Red
            val iosIndicatorText = "iOS"
            val iosIndicatorColor = Color.Blue

            val indicatorRect = Rect(
                topLeft = Offset(
                    graphRect.left,
                    graphRect.bottom - indicatorHeight.toPx() - padding.toPx()
                ),
                bottomRight = Offset(graphRect.right, graphRect.bottom - padding.toPx()),
            )
            val contentRect = Rect(
                topLeft = Offset(
                    graphRect.left + padding.toPx(),
                    graphRect.top + padding.toPx(),
                ),
                bottomRight = Offset(
                    graphRect.right - padding.toPx(),
                    indicatorRect.top - padding.toPx(),
                ),
            )

            drawTwoIndicators(
                this,
                textMeasurer,
                indicatorRect,
                androidIndicatorText,
                androidIndicatorColor,
                iosIndicatorText,
                iosIndicatorColor,
            )
            drawContent(
                this,
                textMeasurer,
                contentRect,
                androidIndicatorColor,
                iosIndicatorColor,
                listOf(
                    Item("APR", 1000.0, 1500.0),
                    Item("MAY", 100.0, 150.0),
                    Item("JUN", 2000.0, 2499.9),
                    Item("JUL", 1500.0, 3500.0),
                    Item("AUG", 4999.0, 2200.0),
                    Item("SEP", 3200.0, 900.0),
                )
            )
        }
    }

    @OptIn(ExperimentalTextApi::class)
    private fun drawTwoIndicators(
        drawScope: DrawScope,
        textMeasurer: TextMeasurer,
        rect: Rect,
        text1: CharSequence,
        color1: Color,
        text2: CharSequence,
        color2: Color,
    ) {
        val textStyle = TextStyle(fontSize = 16.sp)
        val textLayoutResult1 = textMeasurer.measure(
            text = AnnotatedString(text1.toString()),
            style = textStyle
        )
        val textLayoutResult2 = textMeasurer.measure(
            text = AnnotatedString(text2.toString()),
            style = textStyle
        )

        drawScope.run {
            // debug
            drawRect(
                color = Color.Cyan,
                alpha = 0.2f,
                topLeft = rect.topLeft,
                size = rect.size,
            )

            val iconTextPadding = 8.dp
            val indicatorPadding = 32.dp
            val textWidth = maxOf(textLayoutResult1.size.width, textLayoutResult2.size.width)
            val textHeight = textLayoutResult1.size.height
            val iconRadius = textHeight * 0.4f
            val indicatorWidth = iconRadius * 2 + iconTextPadding.toPx() + textWidth

            val indicatorOffset1 = Offset(
                x = rect.center.x - indicatorPadding.toPx() / 2 - indicatorWidth,
                y = rect.top
            )
            val indicatorOffset2 = Offset(
                x = rect.center.x + indicatorPadding.toPx() / 2,
                y = rect.top
            )

            drawCircle(
                color = color1,
                radius = iconRadius,
                center = Offset(indicatorOffset1.x + iconRadius, rect.center.y),
            )
            drawText(
                textLayoutResult = textLayoutResult1,
                color = Color.Black,
                topLeft = Offset(
                    x = indicatorOffset1.x + iconRadius * 2 + iconTextPadding.toPx(),
                    y = rect.center.y - textHeight / 2
                ),
            )
            drawCircle(
                color = color2,
                radius = iconRadius,
                center = Offset(indicatorOffset2.x + iconRadius, rect.center.y),
            )
            drawText(
                textLayoutResult = textLayoutResult2,
                color = Color.Black,
                topLeft = Offset(
                    x = indicatorOffset2.x + iconRadius * 2 + iconTextPadding.toPx(),
                    y = rect.center.y - textHeight / 2,
                ),
            )
        }
    }

    @OptIn(ExperimentalTextApi::class)
    private fun drawContent(
        drawScope: DrawScope,
        textMeasurer: TextMeasurer,
        rect: Rect,
        androidIndicatorColor: Color,
        iosIndicatorColor: Color,
        items: List<Item>,
    ) {
        if (items.isEmpty()) return

        val maxMoney = items.maxByOrNull { it.maxValue }?.maxValue ?: throw RuntimeException()
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

        drawScope.run {
            val axisTextStyle = TextStyle(fontSize = 16.sp)
            val scaleTextLayoutResults = scaleMoneyList.map {
                textMeasurer.measure(
                    text = AnnotatedString(it),
                    style = axisTextStyle
                )
            }
            val labelTextLayoutResults = items.map {
                textMeasurer.measure(
                    text = AnnotatedString(it.label),
                    style = axisTextStyle
                )
            }

            val padding = 8.dp
            val focusHeight = 48.dp
            val focusTop = rect.top
            val focusRight = rect.right
            val labelRight = rect.right
            val dataRight = rect.right
            val scaleLeft = rect.left
            val labelBottom = rect.bottom

            val focusBottom = focusTop + focusHeight.toPx()
            val scaleTop = focusBottom + padding.toPx()
            val dataTop = scaleTop

            val scaleWidth =
                scaleTextLayoutResults.maxByOrNull { it.size.width }?.size?.width
                    ?: throw RuntimeException()
            val scaleRight = scaleLeft + scaleWidth
            val focusLeft = scaleRight + padding.toPx()
            val labelLeft = scaleRight + padding.toPx()
            val dataLeft = scaleRight + padding.toPx()

            val axisTextHeight = labelTextLayoutResults.first().size.height
            val labelTop = labelBottom - axisTextHeight
            val scaleBottom = labelTop - padding.toPx()
            val dataBottom = scaleBottom

            drawFocus(
                this,
                Rect(
                    topLeft = Offset(focusLeft, focusTop),
                    bottomRight = Offset(focusRight, focusBottom),
                ),
            )
            drawLabel(
                this,
                Rect(
                    topLeft = Offset(labelLeft, labelTop),
                    bottomRight = Offset(labelRight, labelBottom),
                ),
                labelTextLayoutResults,
            )
            drawScale(
                this,
                Rect(
                    topLeft = Offset(scaleLeft, scaleTop),
                    bottomRight = Offset(scaleRight, scaleBottom),
                ),
                scaleTextLayoutResults,
            )
            drawData(
                this,
                Rect(
                    topLeft = Offset(dataLeft, dataTop),
                    bottomRight = Offset(dataRight, dataBottom),
                ),
                androidIndicatorColor,
                iosIndicatorColor,
                items,
                scaleList
            )

            // TODO item selection
        }
    }

    private fun drawData(
        drawScope: DrawScope,
        rect: Rect,
        color1: Color,
        color2: Color,
        items: List<Item>,
        scaleList: List<Int>,
    ) {
        if (scaleList.size < 2) throw IllegalArgumentException("Scale size should not less than 2")

        drawScope.run {
            // debug
            drawRect(
                color = Color.Cyan,
                alpha = 0.2f,
                topLeft = rect.topLeft,
                size = rect.size,
            )

            val scaleStep = rect.size.height / (scaleList.size - 1)
            scaleList.forEachIndexed { index, _ ->
                drawLine(
                    color = Color.Black,
                    start = Offset(rect.left, rect.bottom - index * scaleStep),
                    end = Offset(rect.right, rect.bottom - index * scaleStep),
                )
            }

            val itemWidth = (rect.right - rect.left) / items.size
            val valueWidth = 8.dp
            val valuePadding = 4.dp

            val maxScale = scaleList.maxOf { it }
            val ratio = rect.height / maxScale

            items.forEachIndexed { index, item ->
                val height1 = item.value1.toFloat() * ratio
                val height2 = item.value2.toFloat() * ratio
                drawRect(
                    color = color1,
                    topLeft = Offset(
                        x = rect.left + itemWidth * index + itemWidth / 2 - valuePadding.toPx() / 2 - valueWidth.toPx(),
                        y = rect.bottom - height1,
                    ),
                    size = Size(
                        width = valueWidth.toPx(),
                        height = height1,
                    ),
                )
                drawRect(
                    color = color2,
                    topLeft = Offset(
                        x = rect.left + itemWidth * index + itemWidth / 2 + valuePadding.toPx() / 2,
                        y = rect.bottom - height2,
                    ),
                    size = Size(
                        width = valueWidth.toPx(),
                        height = height2,
                    ),
                )
            }
        }
    }

    private fun drawFocus(
        drawScope: DrawScope,
        rect: Rect,
    ) {
        val textStyle = TextStyle(fontSize = 24.sp)

        drawScope.run {
            // debug
            drawRect(
                color = Color.Cyan,
                alpha = 0.2f,
                topLeft = rect.topLeft,
                size = rect.size,
            )
        }
    }

    @OptIn(ExperimentalTextApi::class)
    private fun drawLabel(
        drawScope: DrawScope,
        rect: Rect,
        labelTexts: List<TextLayoutResult>,
    ) {
        val itemWidth = (rect.right - rect.left) / labelTexts.size

        drawScope.run {
            // debug
            drawRect(
                color = Color.Cyan,
                alpha = 0.2f,
                topLeft = rect.topLeft,
                size = rect.size,
            )

            labelTexts.forEachIndexed { index, textLayoutResult ->
                val widthDiff = itemWidth - textLayoutResult.size.width
                drawText(
                    textLayoutResult = textLayoutResult,
                    color = Color.Black,
                    topLeft = Offset(
                        rect.left + index * itemWidth + widthDiff / 2,
                        rect.top
                    ),
                )
            }
        }
    }

    @OptIn(ExperimentalTextApi::class)
    private fun drawScale(
        drawScope: DrawScope,
        rect: Rect,
        scaleTexts: List<TextLayoutResult>,
    ) {
        if (scaleTexts.size < 2) throw IllegalArgumentException("Scale size should not less than 2")

        val itemHeight = (rect.top - rect.bottom) / (scaleTexts.size - 1)
        val itemWidth =
            scaleTexts.maxByOrNull { it.size.width }?.size?.width ?: throw RuntimeException()
        val axisTextHeight = scaleTexts.first().size.height

        drawScope.run {
            // debug
            drawRect(
                color = Color.Cyan,
                alpha = 0.2f,
                topLeft = rect.topLeft,
                size = rect.size,
            )

            scaleTexts.forEachIndexed { index, textLayoutResult ->
                val widthDiff = itemWidth - textLayoutResult.size.width
                drawText(
                    textLayoutResult = textLayoutResult,
                    color = Color.Black,
                    topLeft = Offset(
                        rect.left + widthDiff,
                        rect.bottom + itemHeight * index - axisTextHeight / 2,
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
        val label: String,
        val value1: Double,
        val value2: Double,
    ) {
        val maxValue = maxOf(value1, value2)
        val diff = value1 - value2
    }
}
