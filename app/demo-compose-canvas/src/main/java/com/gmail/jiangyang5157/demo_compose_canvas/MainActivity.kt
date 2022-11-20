package com.gmail.jiangyang5157.demo_compose_canvas

import androidx.activity.ComponentActivity

class MainActivity : ComponentActivity() {

//    @ExperimentalTextApi
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//
//        setContentView(R.layout.activity_main)
//        findViewById<ComposeView>(R.id.composeView).setContent {
//            MaterialTheme {
//                Column {
//                    Row {
//                        Button(onClick = {
//                            // TODO YangJ
//                        }) {
//                            Text(text = "next")
//                        }
//                    }
//                    Graph()
//                }
//            }
//        }
//    }
//
//    private val items = listOf(
//        Item("APR", 1000.0, 1500.0),
//        Item("MAY", 100.0, 150.0),
//        Item("JUN", 2000.0, 2499.9),
//        Item("JUL", 1500.0, 3500.0),
//        Item("AUG", 4999.0, 2200.0),
//        Item("SEP", 3200.0, 900.0),
//    )
//    private val itemRects = items.map { ItemRect(it) }
//
//    @OptIn(ExperimentalTextApi::class)
//    @Composable
//    private fun Graph() {
//        val textMeasurer = rememberTextMeasurer()
//        var focusedItemRect: ItemRect? by remember { mutableStateOf(null) }
//
//        Canvas(
//            modifier = Modifier
//                .fillMaxSize()
//                .background(color = Color.LightGray)
//                .pointerInput(Unit) {
//                    detectTapGestures(
//                        onTap = { tapOffset ->
//                            val tapItemRect =
//                                itemRects.firstOrNull { it.rect?.contains(tapOffset) == true }
//                            focusedItemRect = tapItemRect
//                            Log.d("####", "Tap $tapOffset $focusedItemRect")
//                        }
//                    )
//                }
//
//        ) {
//            val graphRect = this.size.toRect()
//
//            val padding = 8.dp
//            val indicatorHeight = 24.dp
//
//            val indicatorRect = Rect(
//                topLeft = Offset(
//                    graphRect.left + 64.dp.toPx(),
//                    graphRect.bottom - indicatorHeight.toPx() - padding.toPx()
//                ),
//                bottomRight = Offset(
//                    graphRect.right - 64.dp.toPx(),
//                    graphRect.bottom - padding.toPx(),
//                ),
//            )
//            val contentRect = Rect(
//                topLeft = Offset(
//                    graphRect.left + padding.toPx(),
//                    graphRect.top + padding.toPx(),
//                ),
//                bottomRight = Offset(
//                    graphRect.right - padding.toPx(),
//                    indicatorRect.top - padding.toPx(),
//                ),
//            )
//
//            drawGraphLabel(
//                textMeasurer = textMeasurer,
//                rect = indicatorRect,
//                orientation = DrawOrientation.Horizontal,
//                items = listOf(
//                    Pair(Color.DarkGray, "iOS"),
//                    Pair(Color.Blue, "Android"),
//                ),
//            )
//
//            drawContent(
//                this,
//                textMeasurer,
//                contentRect,
//                Color.DarkGray,
//                Color.Blue,
//                itemRects,
//                focusedItemRect,
//            )
//        }
//    }
//
//    @OptIn(ExperimentalTextApi::class)
//    private fun drawContent(
//        drawScope: DrawScope,
//        textMeasurer: TextMeasurer,
//        rect: Rect,
//        color1: Color,
//        color2: Color,
//        itemRects: List<ItemRect>,
//        focusedItemRect: ItemRect?,
//    ) {
//        if (itemRects.isEmpty()) return
//
//        val maxMoney =
//            itemRects.maxByOrNull { it.item.maxValue }?.item?.maxValue ?: throw RuntimeException()
//        val primaryFactor = 3
//        val secondaryFactor = 4
//
//        val roundUpMaxMoney = roundUpMoney(maxMoney)
//        Log.d("####", "Round up $maxMoney to $roundUpMaxMoney")
//
//        val primaryDivisor = factor2Divisor(roundUpMaxMoney, primaryFactor)
//        val secondaryDivisor = factor2Divisor(roundUpMaxMoney, secondaryFactor)
//        Log.d(
//            "####",
//            "Prepare divisors $primaryDivisor (primary) and $secondaryDivisor from factors $primaryFactor (primary) and $secondaryFactor"
//        )
//
//        val moneyToDivision = roundUpToDivision(roundUpMaxMoney, primaryDivisor, secondaryDivisor)
//        val highest = moneyToDivision.first
//        val factory = divisor2Factor(highest, moneyToDivision.second)
//        Log.d(
//            "####",
//            "Round up $roundUpMaxMoney to $highest by chosen division ${moneyToDivision.second} factory $factory"
//        )
//
//        val scaleList = buildScaleList(highest, factory)
//        val scaleMoneyList = scaleList.map { buildMoneyAbbr(it) }
//        Log.d("####", "Build scale list $scaleList")
//        Log.d("####", "Build scale money list $scaleMoneyList")
//
//        drawScope.run {
//            val axisTextStyle = TextStyle(fontSize = 16.sp)
//            val scaleTextLayoutResults = scaleMoneyList.map {
//                textMeasurer.measure(
//                    text = AnnotatedString(it),
//                    style = axisTextStyle
//                )
//            }
//            val labelTexts = itemRects.map { it.item.label }
//
//            val padding = 4.dp
//            val focusHeight = 48.dp
//            val focusTop = rect.top
//            val dataRight = rect.right
//            val scaleLeft = rect.left
//            val labelBottom = rect.bottom
//            val axisTextHeight = itemRects.first().let {
//                textMeasurer.measure(
//                    text = AnnotatedString(it.item.label),
//                    style = axisTextStyle
//                ).size.height
//            }
//            val labelHeight = axisTextHeight
//
//            val focusBottom = focusTop + focusHeight.toPx()
//            val scaleTop = focusBottom + padding.toPx()
//            val dataTop = scaleTop
//
//            val scaleWidth =
//                scaleTextLayoutResults.maxByOrNull { it.size.width }?.size?.width
//                    ?: throw RuntimeException()
//            val scaleRight = scaleLeft + scaleWidth
//            val dataLeft = scaleRight + padding.toPx()
//
//            val labelTop = labelBottom - labelHeight
//
//            val itemRectWidth = (dataRight - dataLeft) / itemRects.size
//            val itemRectHeight = labelBottom - dataTop
//            itemRects.forEachIndexed { index, itemRect ->
//                itemRect.rect = Rect(
//                    offset = Offset(
//                        x = dataLeft + itemRectWidth * index,
//                        y = dataTop,
//                    ),
//                    size = Size(
//                        width = itemRectWidth,
//                        height = itemRectHeight,
//                    ),
//                )
//            }
//
//            // ================================================================
//
//            val dataRect = drawDataAxis(
//                rect = Rect(
//                    topLeft = Offset(
//                        x = rect.left,
//                        y = focusBottom,
//                    ),
//                    bottomRight = Offset(
//                        x = rect.right,
//                        y = labelTop,
//                    ),
//                ),
//                textStyle = TextStyle(fontSize = 16.sp, color = color1),
//                textMeasurer = textMeasurer,
//                lineColor = Color.DarkGray,
//                items = scaleMoneyList,
//            )
//
//            // ================================================================
//
//            drawDialog(
//                this,
//                textMeasurer,
//                Rect(
//                    topLeft = Offset(
//                        x = dataRect.left,
//                        y = rect.top,
//                    ),
//                    bottomRight = Offset(
//                        x = dataRect.right,
//                        y = dataRect.top,
//                    )
//                ),
//                focusedItemRect,
//            )
//
//            // ================================================================
//
//            val underlineLabelPaddingTop = 6.dp.toPx()
//            val underlineLabelRect = Rect(
//                offset = dataRect.bottomLeft.plus(Offset(0f, underlineLabelPaddingTop)),
//                size = Size(
//                    width = dataRect.width,
//                    height = labelHeight.toFloat(),
//                )
//            )
//            val underlineItemWidth = underlineLabelRect.width / labelTexts.size
//
//            labelTexts.forEachIndexed { index, text ->
//                val selected = text == focusedItemRect?.item?.label
//                drawUnderlineLabel(
//                    textMeasurer = textMeasurer,
//                    underlineColor = Color.Blue,
//                    text = text,
//                    textStyle = TextStyle(fontSize = 16.sp, color = Color.DarkGray),
//                    selected = selected,
//                    rect = Rect(
//                        offset = Offset(
//                            x = underlineLabelRect.left + underlineItemWidth * index,
//                            y = underlineLabelRect.top,
//                        ),
//                        size = Size(
//                            width = underlineItemWidth,
//                            height = underlineLabelRect.height
//                        ),
//                    ),
//                )
//            }
//
//            // ================================================================
//
//            drawData(
//                this,
//                dataRect,
//                color2,
//                color1,
//                itemRects,
//                scaleList
//            )
//
//            // ================================================================
//        }
//    }
//
//    private fun drawData(
//        drawScope: DrawScope,
//        rect: Rect,
//        color1: Color,
//        color2: Color,
//        itemRects: List<ItemRect>,
//        scaleList: List<Int>,
//    ) {
//        if (scaleList.size < 2) throw IllegalArgumentException("Scale size should not less than 2")
//
//        drawScope.run {
//            // debug
//            drawRect(
//                color = Color.Cyan,
//                alpha = 0.2f,
//                topLeft = rect.topLeft,
//                size = rect.size,
//            )
//
//            val scaleStep = rect.size.height / (scaleList.size - 1)
//            scaleList.forEachIndexed { index, _ ->
//                drawLine(
//                    color = Color.Black,
//                    start = Offset(rect.left, rect.bottom - index * scaleStep),
//                    end = Offset(rect.right, rect.bottom - index * scaleStep),
//                )
//            }
//
//            val itemWidth = (rect.right - rect.left) / itemRects.size
//            val maxScale = scaleList.maxOf { it }
//            val ratio = rect.height / maxScale
//
//            itemRects.forEachIndexed { index, itemRect ->
//                val height1 = itemRect.item.value1.toFloat() * ratio
//                val height2 = itemRect.item.value2.toFloat() * ratio
//
//                drawColumn(
//                    items = listOf(
//                        Pair(color1, height1),
//                        Pair(color2, height2),
//                    ),
//                    rect = Rect(
//                        offset = Offset(
//                            x = rect.left + itemWidth * index,
//                            y = rect.top,
//                        ),
//                        size = Size(
//                            width = itemWidth,
//                            height = rect.height,
//                        ),
//                    ),
//                )
//            }
//        }
//    }
//
//    @OptIn(ExperimentalTextApi::class)
//    private fun drawDialog(
//        drawScope: DrawScope,
//        textMeasurer: TextMeasurer,
//        rect: Rect,
//        focusedItemRect: ItemRect?,
//    ) {
//        val item = focusedItemRect?.item ?: return
//        val itemRect = focusedItemRect.rect ?: return
//
//        val symbol = if (item.diff >= 0) "+" else "-"
//        val symbolColor = if (item.diff >= 0) Color.Green else Color.Red
//        val diff = abs(item.diff)
//
//        val symbolTextLayoutResult = textMeasurer.measure(
//            text = AnnotatedString(symbol),
//            style = TextStyle(fontSize = 20.sp)
//        )
//        val diffTextLayoutResult = textMeasurer.measure(
//            text = AnnotatedString("$${String.format("%.2f", diff)}"),
//            style = TextStyle(fontSize = 20.sp)
//        )
//
//        drawScope.run {
//            drawUpperDialog(
//                rect = rect,
//                color = Color.Cyan,
//                x = itemRect.center.x,
//                calculateContent = {
//                    Size(
//                        width = (symbolTextLayoutResult.size.width + diffTextLayoutResult.size.width).toFloat(),
//                        height = symbolTextLayoutResult.size.height.toFloat()
//                    )
//                },
//                // draw content
//                drawContent = { drawScope, rect ->
//                    drawScope.drawText(
//                        textLayoutResult = symbolTextLayoutResult,
//                        color = symbolColor,
//                        topLeft = rect.topLeft,
//                    )
//                    drawScope.drawText(
//                        textLayoutResult = diffTextLayoutResult,
//                        color = Color.Black,
//                        topLeft = rect.topLeft.copy(
//                            x = rect.topLeft.x + symbolTextLayoutResult.size.width
//                        ),
//                    )
//                }
//            )
//        }
//    }
//
//    data class Item(
//        val label: String,
//        val value1: Double,
//        val value2: Double,
//    ) {
//        val maxValue = maxOf(value1, value2)
//        val diff: Double = value1 - value2
//    }
//
//    data class ItemRect(
//        val item: Item,
//        var rect: Rect? = null,
//    )
}
