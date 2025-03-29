package com.gmail.jiangyang5157.demo_camera

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Handler(Looper.getMainLooper()).postDelayed({
            MyDialogFragment().show(
                supportFragmentManager,
                MyDialogFragment::class.simpleName,
            )
        }, 0)
    }
}
