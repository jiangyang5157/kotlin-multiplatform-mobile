package com.gmail.jiangyang5157.demo_compose.theme

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

val Dimension_None = 0.dp
val Dimension_Tiny = 4.dp
val Dimension_Small = 8.dp
val Dimension_Medium = 16.dp
val Dimension_Large = 24.dp

data class AppDimensions(
    val paddingHorizontal: Dp = Dp.Unspecified,
    val paddingVertical: Dp = Dp.Unspecified,
    val elevationCard: Dp = Dp.Unspecified,
)

val PhoneDimensions = AppDimensions(
    paddingHorizontal = Dimension_Medium,
    paddingVertical = Dimension_Small,
    elevationCard = 3.dp,
)

val TabletDimensions = PhoneDimensions.copy(
    paddingHorizontal = Dimension_Large,
)
