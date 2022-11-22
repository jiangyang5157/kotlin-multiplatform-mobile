package com.gmail.jiangyang5157.demo_compose_canvas

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import kotlin.random.Random

/**
 * Testing some recomposition behaviour
 */
class RecompositionActivity : ComponentActivity() {

    @ExperimentalTextApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<ComposeView>(R.id.composeView).setContent {
            MaterialTheme {
                Log.d("####", "MaterialTheme scope")
                Content()
            }
        }
    }

    /*

# Launch

2022-11-22 14:25:58.022 21278-21278 ####                    com...gyang5157.demo_compose_canvas  D  MaterialTheme scope
2022-11-22 14:25:58.045 21278-21278 ####                    com...gyang5157.demo_compose_canvas  D  Column Scope
2022-11-22 14:25:58.169 21278-21278 ####                    com...gyang5157.demo_compose_canvas  D  Button 'show uio' scope
2022-11-22 14:25:58.195 21278-21278 ####                    com...gyang5157.demo_compose_canvas  D  Button 'do nothing' scope
2022-11-22 14:25:58.205 21278-21278 ####                    com...gyang5157.demo_compose_canvas  D  Button 'update value' scope
2022-11-22 14:25:58.369 21278-21278 ####                    com...gyang5157.demo_compose_canvas  D  Canvas scope

# Click "Button 0 show uio"

2022-11-22 14:26:07.250 21278-21278 ####                    com...gyang5157.demo_compose_canvas  D  Canvas scope
2022-11-22 14:26:07.370 21278-21278 ####                    com...gyang5157.demo_compose_canvas  D  Canvas scope

# Click "Button 1 do nothing"

2022-11-22 14:26:12.996 21278-21278 ####                    com...gyang5157.demo_compose_canvas  D  Canvas scope
2022-11-22 14:26:13.110 21278-21278 ####                    com...gyang5157.demo_compose_canvas  D  Canvas scope

# Click "Button 2 update value"

2022-11-22 14:26:19.808 21278-21278 ####                    com...gyang5157.demo_compose_canvas  D  Canvas scope
2022-11-22 14:26:19.932 21278-21278 ####                    com...gyang5157.demo_compose_canvas  D  Button 'show uio' scope
2022-11-22 14:26:19.951 21278-21278 ####                    com...gyang5157.demo_compose_canvas  D  Canvas scope
2022-11-22 14:26:19.969 21278-21278 ####                    com...gyang5157.demo_compose_canvas  D  Canvas scope

# Click "Canvas to update value"

2022-11-22 14:26:32.481 21278-21278 ####                    com...gyang5157.demo_compose_canvas  D  Button 'show uio' scope
2022-11-22 14:26:32.490 21278-21278 ####                    com...gyang5157.demo_compose_canvas  D  Canvas scope

     */

    data class Uio(val name: String, val value: Int)

    @ExperimentalTextApi
    @Composable
    private fun Content() {
        val textMeasurer = rememberTextMeasurer()
        var uio by remember { mutableStateOf(Uio(name = "Uio", value = -1)) }

        Column {
            Log.d("####", "Column Scope")

            // trigger column recomposition
//            Text(text = "$uio")

            Button(onClick = {
            }) {
                Log.d("####", "Button 'show uio' scope")
                Text(text = "$uio")
            }

            Button(onClick = {
            }) {
                Log.d("####", "Button 'do nothing' scope")
                Text(text = "do nothing")
            }

            Button(onClick = {
                uio = uio.copy(value = Random.nextInt())
            }) {
                Log.d("####", "Button 'update value' scope")
                Text(text = "update value")
            }

            Canvas(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = Color.LightGray)
                    .pointerInput(Unit) {
                        detectTapGestures(
                            onTap = { uio = uio.copy(value = Random.nextInt()) }
                        )
                    },
            ) {
                Log.d("####", "Canvas scope")

                drawText(textMeasurer.measure(AnnotatedString(uio.toString())))
            }
        }
    }
}