package com.gmail.jiangyang5157.sudoku

import androidx.compose.runtime.Composable

actual fun getPlatform(): String =
    "Android ${android.os.Build.VERSION.SDK_INT}"

@Composable
fun MainView() = App()
