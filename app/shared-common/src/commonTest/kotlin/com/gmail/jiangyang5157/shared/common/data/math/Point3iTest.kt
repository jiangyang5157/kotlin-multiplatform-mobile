package com.gmail.jiangyang5157.shared.common.data.math

import com.gmail.jiangyang5157.shared.common.math.Point3i
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals
import kotlin.test.assertTrue

/**
 * Created by Yang Jiang on April 22, 2018
 */
class Point3iTest {

    @Test
    fun test_constructor() {
        val empty = Point3i()
        assertEquals(0, empty.x)
        assertEquals(0, empty.y)
        assertEquals(0, empty.z)

        val oneVal = Point3i(1)
        assertEquals(1, oneVal.x)
        assertEquals(1, oneVal.y)
        assertEquals(1, oneVal.z)

        val threeVal = Point3i(1, 2, 3)
        assertEquals(1, threeVal.x)
        assertEquals(2, threeVal.y)
        assertEquals(3, threeVal.z)
    }

    @Test
    fun test_equality() {
        assertEquals(Point3i(), Point3i())
        assertNotEquals(Point3i(), Point3i(1))

        assertEquals(Point3i(), Point3i())
        assertTrue(Point3i() !== Point3i())
    }
}