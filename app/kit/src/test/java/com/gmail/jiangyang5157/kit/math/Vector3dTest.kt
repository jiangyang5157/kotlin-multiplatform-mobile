package com.gmail.jiangyang5157.kit.math

import kotlin.test.assertEquals
import kotlin.test.assertNotEquals
import kotlin.test.assertTrue

/**
 * Created by Yang Jiang on June 27, 2017
 */
class Vector3dTest {

    @org.junit.Test
    fun test_constructor() {
        val empty = Vector3d()
        assertEquals(0.0, empty.x)
        assertEquals(0.0, empty.y)
        assertEquals(0.0, empty.z)

        val oneVal = Vector3d(1.1)
        assertEquals(1.1, oneVal.x)
        assertEquals(1.1, oneVal.y)
        assertEquals(1.1, oneVal.z)

        val threeVal = Vector3d(1.0, 2.0, 3.0)
        assertEquals(1.0, threeVal.x)
        assertEquals(2.0, threeVal.y)
        assertEquals(3.0, threeVal.z)
    }

    @org.junit.Test
    fun test_equality() {
        assertEquals(Vector3d(), Vector3d())
        assertNotEquals(Vector3d(), Vector3d(1.1))

        assertTrue(Vector3d() == Vector3d())
        assertTrue(Vector3d() !== Vector3d())
    }

    @org.junit.Test
    fun test_overrider_operator() {
        val a = Vector3d(10.0, 10.0, 10.0)
        val b = Vector3d(-2.0, 2.0, 1.0)

        assertEquals(Vector3d(2.0, -2.0, -1.0), -b)

        assertEquals(Vector3d(8.0, 12.0, 11.0), a + b)
        assertEquals(Vector3d(30.0, 30.0, 30.0), a + 20)
        assertEquals(Vector3d(30.2, 30.2, 30.2), a + 20.2)

        assertEquals(Vector3d(12.0, 8.0, 9.0), a - b)
        assertEquals(Vector3d(-10.0, -10.0, -10.0), a - 20)
        assertEquals(Vector3d(-10.2, -10.2, -10.2), a - 20.2)

        assertEquals(Vector3d(-20.0, 20.0, 10.0), a * b)
        assertEquals(Vector3d(200.0, 200.0, 200.0), a * 20)
        assertEquals(Vector3d(202.0, 202.0, 202.0), a * 20.2)

        assertEquals(Vector3d(-5.0, 5.0, 10.0), a / b)
        assertEquals(Vector3d(0.5, 0.5, 0.5), a / 20)
        assertEquals(Vector3d(1.0, 1.0, 1.0), a / 10)
    }

    @org.junit.Test
    fun test_length() {
        val x = 1.5
        val y = 2.5
        val z = 2.5
        val length = Math.sqrt(x * x + y * y + z * z)
        assertEquals(length, Vector3d(x, y, z).length)
    }

    @org.junit.Test
    fun test_normalize() {
        val x = 1.5
        val y = 2.5
        val z = 2.5
        val length = Math.sqrt(x * x + y * y + z * z)
        val normalize = Vector3d(x / length, y / length, z / length)
        assertEquals(normalize, Vector3d(x, y, z).normalize)
    }

    @org.junit.Test
    fun test_dot() {
        val x1 = 1.5
        val y1 = 2.5
        val z1 = 2.5
        val x2 = 3.5
        val y2 = 4.5
        val z2 = 4.5
        val dot = x1 * x2 + y1 * y2 + z1 * z2
        assertEquals(dot, Vector3d(x1, y1, z1).dot(Vector3d(x2, y2, z2)))
    }

    @org.junit.Test
    fun test_cross() {
        val x1 = 1.5
        val y1 = 2.5
        val z1 = 2.5
        val x2 = 3.5
        val y2 = 4.5
        val z2 = 4.5
        val cross = Vector3d(
            y1 * z2 - z1 * y2,
            z1 * x2 - x1 * z2,
            x1 * y2 - y1 * x2
        )
        assertEquals(cross, Vector3d(x1, y1, z1).cross(Vector3d(x2, y2, z2)))
    }
}
