package com.gmail.jiangyang5157.router.fragment

import androidx.fragment.app.Fragment
import com.gmail.jiangyang5157.router.core.Route
import com.gmail.jiangyang5157.router.core.RoutingStack

abstract class FragmentElement<T : Route> : RoutingStack.Element<T>() {

    interface Factory<T : Route> {
        operator fun invoke(element: RoutingStack.Element<T>): FragmentElement<T>
    }

    abstract fun createFragment(): Fragment
}
