package com.gmail.jiangyang5157.shared.puzzle.sudoku

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.time.Duration
import kotlin.time.ExperimentalTime
import kotlin.time.measureTime

class SudokuPuzzleTest {

    @Test
    fun SudokuPuzzle_Length_1_1_has_1_solutions() {
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
    fun SudokuPuzzle_Length_4_0_has_0_solutions() {
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
    fun SudokuPuzzle_Length_4_3_has_3_solutions() {
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
    fun SudokuPuzzle_Length_9_0_has_0_solutions() {
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
    fun SudokuPuzzle_Length_9_1_has_1_solutions() {
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
    fun SudokuPuzzle_Length_9_2_has_2_solutions() {
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
    fun SudokuPuzzle_Length_9_188_has_188_solutions() {
        val puzzle = SudokuPuzzle(SudokuTerminalTemplate.Length_9_188.data)
        var found = 0
        val elapsed: Duration = measureTime {
            puzzle.solve {
                found++
                false
            }
        }
        println("SudokuPuzzle_Length_9_188_has_188 solutions - Measuring solve time: $elapsed")
        assertEquals(188, found)
        assertEquals(false, puzzle.hasUniqueSolution())
    }

    @OptIn(ExperimentalTime::class)
    @Test
    fun SudokuPuzzle_Length_9_188_has_188_solutions_and_solve_for_first_found() {
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
    fun SudokuPuzzle_Length_9_188_has_188_solutions_and_solve_many_times_without_impact_original_terminal() {
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
}