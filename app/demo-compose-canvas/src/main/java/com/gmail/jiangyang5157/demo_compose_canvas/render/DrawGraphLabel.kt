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
    labels: List<Pair<Color, CharSequence>>,
    rect: Rect,
    orientation: Int = DrawOrientation.Horizontal,
    textDecoration: TextDecoration? = null,
) {
    // same text style for labels
    val textStyle = TextStyle(fontSize = 16.sp)
    val labelTexts = labels.map {
        Pair(
            it.first, textMeasurer.measure(
                text = AnnotatedString(it.second.toString()),
                style = textStyle,
            )
        )
    }
    // recognize the uniform text height
    val textHeight = labelTexts.first().second.size.height
    // recognize the largest text width
    var textWidth = labelTexts.first().second.size.width
    for (labelText in labelTexts) {
        textWidth = maxOf(textWidth, labelText.second.size.width)
    }

    // circle size relative to text height (less than)
    val circleRadius = textHeight * 0.3f
    val circleDiameter = circleRadius * 2
    val circleTextPadding = 8.dp.toPx()

    // calculate label size
    val labelHeight = textHeight
    val labelWidth = circleDiameter + circleTextPadding + textWidth

    // space between 2 labels
    val labelSpacing = when (orientation) {
        DrawOrientation.Horizontal -> {
            (rect.width - labelWidth * labelTexts.size) / (labelTexts.size + 1)
        }
        DrawOrientation.Vertical -> {
            (rect.height - labelHeight * labelTexts.size) / (labelTexts.size + 1)
        }
        else -> throw IllegalArgumentException("Undefined orientation")
    }

    labelTexts.forEachIndexed { index, pair ->
        when (orientation) {
            DrawOrientation.Horizontal -> {
                val circleTopLeft = Offset(
                    x = rect.left + labelWidth * index + labelSpacing * (index + 1),
                    y = rect.top
                )
                val circleBottomRight = Offset(
                    x = circleTopLeft.x + circleDiameter + circleTextPadding,
                    y = rect.bottom,
                )
                drawCircleInRect(
                    color = pair.first,
                    radius = circleRadius,
                    gravity = DrawGravity.CenterVertical.addFlag(DrawGravity.Left),
                    rect = Rect(
                        topLeft = circleTopLeft,
                        bottomRight = circleBottomRight
                    ),
                )
                drawTextInRect(
                    textLayoutResult = pair.second,
                    textDecoration = textDecoration,
                    gravity = DrawGravity.CenterVertical,
                    rect = Rect(
                        topLeft = Offset(
                            x = circleBottomRight.x,
                            y = rect.top,
                        ),
                        bottomRight = Offset(
                            x = circleBottomRight.x + textWidth,
                            y = rect.bottom,
                        )
                    ),
                )
            }
            DrawOrientation.Vertical -> {
                val circleTopLeft = Offset(
                    x = rect.left + (rect.size.width - labelWidth) / 2,
                    y = rect.top + (labelHeight * index + labelSpacing * (index + 1))
                )
                val circleBottomRight = Offset(
                    x = circleTopLeft.x + circleDiameter + circleTextPadding,
                    y = circleTopLeft.y + labelHeight,
                )
                drawCircleInRect(
                    color = pair.first,
                    radius = circleRadius,
                    gravity = DrawGravity.CenterVertical.addFlag(DrawGravity.Left),
                    rect = Rect(
                        topLeft = circleTopLeft,
                        bottomRight = circleBottomRight
                    ),
                )
                drawTextInRect(
                    textLayoutResult = pair.second,
                    textDecoration = textDecoration,
                    gravity = DrawGravity.CenterVertical,
                    rect = Rect(
                        topLeft = Offset(
                            x = circleBottomRight.x,
                            y = circleTopLeft.y,
                        ),
                        bottomRight = Offset(
                            x = circleBottomRight.x + textWidth,
                            y = circleBottomRight.y,
                        )
                    ),
                )
            }
            else -> throw IllegalArgumentException("Undefined orientation")
        }
    }
}

@Preview
@ExperimentalTextApi
@Composable
private fun DrawGraphLabelPreview() {
    MaterialTheme {
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
                    Pair(Color.Red, "12345"),
                    Pair(Color.Blue, "67890"),
                ),
                rect = horizontalRect,
                orientation = DrawOrientation.Horizontal,
            )
            drawGraphLabel(
                textMeasurer = textMeasurer,
                listOf(
                    Pair(Color.Red, "1234567890"),
                    Pair(Color.Blue, "12345"),
                ),
                rect = verticalRect,
                orientation = DrawOrientation.Vertical,
            )
        }
    }
}