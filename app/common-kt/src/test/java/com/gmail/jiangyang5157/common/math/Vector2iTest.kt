package com.gmail.jiangyang5157.common.math

import kotlin.test.assertEquals
import kotlin.test.assertNotEquals
import kotlin.test.assertTrue

/**
 * Created by Yang Jiang on April 24, 2018
 */

class Vector2iTest {

    @org.junit.Test
    fun test_constructor() {
        val empty = Vector2i()
        assertEquals(0, empty.x)
        assertEquals(0, empty.y)

        val oneVal = Vector2i(1)
        assertEquals(1, oneVal.x)
        assertEquals(1, oneVal.y)

        val twoVal = Vector2i(1, 2)
        assertEquals(1, twoVal.x)
        assertEquals(2, twoVal.y)
    }

    @org.junit.Test
    fun test_equality() {
        assertEquals(Vector2i(), Vector2i())
        assertNotEquals(Vector2i(), Vector2i(1))

        assertTrue(Vector2i() == Vector2i())
        assertTrue(Vector2i() !== Vector2i())
    }

    @org.junit.Test
    fun test_overrider_operator() {
        val a = Vector2i(10, 10)
        val b = Vector2i(-2, 2)

        assertEquals(Vector2i(2, -2), -b)

        assertEquals(Vector2i(8, 12), a + b)
        assertEquals(Vector2i(30, 30), a + 20)

        assertEquals(Vector2i(12, 8), a - b)
        assertEquals(Vector2i(-10, -10), a - 20)

        assertEquals(Vector2i(-20, 20), a * b)
        assertEquals(Vector2i(200, 200), a * 20)

        assertEquals(Vector2i(-5, 5), a / b)
        assertEquals(Vector2i(0, 0), a / 20)
        assertEquals(Vector2i(1, 1), a / 10)
    }

    @org.junit.Test
    fun test_length() {
        val x = 1
        val y = 2
        val length = Math.sqrt((x * x + y * y).toDouble())
        assertEquals(length, Vector2i(x, y).length)
    }

    @org.junit.Test
    fun test_normalize() {
        val x = 1
        val y = 2
        val length = Math.sqrt((x * x + y * y).toDouble())
        val normalize = Vector2i((x / length).toInt(), (y / length).toInt())
        assertEquals(normalize, Vector2i(x, y).normalize)
    }

    @org.junit.Test
    fun test_dot() {
        val x1 = 1
        val y1 = 2
        val x2 = 3
        val y2 = 4
        val dot = x1 * x2 + y1 * y2
        assertEquals(dot.toDouble(), Vector2i(x1, y1).dot(Vector2i(x2, y2)))
    }

    @org.junit.Test
    fun test_cross() {
        val x1 = 1
        val y1 = 2
        val x2 = 3
        val y2 = 4
        val cross = x1 * y2 + y1 * x2
        assertEquals(cross.toDouble(), Vector2i(x1, y1).cross(Vector2i(x2, y2)))
    }
}
