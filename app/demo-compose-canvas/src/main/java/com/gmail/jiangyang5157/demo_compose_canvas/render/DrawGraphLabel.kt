package com.gmail.jiangyang5157.demo_compose_canvas.render

import android.content.res.Configuration
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
import androidx.compose.ui.text.*
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * Draw vertical or horizontal graph label (color circle + uniform text)
 */
@ExperimentalTextApi
fun DrawScope.drawGraphLabel(
    textMeasurer: TextMeasurer,
    items: List<Pair<Color, CharSequence>>,
    rect: Rect,
    textColor: Color = Color.Unspecified,
    orientation: Int = DrawOrientation.Horizontal,
    textStyle: TextStyle = TextStyle(fontSize = 16.sp),
    textDecoration: TextDecoration? = null,
) {
    if (items.isEmpty()) return

    val labels = items.map {
        Pair(
            it.first,
            textMeasurer.measure(
                text = AnnotatedString(it.second.toString()),
                style = textStyle,
            ),
        )
    }
    // recognize the uniform text height
    val textHeight = labels.first().second.size.height
    // recognize the largest text width
    var textWidth = labels.first().second.size.width
    for (labelText in labels) {
        textWidth = maxOf(textWidth, labelText.second.size.width)
    }

    // circle size relative to text height
    val circleRadius = textHeight * 0.3f
    val circleDiameter = circleRadius * 2
    val circleTextPadding = 8.dp.toPx()

    // calculate label size
    val labelHeight = textHeight
    val labelWidth = circleDiameter + circleTextPadding + textWidth

    // padding between 2 labels
    val labelPadding = when (orientation) {
        DrawOrientation.Horizontal -> {
            (rect.width - labelWidth * labels.size) / (labels.size + 1)
        }
        DrawOrientation.Vertical -> {
            (rect.height - labelHeight * labels.size) / (labels.size + 1)
        }
        else -> throw IllegalArgumentException("Undefined orientation")
    }

    labels.forEachIndexed { index, pair ->
        val color = pair.first
        val textLayoutResult = pair.second

        when (orientation) {
            DrawOrientation.Horizontal -> {
                val circleTopLeft = Offset(
                    x = rect.left + labelWidth * index + labelPadding * (index + 1),
                    y = rect.top,
                )
                val circleBottomRight = Offset(
                    x = circleTopLeft.x + circleDiameter + circleTextPadding,
                    y = rect.bottom,
                )
                val textTopLeft = Offset(
                    x = circleBottomRight.x,
                    y = rect.top,
                )
                val textBottomRight = Offset(
                    x = circleBottomRight.x + textWidth,
                    y = rect.bottom,
                )
                drawCircleInRect(
                    color = color,
                    radius = circleRadius,
                    gravity = DrawGravity.CenterVertical.addFlag(DrawGravity.Left),
                    rect = Rect(
                        topLeft = circleTopLeft,
                        bottomRight = circleBottomRight,
                    ),
                )
                drawTextInRect(
                    textLayoutResult = textLayoutResult,
                    textDecoration = textDecoration,
                    gravity = DrawGravity.CenterVertical,
                    color = textColor,
                    rect = Rect(
                        topLeft = textTopLeft,
                        bottomRight = textBottomRight,
                    ),
                )
            }
            DrawOrientation.Vertical -> {
                val circleTopLeft = Offset(
                    x = rect.left + (rect.size.width - labelWidth) / 2,
                    y = rect.top + (labelHeight * index + labelPadding * (index + 1)),
                )
                val circleBottomRight = Offset(
                    x = circleTopLeft.x + circleDiameter + circleTextPadding,
                    y = circleTopLeft.y + labelHeight,
                )
                val textTopLeft = Offset(
                    x = circleBottomRight.x,
                    y = circleTopLeft.y,
                )
                val textBottomRight = Offset(
                    x = circleBottomRight.x + textWidth,
                    y = circleBottomRight.y,
                )
                drawCircleInRect(
                    color = color,
                    radius = circleRadius,
                    gravity = DrawGravity.CenterVertical.addFlag(DrawGravity.Left),
                    rect = Rect(
                        topLeft = circleTopLeft,
                        bottomRight = circleBottomRight,
                    ),
                )
                drawTextInRect(
                    textLayoutResult = textLayoutResult,
                    textDecoration = textDecoration,
                    gravity = DrawGravity.CenterVertical,
                    color = textColor,
                    rect = Rect(
                        topLeft = textTopLeft,
                        bottomRight = textBottomRight,
                    ),
                )
            }
            else -> throw IllegalArgumentException("Undefined orientation")
        }
    }
}

@ExperimentalTextApi
@Preview(showBackground = true, heightDp = 200, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(showBackground = true, heightDp = 200, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun DrawGraphLabelPreview() {
    MaterialTheme {
        val color1 = Color.DarkGray
        val color2 = Color.Blue
        val color3 = Color.Red
        val textMeasurer = rememberTextMeasurer()

        Canvas(
            modifier = Modifier.fillMaxSize()
        ) {
            val graphRect = this.size.toRect()
            val padding = 16.dp.toPx()

            val horizontalRect = Rect(
                offset = Offset(
                    x = graphRect.left + padding,
                    y = graphRect.top + padding,
                ),
                size = Size(
                    width = 200.dp.toPx(),
                    height = 40.dp.toPx(),
                ),
            )
            val verticalRect = Rect(
                offset = Offset(
                    x = graphRect.left + padding,
                    y = graphRect.top + padding + 40.dp.toPx() + padding,
                ),
                size = Size(
                    width = 160.dp.toPx(),
                    height = 80.dp.toPx(),
                ),
            )

            drawRect(
                color = Color.LightGray,
                topLeft = horizontalRect.topLeft,
                size = horizontalRect.size,
            )
            drawRect(
                color = Color.LightGray,
                topLeft = verticalRect.topLeft,
                size = verticalRect.size,
            )

            drawGraphLabel(
                textMeasurer = textMeasurer,
                listOf(
                    Pair(color2, "12345"),
                    Pair(color3, "67890"),
                ),
                textColor = color1,
                rect = horizontalRect,
                orientation = DrawOrientation.Horizontal,
            )
            drawGraphLabel(
                textMeasurer = textMeasurer,
                listOf(
                    Pair(color2, "1234567890"),
                    Pair(color3, "12345"),
                ),
                textColor = color1,
                rect = verticalRect,
                orientation = DrawOrientation.Vertical,
            )
        }
    }
}