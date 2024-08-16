package com.gmail.jiangyang5157.shared.common.data.dlx

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import kotlin.test.fail

class DlxTest {

    @Test
    fun dlx_with_0() {
        val dlx = Dlx(0)
        assertTrue(dlx.columns.size == 1)
    }

    @Test
    fun dlx_with_10() {
        val dlx = Dlx(10)
        assertTrue(dlx.columns.size == 11)
    }

    @Test
    fun dlx_feed_with_empty_index_do_nothing() {
        val dlx = Dlx(10)
        dlx.feed(arrayOf())

        assertTrue(dlx.columns.all { it.size == 0 })
    }

    @Test
    fun dlx_feed_with_index_1_to_size() {
        val dlx = Dlx(10)
        dlx.feed(arrayOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10))

        assertEquals(0, dlx.columns[0].size)
        assertTrue(dlx.columns.drop(1).all { it.size == 1 })
    }

    @Test
    fun dlx_solve_4_with_2_solutions() {
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

        var solutionCount = 0
        dlx.solve { cells ->
            cells.forEach { cell ->
                println("Dlx solved: ${cell.rowCells().toColumnString()}}")
            }
            solutionCount++
            false
        }

        assertEquals(2, solutionCount)
    }

    @Test
    fun dlx_solve_7_with_2_solutions() {
        val dlx = Dlx(7)
        dlx.feed(arrayOf(1, 4, 7))
        dlx.feed(arrayOf(1, 4))
        dlx.feed(arrayOf(4, 5, 7))
        dlx.feed(arrayOf(3, 5, 6))
        dlx.feed(arrayOf(2, 3, 6, 7))
        dlx.feed(arrayOf(2, 7))
        dlx.feed(arrayOf(1, 4))

        var solutionCount = 0
        dlx.solve { cells ->
            cells.forEach { cell ->
                println("Dlx solved: ${cell.rowCells().toColumnString()}")
            }
            solutionCount++
            false
        }

        assertEquals(2, solutionCount)
    }

    @Test
    fun dlx_solve_4_with_0_solution() {
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
            fail("No solutions should be found")
            @Suppress("UNREACHABLE_CODE")
            false
        }
    }
}