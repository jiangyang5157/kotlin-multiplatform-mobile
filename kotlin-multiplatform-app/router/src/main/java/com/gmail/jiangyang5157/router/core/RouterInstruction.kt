package com.gmail.jiangyang5157.router.core

import com.gmail.jiangyang5157.router.fragment.FragmentRouter

/**
 * # RoutingStackInstruction
 * A [Router] can execute arbitrary changes in the [RoutingStack].
 * All instructions that one can send to a [Router] are represented as a function from the current [RoutingStack] to the desired one.
 *
 * ## Note
 * - This function has to be pure
 * - This function should not have any side-effects
 * - This function should not mutate the current [RoutingStack], but create a new one.
 *
 * @see Router
 */
typealias RoutingStackInstruction<T> = RoutingStack<T>.() -> RoutingStack<T>

/**
 * @return A combination of the receiver instruction and the [other] instruction.
 */
operator fun <T : Route> RoutingStackInstruction<T>.plus(other: RoutingStackInstruction<T>): RoutingStackInstruction<T> =
    { other(this@plus.invoke(this)) }

/**
 * Creates an instance of [RoutingStackInstruction] that represents a "noop" by just retuning the [RoutingStack] as it is.
 */
@Suppress("FunctionName")
fun <T : Route> emptyRouterInstruction(): RoutingStackInstruction<T> =
    { this }

/**
 * @see Router
 * @see FragmentRouter.State
 */
interface RouterInstructionExecutor<T : Route> {

    /**
     * Will execute a [RoutingStackInstruction] which represents any arbitrary change of the [RoutingStack] by creating a new [RoutingStack]
     *
     * @see RoutingStackInstruction
     */
    fun routingStackInstruction(instruction: RoutingStackInstruction<T>)
}
