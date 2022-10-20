package com.gmail.jiangyang5157.demo_transaction

import android.app.UiAutomation
import android.os.ParcelFileDescriptor
import androidx.test.platform.app.InstrumentationRegistry

/**
 * Update device settings for test, and restore these settings after test completed
 */
class DeviceSettingsTestRunnerDelegate : HiltTestRunner.TestRunnerDelegate {

    private var originalAnimationValue: Float? = null

    private val uiAutomation: UiAutomation
        get() = InstrumentationRegistry.getInstrumentation().uiAutomation

    override fun onCreate() {
        originalAnimationValue = readAnimationValue()
        setAnimationValue(0.0f)
    }

    override fun onDestroy() {
        originalAnimationValue?.run { setAnimationValue(this) }
    }

    private fun readFloatValue(command: String): Float? {
        val pfd = uiAutomation.executeShellCommand(command)
        pfd.checkError()
        return ParcelFileDescriptor.AutoCloseInputStream(pfd)
            .bufferedReader()
            .readLine()
            .toFloatOrNull()
    }

    private fun executeShellCommands(commandList: List<String>) {
        commandList.forEach { command ->
            uiAutomation.executeShellCommand(command).run {
                checkError()
                close()
            }
        }
    }

    private fun readAnimationValue(): Float? {
        return readFloatValue("settings get global animator_duration_scale")
    }

    private fun setAnimationValue(value: Float) {
        executeShellCommands(
            listOf(
                "settings put global animator_duration_scale $value",
                "settings put global transition_animation_scale $value",
                "settings put global window_animation_scale $value"
            )
        )
    }
}