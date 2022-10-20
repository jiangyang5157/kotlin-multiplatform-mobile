package com.gmail.jiangyang5157.router.fragment.transition

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.gmail.jiangyang5157.router.core.Route

internal object EmptyFragmentTransition : FragmentTransition {

    override fun setup(
        transaction: FragmentTransaction,
        exitFragment: Fragment,
        exitRoute: Route,
        enterFragment: Fragment,
        enterRoute: Route
    ) = Unit
}
