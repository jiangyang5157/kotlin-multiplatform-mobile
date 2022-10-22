package com.gmail.jiangyang5157.kit.math

import kotlin.test.assertEquals
import kotlin.test.assertNotEquals
import kotlin.test.assertTrue

/**
 * Created by Yang Jiang on June 26, 2017
 */
class Vector2dTest {

    @org.junit.Test
    fun test_constructor() {
        val empty = Vector2d()
        assertEquals(0.0, empty.x)
        assertEquals(0.0, empty.y)

        val oneVal = Vector2d(1.1)
        assertEquals(1.1, oneVal.x)
        assertEquals(1.1, oneVal.y)

        val twoVal = Vector2d(1.0, 2.0)
        assertEquals(1.0, twoVal.x)
        assertEquals(2.0, twoVal.y)
    }

    @org.junit.Test
    fun test_equality() {
        assertEquals(Vector2d(), Vector2d())
        assertNotEquals(Vector2d(), Vector2d(1.1))

        assertTrue(Vector2d() == Vector2d())
        assertTrue(Vector2d() !== Vector2d())
    }

    @org.junit.Test
    fun test_overrider_operator() {
        val a = Vector2d(10.0, 10.0)
        val b = Vector2d(-2.0, 2.0)

        assertEquals(Vector2d(2.0, -2.0), -b)

        assertEquals(Vector2d(8.0, 12.0), a + b)
        assertEquals(Vector2d(30.0, 30.0), a + 20)
        assertEquals(Vector2d(30.2, 30.2), a + 20.2)

        assertEquals(Vector2d(12.0, 8.0), a - b)
        assertEquals(Vector2d(-10.0, -10.0), a - 20)
        assertEquals(Vector2d(-10.2, -10.2), a - 20.2)

        assertEquals(Vector2d(-20.0, 20.0), a * b)
        assertEquals(Vector2d(200.0, 200.0), a * 20)
        assertEquals(Vector2d(202.0, 202.0), a * 20.2)

        assertEquals(Vector2d(-5.0, 5.0), a / b)
        assertEquals(Vector2d(0.5, 0.5), a / 20)
        assertEquals(Vector2d(1.0, 1.0), a / 10)
    }

    @org.junit.Test
    fun test_length() {
        val x = 1.5
        val y = 2.5
        val length = Math.sqrt(x * x + y * y)
        assertEquals(length, Vector2d(x, y).length)
    }

    @org.junit.Test
    fun test_normalize() {
        val x = 1.5
        val y = 2.5
        val length = Math.sqrt(x * x + y * y)
        val normalize = Vector2d(x / length, y / length)
        assertEquals(normalize, Vector2d(x, y).normalize)
    }

    @org.junit.Test
    fun test_dot() {
        val x1 = 1.5
        val y1 = 2.5
        val x2 = 3.5
        val y2 = 4.5
        val dot = x1 * x2 + y1 * y2
        assertEquals(dot, Vector2d(x1, y1).dot(Vector2d(x2, y2)))
    }

    @org.junit.Test
    fun test_cross() {
        val x1 = 1.5
        val y1 = 2.5
        val x2 = 3.5
        val y2 = 4.5
        val cross = x1 * y2 + y1 * x2
        assertEquals(cross, Vector2d(x1, y1).cross(Vector2d(x2, y2)))
    }
}
