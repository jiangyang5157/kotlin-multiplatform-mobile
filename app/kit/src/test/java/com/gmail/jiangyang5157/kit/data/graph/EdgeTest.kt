package com.gmail.jiangyang5157.kit.data.graph

import org.junit.Test
import kotlin.test.assertEquals

/**
 * Created by Yang Jiang on June 30, 2017
 */
class EdgeTest {

    @Test
    fun test_w() {
        val w = 1.1
        val edge = Edge(w)
        assertEquals(w, edge.w)
    }

    @Test
    fun test_w_empty() {
        val edge = Edge()
        assertEquals(0.0, edge.w)
    }
}
