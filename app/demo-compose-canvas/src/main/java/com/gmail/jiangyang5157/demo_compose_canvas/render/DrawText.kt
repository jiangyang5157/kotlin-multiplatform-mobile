package com.gmail.jiangyang5157.demo_compose_canvas.render

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.style.TextDecoration

@ExperimentalTextApi
data class DrawText(
    val textLayoutResult: TextLayoutResult,
    val color: Color = Color.Unspecified,
    val alpha: Float = Float.NaN,
    val textDecoration: TextDecoration? = null,
    val draw: DrawScope.(Offset, Size) -> Unit = { topLeft, size ->
        /**
         * Draw text center if a [size] that is larger than text size.
         */
        drawText(
            textLayoutResult = textLayoutResult,
            topLeft = if (size != Size.Unspecified) {
                Offset(
                    x = maxOf(
                        topLeft.x,
                        topLeft.x + (size.width - textLayoutResult.size.width) / 2
                    ),
                    y = maxOf(
                        topLeft.y,
                        topLeft.y + (size.height - textLayoutResult.size.height) / 2
                    ),
                )
            } else {
                topLeft
            },
            alpha = alpha,
            textDecoration = textDecoration,
        )
    }
)