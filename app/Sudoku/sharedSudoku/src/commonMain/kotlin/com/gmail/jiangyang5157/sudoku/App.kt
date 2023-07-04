package com.gmail.jiangyang5157.sudoku

import androidx.compose.material.Text
import androidx.compose.runtime.Composable

expect fun getPlatform(): String

@Composable
internal fun App() {
    Text(text = getPlatform())
}