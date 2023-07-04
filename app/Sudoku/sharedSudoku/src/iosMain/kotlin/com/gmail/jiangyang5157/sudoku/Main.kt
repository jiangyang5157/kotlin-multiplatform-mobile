package com.gmail.jiangyang5157.sudoku

import androidx.compose.ui.window.ComposeUIViewController
import platform.UIKit.UIDevice

actual fun getPlatform(): String =
    "${UIDevice.currentDevice.systemName()} ${UIDevice.currentDevice.systemVersion}"

fun MainViewController() = ComposeUIViewController { App() }
