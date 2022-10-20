package com.gmail.jiangyang5157.common.ext

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes

/**
 * Created by Yang Jiang on May 19, 2019
 */

fun ViewGroup.inflate(@LayoutRes layoutRes: Int, attachToRoot: Boolean = false): View {
    return LayoutInflater.from(context).inflate(layoutRes, this, attachToRoot)
}

val ViewGroup.children
    get() = 0.until(childCount).asSequence().map { getChildAt(it) }.toList()

