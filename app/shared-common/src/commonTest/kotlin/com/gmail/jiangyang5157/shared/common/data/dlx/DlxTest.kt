package com.gmail.jiangyang5157.shared.common.data.dlx

import kotlin.test.Test
import kotlin.test.assertTrue
import kotlin.test.fail

class DlxTest {

    @Test
    fun `Dlx with 0`() {
        val dlx = Dlx(0)
        assertTrue(dlx.columnSize() == 1)
    }

    @Test
    fun `Dlx with 10`() {
        val dlx = Dlx(10)
        assertTrue(dlx.columnSize() == 11)
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

    @Test
    fun `Dlx solve 4 with 2 solutions`() {
        /*
        Example:
        {1,2,3,4}

        set-1 = {1,2}
        set-2 = {3,4}
        set-3 = {3}
        set-4 = {4}

        Solutions:
        {1,2}, {3,4} // set-1,3
        {1,2}, {3}, {4} // set-1,3,4
        */
        val dlx = Dlx(4)
        dlx.feed(arrayOf(1, 2))
        dlx.feed(arrayOf(3, 4))
        dlx.feed(arrayOf(3))
        dlx.feed(arrayOf(4))
        dlx.solve { cells ->
            cells.forEach { cell ->
                println("Dlx solved: ${cell.rowCellsToString()}}")
            }
            false
        }
    }

    @Test
    fun `Dlx solve 7 with 2 solutions`() {
        /*
        Example:
        {1,2,3,4,5,6,7}

        set-1 = {1,4,7}
        set-2 = {1,4}
        set-3 = {4,5,7}
        set-4 = {3,5,6}
        set-5 = {2,3,6,7}
        set-6 = {2,7}
        set-7 = {1,4}

        Solutions:
        {2,7}, {3,5,6}, {1,4} // set-6,4,2
        {2,7}, {3,5,6}, {1,4} // set-6,4,7
        */
        val dlx = Dlx(7)
        dlx.feed(arrayOf(1, 4, 7))
        dlx.feed(arrayOf(1, 4))
        dlx.feed(arrayOf(4, 5, 7))
        dlx.feed(arrayOf(3, 5, 6))
        dlx.feed(arrayOf(2, 3, 6, 7))
        dlx.feed(arrayOf(2, 7))
        dlx.feed(arrayOf(1, 4))
        dlx.solve { cells ->
            cells.forEach { cell ->
                println("Dlx solved: ${cell.rowCellsToString()}}")
            }
            false
        }
    }

    @Test
    fun `Dlx solve 4 with 0 solution`() {
        /*
        Example:
        {1,2,3,4}

        set-1 = {1,2}
        set-4 = {4}

        Solutions: NA
        */
        val dlx = Dlx(4)
        dlx.feed(arrayOf(1, 2))
        dlx.feed(arrayOf(4))
        dlx.solve {
            fail()
            @Suppress("UNREACHABLE_CODE")
            false
        }
    }
}