package com.gmail.jiangyang5157.demo_compose_canvas.render

import android.content.res.Configuration
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.text.*
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * Draw a bottom-aligned dialog within the [rect] area
 */
fun DrawScope.drawUpperDialog(
    color: Color,
    // label position
    x: Float,
    // rect of the dialog area
    rect: Rect,
    // calculate content receive the size of content
    calculateContent: () -> Size,
    // draw content
    drawContent: (DrawScope, Rect) -> Unit,
) {
    val labelHeight = 8.dp.toPx()
    val labelWidth = 16.dp.toPx()
    val labelCornerRadius = 3.dp.toPx()
    val labelRect = Rect(
        offset = Offset(
            x = x - labelWidth / 2,
            y = rect.bottom - labelHeight,
        ),
        size = Size(
            width = labelWidth,
            height = labelHeight,
        ),
    )

    // inverted triangle label hide it's topLeft and topRight corner Radius
    val yTranslate = -labelCornerRadius / 2
    val labelPath = Path().apply {
        moveTo(labelRect.center.x, labelRect.bottom)
        lineTo(labelRect.topRight.x, labelRect.topRight.y + yTranslate)
        lineTo(labelRect.topLeft.x, labelRect.topLeft.y + yTranslate)
        close()
    }
    drawIntoCanvas { canvas ->
        canvas.drawOutline(outline = Outline.Generic(labelPath), paint = Paint().apply {
            this.color = color
            this.pathEffect = PathEffect.cornerPathEffect(labelCornerRadius)
        })
    }

    val contentPadding = 8.dp.toPx()
    val dialogCornerRadius = CornerRadius(6.dp.toPx())
    val contentSize = calculateContent()
    // dialog rect with padding at topLeft(0, 0)
    var dialogRect = contentSize.toRect().inflate(contentPadding)
    // move dialog rect on center top of label
    dialogRect = dialogRect.translate(
        translateX = x - (dialogRect.width - labelWidth) / 2,
        translateY = rect.bottom - dialogRect.bottom - labelHeight
    )

    if (dialogRect.width < rect.width) {
        // dialog move right so it doesn't go beyond the left border
        if (dialogRect.left < rect.left) {
            dialogRect = dialogRect.translate(
                translateX = rect.left - dialogRect.left,
                translateY = 0f,
            )
        }
        // dialog move left so it doesn't go beyond the right border
        if (dialogRect.right > rect.right) {
            dialogRect = dialogRect.translate(
                translateX = rect.right - dialogRect.right,
                translateY = 0f,
            )
        }
    }

    // draw dialog background
    drawRoundRect(
        color = color,
        cornerRadius = dialogCornerRadius,
        topLeft = dialogRect.topLeft,
        size = dialogRect.size,
    )

    // draw content on top of background
    drawContent(this, dialogRect.deflate(contentPadding))
}

@ExperimentalTextApi
@Preview(showBackground = true, heightDp = 120, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(showBackground = true, heightDp = 120, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun DrawUnderlineLabelPreview() {
    MaterialTheme {
        val color1 = Color.DarkGray
        val color2 = Color.LightGray
        val color3 = Color.Blue
        val textMeasurer = rememberTextMeasurer()

        Canvas(
            modifier = Modifier.fillMaxSize()
        ) {
            val graphRect = this.size.toRect()
            val padding = 16.dp.toPx()

            val upperDialogRect = Rect(
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
                topLeft = upperDialogRect.topLeft,
                size = upperDialogRect.size,
            )

            val text1 = textMeasurer.measure(
                text = AnnotatedString("Hello"), style = TextStyle(fontSize = 16.sp, color = color2)
            )
            val text2 = textMeasurer.measure(
                text = AnnotatedString("World"), style = TextStyle(fontSize = 16.sp, color = color3)
            )
            val text2small = textMeasurer.measure(
                text = AnnotatedString("World"), style = TextStyle(fontSize = 10.sp, color = color3)
            )
            val textSpace = textMeasurer.measure(
                text = AnnotatedString(" "),
            )
            val circleRadius = 5.dp.toPx()

            drawUpperDialog(color = color1,
                x = 34.dp.toPx(),
                rect = upperDialogRect,
                calculateContent = {
                    Size(
                        width = (text1.size.width + textSpace.size.width + text2.size.width).toFloat(),
                        height = text1.size.height.toFloat(),
                    )
                },
                drawContent = { drawScope, rect ->
                    drawScope.drawText(
                        textLayoutResult = text1,
                        topLeft = rect.topLeft,
                    )
                    drawScope.drawText(
                        textLayoutResult = textSpace,
                        topLeft = rect.topLeft.copy(
                            x = rect.topLeft.x + text1.size.width
                        ),
                    )
                    drawScope.drawText(
                        textLayoutResult = text2,
                        topLeft = rect.topLeft.copy(
                            x = rect.topLeft.x + text1.size.width + textSpace.size.width
                        ),
                    )
                })

            drawUpperDialog(color = color1,
                x = upperDialogRect.center.x,
                rect = upperDialogRect,
                calculateContent = {
                    Size(
                        width = (text2small.size.width + circleRadius * 2) + 3.dp.toPx(),
                        height = text2small.size.height.toFloat(),
                    )
                },
                drawContent = { drawScope, rect ->
                    drawScope.drawCircleInRect(
                        color = color3,
                        radius = circleRadius,
                        gravity = DrawGravity.CenterVertical.addFlag(DrawGravity.Left),
                        rect = rect,
                    )
                    drawScope.drawText(
                        textLayoutResult = text2small,
                        textDecoration = TextDecoration.LineThrough,
                        topLeft = rect.topLeft.copy(
                            x = rect.topLeft.x + circleRadius * 2 + 3.dp.toPx()
                        ),
                    )
                })

            drawUpperDialog(color = color1,
                x = 350.dp.toPx(),
                rect = upperDialogRect,
                calculateContent = {
                    Size(
                        width = (text1.size.width + circleRadius * 2 * 2) + 6.dp.toPx(),
                        height = text1.size.height + circleRadius * 2 * 2 + 6.dp.toPx(),
                    )
                },
                drawContent = { drawScope, rect ->
                    drawScope.drawTextInRect(
                        textLayoutResult = text1,
                        gravity = DrawGravity.Center,
                        rect = rect,
                    )
                    drawScope.drawCircleInRect(
                        color = color2,
                        radius = circleRadius,
                        gravity = DrawGravity.Top.addFlag(DrawGravity.Left),
                        rect = rect,
                    )
                    drawScope.drawCircleInRect(
                        color = color2,
                        radius = circleRadius,
                        gravity = DrawGravity.Top.addFlag(DrawGravity.Right),
                        rect = rect,
                    )
                    drawScope.drawCircleInRect(
                        color = color2,
                        radius = circleRadius,
                        gravity = DrawGravity.Bottom.addFlag(DrawGravity.Left),
                        rect = rect,
                    )
                    drawScope.drawCircleInRect(
                        color = color2,
                        radius = circleRadius,
                        gravity = DrawGravity.Bottom.addFlag(DrawGravity.Right),
                        rect = rect,
                    )
                })
        }
    }
}
