package com.gmail.jiangyang5157.demo_compose_canvas.render

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.geometry.toRect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * Draw y-axis labels and return the data rect
 */
@ExperimentalTextApi
fun DrawScope.drawDataAxis(
    rect: Rect,
    items: List<CharSequence>,
    textStyle: TextStyle = TextStyle(fontSize = 16.sp),
    textMeasurer: TextMeasurer,
    lineColor: Color = Color.LightGray,
    lineStrokeWidth: Float = Stroke.HairlineWidth,
    lineAlpha: Float = 1.0f,
): Rect {
    if (items.isEmpty()) throw IllegalArgumentException("Empty items")

    val itemTextLayoutResults = items.map {
        textMeasurer.measure(
            text = AnnotatedString(it.toString()), style = textStyle
        )
    }
    // recognize the uniform text height
    val itemHeight = itemTextLayoutResults.first().size.height.toFloat()
    // recognize the largest text width
    val itemWidth = itemTextLayoutResults.maxByOrNull { it.size.width }?.size?.width?.toFloat()
        ?: throw RuntimeException()

    // x-axis starts from the middle of the y-axis text
    val verticalPadding = itemHeight / 2
    val itemPaddingRight = verticalPadding

    val ret = Rect(
        topLeft = rect.topLeft.plus(
            Offset((itemWidth + itemPaddingRight), verticalPadding),
        ),
        bottomRight = rect.bottomRight.plus(
            Offset(0f, -verticalPadding),
        ),
    )

    val itemPaddingBetween = if (items.size == 1) {
        0f
    } else {
        ret.height / (items.size - 1) - itemHeight
    }
    items.forEachIndexed { index, item ->
        val itemTopLeft = rect.bottomLeft.plus(
            Offset(0f, -(itemHeight * (index + 1) + itemPaddingBetween * index))
        )
        val itemBottomRight = rect.bottomLeft.plus(
            Offset(itemWidth, -(itemHeight * index + itemPaddingBetween * index))
        )
        val itemRect = Rect(
            topLeft = itemTopLeft,
            bottomRight = itemBottomRight,
        )
        drawTextInRect(
            text = item,
            textStyle = textStyle,
            textMeasurer = textMeasurer,
            gravity = DrawGravity.Right,
            rect = itemRect,
        )
        drawLine(
            color = lineColor,
            alpha = lineAlpha,
            strokeWidth = lineStrokeWidth,
            start = Offset(ret.left, itemRect.center.y),
            end = Offset(ret.right, itemRect.center.y),
        )
    }

    return ret
}

@ExperimentalTextApi
@Preview(widthDp = 300, heightDp = 800)
@Composable
private fun DrawDataAxisPreview() {
    MaterialTheme {
        val color1 = Color.DarkGray
        val textMeasurer = rememberTextMeasurer()

        Canvas(
            modifier = Modifier.fillMaxSize()
        ) {
            val graphRect = this.size.toRect()
            val padding = 16.dp.toPx()

            val rect1 = Rect(
                offset = Offset(
                    x = graphRect.left + padding,
                    y = graphRect.top + padding,
                ),
                size = Size(
                    width = 240.dp.toPx(),
                    height = 120.dp.toPx(),
                ),
            )
            val rect2 = Rect(
                offset = Offset(
                    x = rect1.left,
                    y = rect1.bottom + padding,
                ),
                size = rect1.size,
            )
            val rect3 = Rect(
                offset = Offset(
                    x = rect2.left,
                    y = rect2.bottom + padding,
                ),
                size = rect2.size,
            )
            val rect4 = Rect(
                offset = Offset(
                    x = rect3.left,
                    y = rect3.bottom + padding,
                ),
                size = rect3.size,
            )
            drawRect(
                color = Color.LightGray,
                topLeft = rect1.topLeft,
                size = rect1.size,
            )
            drawRect(
                color = Color.LightGray,
                topLeft = rect2.topLeft,
                size = rect2.size,
            )
            drawRect(
                color = Color.LightGray,
                topLeft = rect3.topLeft,
                size = rect3.size,
            )
            drawRect(
                color = Color.LightGray,
                topLeft = rect4.topLeft,
                size = rect4.size,
            )

            val dataRect1 = drawDataAxis(
                rect = rect1,
                textStyle = TextStyle(fontSize = 10.sp, color = color1),
                textMeasurer = textMeasurer,
                lineColor = color1,
                lineAlpha = 0.5f,
                items = listOf(
                    "right", "align ---------------->",
                )
            )
            val dataRect2 = drawDataAxis(
                rect = rect2,
                textStyle = TextStyle(fontSize = 20.sp, color = color1),
                textMeasurer = textMeasurer,
                lineColor = color1,
                lineAlpha = 0.5f,
                items = listOf(
                    "20", "size", "font"
                )
            )
            val dataRect3 = drawDataAxis(
                rect = rect3,
                textStyle = TextStyle(fontSize = 10.sp, color = color1),
                textMeasurer = textMeasurer,
                lineColor = color1,
                lineAlpha = 0.5f,
                items = listOf(
                    "$0", "$1k", "$123k", "$1,234k"
                )
            )
            val dataRect4 = drawDataAxis(
                rect = rect4,
                textStyle = TextStyle(fontSize = 10.sp, color = color1),
                textMeasurer = textMeasurer,
                lineColor = color1,
                lineAlpha = 0.5f,
                items = listOf(
                    "A",
                    "Ab",
                    "Abc",
                    "Abcd",
                    "Abcde",
                    "Abcdef",
                    "Abcdefg",
                    "Abcdefgh",
                    "Abcdefghi",
                    "Abcdefghij",
                    "Abcdefghijk",
                )
            )
            drawRect(
                color = color1,
                topLeft = dataRect1.topLeft,
                size = dataRect1.size,
                alpha = 0.2f,
            )
            drawRect(
                color = color1,
                topLeft = dataRect2.topLeft,
                size = dataRect2.size,
                alpha = 0.2f,
            )
        }
    }
}