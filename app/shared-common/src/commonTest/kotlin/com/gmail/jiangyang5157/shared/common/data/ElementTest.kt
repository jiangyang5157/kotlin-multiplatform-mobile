package com.gmail.jiangyang5157.shared.common.data

import kotlin.test.*

class ElementTest {

    @Test
    fun `sameValue sameKey equals`() {
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
    fun `sameValue diffKey not equals`() {
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
    fun `diffValue sameKey not equals`() {
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
    fun `sameValue randomKey not equals`() {
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
    fun `toElements use randomKeys not equals`() {
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
}
