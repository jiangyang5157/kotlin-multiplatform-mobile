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
 * Draw vertical or horizontal graph label (indicator + text)
 */
@ExperimentalTextApi
fun DrawScope.drawGraphLabel(
    textMeasurer: TextMeasurer,
    labels: List<Pair<Color, CharSequence>>,
    rect: Rect,
    orientation: Int = DrawOrientation.Horizontal,
    textDecoration: TextDecoration? = null,
) {
    val textStyle = TextStyle(fontSize = 16.sp)
    val labelTexts = labels.map {
        Pair(
            it.first, textMeasurer.measure(
                text = AnnotatedString(it.second.toString()), style = textStyle
            )
        )
    }

    var textWidth = labelTexts.first().second.size.width
    for (labelText in labelTexts) {
        textWidth = maxOf(textWidth, labelText.second.size.width)
    }

    val textHeight = labelTexts.first().second.size.height
    val indicatorRadius = textHeight * 0.3f
    val indicatorDiameter = indicatorRadius * 2
    val indicatorTextPadding = 8.dp.toPx()
    val labelWidth = indicatorDiameter + indicatorTextPadding + textWidth
    val labelHeight = textHeight

    // space between 2 labels
    val labelSpacing = when (orientation) {
        DrawOrientation.Horizontal -> {
            (rect.width - labelWidth * labelTexts.size) / (labelTexts.size + 1)
        }
        DrawOrientation.Vertical -> {
            (rect.height - labelHeight * labelTexts.size) / (labelTexts.size + 1)
        }
        else -> 0.dp.toPx()
    }

    labelTexts.forEachIndexed { index, pair ->
        when (orientation) {
            DrawOrientation.Horizontal -> {
                val indicatorTopLeft = Offset(
                    x = rect.left + labelWidth * index + labelSpacing * (index + 1),
                    y = rect.top
                )
                val indicatorBottomRight = Offset(
                    x = indicatorTopLeft.x + indicatorDiameter + indicatorTextPadding,
                    y = rect.bottom,
                )
                drawCircleRect(
                    color = pair.first,
                    radius = indicatorRadius,
                    gravity = DrawGravity.CenterVertical.addFlag(DrawGravity.Left),
                    rect = Rect(
                        topLeft = indicatorTopLeft,
                        bottomRight = indicatorBottomRight
                    ),
                )
                drawTextRect(
                    textLayoutResult = pair.second,
                    textDecoration = textDecoration,
                    gravity = DrawGravity.CenterVertical,
                    rect = Rect(
                        topLeft = Offset(
                            x = indicatorBottomRight.x,
                            y = rect.top,
                        ),
                        bottomRight = Offset(
                            x = indicatorBottomRight.x + textWidth,
                            y = rect.bottom,
                        )
                    ),
                )
            }
            DrawOrientation.Vertical -> {
                val indicatorTopLeft = Offset(
                    x = rect.left + (rect.size.width - labelWidth) / 2,
                    y = rect.top + (labelHeight * index + labelSpacing * (index + 1))
                )
                val indicatorBottomRight = Offset(
                    x = indicatorTopLeft.x + indicatorDiameter + indicatorTextPadding,
                    y = indicatorTopLeft.y + labelHeight,
                )
                drawCircleRect(
                    color = pair.first,
                    radius = indicatorRadius,
                    gravity = DrawGravity.CenterVertical.addFlag(DrawGravity.Left),
                    rect = Rect(
                        topLeft = indicatorTopLeft,
                        bottomRight = indicatorBottomRight
                    ),
                )
                drawTextRect(
                    textLayoutResult = pair.second,
                    textDecoration = textDecoration,
                    gravity = DrawGravity.CenterVertical,
                    rect = Rect(
                        topLeft = Offset(
                            x = indicatorBottomRight.x,
                            y = indicatorTopLeft.y,
                        ),
                        bottomRight = Offset(
                            x = indicatorBottomRight.x + textWidth,
                            y = indicatorBottomRight.y,
                        )
                    ),
                )
            }
            else -> {}
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
            val padding = 40.dp.toPx()

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