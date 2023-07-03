package com.gmail.jiangyang5157.router.core

import com.gmail.jiangyang5157.kit.data.Key
import com.gmail.jiangyang5157.router.core.RoutingStack.Element

/**
 * # RoutingStack
 * Represents a "routing state" where the most "top" (last) route is representing the currently "active" (displayed) route and the most "bottom" (first) route is the "root".
 *
 * ## Usage
 * The common operations of a [RoutingStack] are [push] and [pop]. There are also [clear], [pushDistinct], [popUntilPredicate], [popUntil], [replaceTopWith]
 *
 * ## Note
 * - Implementations of [RoutingStack] should implement a [equals] and [hashCode] function that makes [RoutingStack]'s comparable
 * - Implementations of [RoutingStack] should always be implemented immutable
 *
 * @see [RoutingStackElementsInstructionExecutor]
 */
@Deprecated("use [Stack] from shared-common")
interface RoutingStack<T : Route> :
    RoutingStackElementsInstructionExecutor<T, RoutingStack<T>>,
    Iterable<Element<T>> {

    /**
     * All routes that represent this routing stack.
     * This routes are stored as [Element] which makes routes identifiable by associating each entry in an stack with a [Key]
     */
    val elements: List<Element<T>>

    override fun iterator(): Iterator<Element<T>> = elements.iterator()

    /**
     * Creates a new [RoutingStack] based on the specified [instruction].
     *
     * @param instruction A simple function that describes how a new stack can be created from current stack
     *
     * @see with
     */
    override fun routingStackElementsInstruction(instruction: RoutingStackElementsInstruction<T>): RoutingStack<T> =
        with(elements.instruction())

    /**
     * @return
     * - A new [RoutingStack] that contains the specified routes
     * - This [RoutingStack] if the routes did not change.
     *
     * ## Note
     * - [RoutingStack] implementations are required to be immutable
     */
    fun with(elements: Iterable<Element<T>> = this.elements): RoutingStack<T>

    /**
     * # Element
     * Represents one entry of the [RoutingStack] that is able to identify each given [Route] by a unique [Key], so that even if the [Route]s in the stack are not distinct, the [Element]s are.
     *
     * ## Note
     * - [Element] are compared by [route] and [key], thus the behaviour of the [equals] and [hashCode] functions are guaranteed to behave consistently for all implementations.
     *
     * @see Key
     * @see Route
     */
    abstract class Element<out T : Route> {

        /**
         * Unique [Key] that can be used to identify the route inside a [RoutingStack]
         */
        abstract val key: Key

        /**
         * The associated [Route]
         *
         * # Note
         * - [Route]s are not required to be distinct in a [RoutingStack]. Use [key] to properly identify [Element]s in the [RoutingStack]
         */
        abstract val route: T

        override fun toString(): String =
            "Element(key=${key.value}, route=$route)"

        final override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (other !is Element<*>) return false
            if (this.key != other.key) return false
            if (this.route != other.route) return false
            return true
        }

        final override fun hashCode(): Int {
            var hashCode = 31
            hashCode += 31 * this.key.hashCode()
            hashCode += 31 * this.route.hashCode()
            return hashCode
        }

        companion object Factory {

            /**
             * @return A default implementation of [Element] for the given [route] and [key]
             *
             * @see RoutingStackElementImpl
             */
            operator fun <T : Route> invoke(route: T, key: Key = Key()): Element<T> =
                RoutingStackElementImpl(route, key)
        }
    }

    companion object Factory {

        fun <T : Route> empty(): RoutingStack<T> =
            RoutingStackImpl(emptyList())

        fun <T : Route> just(element: T): RoutingStack<T> =
            RoutingStackImpl(listOf(element).toElements())

        @JvmName("from")
        fun <T : Route> from(vararg elements: T): RoutingStack<T> =
            RoutingStackImpl(elements.toList().toElements())

        @JvmName("fromIterable")
        fun <T : Route> from(elements: Iterable<T>): RoutingStack<T> =
            RoutingStackImpl(elements.toList().toElements())

        @JvmName("fromArray")
        fun <T : Route> from(elements: Array<T>): RoutingStack<T> =
            RoutingStackImpl(elements.toList().toElements())

        @JvmName("fromSequence")
        fun <T : Route> from(elements: Sequence<T>): RoutingStack<T> =
            RoutingStackImpl(elements.toList().toElements())
    }
}

/**
 * Convenience function to work on the [Route] objects directly
 */
val <T : Route> RoutingStack<T>.routes
    get() = elements.map(Element<T>::route)

/**
 * @return Whether or not the [RoutingStack] contains the specified [key]
 */
operator fun RoutingStack<*>.contains(key: Key): Boolean =
    this.elements.any { it.key == key }

/**
 * @return Whether or not the [RoutingStack] contains the specified [element].
 *
 * ## Note
 * - The [Element] will be compared by route and key (not just key)
 */
operator fun RoutingStack<*>.contains(element: Element<*>): Boolean =
    this.elements.any { it == element }

/**
 * @return Whether or not the [RoutingStack] contains the specified [route]
 *
 * ## Note
 * - [Route]s may not be distinct in the [RoutingStack]. It is possible, that the a stack contains a given [Route] instance multiple times
 */
operator fun RoutingStack<*>.contains(route: Route): Boolean =
    this.routes.contains(route)

/**
 * Convert [Element]s from [Route]s
 */
private fun <T : Route> Iterable<T>.toElements() =
    this.map { element -> RoutingStackElementImpl(element) }

/**
 * ## Note
 * - If no [key] was specified, then a new random [Key] will be used.
 *
 * @return
 * - A default implementation of [Element] for this given [Route] with the specified [key].
 */
fun <T : Route> T.asElement(key: Key = Key()) =
    Element(this, key)
