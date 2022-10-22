package com.gmail.jiangyang5157.kit.math

import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals
import kotlin.test.assertTrue

/**
 * Created by Yang Jiang on June 26, 2017
 */
class Point2dTest {

    @Test
    fun test_constructor() {
        val empty = Point2d()
        assertEquals(0.0, empty.x)
        assertEquals(0.0, empty.y)

        val oneVal = Point2d(1.1)
        assertEquals(1.1, oneVal.x)
        assertEquals(1.1, oneVal.y)

        val twoVal = Point2d(1.0, 2.0)
        assertEquals(1.0, twoVal.x)
        assertEquals(2.0, twoVal.y)
    }

    @Test
    fun test_equality() {
        assertEquals(Point2d(), Point2d())
        assertNotEquals(Point2d(), Point2d(1.1))

        assertTrue(Point2d() == Point2d())
        assertTrue(Point2d() !== Point2d())
    }
}
