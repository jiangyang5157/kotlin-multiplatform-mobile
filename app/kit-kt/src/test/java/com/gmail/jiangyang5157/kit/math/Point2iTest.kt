package com.gmail.jiangyang5157.kit.math

import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals
import kotlin.test.assertTrue

/**
 * Created by Yang Jiang on April 22, 2018
 */
class Point2iTest {

    @Test
    fun test_constructor() {
        val empty = Point2i()
        assertEquals(0, empty.x)
        assertEquals(0, empty.y)

        val oneVal = Point2i(1)
        assertEquals(1, oneVal.x)
        assertEquals(1, oneVal.y)

        val twoVal = Point2i(1, 2)
        assertEquals(1, twoVal.x)
        assertEquals(2, twoVal.y)
    }

    @Test
    fun test_equality() {
        assertEquals(Point2i(), Point2i())
        assertNotEquals(Point2i(), Point2i(1))

        assertTrue(Point2i() == Point2i())
        assertTrue(Point2i() !== Point2i())
    }
}
