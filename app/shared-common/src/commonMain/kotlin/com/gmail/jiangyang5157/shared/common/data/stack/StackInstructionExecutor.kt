package com.gmail.jiangyang5157.shared.common.data.stack

import com.gmail.jiangyang5157.shared.common.data.Element
import com.gmail.jiangyang5157.shared.common.data.Key

typealias ElementsInstruction<T> = List<Element<T>>.() -> Iterable<Element<T>>

interface StackInstructionExecutor<T, R> {
    fun elementsInstruction(instruction: ElementsInstruction<T>): R
}

fun <T, R> StackInstructionExecutor<T, R>.clear(): R =
    elementsInstruction {
        emptyList()
    }

infix fun <T, R> StackInstructionExecutor<T, R>.clear(key: Key): R =
    elementsInstruction {
        filterNot { it.key == key }
    }

infix fun <T, R> StackInstructionExecutor<T, R>.clear(element: Element<T>): R =
    elementsInstruction {
        filterNot { it == element }
    }

infix fun <T, R> StackInstructionExecutor<T, R>.clear(value: T): R =
    elementsInstruction {
        filterNot { it.value == value }
    }

/**
 * A [Element] with the same key will be removed from the stack before pushing the new [Element] to the top.
 * Push the [element] to the top of the stack.
 */
infix fun <T, R> StackInstructionExecutor<T, R>.push(element: Element<T>): R =
    elementsInstruction {
        filterNot { it.key == element.key } + element
    }

/**
 * Create a [Element] with a unique key
 * Push it to the top of the stack.
 */
infix fun <T, R> StackInstructionExecutor<T, R>.push(value: T): R =
    elementsInstruction {
        this + Element(value)
    }

/**
 * Create a [Element] with a unique key
 * Push it to the top of the stack, but will make sure that the value is only present once in the stack.
 *
 * Note:
 * - All occurrences of the [value] in the current stack will be removed so that the route is just present at the top
 */
infix fun <T, R> StackInstructionExecutor<T, R>.pushDistinct(value: T): R =
    elementsInstruction {
        filterNot { it.value == value } + Element(value)
    }

fun <T, R> StackInstructionExecutor<T, R>.pop(): R =
    elementsInstruction {
        if (isEmpty()) {
            emptyList()
        } else {
            subList(0, lastIndex)
        }
    }

inline infix fun <T, R> StackInstructionExecutor<T, R>.popUntilPredicate(
    crossinline predicate: (Element<T>) -> Boolean,
): R =
    elementsInstruction {
        if (isEmpty()) {
            emptyList()
        } else {
            dropLastWhile { element ->
                !predicate(element)
            }
        }
    }

infix fun <T, R> StackInstructionExecutor<T, R>.popUntil(key: Key): R =
    popUntilPredicate {
        it.key == key
    }

infix fun <T, R> StackInstructionExecutor<T, R>.popUntil(element: Element<T>): R =
    popUntilPredicate {
        it == element
    }

infix fun <T, R> StackInstructionExecutor<T, R>.popUntil(value: T): R =
    popUntilPredicate {
        it.value == value
    }

/**
 * A [Element] with the same key will be removed from the stack before pushing the new [Element] to the top.
 */
infix fun <T, R> StackInstructionExecutor<T, R>.replaceTopWith(element: Element<T>): R =
    elementsInstruction {
        if (isEmpty()) {
            listOf(element)
        } else {
            dropLast(1).filterNot { it.key == element.key } + element
        }
    }

/**
 * Create a [Element] with a unique key
 * Replace top with it
 */
infix fun <T, R> StackInstructionExecutor<T, R>.replaceTopWith(value: T): R =
    replaceTopWith(Element(value))
