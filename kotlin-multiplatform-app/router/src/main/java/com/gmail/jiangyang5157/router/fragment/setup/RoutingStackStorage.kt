package com.gmail.jiangyang5157.router.fragment.setup

import android.os.Bundle
import com.gmail.jiangyang5157.router.core.Route
import com.gmail.jiangyang5157.router.core.RoutingStack

interface RoutingStackStorage<T : Route> {

    fun RoutingStack<T>.saveTo(outState: Bundle)

    fun Bundle.restore(): RoutingStack<T>?
}
