package com.gmail.jiangyang5157.router.fragment

import com.gmail.jiangyang5157.router.core.Route
import com.gmail.jiangyang5157.router.fragment.mapping.FragmentMap
import com.gmail.jiangyang5157.router.fragment.setup.FragmentRouteStorage
import com.gmail.jiangyang5157.router.fragment.setup.RoutingStackStorage

internal interface FragmentRouterConfiguration<T : Route> {
    val fragmentMap: FragmentMap
    val fragmentRouteStorage: FragmentRouteStorage<T>
    val routingStackStorage: RoutingStackStorage<T>
}
