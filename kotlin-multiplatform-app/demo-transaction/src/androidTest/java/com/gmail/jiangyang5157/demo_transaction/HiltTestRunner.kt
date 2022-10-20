package com.gmail.jiangyang5157.demo_transaction

import android.app.Application
import android.content.Context
import android.os.Bundle
import androidx.test.runner.AndroidJUnitRunner
import dagger.hilt.android.testing.HiltTestApplication

class HiltTestRunner : AndroidJUnitRunner() {

    interface TestRunnerDelegate {
        fun onCreate()
        fun onDestroy()
    }

    private val delegates: List<TestRunnerDelegate> = listOf(
        DeviceSettingsTestRunnerDelegate()
    )

    override fun newApplication(cl: ClassLoader?, name: String?, context: Context?): Application {
        return super.newApplication(cl, HiltTestApplication::class.java.name, context)
    }

    override fun callApplicationOnCreate(application: Application) {
        delegates.forEach { it.onCreate() }
        super.callApplicationOnCreate(application)
    }

    override fun finish(resultCode: Int, results: Bundle?) {
        delegates.forEach { it.onDestroy() }
        super.finish(resultCode, results)
    }
}
