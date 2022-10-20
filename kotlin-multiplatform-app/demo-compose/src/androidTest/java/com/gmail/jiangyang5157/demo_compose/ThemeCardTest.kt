package com.gmail.jiangyang5157.demo_compose

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import org.junit.Rule
import org.junit.Test

class ThemeCardTest {

    @Rule
    @JvmField
    var composeTestRule = createComposeRule()

    @Test
    fun cardWithProgressIndicator() {
        composeTestRule.setContent {
            ThemeCard(MainViewModel())
        }

        composeTestRule.onNodeWithText("Dark Mode").performClick()
        composeTestRule.onNodeWithText("Light Mode").performClick()
    }
}
