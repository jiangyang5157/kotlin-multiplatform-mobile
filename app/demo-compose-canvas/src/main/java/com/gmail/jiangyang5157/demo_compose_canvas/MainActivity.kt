package com.gmail.jiangyang5157.demo_compose_canvas

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.geometry.toRect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.text.*
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gmail.jiangyang5157.demo_compose_canvas.render.*
import kotlin.math.abs
import kotlin.random.Random

class MainActivity : ComponentActivity() {

    data class Item(
        val name: String,
        val value1: Double,
        val value2: Double,
    ) {
        val maxValue = maxOf(value1, value2)
        val diff: Double = value1 - value2

        companion object {
            const val value1Name = "Android"
            const val value2Name = "iOS"
            val value1Color = Color.DarkGray
            val value2Color = Color.Blue
        }
    }

    private val itemRepository = listOf(
        Item("001", 1777.0, 1500.0),
        Item("002", 100.0, 150.0),
        Item("003", 3456.0, 2499.9),
        Item("004", 1500.0, 3500.0),
        Item("005", 4999.0, 2200.0),
        Item("006", 3200.0, 900.0),
        Item("007", 345.0, 456.0),
        Item("008", 1111.11, 2222.22),
        Item("009", 2222.22, 1111.11),
    )

    private fun nextItems(): List<Item> {
        return itemRepository.filter { Random.nextInt() % 2 == 0 }.also {
            Log.d("####", "nextItems size ${it.size}: $it")
        }
    }

    private fun buildScaleList(
        items: List<Item>,
        primaryFactor: Int,
        secondaryFactor: Int
    ): List<Long> {
        val maxValue = items.maxByOrNull { it.maxValue }?.maxValue ?: throw RuntimeException()
        val roundUpValue = AxisScaleUtils.RoundUpScale.roundUp(maxValue)
        Log.d("####", "Round up $maxValue to $roundUpValue")

        val axisScaleUtils = AxisScaleUtils()
        val primaryDivisor = axisScaleUtils.factorToDivisor(roundUpValue, primaryFactor)
        val secondaryDivisor = axisScaleUtils.factorToDivisor(roundUpValue, secondaryFactor)
        Log.d(
            "####",
            "Prepare divisors $primaryDivisor (primary) and $secondaryDivisor from factors $primaryFactor (primary) and $secondaryFactor"
        )
        val valueForDivision =
            axisScaleUtils.roundUpForDivision(roundUpValue, primaryDivisor, secondaryDivisor)
        val highest = valueForDivision.first
        val factory = axisScaleUtils.divisorToFactor(highest, valueForDivision.second)
        Log.d(
            "####",
            "Round up $roundUpValue to $highest by chosen division ${valueForDivision.second} (factory $factory)"
        )

        val scaleList = axisScaleUtils.buildScaleList(highest, factory)
        Log.d("####", "Scale list $scaleList")

        return scaleList
    }

    @ExperimentalTextApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<ComposeView>(R.id.composeView).setContent {
            MaterialTheme {
                Content()
            }
        }
    }

    data class CanvasUio(
        val items: List<Item>,
        val itemRects: List<Rect>,
        val itemIndex: Int,
        val factor: Pair<Int, Int>,
    )

    @ExperimentalTextApi
    @Composable
    private fun Content() {
        Log.d("####", "Content")
        val textMeasurer = rememberTextMeasurer()
        var uio by remember {
            mutableStateOf(
                CanvasUio(
                    items = nextItems(),
                    itemRects = emptyList(),
                    itemIndex = -1,
                    factor = Pair(3, 2),
                )
            )
        }

        Column {
            Row {
                Button(onClick = {
                    uio = uio.copy(
                        items = nextItems(),
                        itemRects = emptyList(),
                        itemIndex = -1,
                    )
                }) {
                    Text(text = "next")
                }
            }
            Row {
                Button(onClick = {
                    uio = uio.copy(factor = Pair(2, uio.factor.second))
                }) {
                    Text(text = "primary factor 2")
                }
                Button(onClick = {
                    uio = uio.copy(factor = Pair(3, uio.factor.second))
                }) {
                    Text(text = "3")
                }
                Button(onClick = {
                    uio = uio.copy(factor = Pair(4, uio.factor.second))
                }) {
                    Text(text = "4")
                }
            }
            Row {
                Button(onClick = {
                    uio = uio.copy(factor = Pair(uio.factor.first, 2))
                }) {
                    Text(text = "secondary factor 2")
                }
                Button(onClick = {
                    uio = uio.copy(factor = Pair(uio.factor.first, 3))
                }) {
                    Text(text = "3")
                }
                Button(onClick = {
                    uio = uio.copy(factor = Pair(uio.factor.first, 4))
                }) {
                    Text(text = "4")
                }
            }
            Canvas(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = Color.LightGray)
                    .pointerInput(Unit) {
                        detectTapGestures(
                            onTap = { tapOffset ->
                                uio = uio.copy(itemIndex = uio.itemRects.indexOfFirst {
                                    it.contains(tapOffset)
                                })
                                Log.d("####", "Tap $tapOffset belongs to item ${uio.itemIndex}")
                            }
                        )
                    },
            ) {
                Log.d("####", "Canvas")
                if (uio.items.isEmpty()) return@Canvas

                val padding = 8.dp.toPx()
                val rect = this.size.toRect().deflate(padding)
                val itemsFontSize = 16.sp
                val underlineLabelTextLayoutResult = uio.items.first().let {
                    textMeasurer.measure(
                        AnnotatedString(it.name),
                        TextStyle(fontSize = itemsFontSize, color = Color.DarkGray)
                    )
                }
                val underlineTextHeight = underlineLabelTextLayoutResult.size.height
                val underlineHeight = 4.dp.toPx()
                val underlineLabelHeight = underlineTextHeight + underlineHeight

                // #### GraphLabel ================================================================

                val graphLabelHeight = 24.dp.toPx()
                val graphLabelTextLayoutResult = Item.value1Name.let {
                    textMeasurer.measure(
                        AnnotatedString(it),
                        TextStyle(fontSize = itemsFontSize)
                    )
                }

                val graphLabelRect = Rect(
                    topLeft = Offset(
                        rect.left + 64.dp.toPx(),
                        rect.bottom - graphLabelHeight
                    ),
                    bottomRight = Offset(
                        rect.right - 64.dp.toPx(),
                        rect.bottom,
                    ),
                )
                drawGraphLabel(
                    textMeasurer = textMeasurer,
                    rect = graphLabelRect,
                    textStyle = graphLabelTextLayoutResult.layoutInput.style,
                    orientation = DrawOrientation.Horizontal,
                    items = listOf(
                        Pair(Item.value1Name, Item.value1Color),
                        Pair(Item.value2Name, Item.value2Color),
                    ),
                )

                // #### DataAxis ================================================================

                val scaleList = buildScaleList(uio.items, uio.factor.first, uio.factor.second)
                val scaleListString = scaleList.map { it.toString() }
                val dataAxisTextLayoutResult = scaleListString.first().let {
                    textMeasurer.measure(
                        AnnotatedString(it),
                        TextStyle(fontSize = itemsFontSize, color = Color.DarkGray)
                    )
                }
                val upperDialogHeight = 48.dp.toPx()
                val dataAxisRect = Rect(
                    topLeft = Offset(
                        x = rect.left,
                        y = rect.top + upperDialogHeight,
                    ),
                    bottomRight = Offset(
                        x = rect.right,
                        y = graphLabelRect.top - underlineLabelHeight - padding,
                    ),
                )
                val dataRect = drawDataAxis(
                    rect = dataAxisRect,
                    textStyle = dataAxisTextLayoutResult.layoutInput.style,
                    textMeasurer = textMeasurer,
                    lineColor = dataAxisTextLayoutResult.layoutInput.style.color,
                    items = scaleListString,
                )

                // #### UnderlineLabel(s) ================================================================

                val positiveColor = Color.Yellow
                val negativeColor = Color.Red
                val underlineColor = Color.DarkGray
                val upperDialogColor = Color.DarkGray
                val upperDialogFontSize = 20.sp

                val underlineLabelsRect = Rect(
                    topLeft = Offset(
                        x = dataRect.left,
                        y = dataRect.bottom + padding,
                    ),
                    bottomRight = Offset(
                        x = dataRect.right,
                        y = dataRect.bottom + padding + underlineLabelHeight,
                    )
                )
                val underlineItemWidth = underlineLabelsRect.width / uio.items.size
                val newItemRects = mutableListOf<Rect>()
                uio.items.forEachIndexed { index, item ->
                    val selected = index == uio.itemIndex
                    val underlineLabelRect = Rect(
                        offset = Offset(
                            x = underlineLabelsRect.left + underlineItemWidth * index,
                            y = underlineLabelsRect.top,
                        ),
                        size = Size(
                            width = underlineItemWidth,
                            height = underlineLabelsRect.height
                        ),
                    )
                    newItemRects.add(index, underlineLabelRect.copy(top = dataRect.top))
                    drawUnderlineLabel(
                        textMeasurer = textMeasurer,
                        underlineColor = underlineColor,
                        underlineHeight = underlineHeight,
                        text = item.name,
                        textStyle = underlineLabelTextLayoutResult.layoutInput.style,
                        selected = selected,
                        rect = underlineLabelRect,
                    )
                }
                uio = uio.copy(itemRects = newItemRects)

                // #### Column(s) ================================================================

                val maxValue = scaleList.maxOf { it }
                val ratio = dataRect.height / maxValue
                val columnWidth = dataRect.width / uio.items.size
                uio.items.forEachIndexed { index, item ->
                    val height1 = item.value1 * ratio
                    val height2 = item.value2 * ratio

                    drawColumn(
                        items = listOf(
                            Pair(height1.toFloat(), Item.value1Color),
                            Pair(height2.toFloat(), Item.value2Color),
                        ),
                        rect = Rect(
                            offset = Offset(
                                x = dataRect.left + columnWidth * index,
                                y = dataRect.top,
                            ),
                            size = Size(
                                width = columnWidth,
                                height = dataRect.height,
                            ),
                        ),
                    )
                }

                // #### UpperDialog ================================================================

                if (uio.itemIndex != -1) {
                    val item = uio.items[uio.itemIndex]
                    val itemRect = uio.itemRects[uio.itemIndex]
                    val symbol = if (item.diff >= 0) "+" else "-"
                    val upperDialogTextColor = if (item.diff >= 0) positiveColor else negativeColor
                    val diff = abs(item.diff)
                    val symbolTextLayoutResult = textMeasurer.measure(
                        text = AnnotatedString(symbol),
                        style = TextStyle(
                            fontSize = upperDialogFontSize,
                            color = upperDialogTextColor
                        )
                    )
                    val diffTextLayoutResult = textMeasurer.measure(
                        text = AnnotatedString("$${String.format("%.2f", diff)}"),
                        style = TextStyle(
                            fontSize = upperDialogFontSize,
                            color = upperDialogTextColor
                        )
                    )
                    val upperDialogRect = Rect(
                        topLeft = Offset(
                            x = dataRect.left,
                            y = rect.top,
                        ),
                        bottomRight = Offset(
                            x = dataRect.right,
                            y = dataRect.top,
                        )
                    )
                    drawUpperDialog(
                        rect = upperDialogRect,
                        color = upperDialogColor,
                        x = itemRect.center.x,
                        calculateContent = {
                            Size(
                                width = (symbolTextLayoutResult.size.width + diffTextLayoutResult.size.width).toFloat(),
                                height = symbolTextLayoutResult.size.height.toFloat(),
                            )
                        },
                        drawContent = { contentRect ->
                            drawText(
                                textLayoutResult = symbolTextLayoutResult,
                                topLeft = contentRect.topLeft,
                            )
                            drawText(
                                textLayoutResult = diffTextLayoutResult,
                                topLeft = contentRect.topLeft.copy(
                                    x = contentRect.topLeft.x + symbolTextLayoutResult.size.width,
                                ),
                            )
                        }
                    )
                }
            }
        }
    }
}