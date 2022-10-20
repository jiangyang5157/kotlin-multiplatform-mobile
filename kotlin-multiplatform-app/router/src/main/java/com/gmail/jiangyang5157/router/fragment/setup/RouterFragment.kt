package com.gmail.jiangyang5157.router.fragment.setup

import android.os.Bundle
import androidx.annotation.IdRes
import androidx.fragment.app.FragmentManager
import com.gmail.jiangyang5157.router.fragment.FragmentRouter

interface RouterFragment :
    AsFragment,
    FragmentRouteConsumer {

    override val router: FragmentRouter<*>

    fun FragmentRouter<*>.setup(
        savedInstanceState: Bundle?,
        @IdRes containerId: Int,
        fragmentManager: FragmentManager = expectThisToBeAFragment().childFragmentManager
    ) =
        AsFragmentRouterSetup(
            expectThisToBeAFragment()
        ).run {
            setup(savedInstanceState, containerId, fragmentManager)
        }
}
