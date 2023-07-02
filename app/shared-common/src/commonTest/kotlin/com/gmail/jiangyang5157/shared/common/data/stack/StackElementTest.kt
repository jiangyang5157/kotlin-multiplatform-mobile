package com.gmail.jiangyang5157.shared.common.data.stack

import com.gmail.jiangyang5157.shared.common.data.Key
import kotlin.test.*

class StackElementTest {

    data class StringData(val id: String)

    val d1 = StringData("1")
    val d2 = StringData("2")
    val d3 = 1
    val d4 = 2

    @Test
    fun `sameValue sameKey equals`() {
        val e1 = d1.asStackElement(key = Key("1"))
        val e2 = d1.asStackElement(key = Key("1"))
        assertEquals(e1, e2)

        val e3 = d3.asStackElement(key = Key("1"))
        val e4 = d3.asStackElement(key = Key("1"))
        assertEquals(e3, e4)
    }

    @Test
    fun `sameValue random_or_diff not equals`() {
        val e1 = d1.asStackElement()
        val e2 = d1.asStackElement()
        assertNotEquals(e1, e2)

        val e3 = d3.asStackElement()
        val e4 = d3.asStackElement()
        assertNotEquals(e3, e4)

        val e5 = d1.asStackElement(key = Key("1"))
        val e6 = d1.asStackElement(key = Key("2"))
        assertNotEquals(e5, e6)

        val e7 = d3.asStackElement(key = Key("1"))
        val e8 = d3.asStackElement(key = Key("2"))
        assertNotEquals(e7, e8)
    }

    @Test
    fun `diffValue sameKey not equals`() {
        val e1 = d1.asStackElement(key = Key("1"))
        val e2 = d2.asStackElement(key = Key("1"))
        assertNotEquals(e1, e2)

        val e3 = d3.asStackElement(key = Key("1"))
        val e4 = d4.asStackElement(key = Key("1"))
        assertNotEquals(e3, e4)
    }
}
