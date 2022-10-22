package com.gmail.jiangyang5157.common.data.graph

import kotlin.test.assertEquals
import kotlin.test.assertNotEquals
import kotlin.test.assertTrue

/**
 * Created by Yang Jiang on June 30, 2017
 */
class NodeTest {

    @org.junit.Test
    fun test_id_Int() {
        val id = 1
        val node: Node<Int> =
            Node(id)
        assertEquals(id, node.id)
    }

    @org.junit.Test
    fun test_id_Double() {
        val id = 1.1
        val node: Node<Double> =
            Node(id)
        assertEquals(id, node.id)
    }

    @org.junit.Test
    fun test_id_String() {
        val id = "A"
        val node: Node<String> =
            Node(id)
        assertEquals(id, node.id)
    }

    @org.junit.Test
    fun test_equality() {
        assertEquals(
            Node("A"),
            Node("A")
        )
        assertEquals(
            Node(1),
            Node(1)
        )
        assertEquals(
            Node(1.1),
            Node(1.1)
        )
        assertNotEquals(
            Node(
                "A"
            ),
            Node("B")
        )
        assertNotEquals(
            Node(
                1
            ),
            Node(2)
        )
        assertNotEquals(
            Node(
                1.1
            ),
            Node(2.2)
        )

        assertTrue(
            Node(1) == Node(
                1
            )
        )
        assertTrue(
            Node(1) !== Node(
                1
            )
        )
    }
}
