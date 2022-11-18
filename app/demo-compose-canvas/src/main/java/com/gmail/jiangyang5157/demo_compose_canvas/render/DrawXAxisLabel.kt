package com.gmail.jiangyang5157.demo_compose_canvas.render

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.text.*
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * Draw x-axis label (text + round underline) center horizontal
 */
@ExperimentalTextApi
fun DrawScope.drawXAxisLabel(
    textMeasurer: TextMeasurer,
    color: Color,
    text: CharSequence,
    rect: Rect,
    selected: Boolean = false,
    textDecoration: TextDecoration? = null,
) {
    val textColor = if (selected) Color.Black else Color.DarkGray
    val textStyle = TextStyle(fontSize = 16.sp)
    val textLayoutResult = textMeasurer.measure(
        text = AnnotatedString(text.toString()),
        style = textStyle,
    )
    val textHeight = textLayoutResult.size.height
    val textWidth = textLayoutResult.size.width

    // Underline Width relative to text width
    val underlineWidth = textWidth * 1.2f
    val underlineHeight = 4.dp.toPx()
    val underLineCornerRadius = CornerRadius(2.dp.toPx())

    val textTopLeft = Offset(
        x = rect.left,
        y = rect.top + (rect.height - textHeight - underlineHeight) / 2,
    )
    val textBottomRight = Offset(
        x = rect.right,
        y = textTopLeft.y + textHeight
    )
    val underLineTopLeft = Offset(
        x = rect.left + (rect.width - underlineWidth) / 2,
        y = textBottomRight.y,
    )
    val underLineSize = Size(
        width = underlineWidth,
        height = underlineHeight,
    )

    drawTextInRect(
        textLayoutResult = textLayoutResult,
        textDecoration = textDecoration,
        color = textColor,
        gravity = DrawGravity.CenterHorizontal.addFlag(DrawGravity.Top),
        rect = Rect(
            topLeft = textTopLeft,
            bottomRight = textBottomRight,
        ),
    )
    if (selected) {
        drawRoundRect(
            color = color,
            cornerRadius = underLineCornerRadius,
            topLeft = underLineTopLeft,
            size = underLineSize,
        )
    }
}

@ExperimentalTextApi
@Preview
@Composable
private fun DrawXAxisLabelPreview() {
    MaterialTheme {
        val textMeasurer = rememberTextMeasurer()

        Canvas(
            modifier = Modifier.fillMaxSize()
        ) {
            val graphRect = this.size.toRect()
            val padding = 16.dp.toPx()

            val boxSize = Size(
                width = 50.dp.toPx(),
                height = 30.dp.toPx(),
            )
            val column1row1 = Offset(
                x = graphRect.left + padding,
                y = graphRect.top + padding,
            )
            val column2row1 = Offset(
                x = graphRect.left + padding + boxSize.width + padding,
                y = graphRect.top + padding,
            )
            val column3row1 = Offset(
                x = graphRect.left + padding + boxSize.width * 2 + padding * 2,
                y = graphRect.top + padding,
            )
            val column1row2 = Offset(
                x = graphRect.left + padding,
                y = graphRect.top + padding + boxSize.height + padding,
            )

            drawRect(
                color = Color.LightGray,
                topLeft = column1row1,
                size = boxSize,
            )
            drawRect(
                color = Color.LightGray,
                topLeft = column2row1,
                size = boxSize,
            )
            drawRect(
                color = Color.LightGray,
                topLeft = column3row1,
                size = boxSize,
            )

            drawXAxisLabel(
                textMeasurer = textMeasurer,
                color = Color.Red,
                "iOS",
                selected = false,
                rect = Rect(
                    offset = column1row1,
                    size = boxSize,
                ),
            )
            drawXAxisLabel(
                textMeasurer = textMeasurer,
                color = Color.Red,
                "A",
                selected = true,
                rect = Rect(
                    offset = column2row1,
                    size = boxSize,
                ),
            )
            drawXAxisLabel(
                textMeasurer = textMeasurer,
                color = Color.Red,
                "Win",
                selected = false,
                rect = Rect(
                    offset = column3row1,
                    size = boxSize,
                ),
            )

            val labels = listOf("Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul")
            drawRect(
                color = Color.LightGray,
                topLeft = column1row2,
                size = boxSize.copy(width = boxSize.width * labels.size),
            )
            labels.forEachIndexed { index, text ->
                drawRect(
                    color = Color.LightGray,
                    topLeft = column1row2.copy(
                        x = column1row2.x + boxSize.width * index
                    ),
                    size = boxSize,
                )

                val selected = index % 2 == 0
                drawXAxisLabel(
                    textMeasurer = textMeasurer,
                    color = Color.Blue,
                    text,
                    selected = selected,
                    rect = Rect(
                        offset = column1row2.copy(
                            x = column1row2.x + boxSize.width * index
                        ),
                        size = boxSize,
                    ),
                )
            }
        }
    }
}