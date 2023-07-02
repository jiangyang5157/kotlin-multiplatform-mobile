package com.gmail.jiangyang5157.shared.common.data.stack

import com.gmail.jiangyang5157.shared.common.data.Element
import com.gmail.jiangyang5157.shared.common.data.Key
import com.gmail.jiangyang5157.shared.common.data.toElements
import kotlin.jvm.JvmName

data class Stack<T>(
    private var elements: List<Element<T>>
) :
    Iterable<Element<T>>,
    StackInstructionExecutor<T, Stack<T>> {

    init {
        check(
            elements.groupBy { it.key }.none { (_, values) -> values.size > 1 }
        ) {
            "Stack cannot contain entries with the same key twice."
        }
    }

    val size get() = elements.size
    val keys get() = elements.map { it.key }
    val values get() = elements.map { it.value }

    fun contains(key: Key): Boolean = elements.any { it.key == key }
    fun contains(element: Element<T>): Boolean = elements.any { it == element }
    fun contains(value: T): Boolean = values.contains(value)

    override fun iterator(): Iterator<Element<T>> = elements.iterator()

    override fun elementsInstruction(instruction: ElementsInstruction<T>): Stack<T> =
        with(elements.instruction())

    fun with(elements: Iterable<Element<T>> = this.elements): Stack<T> {
        if (this.elements != elements) {
            this.elements = elements.toList()
        }
        return this
    }

    companion object Factory {

        fun <T> empty(): Stack<T> = Stack(emptyList())

        /**
         * With unique Keys
         */
        @JvmName("from")
        fun <T> from(vararg elements: T): Stack<T> = Stack(elements.toList().toElements())

        /**
         * With unique Keys
         */
        @JvmName("fromIterable")
        fun <T> from(elements: Iterable<T>): Stack<T> = Stack(elements.toList().toElements())

        /**
         * With unique Keys
         */
        @JvmName("fromArray")
        fun <T> from(elements: Array<T>): Stack<T> = Stack(elements.toList().toElements())

        /**
         * With unique Keys
         */
        @JvmName("fromSequence")
        fun <T> from(elements: Sequence<T>): Stack<T> = Stack(elements.toList().toElements())
    }
}