package com.gmail.jiangyang5157.router.core

import com.gmail.jiangyang5157.kit.data.Key
import com.gmail.jiangyang5157.router.core.RoutingStack.Element

/**
 * # RoutingStackElementsInstruction
 * Function that describes a manipulation of a [RoutingStack] by receiving a list of [Element]s to create a new list.
 */
typealias RoutingStackElementsInstruction<T> = List<RoutingStack.Element<T>>.() -> Iterable<RoutingStack.Element<T>>

@RoutingStackDsl
interface RoutingStackElementsInstructionExecutor<T : Route, R> {

    fun routingStackElementsInstruction(instruction: RoutingStackElementsInstruction<T>): R
}

/**
 * Will remove all routes from the stack
 */
@RoutingStackDsl
fun <T : Route, R> RoutingStackElementsInstructionExecutor<T, R>.clear(): R =
    routingStackElementsInstruction {
        emptyList()
    }

/**
 * Will remove all the routes has same [key] from the stack if found
 * Since only one [Key] can exist in [RoutingStack], there will be maximum 1 route be removed.
 */
@RoutingStackDsl
fun <T : Route, R> RoutingStackElementsInstructionExecutor<T, R>.clear(key: Key): R =
    routingStackElementsInstruction {
        filterNot { it.key == key }
    }

/**
 * Will remove the route has same [element] from the stack if found
 *
 * ## Note
 * - The [Element] will be compared by route and key (not just key)
 * - Since only one [Key] can exist in [RoutingStack], there will be maximum 1 route be removed.
 */
@RoutingStackDsl
fun <T : Route, R> RoutingStackElementsInstructionExecutor<T, R>.clear(element: RoutingStack.Element<T>): R =
    routingStackElementsInstruction {
        filterNot { it == element }
    }

/**
 * Will remove all the routes has same [route] from the stack if found
 */
@RoutingStackDsl
fun <T : Route, R> RoutingStackElementsInstructionExecutor<T, R>.clear(route: T): R =
    routingStackElementsInstruction {
        filterNot { it.route == route }
    }

/**
 * Will push the [element] to the top of the stack
 *
 * ## Note
 * - Since [element] keys are required to be distinct in the routing stack, an [Element] with the same key will be removed from the stack before pushing the new [element] to the top.
 * - The [Element] will be compared by route and key (not just key)
 */
@RoutingStackDsl
infix fun <T : Route, R> RoutingStackElementsInstructionExecutor<T, R>.push(element: RoutingStack.Element<T>): R =
    routingStackElementsInstruction {
        filterNot { it.key == element.key } + element
    }

/**
 * Will push the route to the top of the stack.
 *
 * ## Note
 * - This operation will create [RoutingStack.Element] with a random [Key]
 * - This operation is not distinct, if the [route] is already present in the stack, it will simply be duplicated.
 */
@RoutingStackDsl
infix fun <T : Route, R> RoutingStackElementsInstructionExecutor<T, R>.push(route: T): R =
    routingStackElementsInstruction {
        this + RoutingStack.Element(route)
    }

/**
 * Will push the [route] to the top of the stack, but will make sure that the route is only present once in the stack.
 *
 * ## Note
 * - All occurrences of the [route] in the current stack will be removed so that the route is just present at the top
 * - [pushDistinct] with given [RoutingStack.Element] equals to [push] with given [RoutingStack.Element]
 */
@RoutingStackDsl
infix fun <T : Route, R> RoutingStackElementsInstructionExecutor<T, R>.pushDistinct(route: T): R =
    routingStackElementsInstruction {
        val top = lastOrNull { it.route == route } ?: RoutingStack.Element(route)
        filterNot { it.route == route } + top
    }

/**
 * Will pop the top/active route if the routing stack is not empty
 */
@RoutingStackDsl
fun <T : Route, R> RoutingStackElementsInstructionExecutor<T, R>.pop(): R =
    routingStackElementsInstruction {
        if (isEmpty()) {
            emptyList()
        } else {
            subList(0, lastIndex)
        }
    }

/**
 * Will pop all routes from the top until the condition -- [predicate] -- is hit (while the element that fulfills the condition is not popped)
 */
@RoutingStackDsl
inline infix fun <T : Route, R> RoutingStackElementsInstructionExecutor<T, R>.popUntilPredicate(
    crossinline predicate: (RoutingStack.Element<T>) -> Boolean,
): R =
    routingStackElementsInstruction {
        if (isEmpty()) {
            emptyList()
        } else {
            dropLastWhile { element ->
                !predicate(element)
            }
        }
    }

/**
 * Will pop all routes from the top until the specified [key], while the given [key] itself is not popped
 */
@RoutingStackDsl
infix fun <T : Route, R> RoutingStackElementsInstructionExecutor<T, R>.popUntil(key: Key): R =
    popUntilPredicate {
        it.key == key
    }

/**
 * Will pop all routes from the top until the specified [element], while the given [element] itself is not popped
 */
@RoutingStackDsl
infix fun <T : Route, R> RoutingStackElementsInstructionExecutor<T, R>.popUntil(element: RoutingStack.Element<T>): R =
    popUntilPredicate {
        it == element
    }

/**
 * Will pop all routes from the top until the specified [route], while the given [route] itself is not popped
 */
@RoutingStackDsl
infix fun <T : Route, R> RoutingStackElementsInstructionExecutor<T, R>.popUntil(route: T): R =
    popUntilPredicate {
        it.route == route
    }

/**
 * Will replace the current top route with the new [element].
 *
 * ## Note
 * - The route will be compared by [Route] and [Key] (not just key)
 * - This is effectively just a chained `pop().push(element)`
 */
@RoutingStackDsl
infix fun <T : Route, R> RoutingStackElementsInstructionExecutor<T, R>.replaceTopWith(element: RoutingStack.Element<T>): R =
    routingStackElementsInstruction {
        if (isEmpty()) {
            listOf(element)
        } else {
            dropLast(1).filter { it.key != element.key }.plus(element)
        }
    }

/**
 * Will replace the current top route with a new [route].
 *
 * ## Note
 * - Like [push]: This operation is not distinct. If the route is already present in the routing stack, it will be duplicated (unless it already was the top route)
 * - This is effectively just a chained `pop().push(route)`
 */
@RoutingStackDsl
infix fun <T : Route, R> RoutingStackElementsInstructionExecutor<T, R>.replaceTopWith(route: T): R =
    replaceTopWith(
        RoutingStack.Element(route)
    )
