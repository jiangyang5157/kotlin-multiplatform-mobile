package com.gmail.jiangyang5157.demo_compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import androidx.compose.ui.platform.ComposeView

class MainActivity : ComponentActivity() {

    private val progressViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        ComposeView(this).also {
            setContentView(it)
        }.setContent {
            ThemeCard(progressViewModel)
        }
    }
}
