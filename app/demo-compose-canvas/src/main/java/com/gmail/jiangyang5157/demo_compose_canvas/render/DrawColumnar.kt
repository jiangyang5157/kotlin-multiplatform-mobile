package com.gmail.jiangyang5157.demo_compose_canvas.render

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

/**
 * Draw columnar data center horizontal
 */
fun DrawScope.drawColumnar(
    items: List<Pair<Color, Float>>,
    rect: Rect,
) {
    if (items.isEmpty()) return

    val columnCornerRadius = CornerRadius(2.dp.toPx())
    val columnWidth = 8.dp.toPx()
    // padding between 2 column
    val columnPadding = 4.dp.toPx()
    val columnsWidth = columnWidth * items.size + columnPadding * (items.size - 1)
    val columnTopLeft = Offset(
        x = rect.left + (rect.width - columnsWidth) / 2,
        y = rect.top,
    )
    items.forEachIndexed { index, pair ->
        val color = pair.first
        val height = pair.second

        drawPath(
            color = color,
            path = Path().apply {
                addRoundRect(
                    RoundRect(
                        rect = Rect(
                            offset = Offset(
                                x = columnTopLeft.x + columnWidth * index + columnPadding * index,
                                y = rect.bottom - height,
                            ),
                            size = Size(
                                width = columnWidth,
                                height = height,
                            ),
                        ),
                        topLeft = columnCornerRadius,
                        topRight = columnCornerRadius,
                    )
                )
            },
        )
    }
}

@Preview
@ExperimentalTextApi
@Composable
private fun DrawColumnarPreview() {
    MaterialTheme {
        Canvas(
            modifier = Modifier.fillMaxSize()
        ) {
            val graphRect = this.size.toRect()
            val padding = 16.dp.toPx()

            val smallRect = Rect(
                offset = Offset(
                    x = graphRect.left + padding,
                    y = graphRect.top + padding,
                ),
                size = Size(
                    width = 40.dp.toPx(),
                    height = 200.dp.toPx(),
                ),
            )
            val largeRect = Rect(
                offset = Offset(
                    x = graphRect.left + padding,
                    y = graphRect.top + padding + 200.dp.toPx() + padding,
                ),
                size = Size(
                    width = 160.dp.toPx(),
                    height = 200.dp.toPx(),
                ),
            )

            drawRect(
                color = Color.LightGray,
                topLeft = smallRect.topLeft,
                size = smallRect.size,
            )
            drawRect(
                color = Color.LightGray,
                topLeft = largeRect.topLeft,
                size = largeRect.size,
            )

            drawColumnar(
                items = listOf(
                    Pair(Color.Red, 100.dp.toPx()),
                    Pair(Color.Blue, 160.dp.toPx()),
                ),
                rect = smallRect,
            )
            drawColumnar(
                items = listOf(
                    Pair(Color.Red, 100.dp.toPx()),
                    Pair(Color.Blue, 160.dp.toPx()),
                ),
                rect = largeRect,
            )
        }
    }
}