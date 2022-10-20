package com.gmail.jiangyang5157.demo_compose

import android.widget.TextView
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.ProgressIndicatorDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.viewinterop.AndroidView
import com.gmail.jiangyang5157.demo_compose.theme.AppTheme
import com.gmail.jiangyang5157.demo_compose.theme.DarkPhoneTheme
import com.gmail.jiangyang5157.demo_compose.theme.Dimension_Medium
import com.gmail.jiangyang5157.demo_compose.theme.LightPhoneTheme


@Composable
fun ThemeCard(viewModel: MainViewModel) {
    val isDarkMode by viewModel.isDarkMode.observeAsState(false)
    if (isDarkMode) {
        DarkPhoneTheme {
            ThemeCardContent(
                darkMode = { viewModel.darkMode() },
                lightMode = { viewModel.lightMode() },
            )
        }
    } else {
        LightPhoneTheme {
            ThemeCardContent(
                darkMode = { viewModel.darkMode() },
                lightMode = { viewModel.lightMode() },
            )
        }
    }
}

@Composable
private fun ThemeCardContent(
    darkMode: () -> Unit,
    lightMode: () -> Unit,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(AppTheme.dimensions.paddingHorizontal),
        elevation = AppTheme.dimensions.elevationCard
    ) {
        Column(
            modifier = Modifier.padding(AppTheme.dimensions.paddingHorizontal)
        ) {
            AndroidTextView()

            Spacer(Modifier.height(Dimension_Medium))
            Divider()
            Spacer(Modifier.height(Dimension_Medium))

            AppTheme {
                Text(
                    buildAnnotatedString {
                        withStyle(
                            style = SpanStyle(
                                fontWeight = FontWeight.Bold,
                                color = AppTheme.colors.brand,
                            )
                        ) {
                            append("The color of this text")
                        }
                        append(" is directly related to the ")
                        withStyle(
                            style = SpanStyle(
                                fontWeight = FontWeight.Bold,
                                color = AppTheme.colors.brand,
                            )
                        ) {
                            append("system settings (dark/light)")
                        }
                        append(" of the device.")
                    }
                )
            }

            Spacer(Modifier.height(Dimension_Medium))
            Divider()
            Spacer(Modifier.height(Dimension_Medium))

            Text(
                buildAnnotatedString {
                    append("The theme of the rest is directly related to a variable, ")
                    withStyle(
                        style = SpanStyle(
                            fontWeight = FontWeight.Bold,
                            color = AppTheme.colors.brand,
                        )
                    ) {
                        append("controlled by below buttons")
                    }
                    append(".")
                }
            )

            Spacer(Modifier.height(Dimension_Medium))
            Divider()
            Spacer(Modifier.height(Dimension_Medium))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                Button(onClick = { darkMode() }) { Text("Dark Mode") }
                Button(onClick = { lightMode() }) { Text("Light Mode") }
            }
        }
    }
}

@Composable
private fun Divider() {
    LinearProgressIndicator(
        progress = animateFloatAsState(
            targetValue = 0.5f,
            animationSpec = ProgressIndicatorDefaults.ProgressAnimationSpec,
        ).value,
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
private fun AndroidTextView() {
    AndroidView(
        factory = { context ->
            TextView(context)
        }
    ) { textView ->
        textView.text =
            "This is android.widget.TextView with color R.color.teal_200 inside Composable."
        textView.setTextColor(
            textView.resources.getColor(
                R.color.teal_200,
                textView.context.theme
            )
        )
    }
}