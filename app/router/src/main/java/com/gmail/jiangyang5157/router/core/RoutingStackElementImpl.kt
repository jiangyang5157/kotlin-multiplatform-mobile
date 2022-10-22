package com.gmail.jiangyang5157.router.core

import com.gmail.jiangyang5157.common.data.Key

/**
 * The default implementation of [RoutingStack.Element]
 */
data class RoutingStackElementImpl<T : Route>(
    override val route: T,
    override val key: Key = Key(),
) : RoutingStack.Element<T>()
