package com.gmail.jiangyang5157.kmm.puzzle.sudoku

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.time.Duration
import kotlin.time.ExperimentalTime
import kotlin.time.measureTime

class SudokuPuzzleTest {

    @Test
    fun `SudokuPuzzle Length_1_1 has 1 solutions`() {
        val puzzle = SudokuPuzzle(SudokuTerminalTemplate.Length_1_1.data)
        var found = 0
        puzzle.solve {
            found++
            false
        }
        assertEquals(1, found)
        assertEquals(true, puzzle.withUniqueSolution())
    }

    @Test
    fun `SudokuPuzzle Length_4_0 has 0 solutions`() {
        val puzzle = SudokuPuzzle(SudokuTerminalTemplate.Length_4_0.data)
        var found = 0
        puzzle.solve {
            found++
            false
        }
        assertEquals(0, found)
        assertEquals(false, puzzle.withUniqueSolution())
    }

    @Test
    fun `SudokuPuzzle Length_4_3 has 3 solutions`() {
        val puzzle = SudokuPuzzle(SudokuTerminalTemplate.Length_4_3.data)
        var found = 0
        puzzle.solve {
            found++
            false
        }
        assertEquals(3, found)
        assertEquals(false, puzzle.withUniqueSolution())
    }

    @Test
    fun `SudokuPuzzle Length_9_0 has 0 solutions`() {
        val puzzle = SudokuPuzzle(SudokuTerminalTemplate.Length_9_0.data)
        var found = 0
        puzzle.solve {
            found++
            false
        }
        assertEquals(0, found)
        assertEquals(false, puzzle.withUniqueSolution())
    }

    @Test
    fun `SudokuPuzzle Length_9_1 has 1 solutions`() {
        val puzzle = SudokuPuzzle(SudokuTerminalTemplate.Length_9_1.data)
        var found = 0
        puzzle.solve {
            found++
            false
        }
        assertEquals(1, found)
        assertEquals(true, puzzle.withUniqueSolution())
    }

    @OptIn(ExperimentalTime::class)
    @Test
    fun `SudokuPuzzle Length_9_2 has 2 solutions`() {
        val puzzle = SudokuPuzzle(SudokuTerminalTemplate.Length_9_2.data)
        var found = 0
        val elapsed: Duration = measureTime {
            puzzle.solve {
                found++
                false
            }
        }
        println("SudokuPuzzle Length_9_2 has 2 solutions - Measuring solve time: $elapsed")
        assertEquals(2, found)
        assertEquals(false, puzzle.withUniqueSolution())
    }

    @OptIn(ExperimentalTime::class)
    @Test
    fun `SudokuPuzzle Length_9_188 has 188 solutions`() {
        val puzzle = SudokuPuzzle(SudokuTerminalTemplate.Length_9_188.data)
        var found = 0
        val elapsed: Duration = measureTime {
            puzzle.solve {
                found++
                false
            }
        }
        println("SudokuPuzzle Length_9_188 has 188 solutions - Measuring solve time: $elapsed")
        assertEquals(188, found)
        assertEquals(false, puzzle.withUniqueSolution())
    }

    @OptIn(ExperimentalTime::class)
    @Test
    fun `SudokuPuzzle withUniqueSolution`() {
        val elapsed: Duration = measureTime {
            SudokuPuzzle.withUniqueSolution(9, 8, 70)
        }
        println("SudokuPuzzle withUniqueSolution - Measuring solve time: $elapsed")
    }
}