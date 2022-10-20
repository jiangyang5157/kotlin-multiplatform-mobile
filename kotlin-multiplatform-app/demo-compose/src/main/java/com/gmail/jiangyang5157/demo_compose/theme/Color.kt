package com.gmail.jiangyang5157.demo_compose.theme

import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.ui.graphics.Color

val Yellow_800 = Color(0xFFF29F05)
val Red_300 = Color(0xFFEA6D7E)
val Purple_500 = Color(0xFF6200EE)

val LightMaterialColors = lightColors(
    primary = Red_300,
    primaryVariant = Color(0xFF3700B3),
    secondary = Color(0xFF03DAC6),
    secondaryVariant = Color(0xFF018786),
    background = Color.White,
    surface = Color.White,
    error = Color(0xFFB00020),
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
    onError = Color.White,
)

val DarkMaterialColors = darkColors(
    primary = Yellow_800,
    primaryVariant = Color(0xFF3700B3),
    secondary = Color(0xFF03DAC6),
    secondaryVariant = Color(0xFF03DAC6),
    background = Color(0xFF121212),
    surface = Color(0xFF121212),
    error = Color(0xFFCF6679),
    onPrimary = Color.Black,
    onSecondary = Color.Black,
    onBackground = Color.White,
    onSurface = Color.White,
    onError = Color.Black,
)

data class AppColors(
    val brand: Color = Color.Unspecified,
)

val LightAppColors = AppColors(
    brand = Yellow_800,
)

val DarkAppColors = AppColors(
    brand = Red_300,
)