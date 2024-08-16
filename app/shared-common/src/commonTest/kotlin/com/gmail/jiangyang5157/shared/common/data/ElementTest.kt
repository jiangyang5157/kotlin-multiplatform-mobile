package com.gmail.jiangyang5157.shared.common.data

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals
import kotlin.test.assertNotSame

class ElementTest {

    @Test
    fun sameValue_sameKey_equals() {
        assertEquals(
            "1".toElement(key = Key("1")),
            "1".toElement(key = Key("1")),
        )
        assertEquals(
            1.toElement(key = Key("1")),
            1.toElement(key = Key("1")),
        )
    }

    @Test
    fun sameValue_diffKey_not_equals() {
        assertNotEquals(
            "1".toElement(key = Key("1")),
            "1".toElement(key = Key("2")),
        )
        assertNotEquals(
            1.toElement(key = Key("1")),
            1.toElement(key = Key("2")),
        )
    }

    @Test
    fun diffValue_sameKey_not_equals() {
        assertNotEquals(
            "1".toElement(key = Key("1")),
            "2".toElement(key = Key("1")),
        )
        assertNotEquals(
            1.toElement(key = Key("1")),
            2.toElement(key = Key("1")),
        )
    }

    @Test
    fun sameValue_randomKey_not_equals() {
        assertNotEquals(
            "1".toElement(),
            "1".toElement(),
        )
        assertNotEquals(
            1.toElement(),
            1.toElement(),
        )
    }

    @Test
    fun toElements_use_randomKeys_not_equals() {
        val stringElements = listOf("1", "1").toElements()
        val intElements = listOf(1, 1).toElements()
        assertNotEquals(
            stringElements[0],
            stringElements[1],
        )
        assertNotEquals(
            intElements[0],
            intElements[1],
        )
    }

    @Test
    fun copy_equals_but_not_same() {
        val element = 1.toElement(key = Key("1"))
        val copy = element.copy()
        assertEquals(element, copy)
        assertNotSame(element, copy)
    }
}
