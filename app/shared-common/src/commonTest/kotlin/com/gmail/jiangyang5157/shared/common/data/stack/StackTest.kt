package com.gmail.jiangyang5157.shared.common.data.stack

import com.gmail.jiangyang5157.shared.common.data.Key
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

class StackTest {

    data class StringData(val id: String)

    val d1 = StringData("1")
    val d2 = StringData("2")
    val d3 = 1
    val d4 = 2

    @Test
    fun `from size++`() {
        val intStack: Stack<Int> = Stack.from(
            d3,
            d4,
        )
        assertEquals(2, intStack.elements.size)

        val anyStack = Stack.from(
            d1,
            d2,
            d3,
            d4,
        )
        assertEquals(4, anyStack.elements.size)
    }


    @Test
    fun `sameKey throw exception`() {
        var exception: RuntimeException? = null
        try {
            Stack(
                listOf(
                    d1.asStackElement(key = Key("1")),
                    d2.asStackElement(key = Key("1")),
                )
            )
        } catch (e: IllegalStateException) {
            exception = e
        }
        assertEquals("Stack cannot contain entries with the same key twice.", exception?.message)
    }

    @Test
    fun `diffKey size++`() {
        val stack = Stack(
            listOf(
                d1.asStackElement(key = Key("1")),
                d2.asStackElement(key = Key("2")),
            )
        )
        assertEquals(2, stack.elements.size)
    }

    @Test
    fun `push sameValue diffKey size++`() {
        val e11 = d1.asStackElement(key = Key("1"))
        val e12 = d1.asStackElement(key = Key("2"))
        val e13 = d1.asStackElement(key = Key("3"))

        var stack = Stack(
            listOf(
                e11,
                e12,
            )
        )
        stack = stack push e13
        assertEquals(3, stack.elements.size)
        assertEquals(e13, stack.elements.last())
    }

    @Test
    fun `push sameValue sameKey size==`() {
        val e11 = d1.asStackElement(key = Key("1"))
        val e12 = d1.asStackElement(key = Key("2"))

        var stack = Stack(
            listOf(
                e11,
                e12,
            )
        )
        stack = stack push e12
        assertEquals(2, stack.elements.size)
        assertEquals(e12, stack.elements.last())
    }

    @Test
    fun `push diffValue sameKey size==`() {
        val e11 = d1.asStackElement(key = Key("1"))
        val e12 = d1.asStackElement(key = Key("2"))
        val e22 = d2.asStackElement(key = Key("2"))

        var stack = Stack(
            listOf(
                e11,
                e12,
            )
        )
        stack = stack push e22
        assertEquals(2, stack.elements.size)
        assertEquals(e22, stack.elements.last())
    }
}
