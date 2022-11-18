package com.gmail.jiangyang5157.demo_compose_canvas.render

import android.content.res.Configuration
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
 * Draw column(s) center horizontal
 */
fun DrawScope.drawColumn(
    items: List<Pair<Color, Float>>,
    rect: Rect,
) {
    if (items.isEmpty()) return

    val columnCornerRadius = CornerRadius(2.dp.toPx())
    val columnWidth = 8.dp.toPx()
    // padding between 2 columns
    val columnPadding = 4.dp.toPx()
    val itemsWidth = columnWidth * items.size + columnPadding * (items.size - 1)
    val columnTopLeft = Offset(
        x = rect.left + (rect.width - itemsWidth) / 2,
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

@ExperimentalTextApi
@Preview(
    showBackground = true,
    widthDp = 200,
    heightDp = 500,
    uiMode = Configuration.UI_MODE_NIGHT_NO,
)
@Preview(
    showBackground = true,
    widthDp = 200,
    heightDp = 500,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
)
@Composable
private fun DrawColumnPreview() {
    MaterialTheme {
        val color1 = Color.DarkGray
        val color2 = Color.Blue
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

            drawColumn(
                items = listOf(
                    Pair(color1, 100.dp.toPx()),
                    Pair(color2, 160.dp.toPx()),
                ),
                rect = smallRect,
            )
            drawColumn(
                items = listOf(
                    Pair(color1, 100.dp.toPx()),
                    Pair(color2, 160.dp.toPx()),
                ),
                rect = largeRect,
            )
        }
    }
}