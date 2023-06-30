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

    @Test
    fun `Dlx solve 4`() {
        /*
        Example X = {1,2,3,4}
        set-1 = {1,2}
        set-2 = {3,4}
        set-3 = {3}
        set-4 = {4}
        Solutions = {6 ,4, 2} and {6, 4, 7}
    */
        val dlx = Dlx(4)
        dlx.feed(arrayOf(1, 2)) // 1
        dlx.feed(arrayOf(3, 4)) // 2
        dlx.feed(arrayOf(3)) // 3
        dlx.feed(arrayOf(4)) // 4
        println("#### dlx=${dlx}")
        dlx.solve {
            println("#### it=${it}") // 1,3 ; 1,3,4
//            println("#### it=${it.first()?.rowToString()}")
            false
        }
    }

    @Test
    fun `Dlx solve 7`() {
        /*
        Example X = {1,2,3,4,5,6,7}
        set-1 = {1,4,7}
        set-2 = {1,4}
        set-3 = {4,5,7}
        set-4 = {3,5,6}
        set-5 = {2,3,6,7}
        set-6 = {2,7}
        set-7 = {1,4}
        Solutions = {6 ,4, 2} and {6, 4, 7}
    */
        val dlx = Dlx(7)
        dlx.feed(arrayOf(1, 4, 7)) // 1
        dlx.feed(arrayOf(1, 4)) // 2
        dlx.feed(arrayOf(4, 5, 7)) // 3
        dlx.feed(arrayOf(3, 5, 6)) // 4
        dlx.feed(arrayOf(2, 3, 6, 7)) // 5
        dlx.feed(arrayOf(2, 7)) // 6
        dlx.feed(arrayOf(1, 4)) // 7
        println("#### dlx=${dlx}")
        dlx.solve {
            println("#### it=${it}") // 2,3,1 ; 2,3,1
//            println("#### it=${it.first()?.rowToString()}")
            false
        }
    }
}