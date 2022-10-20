package com.gmail.jiangyang5157.adapter.viewgroup

import android.view.View
import android.view.ViewGroup

/**
 * Adjust the specific layout params of different type of [ViewGroup] for impacted children views.
 */
interface MultiTypeLayoutManager {
    fun removeAllViews(parent: ViewGroup)
    fun addView(parent: ViewGroup, view: View, index: Int)
    fun removeViewAt(parent: ViewGroup, index: Int)
}

class MultiTypeLinearLayoutManager : MultiTypeLayoutManager {

    override fun removeAllViews(parent: ViewGroup) {
        parent.removeAllViews()
    }

    override fun addView(parent: ViewGroup, view: View, index: Int) {
        parent.addView(view, index)
    }

    override fun removeViewAt(parent: ViewGroup, index: Int) {
        parent.removeViewAt(index)
    }
}
