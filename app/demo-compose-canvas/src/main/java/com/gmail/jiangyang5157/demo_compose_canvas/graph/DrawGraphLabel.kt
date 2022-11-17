package com.gmail.jiangyang5157.demo_compose_canvas.graph

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.TextMeasurer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gmail.jiangyang5157.demo_compose_canvas.render.*

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
    val indicatorWidth = indicatorDiameter + indicatorTextPadding + textWidth
    val indicatorHeight = textHeight

    // space between 2 labels
    val labelSpacing = when (orientation) {
        DrawOrientation.Horizontal -> {
            (rect.width - textWidth * labelTexts.size) / (labelTexts.size + 1)
        }
        DrawOrientation.Vertical -> {
            (rect.height - textHeight * labelTexts.size) / (labelTexts.size + 1)
        }
        else -> 0.dp.toPx()
    }

    labelTexts.forEachIndexed { index, pair ->
        when (orientation) {
            DrawOrientation.Horizontal -> {
                val indicatorTopLeft = Offset(
                    x = rect.left + indicatorWidth * index + labelSpacing * (index + 1),
                    y = rect.top
                )
                val indicatorBottomRight = Offset(
                    x = indicatorTopLeft.x + indicatorDiameter + indicatorTextPadding,
                    y = rect.bottom,
                )
                drawCircleRect(
                    color = pair.first,
                    radius = indicatorRadius,
                    gravity = DrawGravity.CenterVertical.withFlag(DrawGravity.Left),
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
                    x = rect.left,
                    y = rect.top + indicatorHeight * index + labelSpacing * (index + 1)
                )
                val indicatorBottomRight = Offset(
                    x = rect.right,
                    y = indicatorTopLeft.y + indicatorDiameter + indicatorTextPadding,
                )
                drawCircleRect(
                    color = pair.first,
                    radius = indicatorRadius,
                    gravity = DrawGravity.CenterHorizontal.withFlag(DrawGravity.Left),
                    rect = Rect(
                        topLeft = indicatorTopLeft,
                        bottomRight = indicatorBottomRight
                    ),
                )
                drawTextRect(
                    textLayoutResult = pair.second,
                    textDecoration = textDecoration,
                    gravity = DrawGravity.CenterHorizontal,
                    rect = Rect(
                        topLeft = Offset(
                            x = indicatorBottomRight.x,
                            y = indicatorBottomRight.y,
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