package com.gmail.jiangyang5157.transaction_presentation.ui.report

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.gmail.jiangyang5157.transaction_presentation.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ReportActivity : AppCompatActivity() {

    private lateinit var nav: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_report)

        nav =
            (supportFragmentManager.findFragmentById(R.id.nav_report_host) as NavHostFragment).navController
        NavigationUI.setupActionBarWithNavController(this, nav)
    }

    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(nav, null)
    }
}
