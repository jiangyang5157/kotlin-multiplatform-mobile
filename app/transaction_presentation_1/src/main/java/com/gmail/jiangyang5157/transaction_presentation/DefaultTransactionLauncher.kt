package com.gmail.jiangyang5157.transaction_presentation

import android.app.Activity
import android.content.Intent
import com.gmail.jiangyang5157.transaction_presentation.ui.report.ReportActivity
import javax.inject.Inject

class DefaultTransactionLauncher @Inject constructor() : TransactionLauncher {

    override fun launch(current: Activity) {
        current.startActivity(Intent(current, ReportActivity::class.java))
    }
}
