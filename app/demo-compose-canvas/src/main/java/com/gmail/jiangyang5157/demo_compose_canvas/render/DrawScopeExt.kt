package com.gmail.jiangyang5157.demo_compose_canvas.render

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.DrawStyle
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.style.TextDecoration
import com.gmail.jiangyang5157.demo_compose_canvas.render.DrawGravity.BOTTOM
import com.gmail.jiangyang5157.demo_compose_canvas.render.DrawGravity.CENTER_HORIZONTAL
import com.gmail.jiangyang5157.demo_compose_canvas.render.DrawGravity.CENTER_VERTICAL
import com.gmail.jiangyang5157.demo_compose_canvas.render.DrawGravity.RIGHT

/**
 * Draw text with size and gravity
 */
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

/**
 * Draw circle with size and gravity
 */
fun DrawScope.drawCircleRect(
    color: Color,
    radius: Float,
    rect: Rect,
    gravity: Int = DrawGravity.NO_GRAVITY,
    alpha: Float = 1.0f,
    style: DrawStyle = Fill,
    colorFilter: ColorFilter? = null,
    blendMode: BlendMode = DrawScope.DefaultBlendMode,
) {

    var center = Offset(
        x = rect.left + radius,
        y = rect.top + radius
    )
    val xSpace = rect.size.width - radius * 2
    val ySpace = rect.size.height - radius * 2

    when {
        gravity.hasFlag(CENTER_VERTICAL) -> {
            center = center.copy(y = center.y + ySpace / 2)
        }
        gravity.hasFlag(BOTTOM) -> {
            center = center.copy(y = center.y + ySpace)
        }
        else -> {}
    }
    when {
        gravity.hasFlag(CENTER_HORIZONTAL) -> {
            center = center.copy(x = center.x + xSpace / 2)
        }
        gravity.hasFlag(RIGHT) -> {
            center = center.copy(x = center.x + xSpace)
        }
        else -> {}
    }

    drawCircle(
        color = color,
        radius = radius,
        center = center,
        alpha = alpha,
        style = style,
        colorFilter = colorFilter,
        blendMode = blendMode,
    )
}