package com.gmail.jiangyang5157.router.fragment.setup

import android.os.Bundle
import androidx.annotation.IdRes
import androidx.fragment.app.FragmentManager
import com.gmail.jiangyang5157.router.fragment.FragmentRouter

interface RouterFragmentActivity :
    AsFragmentActivity,
    PopRetainRootImmediateOrFinish {

    fun FragmentRouter<*>.setup(
        savedInstanceState: Bundle?,
        @IdRes containerId: Int,
        fragmentManager: FragmentManager = expectThisToBeAFragmentActivity().supportFragmentManager
    ) =
        AsFragmentActivityRouterSetup(
            expectThisToBeAFragmentActivity()
        ).run {
            setup(savedInstanceState, containerId, fragmentManager)
        }
}
