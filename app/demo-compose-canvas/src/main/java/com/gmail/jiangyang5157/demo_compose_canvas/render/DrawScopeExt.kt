package com.gmail.jiangyang5157.demo_compose_canvas.render

import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.style.TextDecoration
import com.gmail.jiangyang5157.demo_compose_canvas.render.DrawGravity.BOTTOM
import com.gmail.jiangyang5157.demo_compose_canvas.render.DrawGravity.CENTER_HORIZONTAL
import com.gmail.jiangyang5157.demo_compose_canvas.render.DrawGravity.CENTER_VERTICAL
import com.gmail.jiangyang5157.demo_compose_canvas.render.DrawGravity.RIGHT

@ExperimentalTextApi
fun DrawScope.drawTextRect(
    textLayoutResult: TextLayoutResult,
    rect: Rect,
    gravity: Int = DrawGravity.NO_GRAVITY,
    color: Color = Color.Unspecified,
    alpha: Float = Float.NaN,
    textDecoration: TextDecoration? = null,
) {
    var topLeft = rect.topLeft
    val xSpace = rect.size.width - textLayoutResult.size.width
    val ySpace = rect.size.height - textLayoutResult.size.height

    when {
        gravity.hasFlag(CENTER_VERTICAL) -> {
            topLeft = topLeft.copy(y = topLeft.y + ySpace / 2)
        }
        gravity.hasFlag(BOTTOM) -> {
            topLeft = topLeft.copy(y = topLeft.y + ySpace)
        }
        else -> {}
    }
    when {
        gravity.hasFlag(CENTER_HORIZONTAL) -> {
            topLeft = topLeft.copy(x = topLeft.x + xSpace / 2)
        }
        gravity.hasFlag(RIGHT) -> {
            topLeft = topLeft.copy(x = topLeft.x + xSpace)
        }
        else -> {}
    }

    drawText(
        textLayoutResult = textLayoutResult,
        topLeft = topLeft,
        color = color,
        alpha = alpha,
        textDecoration = textDecoration,
    )
}