package com.gmail.jiangyang5157.router.core

import com.gmail.jiangyang5157.router.error.RouterException
import kotlin.reflect.KClass

/**
 * # RouteConsumer
 * Allows to retrieve the currently associated [Route] from the [Router].
 *
 * ## Note
 * - Targets of routes typically implement this.
 *
 * ## Usage
 * - Fragment implements this which allows the fragment to retrieve the [Route] for which the fragment was created from the [Router].
 */
interface RouteConsumer {

    val router: Router<*>

    /**
     * @return
     * - The associated [Route] with the given [clazz] if possible.
     * - `null` if the type does not match, or the [Route] was not found
     */
    fun <R : Route> getRouteOrNull(clazz: KClass<R>): R?

    /**
     * @return
     * - The associated [Route] with the given [clazz].
     *
     * @throws RouterException if the [Route] is not the correct type or cannot be found
     */
    fun <R : Route> getRoute(clazz: KClass<R>): R =
        getRouteOrNull(clazz) ?: throw RouterException(
            "Route ${clazz.java.simpleName} missing from $this"
        )
}

/**
 * @see RouteConsumer.getRoute
 */
inline fun <reified T : Route> RouteConsumer.getRoute(): T = getRoute(T::class)

/**
 * @see RouteConsumer.getRouteOrNull
 */
inline fun <reified T : Route> RouteConsumer.getRouteOrNull(): T? = getRouteOrNull(T::class)

/**
 * Lazy version of [getRoute]
 */
inline fun <reified T : Route> RouteConsumer.route() = lazy { getRoute(T::class) }

/**
 * Lazy version of [getRouteOrNull]
 */
inline fun <reified T : Route> RouteConsumer.routeOrNull() = lazy { getRouteOrNull(T::class) }
