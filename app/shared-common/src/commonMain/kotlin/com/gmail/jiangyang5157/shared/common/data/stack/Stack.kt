package com.gmail.jiangyang5157.shared.common.data.stack

import com.gmail.jiangyang5157.shared.common.data.Key
import kotlin.jvm.JvmName

data class Stack<T>(
    val elements: List<StackElement<T>>
) :
    Iterable<StackElement<T>>,
    StackElementsInstructionExecutor<T, Stack<T>> {

    init {
        check(elements.groupBy { it.key }.none { (_, values) ->
            println("#### values=$values")
            values.size > 1
        }) {
            "Stack cannot contain entries with the same key twice."
        }
    }

    override fun iterator(): Iterator<StackElement<T>> = elements.iterator()

    override fun stackElementsInstruction(instruction: StackElementsInstruction<T>): Stack<T> =
        with(elements.instruction())

    /**
     * @return
     * A new [Stack] that contains the specified routes
     * This [Stack] if the routes did not change.
     */
    fun with(elements: Iterable<StackElement<T>> = this.elements): Stack<T> =
        if (elements == this.elements) {
            this
        } else {
            copy(elements = elements.toList())
        }

    companion object Factory {

        fun <T> empty(): Stack<T> =
            Stack(emptyList())

        fun <T> just(element: T): Stack<T> =
            Stack(listOf(element).toStackElements())

        @JvmName("from")
        fun <T> from(vararg elements: T): Stack<T> =
            Stack(elements.toList().toStackElements())

        @JvmName("fromIterable")
        fun <T> from(elements: Iterable<T>): Stack<T> =
            Stack(elements.toList().toStackElements())

        @JvmName("fromArray")
        fun <T> from(elements: Array<T>): Stack<T> =
            Stack(elements.toList().toStackElements())

        @JvmName("fromSequence")
        fun <T> from(elements: Sequence<T>): Stack<T> =
            Stack(elements.toList().toStackElements())
    }
}

val <T> Stack<T>.values
    get() = elements.map { it.value }

operator fun Stack<*>.contains(key: Key): Boolean =
    this.elements.any { it.key == key }

operator fun Stack<*>.contains(element: StackElement<*>): Boolean =
    this.elements.any { it == element }

operator fun <T> Stack<*>.contains(value: T): Boolean =
    this.values.contains(value)
