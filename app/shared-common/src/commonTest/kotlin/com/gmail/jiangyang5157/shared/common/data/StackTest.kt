package com.gmail.jiangyang5157.shared.common.data

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

class StackTest {

    @Test
    fun from_size() {
        val anyStack = Stack.from(
            1,
            1,
            "1",
            1f,
            0.0,
        )
        assertEquals(5, anyStack.size)
    }

    @Test
    fun sameKey_throw_exception() {
        var exception: RuntimeException? = null
        try {
            Stack(
                listOf(
                    1.toElement(key = Key("1")),
                    2.toElement(key = Key("1")),
                )
            )
        } catch (e: IllegalStateException) {
            exception = e
        }
        assertEquals("Stack cannot contain entries with the same key twice.", exception?.message)
    }

    @Test
    fun push_sameValue_diffKey_size() {
        val e11 = 1.toElement(key = Key("1"))
        val e12 = 1.toElement(key = Key("2"))
        val e13 = 1.toElement(key = Key("3"))

        val stack = Stack(
            listOf(
                e11,
                e12,
            )
        )
        stack push e13
        assertEquals(3, stack.size)
        assertEquals(e13, stack.lastOrNull())
    }

    @Test
    fun push_sameValue_sameKey_size() {
        val e11 = 1.toElement(key = Key("1"))
        val e12 = 1.toElement(key = Key("2"))
        val e13 = 1.toElement(key = Key("3"))
        val e13_ = 1.toElement(key = Key("3"))

        val stack = Stack(
            listOf(
                e11,
                e12,
                e13,
            )
        )
        stack push e13_
        assertEquals(3, stack.size)
        assertEquals(e13_, stack.lastOrNull())
    }

    @Test
    fun push_diffValue_sameKey_size() {
        val e11 = 1.toElement(key = Key("1"))
        val e21 = 2.toElement(key = Key("1"))
        val e31 = 3.toElement(key = Key("1"))

        val stack = Stack(
            listOf(
                e11,
            )
        )
        stack push e21
        stack push e31
        assertEquals(1, stack.size)
        assertEquals(e31, stack.lastOrNull())
    }

    @Test
    fun clear_all_size() {
        val e11 = 1.toElement(key = Key("1"))
        val e12 = 1.toElement(key = Key("2"))
        val e13 = 1.toElement(key = Key("3"))
        val e14 = 1.toElement(key = Key("4"))

        val stack = Stack(
            listOf(
                e11,
                e12,
                e13,
                e14,
            )
        )
        stack.clear()
        assertEquals(0, stack.size)
        assertEquals(null, stack.lastOrNull())
    }

    @Test
    fun clear_value() {
        val e11 = 1.toElement(key = Key("1"))
        val e22 = 2.toElement(key = Key("2"))
        val e23 = 2.toElement(key = Key("3"))
        val e24 = 2.toElement(key = Key("4"))

        val stack = Stack(
            listOf(
                e11,
                e22,
                e23,
                e24,
            )
        )
        stack clear 2
        assertEquals(1, stack.size)
        assertEquals(e11, stack.lastOrNull())
    }

    @Test
    fun clear_element_size() {
        val e11 = 1.toElement(key = Key("1"))
        val e22 = 2.toElement(key = Key("2"))
        val e23 = 2.toElement(key = Key("3"))
        val e24 = 2.toElement(key = Key("4"))

        val stack = Stack(
            listOf(
                e11,
                e22,
                e23,
                e24,
            )
        )
        stack clear 2.toElement(key = Key("4"))
        assertEquals(3, stack.size)
        assertEquals(e23, stack.lastOrNull())
    }

    @Test
    fun pushDistinct_removed_same_value() {
        val e11 = 1.toElement(key = Key("1"))
        val e22 = 2.toElement(key = Key("2"))
        val e33 = 3.toElement(key = Key("3"))
        val e24 = 2.toElement(key = Key("4"))

        val stack = Stack(
            listOf(
                e11,
                e22,
                e33,
                e24,
            )
        )
        stack pushDistinct 2
        assertEquals(3, stack.size)
        assertEquals(2, stack.lastOrNull()?.value)
        assertNotEquals(e24, stack.lastOrNull())
        assertEquals(e33, stack.iterator().let {
            it.next()
            it.next()
        })
    }

    @Test
    fun popUntil_value_removed_same_value() {
        val e11 = 1.toElement(key = Key("1"))
        val e12 = 1.toElement(key = Key("2"))
        val e23 = 2.toElement(key = Key("3"))
        val e34 = 3.toElement(key = Key("4"))

        val stack = Stack(
            listOf(
                e11,
                e12,
                e23,
                e34,
            )
        )
        stack popUntil 1
        assertEquals(2, stack.size)
        assertEquals(e12, stack.lastOrNull())
    }

    @Test
    fun popUntil_element() {
        val e11 = 1.toElement(key = Key("1"))
        val e12 = 1.toElement(key = Key("2"))
        val e23 = 2.toElement(key = Key("3"))
        val e34 = 3.toElement(key = Key("4"))

        val stack = Stack(
            listOf(
                e11,
                e12,
                e23,
                e34,
            )
        )
        stack popUntil 1.toElement(key = Key("1"))
        assertEquals(1, stack.size)
        assertEquals(e11, stack.lastOrNull())
    }

    @Test
    fun pop_to_key() {
        val e11 = 1.toElement(key = Key("1"))
        val e12 = 1.toElement(key = Key("2"))
        val e23 = 2.toElement(key = Key("3"))
        val e34 = 3.toElement(key = Key("4"))

        val stack = Stack(
            listOf(
                e11,
                e12,
                e23,
                e34,
            )
        )
        stack popUntil Key("3")
        assertEquals(3, stack.size)
        assertEquals(e23, stack.lastOrNull())
    }

    @Test
    fun popUntilPredicate_value() {
        val e11 = 1.toElement(key = Key("1"))
        val e12 = 1.toElement(key = Key("2"))
        val e23 = 2.toElement(key = Key("3"))
        val e34 = 3.toElement(key = Key("4"))

        val stack = Stack(
            listOf(
                e11,
                e12,
                e23,
                e34,
            )
        )
        stack popUntilPredicate { it.value == 2 }
        assertEquals(3, stack.size)
        assertEquals(e23, stack.lastOrNull())
    }

    @Test
    fun replaceTopWith_element() {
        val e11 = 1.toElement(key = Key("1"))
        val e12 = 1.toElement(key = Key("2"))
        val e23 = 2.toElement(key = Key("3"))
        val e34 = 3.toElement(key = Key("4"))

        val stack = Stack(
            listOf(
                e11,
                e12,
                e23,
                e34,
            )
        )
        stack replaceTopWith 1.toElement(key = Key("2"))
        assertEquals(3, stack.size)
        assertEquals(e12, stack.lastOrNull())
        assertEquals(e23, stack.iterator().let {
            it.next()
            it.next()
        })
    }

    @Test
    fun replaceTopWith_value() {
        val e11 = 1.toElement(key = Key("1"))
        val e12 = 1.toElement(key = Key("2"))
        val e23 = 2.toElement(key = Key("3"))
        val e34 = 3.toElement(key = Key("4"))

        val stack = Stack(
            listOf(
                e11,
                e12,
                e23,
                e34,
            )
        )
        stack replaceTopWith 1
        assertEquals(4, stack.size)
        assertEquals(1, stack.lastOrNull()?.value)
        assertEquals(e12, stack.iterator().let {
            it.next()
            it.next()
        })
    }
}