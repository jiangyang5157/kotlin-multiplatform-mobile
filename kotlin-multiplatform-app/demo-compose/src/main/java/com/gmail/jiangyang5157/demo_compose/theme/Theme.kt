package com.gmail.jiangyang5157.demo_compose.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.Colors
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Shapes
import androidx.compose.material.Typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.platform.LocalConfiguration

object AppTheme {
    val localOfColors = staticCompositionLocalOf { AppColors() }
    val localOfDimensions = staticCompositionLocalOf { AppDimensions() }

    val colors: AppColors @Composable get() = localOfColors.current
    val dimensions: AppDimensions @Composable get() = localOfDimensions.current
}

@Composable
fun AppTheme(
    materialColors: Colors = if (isSystemInDarkTheme()) DarkMaterialColors else LightMaterialColors,
    materialTypography: Typography = MaterialTypography,
    materialShapes: Shapes = MaterialShape,
    appColors: AppColors = if (isSystemInDarkTheme()) DarkAppColors else LightAppColors,
    appDimensions: AppDimensions = if (LocalConfiguration.current.screenWidthDp > 360) TabletDimensions else PhoneDimensions,
    content: @Composable () -> Unit,
) {
    CompositionLocalProvider(
        AppTheme.localOfColors provides appColors,
        AppTheme.localOfDimensions provides appDimensions,
    ) {
        MaterialTheme(
            colors = materialColors,
            typography = materialTypography,
            shapes = materialShapes,
            content = content
        )
    }
}


@Composable
fun LightPhoneTheme(
    content: @Composable () -> Unit
) {
    AppTheme(
        materialColors = LightMaterialColors,
        appColors = LightAppColors,
        content = content,
    )
}

@Composable
fun DarkPhoneTheme(
    content: @Composable () -> Unit
) {
    AppTheme(
        materialColors = DarkMaterialColors,
        appColors = DarkAppColors,
        content = content,
    )
}
