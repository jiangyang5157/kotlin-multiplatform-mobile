package com.gmail.jiangyang5157.router.fragment.setup

import androidx.fragment.app.Fragment
import com.gmail.jiangyang5157.router.core.Route

/**
 * # FragmentRouteStorage
 * Defines a way of attaching a route to a fragment in a way, that this fragment can retrieve this router later.
 *
 * ## Usage
 * ```
 * fragmentRouteStorage.run { fragment.attach(route) }
 * val route = fragmentRouteStorage.run {  fragment.getRouteOrNull() }
 * ```
 *
 * @see FragmentRouteConsumer
 */
interface FragmentRouteStorage<T : Route> {
    fun Fragment.attach(route: T)
    fun Fragment.getRouteOrNull(): T?
    fun Fragment.getRoute(): T
}
