package com.gmail.jiangyang5157.router.fragment

import com.gmail.jiangyang5157.router.core.RoutingStack
import com.gmail.jiangyang5157.router.fragment.setup.FragmentContainer
import com.gmail.jiangyang5157.router.fragment.transition.FragmentTransition

interface FragmentStackPatcher {

    operator fun invoke(
        transition: FragmentTransition,
        container: FragmentContainer,
        oldStack: RoutingStack<*>,
        newStack: FragmentRoutingStack<*>
    )
}
