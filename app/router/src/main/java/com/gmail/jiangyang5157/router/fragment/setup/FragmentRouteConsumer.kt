package com.gmail.jiangyang5157.router.fragment.setup

import com.gmail.jiangyang5157.router.core.Route
import com.gmail.jiangyang5157.router.core.RouteConsumer
import com.gmail.jiangyang5157.router.fragment.FragmentRouter
import kotlin.reflect.KClass

interface FragmentRouteConsumer :
    RouteConsumer,
    AsFragment {

    override val router: FragmentRouter<*>

    override fun <R : Route> getRouteOrNull(clazz: KClass<R>): R? {
        val route =
            router.fragmentRouteStorage.run { expectThisToBeAFragment().getRouteOrNull() }
        if (clazz.java.isInstance(route)) {
            @Suppress("UNCHECKED_CAST")
            return route as? R
        } else {
            return null
        }
    }
}
