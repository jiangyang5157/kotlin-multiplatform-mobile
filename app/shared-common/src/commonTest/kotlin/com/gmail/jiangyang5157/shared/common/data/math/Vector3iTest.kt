package com.gmail.jiangyang5157.shared.common.data.math

import com.gmail.jiangyang5157.shared.common.math.Vector3i
import kotlin.math.sqrt
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals
import kotlin.test.assertTrue

/**
 * Created by Yang Jiang on April 24, 2018
 */
class Vector3iTest {

    @Test
    fun test_constructor() {
        val empty = Vector3i()
        assertEquals(0, empty.x)
        assertEquals(0, empty.y)
        assertEquals(0, empty.z)

        val oneVal = Vector3i(1)
        assertEquals(1, oneVal.x)
        assertEquals(1, oneVal.y)
        assertEquals(1, oneVal.z)

        val threeVal = Vector3i(1, 2, 3)
        assertEquals(1, threeVal.x)
        assertEquals(2, threeVal.y)
        assertEquals(3, threeVal.z)
    }

    @Test
    fun test_equality() {
        assertEquals(Vector3i(), Vector3i())
        assertNotEquals(Vector3i(), Vector3i(1))

        assertTrue(Vector3i() == Vector3i())
        assertTrue(Vector3i() !== Vector3i())
    }

    @Test
    fun test_overrider_operator() {
        val a = Vector3i(10, 10, 10)
        val b = Vector3i(-2, 2, 1)

        assertEquals(Vector3i(2, -2, -1), -b)

        assertEquals(Vector3i(8, 12, 11), a + b)
        assertEquals(Vector3i(30, 30, 30), a + 20)

        assertEquals(Vector3i(12, 8, 9), a - b)
        assertEquals(Vector3i(-10, -10, -10), a - 20)

        assertEquals(Vector3i(-20, 20, 10), a * b)
        assertEquals(Vector3i(200, 200, 200), a * 20)

        assertEquals(Vector3i(-5, 5, 10), a / b)
        assertEquals(Vector3i(0, 0, 0), a / 20)
        assertEquals(Vector3i(1, 1, 1), a / 10)
    }

    @Test
    fun test_length() {
        val x = 1
        val y = 2
        val z = 2
        val length = sqrt((x * x + y * y + z * z).toDouble())
        assertEquals(length, Vector3i(x, y, z).length)
    }

    @Test
    fun test_normalize() {
        val x = 1
        val y = 2
        val z = 2
        val length = sqrt((x * x + y * y + z * z).toDouble())
        val normalize = Vector3i((x / length).toInt(), (y / length).toInt(), (z / length).toInt())
        assertEquals(normalize, Vector3i(x, y, z).normalize)
    }

    @Test
    fun test_dot() {
        val x1 = 1
        val y1 = 2
        val z1 = 2
        val x2 = 3
        val y2 = 4
        val z2 = 4
        val dot = x1 * x2 + y1 * y2 + z1 * z2
        assertEquals(dot.toDouble(), Vector3i(x1, y1, z1).dot(Vector3i(x2, y2, z2)))
    }

    @Test
    fun test_cross() {
        val x1 = 1
        val y1 = 2
        val z1 = 2
        val x2 = 3
        val y2 = 4
        val z2 = 4
        val cross = Vector3i(
            y1 * z2 - z1 * y2,
            z1 * x2 - x1 * z2,
            x1 * y2 - y1 * x2
        )
        assertEquals(cross, Vector3i(x1, y1, z1).cross(Vector3i(x2, y2, z2)))
    }
}
