package com.gmail.jiangyang5157.router.fragment.setup

import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Lifecycle

internal class AsFragmentActivityRouterHost(
    override val activity: FragmentActivity
) : FragmentRouterHost {

    override val lifecycle: Lifecycle
        get() = activity.lifecycle
}
