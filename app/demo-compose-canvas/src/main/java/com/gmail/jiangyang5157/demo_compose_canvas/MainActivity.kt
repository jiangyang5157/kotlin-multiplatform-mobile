package com.gmail.jiangyang5157.demo_compose_canvas

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.text.*
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gmail.jiangyang5157.demo_compose_canvas.render.*
import kotlin.math.abs
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
                            Text(text = "next")
                        }
                    }
                    Graph()
                }
            }
        }
    }

    private val items = listOf(
        Item("APR", 1000.0, 1500.0),
        Item("MAY", 100.0, 150.0),
        Item("JUN", 2000.0, 2499.9),
        Item("JUL", 1500.0, 3500.0),
        Item("AUG", 4999.0, 2200.0),
        Item("SEP", 3200.0, 900.0),
    )
    private val itemRects = items.map { ItemRect(it) }

    @OptIn(ExperimentalTextApi::class)
    @Composable
    private fun Graph() {
        val textMeasurer = rememberTextMeasurer()
        var focusedItemRect: ItemRect? by remember { mutableStateOf(null) }

        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color.LightGray)
                .pointerInput(Unit) {
                    detectTapGestures(
                        onTap = { tapOffset ->
                            val tapItemRect =
                                itemRects.firstOrNull { it.rect?.contains(tapOffset) == true }
                            focusedItemRect = tapItemRect
                            Log.d("####", "Tap $tapOffset $focusedItemRect")
                        }
                    )
                }

        ) {
            val graphRect = this.size.toRect()

            val padding = 8.dp
            val indicatorHeight = 24.dp

            val indicatorRect = Rect(
                topLeft = Offset(
                    graphRect.left + 64.dp.toPx(),
                    graphRect.bottom - indicatorHeight.toPx() - padding.toPx()
                ),
                bottomRight = Offset(
                    graphRect.right - 64.dp.toPx(),
                    graphRect.bottom - padding.toPx(),
                ),
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

            drawGraphLabel(
                textMeasurer = textMeasurer,
                rect = indicatorRect,
                orientation = DrawOrientation.Horizontal,
                items = listOf(
                    Pair(Color.DarkGray, "iOS"),
                    Pair(Color.Blue, "Android"),
                ),
            )

            drawContent(
                this,
                textMeasurer,
                contentRect,
                Color.DarkGray,
                Color.Blue,
                itemRects,
                focusedItemRect,
            )
        }
    }

    @OptIn(ExperimentalTextApi::class)
    private fun drawContent(
        drawScope: DrawScope,
        textMeasurer: TextMeasurer,
        rect: Rect,
        color1: Color,
        color2: Color,
        itemRects: List<ItemRect>,
        focusedItemRect: ItemRect?,
    ) {
        if (itemRects.isEmpty()) return

        val maxMoney =
            itemRects.maxByOrNull { it.item.maxValue }?.item?.maxValue ?: throw RuntimeException()
        val primaryFactor = 3
        val secondaryFactor = 4

        val roundUpMaxMoney = roundUpMoney(maxMoney)
        Log.d("####", "Round up $maxMoney to $roundUpMaxMoney")

        val primaryDivisor = factor2Divisor(roundUpMaxMoney, primaryFactor)
        val secondaryDivisor = factor2Divisor(roundUpMaxMoney, secondaryFactor)
        Log.d(
            "####",
            "Prepare divisors $primaryDivisor (primary) and $secondaryDivisor from factors $primaryFactor (primary) and $secondaryFactor"
        )

        val moneyToDivision = roundUpToDivision(roundUpMaxMoney, primaryDivisor, secondaryDivisor)
        val highest = moneyToDivision.first
        val factory = divisor2Factor(highest, moneyToDivision.second)
        Log.d(
            "####",
            "Round up $roundUpMaxMoney to $highest by chosen division ${moneyToDivision.second} factory $factory"
        )

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
            val labelTexts = itemRects.map { it.item.label }

            val padding = 4.dp
            val focusHeight = 48.dp
            val focusTop = rect.top
            val dataRight = rect.right
            val scaleLeft = rect.left
            val labelBottom = rect.bottom
            val axisTextHeight = itemRects.first().let {
                textMeasurer.measure(
                    text = AnnotatedString(it.item.label),
                    style = axisTextStyle
                ).size.height
            }
            val labelHeight = axisTextHeight

            val focusBottom = focusTop + focusHeight.toPx()
            val scaleTop = focusBottom + padding.toPx()
            val dataTop = scaleTop

            val scaleWidth =
                scaleTextLayoutResults.maxByOrNull { it.size.width }?.size?.width
                    ?: throw RuntimeException()
            val scaleRight = scaleLeft + scaleWidth
            val dataLeft = scaleRight + padding.toPx()

            val labelTop = labelBottom - labelHeight

            val itemRectWidth = (dataRight - dataLeft) / itemRects.size
            val itemRectHeight = labelBottom - dataTop
            itemRects.forEachIndexed { index, itemRect ->
                itemRect.rect = Rect(
                    offset = Offset(
                        x = dataLeft + itemRectWidth * index,
                        y = dataTop,
                    ),
                    size = Size(
                        width = itemRectWidth,
                        height = itemRectHeight,
                    ),
                )
            }

            // ================================================================

            val dataRect = drawDataAxis(
                rect = Rect(
                    topLeft = Offset(
                        x = rect.left,
                        y = focusBottom,
                    ),
                    bottomRight = Offset(
                        x = rect.right,
                        y = labelTop,
                    ),
                ),
                textStyle = TextStyle(fontSize = 16.sp, color = color1),
                textMeasurer = textMeasurer,
                lineColor = Color.DarkGray,
                items = scaleMoneyList,
            )

            // ================================================================

            drawDialog(
                this,
                textMeasurer,
                Rect(
                    topLeft = Offset(
                        x = dataRect.left,
                        y = rect.top,
                    ),
                    bottomRight = Offset(
                        x = dataRect.right,
                        y = dataRect.top,
                    )
                ),
                focusedItemRect,
            )

            // ================================================================

            val underlineLabelPaddingTop = 6.dp.toPx()
            val underlineLabelRect = Rect(
                offset = dataRect.bottomLeft.plus(Offset(0f, underlineLabelPaddingTop)),
                size = Size(
                    width = dataRect.width,
                    height = labelHeight.toFloat(),
                )
            )
            val underlineItemWidth = underlineLabelRect.width / labelTexts.size

            labelTexts.forEachIndexed { index, text ->
                val selected = text == focusedItemRect?.item?.label
                drawUnderlineLabel(
                    textMeasurer = textMeasurer,
                    underlineColor = Color.Blue,
                    text = text,
                    textStyle = TextStyle(fontSize = 16.sp, color = Color.DarkGray),
                    selected = selected,
                    rect = Rect(
                        offset = Offset(
                            x = underlineLabelRect.left + underlineItemWidth * index,
                            y = underlineLabelRect.top,
                        ),
                        size = Size(
                            width = underlineItemWidth,
                            height = underlineLabelRect.height
                        ),
                    ),
                )
            }

            // ================================================================

            drawData(
                this,
                dataRect,
                color2,
                color1,
                itemRects,
                scaleList
            )

            // ================================================================
        }
    }

    private fun drawData(
        drawScope: DrawScope,
        rect: Rect,
        color1: Color,
        color2: Color,
        itemRects: List<ItemRect>,
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

            val itemWidth = (rect.right - rect.left) / itemRects.size
            val maxScale = scaleList.maxOf { it }
            val ratio = rect.height / maxScale

            itemRects.forEachIndexed { index, itemRect ->
                val height1 = itemRect.item.value1.toFloat() * ratio
                val height2 = itemRect.item.value2.toFloat() * ratio

                drawColumn(
                    items = listOf(
                        Pair(color1, height1),
                        Pair(color2, height2),
                    ),
                    rect = Rect(
                        offset = Offset(
                            x = rect.left + itemWidth * index,
                            y = rect.top,
                        ),
                        size = Size(
                            width = itemWidth,
                            height = rect.height,
                        ),
                    ),
                )
            }
        }
    }

    @OptIn(ExperimentalTextApi::class)
    private fun drawDialog(
        drawScope: DrawScope,
        textMeasurer: TextMeasurer,
        rect: Rect,
        focusedItemRect: ItemRect?,
    ) {
        val item = focusedItemRect?.item ?: return
        val itemRect = focusedItemRect.rect ?: return

        val symbol = if (item.diff >= 0) "+" else "-"
        val symbolColor = if (item.diff >= 0) Color.Green else Color.Red
        val diff = abs(item.diff)

        val symbolTextLayoutResult = textMeasurer.measure(
            text = AnnotatedString(symbol),
            style = TextStyle(fontSize = 20.sp)
        )
        val diffTextLayoutResult = textMeasurer.measure(
            text = AnnotatedString("$${String.format("%.2f", diff)}"),
            style = TextStyle(fontSize = 20.sp)
        )

        drawScope.run {
            drawUpperDialog(
                rect = rect,
                color = Color.Cyan,
                x = itemRect.center.x,
                calculateContent = {
                    Size(
                        width = (symbolTextLayoutResult.size.width + diffTextLayoutResult.size.width).toFloat(),
                        height = symbolTextLayoutResult.size.height.toFloat()
                    )
                },
                // draw content
                drawContent = { drawScope, rect ->
                    drawScope.drawText(
                        textLayoutResult = symbolTextLayoutResult,
                        color = symbolColor,
                        topLeft = rect.topLeft,
                    )
                    drawScope.drawText(
                        textLayoutResult = diffTextLayoutResult,
                        color = Color.Black,
                        topLeft = rect.topLeft.copy(
                            x = rect.topLeft.x + symbolTextLayoutResult.size.width
                        ),
                    )
                }
            )
        }
    }

    // https://pl.kotl.in/1Dj1dxkeE

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
            money >= 1000 && money < 1000000 -> if (money % 1000 == 0) {
                "$${money / 1000}k"
            } else {
                "$${money / 1000.0}k"
            }
            money >= 1000000 && money <= 1000000000 -> if (money % 1000000 == 0) {
                "$${money / 1000000}k"
            } else {
                "$${money / 1000000.0}k"
            }
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
            money > 10 && money <= 100 -> if (divisor > 10) {
                divisor / 10
            } else {
                divisor
            }
            money > 100 && money <= 1000 -> if (divisor > 100) {
                divisor / 100
            } else {
                divisor / 10
            }
            money > 1000 && money <= 1000000 -> if (divisor > 1000) {
                divisor / 1000
            } else {
                divisor / 100
            }
            money > 1000000 && money <= 1000000000 -> if (divisor > 1000000) {
                divisor / 1000000
            } else {
                divisor / 1000
            }
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
        val diff: Double = value1 - value2
    }

    data class ItemRect(
        val item: Item,
        var rect: Rect? = null,
    )
}
