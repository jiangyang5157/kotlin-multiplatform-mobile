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
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.DrawStyle
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.text.*
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gmail.jiangyang5157.demo_compose_canvas.render.DrawGravity.Bottom
import com.gmail.jiangyang5157.demo_compose_canvas.render.DrawGravity.Center
import com.gmail.jiangyang5157.demo_compose_canvas.render.DrawGravity.CenterHorizontal
import com.gmail.jiangyang5157.demo_compose_canvas.render.DrawGravity.CenterVertical
import com.gmail.jiangyang5157.demo_compose_canvas.render.DrawGravity.Left
import com.gmail.jiangyang5157.demo_compose_canvas.render.DrawGravity.Right
import com.gmail.jiangyang5157.demo_compose_canvas.render.DrawGravity.Top

fun Int.hasFlag(flag: Int): Boolean = flag and this == flag
fun Int.addFlag(flag: Int): Int = this or flag
fun Int.minusFlag(flag: Int): Int = this and flag.inv()

fun Long.hasFlag(flag: Long): Boolean = flag and this == flag
fun Long.addFlag(flag: Long): Long = this or flag
fun Long.minusFlag(flag: Long): Long = this and flag.inv()

object DrawGravity {
    val Left = 1
    val Top = 1 shl 1
    val Right = 1 shl 2
    val Bottom = 1 shl 3

    val CenterVertical = Top or Bottom
    val CenterHorizontal = Left or Right
    val Center = CenterVertical or CenterHorizontal
}

object DrawOrientation {
    val Vertical = 1
    val Horizontal = 1 shl 1
}

/**
 * Draw text with size and gravity
 */
@ExperimentalTextApi
fun DrawScope.drawTextInRect(
    textLayoutResult: TextLayoutResult,
    rect: Rect,
    gravity: Int = Center,
    color: Color = Color.Unspecified,
    alpha: Float = Float.NaN,
    textDecoration: TextDecoration? = null,
) {
    var topLeft = rect.topLeft
    val xSpace = rect.size.width - textLayoutResult.size.width
    val ySpace = rect.size.height - textLayoutResult.size.height

    when {
        gravity.hasFlag(CenterVertical) -> {
            topLeft = topLeft.copy(y = topLeft.y + ySpace / 2)
        }
        gravity.hasFlag(Top) -> {
            // do nothing
        }
        gravity.hasFlag(Bottom) -> {
            topLeft = topLeft.copy(y = topLeft.y + ySpace)
        }
        else -> {}
    }
    when {
        gravity.hasFlag(CenterHorizontal) -> {
            topLeft = topLeft.copy(x = topLeft.x + xSpace / 2)
        }
        gravity.hasFlag(Left) -> {
            // do nothing
        }
        gravity.hasFlag(Right) -> {
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
fun DrawScope.drawCircleInRect(
    color: Color,
    radius: Float,
    rect: Rect,
    gravity: Int = Center,
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
        gravity.hasFlag(CenterVertical) -> {
            center = center.copy(y = center.y + ySpace / 2)
        }
        gravity.hasFlag(Top) -> {
            // do nothing
        }
        gravity.hasFlag(Bottom) -> {
            center = center.copy(y = center.y + ySpace)
        }
        else -> {}
    }
    when {
        gravity.hasFlag(CenterHorizontal) -> {
            center = center.copy(x = center.x + xSpace / 2)
        }
        gravity.hasFlag(Left) -> {
            // do nothing
        }
        gravity.hasFlag(Right) -> {
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

@ExperimentalTextApi
@Preview(showBackground = true, widthDp = 200, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(showBackground = true, widthDp = 200, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun DrawWithGravityPreview() {
    MaterialTheme {
        val color1 = Color.DarkGray
        val textMeasurer = rememberTextMeasurer()

        Canvas(
            modifier = Modifier.fillMaxSize()
        ) {
            val graphRect = this.size.toRect()
            val padding = 16.dp.toPx()
            val circleRadius = 12.dp.toPx()
            val textLayoutResult = textMeasurer.measure(
                text = AnnotatedString("Asd"),
                style = TextStyle(fontSize = 16.sp)
            )
            val boxSize = Size(
                width = 60.dp.toPx(),
                height = 60.dp.toPx(),
            )
            val column1row1 = Offset(
                x = graphRect.left + padding,
                y = graphRect.top + padding,
            )
            val column2row1 = Offset(
                x = graphRect.left + padding + boxSize.width + padding,
                y = graphRect.top + padding,
            )

            val gravityList = listOf(
                Top.addFlag(Left),
                Top.addFlag(Right),
                Bottom.addFlag(Left),
                Bottom.addFlag(Right),
                CenterVertical.addFlag(Left),
                CenterVertical.addFlag(Right),
                CenterHorizontal.addFlag(Top),
                CenterHorizontal.addFlag(Bottom),
                Center,
                CenterVertical,
                CenterHorizontal,
            )
            gravityList.forEachIndexed { index, gravity ->
                drawRect(
                    color = Color.LightGray,
                    topLeft = column1row1.copy(
                        y = column1row1.y + (boxSize.height + padding) * index
                    ),
                    size = boxSize,
                )
                drawCircleInRect(
                    color = color1,
                    radius = circleRadius,
                    gravity = gravity,
                    rect = Rect(
                        offset = column1row1.copy(
                            y = column1row1.y + (boxSize.height + padding) * index
                        ),
                        size = boxSize,
                    ),
                )

                drawRect(
                    color = Color.LightGray,
                    topLeft = column2row1.copy(
                        y = column2row1.y + (boxSize.height + padding) * index
                    ),
                    size = boxSize,
                )
                drawTextInRect(
                    textLayoutResult = textLayoutResult,
                    gravity = gravity,
                    color = color1,
                    rect = Rect(
                        offset = column2row1.copy(
                            y = column2row1.y + (boxSize.height + padding) * index
                        ),
                        size = boxSize,
                    ),
                )
            }
        }
    }
}