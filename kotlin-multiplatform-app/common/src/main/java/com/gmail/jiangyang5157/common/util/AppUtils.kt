package com.gmail.jiangyang5157.common.util

import android.graphics.Color
import android.os.Build
import android.view.View
import android.view.WindowManager
import androidx.fragment.app.FragmentActivity

/**
 * Created by Yang Jiang on May 19, 2019
 */
object AppUtils {

    fun setTransparentStatusBar(activity: FragmentActivity) {
        activity.window.decorView.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setWindowFlag(activity, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, false)
            activity.window.statusBarColor = Color.TRANSPARENT
        }
    }

    fun setWindowFlag(activity: FragmentActivity, bits: Int, on: Boolean) {
        val window = activity.window
        val attr = window.attributes

        if (on) {
            attr.flags = attr.flags or bits
        } else {
            attr.flags = attr.flags and bits.inv()
        }
        window.attributes = attr
    }
}
