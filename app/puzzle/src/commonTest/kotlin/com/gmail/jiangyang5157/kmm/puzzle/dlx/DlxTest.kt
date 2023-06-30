package com.gmail.jiangyang5157.kmm.puzzle.dlx

import kotlin.test.Test
import kotlin.test.assertTrue

class DlxTest {

    @Test
    fun `Dlx create`() {
        val dlx = Dlx(10)
        assertTrue(dlx.columnSize() == 11)
        assertTrue(dlx.peekSolution().isEmpty())
    }

    @Test
    fun `Dlx reset with positive size`() {
        val dlx = Dlx(10)
        assertTrue(dlx.columnSize() == 11)
        dlx.feed(arrayOf(1, 2, 3, 4))
        dlx.feed(arrayOf(2, 3, 4, 5))
        dlx.feed(arrayOf(7, 8, 9, 10))
        dlx.reset(1)
        assertTrue(dlx.columnSize() == 2)
        dlx.reset(0)
        assertTrue(dlx.columnSize() == 1)
    }

    @Test
    fun `Dlx reset with negative size`() {
        val dlx = Dlx(-1)
        assertTrue(dlx.columnSize() == 0)
        dlx.reset(-2)
        assertTrue(dlx.columnSize() == 0)
    }

    @Test
    fun `Dlx feed with empty index do nothing`() {
        val dlx = Dlx(10)
        dlx.feed(arrayOf())
        dlx.peekColumn().forEach {
            assertTrue(it.size == 0)
        }
    }

    @Test
    fun `Dlx feed with negative index do nothing`() {
        val dlx = Dlx(10)
        dlx.feed(arrayOf(-1))
        dlx.peekColumn().forEach {
            assertTrue(it.size == 0)
        }
    }

    @Test
    fun `Dlx feed with index large then size do nothing`() {
        val dlx = Dlx(10)
        dlx.feed(arrayOf(11))
        dlx.peekColumn().forEach {
            assertTrue(it.size == 0)
        }
    }

    @Test
    fun `Dlx feed with head index 0 do nothing`() {
        val dlx = Dlx(10)
        dlx.feed(arrayOf(0))
        dlx.peekColumn().forEach {
            assertTrue(it.size == 0)
        }
    }

    @Test
    fun `Dlx feed with index 1 to size`() {
        val dlx = Dlx(10)
        dlx.feed(arrayOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10))
        dlx.peekColumn().forEach {
            if (it.index == 0) {
                assertTrue(it.size == 0)
            } else {
                assertTrue(it.size == 1)
            }
        }
    }
}