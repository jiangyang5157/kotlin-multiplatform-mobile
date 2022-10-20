package com.gmail.jiangyang5157.router.fragment

import android.os.Bundle
import android.os.Parcelable
import androidx.fragment.app.Fragment
import com.gmail.jiangyang5157.router.core.ParcelableRoute
import com.gmail.jiangyang5157.router.core.Route
import com.gmail.jiangyang5157.router.error.RouterException
import com.gmail.jiangyang5157.router.fragment.setup.FragmentRouteStorage

@Suppress("UNCHECKED_CAST")
internal fun <T : Route> ParcelableFragmentRouteStorage.Companion.createUnsafe():
    FragmentRouteStorage<T> =
    ParcelableFragmentRouteStorage<ParcelableRoute>() as FragmentRouteStorage<T>

class ParcelableFragmentRouteStorage<T>(
    private val bundleKey: String = KEY_ROUTE
) : FragmentRouteStorage<T> where T : Parcelable, T : Route {

    override fun Fragment.attach(route: T) {
        val arguments = this.arguments ?: Bundle()
        arguments.putParcelable(bundleKey, route)
        this.arguments = arguments
    }

    override fun Fragment.getRouteOrNull(): T? =
        arguments?.getParcelable(bundleKey)

    override fun Fragment.getRoute(): T =
        getRouteOrNull() ?: throw RouterException(
            "Expected route with key $bundleKey"
        )

    companion object {
        const val KEY_ROUTE = "route"
    }
}
