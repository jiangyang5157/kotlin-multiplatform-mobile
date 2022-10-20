package com.gmail.jiangyang5157.common.util

import android.content.Context
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.FragmentActivity

/**
 * Created by Yang Jiang on May 19, 2019
 */
object SoftKeyboardUtils {

    fun hideKeyboard(activity: FragmentActivity?) {
        activity?.let {
            it.currentFocus?.run {
                val imm = it.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(windowToken, 0)
            }
        }
    }
}
