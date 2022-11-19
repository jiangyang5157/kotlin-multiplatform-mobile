package com.gmail.jiangyang5157.demo_compose_canvas.render

import android.content.res.Configuration
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.toRect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.text.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@ExperimentalTextApi
fun DrawScope.drawDataAxis(
    rect: Rect,
    items: List<CharSequence>,
    textStyle: TextStyle = TextStyle(fontSize = 16.sp),
    textMeasurer: TextMeasurer,
    lineColor: Color,
): Rect {
    if (items.isEmpty()) throw IllegalArgumentException("Empty items")

    val itemTextLayoutResults = items.map {
        textMeasurer.measure(
            text = AnnotatedString(it.toString()),
            style = textStyle
        )
    }
    // recognize the uniform text height
    val itemHeight = itemTextLayoutResults.first().size.height
    // recognize the largest text width
    val itemWidth = itemTextLayoutResults.maxByOrNull { it.size.width }?.size?.width
        ?: throw RuntimeException()

    // x-axis starts from the middle of the y-axis text
    val verticalPadding = itemHeight / 2f
    val itemPaddingRight = verticalPadding

    val ret = Rect(
        topLeft = rect.topLeft.plus(
            Offset((itemWidth + itemPaddingRight), verticalPadding),
        ),
        bottomRight = rect.bottomRight.minus(
            Offset(0f, verticalPadding),
        )
    )

    when (items.size) {
        1 -> {

        }
        else -> {
            val itemPaddingBetween = ret.height / (items.size - 1) - itemHeight


        }
    }

    return ret
}

@ExperimentalTextApi
@Preview(showBackground = true, heightDp = 200, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(showBackground = true, heightDp = 200, uiMode = Configuration.UI_MODE_NIGHT_YES)
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

            val rect = Rect(
                topLeft = Offset(
                    x = graphRect.left + padding,
                    y = graphRect.top + padding,
                ), bottomRight = Offset(
                    x = graphRect.right - padding,
                    y = graphRect.bottom - padding,
                )
            )
            drawRect(
                color = Color.LightGray,
                topLeft = rect.topLeft,
                size = rect.size,
            )


        }
    }
}