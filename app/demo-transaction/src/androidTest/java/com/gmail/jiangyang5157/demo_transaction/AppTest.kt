package com.gmail.jiangyang5157.demo_transaction

import android.content.Intent
import android.util.Log
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import com.gmail.jiangyang5157.transaction_presentation.ui.report.ReportActivity
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
class AppTest {

    @get:Rule(order = 0)
    var hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    var activityTestRule = ActivityTestRule(ReportActivity::class.java, true, false)

    @Before
    fun setup() {
        hiltRule.inject()
        val targetContext = InstrumentationRegistry.getInstrumentation().targetContext
        activityTestRule.launchActivity(Intent(targetContext, ReportActivity::class.java))
    }

    @After
    fun cleanUp() {
        activityTestRule.finishActivity()
    }

    @Test
    fun test_sleep_1() {
        Log.d("####", "test_sleep_1")
        Thread.sleep(1000)
    }

    @Test
    fun test_sleep_2() {
        Log.d("####", "test_sleep_2")
        Thread.sleep(2000)
    }

    @Test
    fun test_sleep_3() {
        Log.d("####", "test_sleep_3")
        Thread.sleep(3000)
    }

    @Test
    fun test_sleep_4() {
        Log.d("####", "test_sleep_4")
        Thread.sleep(4000)
    }

    @Test
    fun test_sleep_5() {
        Log.d("####", "test_sleep_5")
        Thread.sleep(5000)
    }

    @Test
    fun test_sleep_6() {
        Log.d("####", "test_sleep_6")
        Thread.sleep(6000)
    }

    @Test
    fun test_sleep_7() {
        Log.d("####", "test_sleep_7")
        Thread.sleep(7000)
    }

    @Test
    fun test_sleep_8() {
        Log.d("####", "test_sleep_8")
        Thread.sleep(8000)
    }

    @Test
    fun test_sleep_9() {
        Log.d("####", "test_sleep_9")
        Thread.sleep(9000)
    }
}
