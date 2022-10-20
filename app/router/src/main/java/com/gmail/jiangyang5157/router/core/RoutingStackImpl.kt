package com.gmail.jiangyang5157.router.core

/**
 * The default implementation of [RoutingStack]
 */
internal data class RoutingStackImpl<T : Route>(
    override val elements: List<RoutingStack.Element<T>>
) : RoutingStack<T> {

    override fun with(elements: Iterable<RoutingStack.Element<T>>): RoutingStack<T> =
        if (elements == this.elements) {
            this
        } else {
            copy(elements = elements.toList())
        }

    init {
        check(elements.groupBy(RoutingStack.Element<*>::key).none { (_, routes) -> routes.size > 1 }) {
            "RoutingStack cannot contain entries with the same key twice."
        }
    }
}
