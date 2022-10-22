package com.gmail.jiangyang5157.demo_router.uri

import android.os.Bundle
import android.os.Parcelable
import com.gmail.jiangyang5157.router.core.Route
import com.gmail.jiangyang5157.router.core.RoutingStack
import com.gmail.jiangyang5157.router.fragment.setup.RoutingStackStorage

/**
 * This class demonstrates how we can use a custom stack storage
 *
 * @see ParcelableRoutingStackStorage The Default implementation
 */
class CustomStackStorage<T> : RoutingStackStorage<T> where T : Route, T : Parcelable {

    private val data = mutableMapOf<String, RoutingStack<T>>()

    override fun RoutingStack<T>.saveTo(outState: Bundle) {
        data[KEY_ROUTING_STACK] = this
    }

    override fun Bundle.restore(): RoutingStack<T>? {
        return data[KEY_ROUTING_STACK]
    }

    companion object {
        const val KEY_ROUTING_STACK = "CustomStackStorage"
    }
}
