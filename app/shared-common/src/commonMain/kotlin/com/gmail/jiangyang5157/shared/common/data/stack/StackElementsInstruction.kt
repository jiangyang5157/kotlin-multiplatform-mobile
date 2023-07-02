package com.gmail.jiangyang5157.shared.common.data.stack

import com.gmail.jiangyang5157.shared.common.data.Key

typealias StackElementsInstruction<T> = List<StackElement<T>>.() -> Iterable<StackElement<T>>

@DslMarker
annotation class StackElementsDsl

@StackElementsDsl
interface StackElementsInstructionExecutor<T, R> {
    fun stackElementsInstruction(instruction: StackElementsInstruction<T>): R
}

@StackElementsDsl
fun <T, R> StackElementsInstructionExecutor<T, R>.clear(): R =
    stackElementsInstruction {
        emptyList()
    }

@StackElementsDsl
fun <T, R> StackElementsInstructionExecutor<T, R>.clear(key: Key): R =
    stackElementsInstruction {
        filterNot { it.key == key }
    }

@StackElementsDsl
fun <T, R> StackElementsInstructionExecutor<T, R>.clear(element: StackElement<T>): R =
    stackElementsInstruction {
        filterNot { it == element }
    }

@StackElementsDsl
fun <T, R> StackElementsInstructionExecutor<T, R>.clear(value: T): R =
    stackElementsInstruction {
        filterNot { it.value == value }
    }

/**
 * A [StackElement] with the same key will be removed from the stack before pushing the new [StackElement] to the top.
 * Push the [element] to the top of the stack.
 */
@StackElementsDsl
infix fun <T, R> StackElementsInstructionExecutor<T, R>.push(element: StackElement<T>): R =
    stackElementsInstruction {
        filterNot { it.key == element.key } + element
    }

/**
 * Create a [StackElement] with a random key.
 * Push the [element] to the top of the stack.
 */
@StackElementsDsl
infix fun <T, R> StackElementsInstructionExecutor<T, R>.push(value: T): R =
    stackElementsInstruction {
        this + StackElement(value)
    }

@StackElementsDsl
infix fun <T, R> StackElementsInstructionExecutor<T, R>.pushDistinct(value: T): R =
    stackElementsInstruction {
        val top = lastOrNull { it.value == value } ?: StackElement(value)
        filterNot { it.value == value } + top
    }

@StackElementsDsl
fun <T, R> StackElementsInstructionExecutor<T, R>.pop(): R =
    stackElementsInstruction {
        if (isEmpty()) {
            emptyList()
        } else {
            subList(0, lastIndex)
        }
    }

@StackElementsDsl
inline infix fun <T, R> StackElementsInstructionExecutor<T, R>.popUntilPredicate(
    crossinline predicate: (StackElement<T>) -> Boolean,
): R =
    stackElementsInstruction {
        if (isEmpty()) {
            emptyList()
        } else {
            dropLastWhile { element ->
                !predicate(element)
            }
        }
    }

@StackElementsDsl
infix fun <T, R> StackElementsInstructionExecutor<T, R>.popUntil(key: Key): R =
    popUntilPredicate {
        it.key == key
    }

@StackElementsDsl
infix fun <T, R> StackElementsInstructionExecutor<T, R>.popUntil(element: StackElement<T>): R =
    popUntilPredicate {
        it == element
    }

@StackElementsDsl
infix fun <T, R> StackElementsInstructionExecutor<T, R>.popUntil(value: T): R =
    popUntilPredicate {
        it.value == value
    }

@StackElementsDsl
infix fun <T, R> StackElementsInstructionExecutor<T, R>.replaceTopWith(element: StackElement<T>): R =
    stackElementsInstruction {
        if (isEmpty()) {
            listOf(element)
        } else {
            dropLast(1).filter { it.key != element.key }.plus(element)
        }
    }

@StackElementsDsl
infix fun <T, R> StackElementsInstructionExecutor<T, R>.replaceTopWith(value: T): R =
    replaceTopWith(
        StackElement(value)
    )