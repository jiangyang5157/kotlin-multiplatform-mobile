package com.gmail.jiangyang5157.router.fragment

import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import com.gmail.jiangyang5157.router.core.ParcelableRoute
import com.gmail.jiangyang5157.router.core.ParcelableRoutingStack
import com.gmail.jiangyang5157.router.core.Route
import com.gmail.jiangyang5157.router.core.RoutingStack
import com.gmail.jiangyang5157.router.core.parcelable
import com.gmail.jiangyang5157.router.core.routes
import com.gmail.jiangyang5157.router.fragment.setup.RoutingStackStorage
import com.gmail.jiangyang5157.router.utils.Constant

@Suppress("UNCHECKED_CAST")
internal fun <T : Route> ParcelableRoutingStackStorage.Companion.createUnsafe(
    key: String = KEY_ROUTING_STACK
): RoutingStackStorage<T> =
    ParcelableRoutingStackStorage<ParcelableRoute>(key) as RoutingStackStorage<T>

class ParcelableRoutingStackStorage<T>(
    private val key: String = KEY_ROUTING_STACK
) : RoutingStackStorage<T> where T : Route, T : Parcelable {

    override fun RoutingStack<T>.saveTo(outState: Bundle) {
        Log.d(Constant.TAG, "saving routes: ${this.routes.joinToString(", ")}")
        outState.putParcelable(key, parcelable())
    }

    override fun Bundle.restore(): RoutingStack<T>? =
        getParcelable<ParcelableRoutingStack<T>>(key).also {
            Log.d(Constant.TAG, "restored routes: ${it?.routes?.joinToString(", ")}")
        }

    companion object {
        const val KEY_ROUTING_STACK = "routing_stack"
    }
}
