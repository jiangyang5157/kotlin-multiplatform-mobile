package com.gmail.jiangyang5157.router.fragment.setup

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Lifecycle

internal class AsFragmentRouterHost(
    private val fragment: Fragment
) : FragmentRouterHost {

    override val lifecycle: Lifecycle
        get() = fragment.lifecycle

    override val activity: FragmentActivity
        get() = fragment.requireActivity()
}
