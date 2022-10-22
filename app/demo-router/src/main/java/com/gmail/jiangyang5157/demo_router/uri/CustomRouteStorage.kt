package com.gmail.jiangyang5157.demo_router.uri

import androidx.fragment.app.Fragment
import com.gmail.jiangyang5157.router.core.Route
import com.gmail.jiangyang5157.router.error.RouterException
import com.gmail.jiangyang5157.router.fragment.setup.FragmentRouteStorage

/**
 * This class demonstrates how we can use a custom route storage
 *
 * @see ParcelableFragmentRouteStorage The Default implementation
 */
class CustomRouteStorage<T : Route> : FragmentRouteStorage<T> {

    private val data = mutableMapOf<String, T>()

    /**
     * ## Defect WNF
     * We should not use Fragment.javaClass.simpleName as key in the map [data], because it is possible a router has duplicated same Fragment instance in the routing stack
     */
    override fun Fragment.attach(route: T) {
        val key = this.javaClass.simpleName
        data[key] = route
    }

    override fun Fragment.getRouteOrNull(): T? {
        val key = this.javaClass.simpleName
        return data[key] as T
    }

    override fun Fragment.getRoute(): T {
        val key = this.javaClass.simpleName
        return getRouteOrNull() ?: throw RouterException(
            "Expected route with key $key"
        )
    }
}
