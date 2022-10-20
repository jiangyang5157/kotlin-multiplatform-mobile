package com.gmail.jiangyang5157.router.fragment.setup

import androidx.lifecycle.Lifecycle
import com.gmail.jiangyang5157.router.fragment.FragmentRouter

internal interface FragmentContainerLifecycle {

    interface Factory {
        operator fun invoke(router: FragmentRouter<*>): FragmentContainerLifecycle
    }

    fun setup(lifecycle: Lifecycle, container: FragmentContainer)
}
