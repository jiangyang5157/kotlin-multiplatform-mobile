package com.gmail.jiangyang5157.shared.common.data.math

import com.gmail.jiangyang5157.shared.common.math.Point3d
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals
import kotlin.test.assertTrue

/**
 * Created by Yang Jiang on June 26, 2017
 */
class Point3dTest {

    @Test
    fun test_constructor() {
        val empty = Point3d()
        assertEquals(0.0, empty.x)
        assertEquals(0.0, empty.y)
        assertEquals(0.0, empty.z)

        val oneVal = Point3d(1.1)
        assertEquals(1.1, oneVal.x)
        assertEquals(1.1, oneVal.y)
        assertEquals(1.1, oneVal.z)

        val threeVal = Point3d(1.0, 2.0, 3.0)
        assertEquals(1.0, threeVal.x)
        assertEquals(2.0, threeVal.y)
        assertEquals(3.0, threeVal.z)
    }

    @Test
    fun test_equality() {
        assertEquals(Point3d(), Point3d())
        assertNotEquals(Point3d(), Point3d(1.1))

        assertTrue(Point3d() == Point3d())
        assertTrue(Point3d() !== Point3d())
    }
}
