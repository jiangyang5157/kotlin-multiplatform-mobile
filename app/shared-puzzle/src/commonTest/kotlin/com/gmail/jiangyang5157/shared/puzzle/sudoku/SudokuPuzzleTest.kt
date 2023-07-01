package com.gmail.jiangyang5157.shared.puzzle.sudoku

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
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
        assertEquals(true, puzzle.hasUniqueSolution())
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
        assertEquals(false, puzzle.hasUniqueSolution())
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
        assertEquals(false, puzzle.hasUniqueSolution())
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
        assertEquals(false, puzzle.hasUniqueSolution())
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
        assertEquals(true, puzzle.hasUniqueSolution())
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
        assertEquals(false, puzzle.hasUniqueSolution())
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
        assertEquals(false, puzzle.hasUniqueSolution())
    }

    @OptIn(ExperimentalTime::class)
    @Test
    fun `SudokuPuzzle Length_9_188 has 188 solutions and solve for first found`() {
        val puzzle = SudokuPuzzle(SudokuTerminalTemplate.Length_9_188.data)
        var terminal: SudokuTerminal?
        val elapsed: Duration = measureTime {
            terminal = puzzle.solve()
        }
        println("SudokuPuzzle Length_9_188 has 188 solutions and solve for first found - Measuring solve time: $elapsed")
        assertNotNull(terminal)
    }

    @OptIn(ExperimentalTime::class)
    @Test
    fun `SudokuPuzzle Length_9_188 has 188 solutions and solve many times without impact original terminal`() {
        val terminal = SudokuTerminalTemplate.Length_9_188.data
        val puzzle = SudokuPuzzle(terminal)
        assertEquals(terminal, puzzle.terminal)

        var found = 0
        var elapsed: Duration = measureTime {
            puzzle.solve {
                found++
                false
            }
        }
        println("SudokuPuzzle Length_9_188 has 188 solutions - Measuring solve time 1: $elapsed")
        assertEquals(188, found)
        assertEquals(false, puzzle.hasUniqueSolution())
        assertEquals(terminal, puzzle.terminal)

        found = 0
        elapsed = measureTime {
            puzzle.solve {
                found++
                false
            }
        }
        println("SudokuPuzzle Length_9_188 has 188 solutions - Measuring solve time 2: $elapsed")
        assertEquals(188, found)
        assertEquals(false, puzzle.hasUniqueSolution())
        assertEquals(terminal, puzzle.terminal)

        found = 0
        elapsed = measureTime {
            puzzle.solve {
                found++
                false
            }
        }
        println("SudokuPuzzle Length_9_188 has 188 solutions - Measuring solve time 3: $elapsed")
        assertEquals(188, found)
        assertEquals(false, puzzle.hasUniqueSolution())
        assertEquals(terminal, puzzle.terminal)
    }

    @OptIn(ExperimentalTime::class)
    @Test
    fun `SudokuPuzzle buildTerminal`() {
        var terminal: SudokuTerminal
        val elapsed: Duration = measureTime {
            terminal = SudokuPuzzle.buildTerminal(9, 1, 18)
            println("#### SudokuPuzzle.buildTerminal=\n${terminal.toValueString()}")
        }
        println("SudokuPuzzle buildTerminal - Measuring solve time: $elapsed")
        assertEquals(true, SudokuPuzzle(terminal).hasUniqueSolution())
    }
}