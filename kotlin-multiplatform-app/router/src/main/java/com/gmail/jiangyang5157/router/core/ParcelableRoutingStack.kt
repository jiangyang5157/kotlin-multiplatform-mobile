package com.gmail.jiangyang5157.router.core

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * [RoutingStack] implementation that also implements [Parcelable]
 */
interface ParcelableRoutingStack<T : Route> : RoutingStack<T>, Parcelable

/**
 * @return [ParcelableRoutingStack] for the current [RoutingStack], or this instance if it already implements [ParcelableRoutingStack]
 */
fun <T> RoutingStack<T>.parcelable(): ParcelableRoutingStack<T> where T : Route, T : Parcelable =
    when (this) {
        is ParcelableRoutingStack<T> -> this
        else -> ParcelableRoutingStackImpl(this.elements.map { element -> element.parcelable() })
    }

@Parcelize
private class ParcelableRoutingStackImpl<T>(override val elements: List<ParcelableElement<T>>) :
    ParcelableRoutingStack<T> where T : Route, T : Parcelable {

    override fun with(elements: Iterable<RoutingStack.Element<T>>): ParcelableRoutingStack<T> =
        ParcelableRoutingStackImpl(elements.map { element -> element.parcelable() })
}
