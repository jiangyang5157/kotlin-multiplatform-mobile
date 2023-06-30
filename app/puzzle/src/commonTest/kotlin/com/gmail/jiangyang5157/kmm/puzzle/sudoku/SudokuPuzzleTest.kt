package com.gmail.jiangyang5157.kmm.puzzle.sudoku

import kotlin.test.Test
import kotlin.test.assertEquals

class SudokuPuzzleTest {

    @Test
    fun `SudokuPuzzle Length_9_2 creation`() {
        val puzzle = SudokuPuzzle(SudokuTerminalTemplate.Length_9_2.data)
        assertEquals(9, puzzle.terminal.length)
        assertEquals(0, puzzle.terminal.cells[2].block)
        assertEquals(3, puzzle.terminal.cells[2].value)
    }

    @Test
    fun `SudokuPuzzle Length_4_3 has no unique solution`() {
//        ....
//        .4..
//        2...
//        ...3
        val puzzle = SudokuPuzzle(SudokuTerminalTemplate.Length_4_3.data)
        println("#### puzzle=$puzzle")
        println("#### dlx=${puzzle.dlx}")

        puzzle.solve {
            println("#### puzzle.solve=$it")
            false
        }
    }
}