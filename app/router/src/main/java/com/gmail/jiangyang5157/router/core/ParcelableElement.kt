package com.gmail.jiangyang5157.router.core

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * [RoutingStack.Element] implementation that also implements [Parcelable]
 */
@Parcelize
data class ParcelableElement<T>(
    override val key: ParcelableKey,
    override val route: T
) : RoutingStack.Element<T>(), Parcelable where T : Route, T : Parcelable

/**
 * @return [ParcelableElement] for the current element of this instance if it already implements [ParcelableElement]
 */
fun <T> RoutingStack.Element<T>.parcelable(): ParcelableElement<T> where T : Route, T : Parcelable =
    when (this) {
        is ParcelableElement -> this
        else -> ParcelableElement(key.parcelable(), route)
    }

/**
 * Convert all [RoutingStack.Element]s to [ParcelableElement]s
 */
fun <T> Iterable<RoutingStack.Element<T>>.parcelable() where T : Route, T : Parcelable =
    this.map { entry -> entry.parcelable() }
