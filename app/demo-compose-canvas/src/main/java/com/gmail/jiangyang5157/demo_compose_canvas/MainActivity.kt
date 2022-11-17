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
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.text.*
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gmail.jiangyang5157.demo_compose_canvas.render.DrawGravity
import com.gmail.jiangyang5157.demo_compose_canvas.render.drawCircleRect
import com.gmail.jiangyang5157.demo_compose_canvas.render.drawTextRect
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
                itemRects,
                focusedItemRect,
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
        drawScope.run {
            // debug
            drawRect(
                color = Color.Cyan,
                alpha = 0.2f,
                topLeft = rect.topLeft,
                size = rect.size,
            )

            val iconTextPadding = 8.dp.toPx()
            val indicatorPadding = 32.dp.toPx()
            val textStyle = TextStyle(fontSize = 16.sp)
            val textLayoutResult1 = textMeasurer.measure(
                text = AnnotatedString(text1.toString()),
                style = textStyle
            )
            val textLayoutResult2 = textMeasurer.measure(
                text = AnnotatedString(text2.toString()),
                style = textStyle
            )
            val textWidth = maxOf(textLayoutResult1.size.width, textLayoutResult2.size.width)
            val textHeight = textLayoutResult1.size.height
            val iconRadius = textHeight * 0.3f
            val indicatorWidth = iconRadius * 2 + iconTextPadding + textWidth

            val iconTopLeft1 = Offset(
                x = rect.center.x - indicatorPadding / 2 - indicatorWidth,
                y = rect.top
            )
            val iconBottomRight1 = Offset(
                x = iconTopLeft1.x + iconRadius * 2 + iconTextPadding,
                y = rect.bottom,
            )
            val iconTopLeft2 = Offset(
                x = rect.center.x + indicatorPadding / 2,
                y = rect.top
            )
            val iconBottomRight2 = Offset(
                x = iconTopLeft2.x + iconRadius * 2 + iconTextPadding,
                y = rect.bottom,
            )

            drawCircleRect(
                color = color1,
                radius = iconRadius,
                gravity = DrawGravity.CenterVertical,
                rect = Rect(
                    topLeft = iconTopLeft1,
                    bottomRight = iconBottomRight1
                )
            )
            drawTextRect(
                textLayoutResult = textLayoutResult1,
                gravity = DrawGravity.CenterVertical,
                rect = Rect(
                    topLeft = Offset(
                        x = iconBottomRight1.x,
                        y = rect.top,
                    ),
                    bottomRight = Offset(
                        x = iconBottomRight1.x + textWidth,
                        y = rect.bottom,
                    )
                ),
            )
            drawCircleRect(
                color = color2,
                radius = iconRadius,
                gravity = DrawGravity.CenterVertical,
                rect = Rect(
                    topLeft = iconTopLeft2,
                    bottomRight = iconBottomRight2,
                )
            )
            drawTextRect(
                textLayoutResult = textLayoutResult2,
                gravity = DrawGravity.CenterVertical,
                rect = Rect(
                    topLeft = Offset(
                        x = iconBottomRight2.x,
                        y = rect.top,
                    ),
                    bottomRight = Offset(
                        x = iconBottomRight2.x + textWidth,
                        y = rect.bottom,
                    )
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
            val labelTextLayoutResults = itemRects.map {
                textMeasurer.measure(
                    text = AnnotatedString(it.item.label),
                    style = axisTextStyle
                )
            }

            val padding = 4.dp
            val focusHeight = 48.dp
            val focusTop = rect.top
            val dataRight = rect.right
            val scaleLeft = rect.left
            val labelBottom = rect.bottom
            val axisTextHeight = labelTextLayoutResults.first().size.height
            val labelHeight = axisTextHeight

            val focusBottom = focusTop + focusHeight.toPx()
            val scaleTop = focusBottom + padding.toPx()
            val dataTop = scaleTop

            val scaleWidth =
                scaleTextLayoutResults.maxByOrNull { it.size.width }?.size?.width
                    ?: throw RuntimeException()
            val scaleRight = scaleLeft + scaleWidth
            val dataLeft = scaleRight + padding.toPx()
            val focusLeft = dataLeft
            val labelLeft = dataLeft
            val focusRight = dataRight
            val labelRight = dataRight

            val labelTop = labelBottom - labelHeight
            val scaleBottom = labelTop - padding.toPx()
            val dataBottom = scaleBottom

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

            drawFocus(
                this,
                textMeasurer,
                Rect(
                    topLeft = Offset(focusLeft, focusTop),
                    bottomRight = Offset(focusRight, focusBottom),
                ),
                focusedItemRect,
            )
            drawScale(
                this,
                Rect(
                    topLeft = Offset(scaleLeft, scaleTop),
                    bottomRight = Offset(scaleRight, scaleBottom),
                ),
                scaleTextLayoutResults,
            )
            drawLabel(
                this,
                Rect(
                    topLeft = Offset(labelLeft, labelTop),
                    bottomRight = Offset(labelRight, labelBottom),
                ),
                labelTextLayoutResults,
            )
            drawData(
                this,
                Rect(
                    topLeft = Offset(dataLeft, dataTop),
                    bottomRight = Offset(dataRight, dataBottom),
                ),
                androidIndicatorColor,
                iosIndicatorColor,
                itemRects,
                scaleList
            )
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
            val valueWidth = 8.dp
            val valuePadding = 4.dp
            val valueCornerRadius = 2.dp

            val maxScale = scaleList.maxOf { it }
            val ratio = rect.height / maxScale

            itemRects.forEachIndexed { index, itemRect ->
                val height1 = itemRect.item.value1.toFloat() * ratio
                val height2 = itemRect.item.value2.toFloat() * ratio
                drawPath(
                    path = Path().apply {
                        addRoundRect(
                            RoundRect(
                                rect = Rect(
                                    offset = Offset(
                                        x = rect.left + itemWidth * index + itemWidth / 2 - valuePadding.toPx() / 2 - valueWidth.toPx(),
                                        y = rect.bottom - height1,
                                    ),
                                    size = Size(
                                        width = valueWidth.toPx(),
                                        height = height1,
                                    ),
                                ),
                                topLeft = CornerRadius(valueCornerRadius.toPx()),
                                topRight = CornerRadius(valueCornerRadius.toPx()),
                            )
                        )
                    },
                    color = color1,
                )
                drawPath(
                    path = Path().apply {
                        addRoundRect(
                            RoundRect(
                                rect = Rect(
                                    offset = Offset(
                                        x = rect.left + itemWidth * index + itemWidth / 2 + valuePadding.toPx() / 2,
                                        y = rect.bottom - height2,
                                    ),
                                    size = Size(
                                        width = valueWidth.toPx(),
                                        height = height2,
                                    ),
                                ),
                                topLeft = CornerRadius(valueCornerRadius.toPx()),
                                topRight = CornerRadius(valueCornerRadius.toPx()),
                            )
                        )
                    },
                    color = color2,
                )
            }
        }
    }

    @OptIn(ExperimentalTextApi::class)
    private fun drawFocus(
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

        val textStyle = TextStyle(fontSize = 20.sp)
        val symbolTextLayoutResult = textMeasurer.measure(
            text = AnnotatedString(symbol),
            style = textStyle
        )
        val diffTextLayoutResult = textMeasurer.measure(
            text = AnnotatedString("$${String.format("%.2f", diff)}"),
            style = textStyle
        )

        drawScope.run {
            val textPadding = 8.dp
            val boxCornerRadius = 8.dp
            val arrayCornerRadius = 2.dp
            val arrayHeight = 8.dp
            val arrayWidth = 16.dp

            val textHeight = symbolTextLayoutResult.size.height
            val textWidth = symbolTextLayoutResult.size.width + diffTextLayoutResult.size.width

            val arrayRect = Rect(
                topLeft = Offset(
                    itemRect.center.x - arrayWidth.toPx() / 2,
                    rect.bottom - arrayHeight.toPx(),
                ),
                bottomRight = Offset(
                    itemRect.center.x + arrayWidth.toPx() / 2,
                    rect.bottom,
                ),
            )
            val arrayPath = Path().apply {
                moveTo(arrayRect.center.x, arrayRect.bottom)
                lineTo(arrayRect.topRight.x, arrayRect.topRight.y - arrayCornerRadius.toPx())
                lineTo(arrayRect.topLeft.x, arrayRect.topLeft.y - arrayCornerRadius.toPx())
                close()
            }

            val boxHeight = rect.height - arrayHeight.toPx()
            val boxWidth = textWidth + textPadding.toPx() + textPadding.toPx()

            var boxLeft = itemRect.center.x - boxWidth / 2
            if (boxWidth < rect.right - rect.left) {
                boxLeft = maxOf(boxLeft, rect.left)
                boxLeft = minOf(boxLeft, rect.right - boxWidth)
            }
            val boxTopLeft = rect.topLeft.copy(x = boxLeft)

            val symbolTopLeft = Offset(
                x = boxTopLeft.x + textPadding.toPx(),
                y = boxTopLeft.y + boxHeight / 2 - textHeight / 2,
            )
            val diffTopLeft = Offset(
                x = symbolTopLeft.x + symbolTextLayoutResult.size.width,
                y = symbolTopLeft.y,
            )

            // debug
            drawRoundRect(
                color = Color.Cyan,
                alpha = 0.2f,
                cornerRadius = CornerRadius(boxCornerRadius.toPx()),
                topLeft = boxTopLeft,
                size = Size(width = boxWidth, height = boxHeight),
            )
            drawIntoCanvas { canvas ->
                canvas.drawOutline(
                    outline = Outline.Generic(arrayPath),
                    paint = Paint().apply {
                        color = Color.Cyan
                        alpha = 0.2f
                        pathEffect = PathEffect.cornerPathEffect(arrayCornerRadius.toPx())
                    }
                )
            }
            drawText(
                textLayoutResult = symbolTextLayoutResult,
                color = symbolColor,
                topLeft = symbolTopLeft,
            )
            drawText(
                textLayoutResult = diffTextLayoutResult,
                color = Color.Black,
                topLeft = diffTopLeft,
            )
        }
    }

    @OptIn(ExperimentalTextApi::class)
    private fun drawLabel(
        drawScope: DrawScope,
        rect: Rect,
        labelTexts: List<TextLayoutResult>,
    ) {
        drawScope.run {
            // debug
            drawRect(
                color = Color.Cyan,
                alpha = 0.2f,
                topLeft = rect.topLeft,
                size = rect.size,
            )

            val itemWidth = (rect.right - rect.left) / labelTexts.size
            labelTexts.forEachIndexed { index, textLayoutResult ->
                val widthDiff = itemWidth - textLayoutResult.size.width
                drawTextRect(
                    textLayoutResult = textLayoutResult,
                    gravity = DrawGravity.CenterHorizontal,
                    rect = Rect(
                        topLeft = Offset(
                            x = rect.left + index * itemWidth,
                            y = rect.top
                        ),
                        bottomRight = Offset(
                            x = rect.left + (index + 1) * itemWidth,
                            y = rect.bottom
                        ),
                    ),
                )

                val indicatorSize = 3.dp.toPx()
                drawRoundRect(
                    color = Color.Red,
                    cornerRadius = CornerRadius(indicatorSize),
                    topLeft = Offset(
                        rect.left + index * itemWidth + widthDiff / 2,
                        rect.top + textLayoutResult.size.height
                    ),
                    size = Size(
                        width = textLayoutResult.size.width.toFloat(),
                        height = indicatorSize
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

        drawScope.run {
            // debug
            drawRect(
                color = Color.Cyan,
                alpha = 0.2f,
                topLeft = rect.topLeft,
                size = rect.size,
            )

            val axisTextHeight = scaleTexts.first().size.height
            val itemWidth = scaleTexts.maxByOrNull { it.size.width }?.size?.width
                ?: throw RuntimeException()
            val itemHeight = (rect.bottom - rect.top) / (scaleTexts.size - 1)
            scaleTexts.forEachIndexed { index, textLayoutResult ->
                drawTextRect(
                    textLayoutResult = textLayoutResult,
                    gravity = DrawGravity.Right,
                    rect = Rect(
                        offset = Offset(
                            x = rect.left,
                            y = rect.bottom - itemHeight * index - axisTextHeight / 2,
                        ),
                        size = Size(
                            width = itemWidth.toFloat(),
                            height = axisTextHeight.toFloat(),
                        )
                    ),
                )
            }
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
