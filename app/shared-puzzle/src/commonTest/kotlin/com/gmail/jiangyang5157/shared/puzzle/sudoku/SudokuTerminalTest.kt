package com.gmail.jiangyang5157.shared.puzzle.sudoku

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.time.Duration
import kotlin.time.ExperimentalTime
import kotlin.time.measureTime

class SudokuTerminalTest {

    @Test
    fun `SudokuTerminal Length_1_1 from json`() {
        val data = Json.decodeFromString<SudokuTerminal>(SudokuTerminalTemplate.Length_1_1.json)
        assertEquals(data, SudokuTerminalTemplate.Length_1_1.data)
    }

    @Test
    fun `SudokuTerminal Length_4_3 from json`() {
        val data = Json.decodeFromString<SudokuTerminal>(SudokuTerminalTemplate.Length_4_3.json)
        assertEquals(data, SudokuTerminalTemplate.Length_4_3.data)
    }

    @Test
    fun `SudokuTerminal Length_9_0 from json`() {
        val data = Json.decodeFromString<SudokuTerminal>(SudokuTerminalTemplate.Length_9_0.json)
        assertEquals(data, SudokuTerminalTemplate.Length_9_0.data)
    }

    @Test
    fun `SudokuTerminal Length_9_1 from json`() {
        val data = Json.decodeFromString<SudokuTerminal>(SudokuTerminalTemplate.Length_9_1.json)
        assertEquals(data, SudokuTerminalTemplate.Length_9_1.data)
    }

    @Test
    fun `SudokuTerminal Length_9_2 from json`() {
        val data = Json.decodeFromString<SudokuTerminal>(SudokuTerminalTemplate.Length_9_2.json)
        assertEquals(data, SudokuTerminalTemplate.Length_9_2.data)
    }

    @Test
    fun `SudokuTerminal Length_9_188 from json`() {
        val data = Json.decodeFromString<SudokuTerminal>(SudokuTerminalTemplate.Length_9_188.json)
        assertEquals(data, SudokuTerminalTemplate.Length_9_188.data)
    }

    @Test
    fun `SudokuTerminal test index`() {
        assertEquals(0, SudokuTerminalTemplate.Length_9_0.data.index(0, 0))
        assertEquals(80, SudokuTerminalTemplate.Length_9_0.data.index(8, 8))
    }

    @Test
    fun `SudokuTerminal test rowIndex`() {
        assertEquals(0, SudokuTerminalTemplate.Length_9_0.data.rowIndex(0))
        assertEquals(0, SudokuTerminalTemplate.Length_9_0.data.rowIndex(8))
        assertEquals(1, SudokuTerminalTemplate.Length_9_0.data.rowIndex(9))
        assertEquals(7, SudokuTerminalTemplate.Length_9_0.data.rowIndex(71))
        assertEquals(8, SudokuTerminalTemplate.Length_9_0.data.rowIndex(72))
        assertEquals(8, SudokuTerminalTemplate.Length_9_0.data.rowIndex(80))
    }

    @Test
    fun `SudokuTerminal test columnIndex`() {
        assertEquals(0, SudokuTerminalTemplate.Length_9_0.data.columnIndex(0))
        assertEquals(8, SudokuTerminalTemplate.Length_9_0.data.columnIndex(8))
        assertEquals(0, SudokuTerminalTemplate.Length_9_0.data.columnIndex(9))
        assertEquals(8, SudokuTerminalTemplate.Length_9_0.data.columnIndex(71))
        assertEquals(0, SudokuTerminalTemplate.Length_9_0.data.columnIndex(72))
        assertEquals(8, SudokuTerminalTemplate.Length_9_0.data.columnIndex(80))
    }

    @Test
    fun `SudokuTerminal test upIndex`() {
        assertEquals(-1, SudokuTerminalTemplate.Length_9_0.data.upIndex(0))
        assertEquals(-1, SudokuTerminalTemplate.Length_9_0.data.upIndex(1))
        assertEquals(-1, SudokuTerminalTemplate.Length_9_0.data.upIndex(2))
        assertEquals(-1, SudokuTerminalTemplate.Length_9_0.data.upIndex(3))
        assertEquals(-1, SudokuTerminalTemplate.Length_9_0.data.upIndex(4))
        assertEquals(-1, SudokuTerminalTemplate.Length_9_0.data.upIndex(5))
        assertEquals(-1, SudokuTerminalTemplate.Length_9_0.data.upIndex(6))
        assertEquals(-1, SudokuTerminalTemplate.Length_9_0.data.upIndex(7))
        assertEquals(-1, SudokuTerminalTemplate.Length_9_0.data.upIndex(8))

        assertEquals(0, SudokuTerminalTemplate.Length_9_0.data.upIndex(9))
        assertEquals(8, SudokuTerminalTemplate.Length_9_0.data.upIndex(17))

        assertEquals(63, SudokuTerminalTemplate.Length_9_0.data.upIndex(72))
        assertEquals(71, SudokuTerminalTemplate.Length_9_0.data.upIndex(80))
    }

    @Test
    fun `SudokuTerminal test downIndex`() {
        assertEquals(9, SudokuTerminalTemplate.Length_9_0.data.downIndex(0))
        assertEquals(17, SudokuTerminalTemplate.Length_9_0.data.downIndex(8))

        assertEquals(79, SudokuTerminalTemplate.Length_9_0.data.downIndex(70))
        assertEquals(80, SudokuTerminalTemplate.Length_9_0.data.downIndex(71))

        assertEquals(-1, SudokuTerminalTemplate.Length_9_0.data.downIndex(72))
        assertEquals(-1, SudokuTerminalTemplate.Length_9_0.data.downIndex(73))
        assertEquals(-1, SudokuTerminalTemplate.Length_9_0.data.downIndex(74))
        assertEquals(-1, SudokuTerminalTemplate.Length_9_0.data.downIndex(75))
        assertEquals(-1, SudokuTerminalTemplate.Length_9_0.data.downIndex(76))
        assertEquals(-1, SudokuTerminalTemplate.Length_9_0.data.downIndex(77))
        assertEquals(-1, SudokuTerminalTemplate.Length_9_0.data.downIndex(78))
        assertEquals(-1, SudokuTerminalTemplate.Length_9_0.data.downIndex(79))
        assertEquals(-1, SudokuTerminalTemplate.Length_9_0.data.downIndex(80))
    }

    @Test
    fun `SudokuTerminal test leftIndex`() {
        assertEquals(-1, SudokuTerminalTemplate.Length_9_0.data.leftIndex(0))
        assertEquals(-1, SudokuTerminalTemplate.Length_9_0.data.leftIndex(9))
        assertEquals(-1, SudokuTerminalTemplate.Length_9_0.data.leftIndex(18))
        assertEquals(-1, SudokuTerminalTemplate.Length_9_0.data.leftIndex(27))
        assertEquals(-1, SudokuTerminalTemplate.Length_9_0.data.leftIndex(36))
        assertEquals(-1, SudokuTerminalTemplate.Length_9_0.data.leftIndex(45))
        assertEquals(-1, SudokuTerminalTemplate.Length_9_0.data.leftIndex(54))
        assertEquals(-1, SudokuTerminalTemplate.Length_9_0.data.leftIndex(63))
        assertEquals(-1, SudokuTerminalTemplate.Length_9_0.data.leftIndex(72))

        assertEquals(0, SudokuTerminalTemplate.Length_9_0.data.leftIndex(1))
        assertEquals(9, SudokuTerminalTemplate.Length_9_0.data.leftIndex(10))
        assertEquals(18, SudokuTerminalTemplate.Length_9_0.data.leftIndex(19))
        assertEquals(27, SudokuTerminalTemplate.Length_9_0.data.leftIndex(28))
        assertEquals(36, SudokuTerminalTemplate.Length_9_0.data.leftIndex(37))
        assertEquals(45, SudokuTerminalTemplate.Length_9_0.data.leftIndex(46))
        assertEquals(54, SudokuTerminalTemplate.Length_9_0.data.leftIndex(55))
        assertEquals(63, SudokuTerminalTemplate.Length_9_0.data.leftIndex(64))
        assertEquals(72, SudokuTerminalTemplate.Length_9_0.data.leftIndex(73))
    }

    @Test
    fun `SudokuTerminal test rightIndex`() {
        assertEquals(8, SudokuTerminalTemplate.Length_9_0.data.rightIndex(7))
        assertEquals(17, SudokuTerminalTemplate.Length_9_0.data.rightIndex(16))
        assertEquals(26, SudokuTerminalTemplate.Length_9_0.data.rightIndex(25))
        assertEquals(35, SudokuTerminalTemplate.Length_9_0.data.rightIndex(34))
        assertEquals(44, SudokuTerminalTemplate.Length_9_0.data.rightIndex(43))
        assertEquals(53, SudokuTerminalTemplate.Length_9_0.data.rightIndex(52))
        assertEquals(62, SudokuTerminalTemplate.Length_9_0.data.rightIndex(61))
        assertEquals(71, SudokuTerminalTemplate.Length_9_0.data.rightIndex(70))
        assertEquals(80, SudokuTerminalTemplate.Length_9_0.data.rightIndex(79))

        assertEquals(-1, SudokuTerminalTemplate.Length_9_0.data.rightIndex(8))
        assertEquals(-1, SudokuTerminalTemplate.Length_9_0.data.rightIndex(17))
        assertEquals(-1, SudokuTerminalTemplate.Length_9_0.data.rightIndex(26))
        assertEquals(-1, SudokuTerminalTemplate.Length_9_0.data.rightIndex(35))
        assertEquals(-1, SudokuTerminalTemplate.Length_9_0.data.rightIndex(44))
        assertEquals(-1, SudokuTerminalTemplate.Length_9_0.data.rightIndex(53))
        assertEquals(-1, SudokuTerminalTemplate.Length_9_0.data.rightIndex(62))
        assertEquals(-1, SudokuTerminalTemplate.Length_9_0.data.rightIndex(71))
        assertEquals(-1, SudokuTerminalTemplate.Length_9_0.data.rightIndex(80))
    }

    @Test
    fun `SudokuTerminal test neighbourIndexes`() {
        assertEquals(listOf(9, 1), SudokuTerminalTemplate.Length_9_0.data.neighbourIndexes(0))
        assertEquals(listOf(10, 0, 2), SudokuTerminalTemplate.Length_9_0.data.neighbourIndexes(1))
        assertEquals(listOf(0, 18, 10), SudokuTerminalTemplate.Length_9_0.data.neighbourIndexes(9))

        assertEquals(listOf(17, 7), SudokuTerminalTemplate.Length_9_0.data.neighbourIndexes(8))

        assertEquals(
            listOf(31, 49, 39, 41), SudokuTerminalTemplate.Length_9_0.data.neighbourIndexes(40)
        )

        assertEquals(listOf(63, 73), SudokuTerminalTemplate.Length_9_0.data.neighbourIndexes(72))

        assertEquals(
            listOf(62, 80, 70), SudokuTerminalTemplate.Length_9_0.data.neighbourIndexes(71)
        )
        assertEquals(
            listOf(70, 78, 80), SudokuTerminalTemplate.Length_9_0.data.neighbourIndexes(79)
        )
        assertEquals(listOf(71, 79), SudokuTerminalTemplate.Length_9_0.data.neighbourIndexes(80))
    }

    @Test
    fun `SudokuTerminal test toString`() {
        assertEquals(
            """
            SudokuTerminal(
            length=9,
            cells=
            0[0],0[0],0[0],0[1],0[1],0[1],1[2],2[2],3[2],
            0[0],0[0],9[0],0[1],0[1],0[1],0[2],0[2],0[2],
            0[0],0[0],0[0],0[1],0[1],9[1],0[2],0[2],0[2],
            0[3],0[3],0[3],0[4],0[4],0[4],0[5],0[5],0[5],
            0[3],0[3],0[3],0[4],0[4],0[4],0[5],0[5],0[5],
            0[3],0[3],0[3],0[4],0[4],0[4],0[5],0[5],0[5],
            0[6],0[6],0[6],0[7],0[7],0[7],0[8],0[8],0[8],
            0[6],0[6],0[6],0[7],0[7],0[7],0[8],0[8],0[8],
            0[6],0[6],0[6],0[7],0[7],0[7],0[8],0[8],0[8],
            )
        """.trimIndent(), SudokuTerminalTemplate.Length_9_0.data.toString()
        )
    }

    @OptIn(ExperimentalTime::class)
    @Test
    fun `SudokuTerminal withUniqueSolution minSubGiven 0 - 25-81 - 150ms`() {
        val length = 9
        val minSubGiven = 0
        var terminal: SudokuTerminal
        val elapsed: Duration = measureTime {
            terminal = SudokuTerminal.withUniqueSolution(length = length, minSubGiven = minSubGiven)
            println("#### SudokuTerminal.withUniqueSolution($length, $minSubGiven)=\n${terminal.toValueString()}${length * length - terminal.cells.filter { it.value == 0 }.size}/${length * length}")
        }
        println("SudokuTerminal withUniqueSolution - Measuring solve time: $elapsed")
        assertEquals(true, SudokuPuzzle(terminal).hasUniqueSolution())
    }

    @OptIn(ExperimentalTime::class)
    @Test
    fun `SudokuTerminal withUniqueSolution minSubGiven 4 - 28-81 - 120ms`() {
        val length = 9
        val minSubGiven = 4
        var terminal: SudokuTerminal
        val elapsed: Duration = measureTime {
            terminal = SudokuTerminal.withUniqueSolution(length = length, minSubGiven = minSubGiven)
            println("#### SudokuTerminal.withUniqueSolution($length, $minSubGiven)=\n${terminal.toValueString()}${length * length - terminal.cells.filter { it.value == 0 }.size}/${length * length}")
        }
        println("SudokuTerminal withUniqueSolution - Measuring solve time: $elapsed")
        assertEquals(true, SudokuPuzzle(terminal).hasUniqueSolution())
    }

    @OptIn(ExperimentalTime::class)
    @Test
    fun `SudokuTerminal withUniqueSolution minSubGiven 5 - 38-81 - 110ms`() {
        val length = 9
        val minSubGiven = 5
        var terminal: SudokuTerminal
        val elapsed: Duration = measureTime {
            terminal = SudokuTerminal.withUniqueSolution(length = length, minSubGiven = minSubGiven)
            println("#### SudokuTerminal.withUniqueSolution($length, $minSubGiven)=\n${terminal.toValueString()}${length * length - terminal.cells.filter { it.value == 0 }.size}/${length * length}")
        }
        println("SudokuTerminal withUniqueSolution - Measuring solve time: $elapsed")
        assertEquals(true, SudokuPuzzle(terminal).hasUniqueSolution())
    }

    @OptIn(ExperimentalTime::class)
    @Test
    fun `SudokuTerminal withUniqueSolution minSubGiven 6 - 48-81 - 100ms`() {
        val length = 9
        val minSubGiven = 6
        var terminal: SudokuTerminal
        val elapsed: Duration = measureTime {
            terminal = SudokuTerminal.withUniqueSolution(length = length, minSubGiven = minSubGiven)
            println("#### SudokuTerminal.withUniqueSolution($length, $minSubGiven)=\n${terminal.toValueString()}${length * length - terminal.cells.filter { it.value == 0 }.size}/${length * length}")
        }
        println("SudokuTerminal withUniqueSolution - Measuring solve time: $elapsed")
        assertEquals(true, SudokuPuzzle(terminal).hasUniqueSolution())
    }

    @OptIn(ExperimentalTime::class)
    @Test
    fun `SudokuTerminal withUniqueSolution minSubGiven 7 - 58-81 - 90ms`() {
        val length = 9
        val minSubGiven = 7
        var terminal: SudokuTerminal
        val elapsed: Duration = measureTime {
            terminal = SudokuTerminal.withUniqueSolution(length = length, minSubGiven = minSubGiven)
            println("#### SudokuTerminal.withUniqueSolution($length, $minSubGiven)=\n${terminal.toValueString()}${length * length - terminal.cells.filter { it.value == 0 }.size}/${length * length}")
        }
        println("SudokuTerminal withUniqueSolution - Measuring solve time: $elapsed")
        assertEquals(true, SudokuPuzzle(terminal).hasUniqueSolution())
    }

    @OptIn(ExperimentalTime::class)
    @Test
    fun `SudokuTerminal withUniqueSolution minSubGiven 8 - 68-81 - 80ms`() {
        val length = 9
        val minSubGiven = 8
        var terminal: SudokuTerminal
        val elapsed: Duration = measureTime {
            terminal = SudokuTerminal.withUniqueSolution(length = length, minSubGiven = minSubGiven)
            println("#### SudokuTerminal.withUniqueSolution($length, $minSubGiven)=\n${terminal.toValueString()}${length * length - terminal.cells.filter { it.value == 0 }.size}/${length * length}")
        }
        println("SudokuTerminal withUniqueSolution - Measuring solve time: $elapsed")
        assertEquals(true, SudokuPuzzle(terminal).hasUniqueSolution())
    }
}

internal object SudokuTerminalTemplate {

    /*
    length 1x1 with 1 solutions:
    .

    Solution-1:
    1,
     */
    object Length_1_1 {
        val data = SudokuTerminal(
            length = 1,
            cells = arrayOf(
                SudokuCell(block = 0, value = 1),
            ),
        )
        const val json = """
    {
        "length": 1,
		"cells": [
			{"block": 0,"value": 1}
        ]
	}
"""
    }

    /*
    length 4x4 with 0 solutions:
    ....
    .4..
    2...
    ..43
     */
    object Length_4_0 {
        val data: SudokuTerminal
            get() = SudokuTerminal(
                length = 4,
                cells = arrayOf(
                    SudokuCell(block = 0, value = 0),
                    SudokuCell(block = 0, value = 0),
                    SudokuCell(block = 1, value = 0),
                    SudokuCell(block = 1, value = 0),

                    SudokuCell(block = 0, value = 0),
                    SudokuCell(block = 0, value = 4),
                    SudokuCell(block = 1, value = 0),
                    SudokuCell(block = 1, value = 0),

                    SudokuCell(block = 2, value = 2),
                    SudokuCell(block = 2, value = 0),
                    SudokuCell(block = 3, value = 0),
                    SudokuCell(block = 3, value = 0),

                    SudokuCell(block = 2, value = 0),
                    SudokuCell(block = 2, value = 0),
                    SudokuCell(block = 3, value = 4),
                    SudokuCell(block = 3, value = 3),
                ),
            )
        const val json = """
    {
        "length": 4,
		"cells": [
			{"block": 0,"value": 0},
			{"block": 0,"value": 0},
			{"block": 1,"value": 0},
			{"block": 1,"value": 0},

			{"block": 0,"value": 0},
			{"block": 0,"value": 4},
			{"block": 1,"value": 0},
			{"block": 1,"value": 0},

			{"block": 2,"value": 2},
			{"block": 2,"value": 0},
			{"block": 3,"value": 0},
			{"block": 3,"value": 0},

			{"block": 2,"value": 0},
			{"block": 2,"value": 0},
			{"block": 3,"value": 4},
			{"block": 3,"value": 3}
        ]
	}
"""
    }

    /*
    length 4x4 with 3 solutions:
    ....
    .4..
    2...
    ...3

    Solution-1:
    1,2,3,4,
    3,4,1,2,
    2,3,4,1,
    4,1,2,3,

    Solution-2:
    3,2,1,4,
    1,4,3,2,
    2,3,4,1,
    4,1,2,3,

    Solution-3:
    3,2,4,1,
    1,4,3,2,
    2,3,1,4,
    4,1,2,3,
     */
    object Length_4_3 {
        val data: SudokuTerminal
            get() = SudokuTerminal(
                length = 4,
                cells = arrayOf(
                    SudokuCell(block = 0, value = 0),
                    SudokuCell(block = 0, value = 0),
                    SudokuCell(block = 1, value = 0),
                    SudokuCell(block = 1, value = 0),

                    SudokuCell(block = 0, value = 0),
                    SudokuCell(block = 0, value = 4),
                    SudokuCell(block = 1, value = 0),
                    SudokuCell(block = 1, value = 0),

                    SudokuCell(block = 2, value = 2),
                    SudokuCell(block = 2, value = 0),
                    SudokuCell(block = 3, value = 0),
                    SudokuCell(block = 3, value = 0),

                    SudokuCell(block = 2, value = 0),
                    SudokuCell(block = 2, value = 0),
                    SudokuCell(block = 3, value = 0),
                    SudokuCell(block = 3, value = 3),
                ),
            )
        const val json = """
    {
        "length": 4,
		"cells": [
			{"block": 0,"value": 0},
			{"block": 0,"value": 0},
			{"block": 1,"value": 0},
			{"block": 1,"value": 0},

			{"block": 0,"value": 0},
			{"block": 0,"value": 4},
			{"block": 1,"value": 0},
			{"block": 1,"value": 0},

			{"block": 2,"value": 2},
			{"block": 2,"value": 0},
			{"block": 3,"value": 0},
			{"block": 3,"value": 0},

			{"block": 2,"value": 0},
			{"block": 2,"value": 0},
			{"block": 3,"value": 0},
			{"block": 3,"value": 3}
        ]
	}
"""
    }

    /*
    length 9x9 with 0 solutions:
    ......123
 	..9......
 	.....9...
 	.........
 	.........
 	.........
 	.........
 	.........
 	.........
     */
    object Length_9_0 {
        val data: SudokuTerminal
            get() = SudokuTerminal(
                length = 9,
                cells = arrayOf(
                    SudokuCell(block = 0, value = 0),
                    SudokuCell(block = 0, value = 0),
                    SudokuCell(block = 0, value = 0),
                    SudokuCell(block = 1, value = 0),
                    SudokuCell(block = 1, value = 0),
                    SudokuCell(block = 1, value = 0),
                    SudokuCell(block = 2, value = 1),
                    SudokuCell(block = 2, value = 2),
                    SudokuCell(block = 2, value = 3),

                    SudokuCell(block = 0, value = 0),
                    SudokuCell(block = 0, value = 0),
                    SudokuCell(block = 0, value = 9),
                    SudokuCell(block = 1, value = 0),
                    SudokuCell(block = 1, value = 0),
                    SudokuCell(block = 1, value = 0),
                    SudokuCell(block = 2, value = 0),
                    SudokuCell(block = 2, value = 0),
                    SudokuCell(block = 2, value = 0),

                    SudokuCell(block = 0, value = 0),
                    SudokuCell(block = 0, value = 0),
                    SudokuCell(block = 0, value = 0),
                    SudokuCell(block = 1, value = 0),
                    SudokuCell(block = 1, value = 0),
                    SudokuCell(block = 1, value = 9),
                    SudokuCell(block = 2, value = 0),
                    SudokuCell(block = 2, value = 0),
                    SudokuCell(block = 2, value = 0),

                    SudokuCell(block = 3, value = 0),
                    SudokuCell(block = 3, value = 0),
                    SudokuCell(block = 3, value = 0),
                    SudokuCell(block = 4, value = 0),
                    SudokuCell(block = 4, value = 0),
                    SudokuCell(block = 4, value = 0),
                    SudokuCell(block = 5, value = 0),
                    SudokuCell(block = 5, value = 0),
                    SudokuCell(block = 5, value = 0),

                    SudokuCell(block = 3, value = 0),
                    SudokuCell(block = 3, value = 0),
                    SudokuCell(block = 3, value = 0),
                    SudokuCell(block = 4, value = 0),
                    SudokuCell(block = 4, value = 0),
                    SudokuCell(block = 4, value = 0),
                    SudokuCell(block = 5, value = 0),
                    SudokuCell(block = 5, value = 0),
                    SudokuCell(block = 5, value = 0),

                    SudokuCell(block = 3, value = 0),
                    SudokuCell(block = 3, value = 0),
                    SudokuCell(block = 3, value = 0),
                    SudokuCell(block = 4, value = 0),
                    SudokuCell(block = 4, value = 0),
                    SudokuCell(block = 4, value = 0),
                    SudokuCell(block = 5, value = 0),
                    SudokuCell(block = 5, value = 0),
                    SudokuCell(block = 5, value = 0),

                    SudokuCell(block = 6, value = 0),
                    SudokuCell(block = 6, value = 0),
                    SudokuCell(block = 6, value = 0),
                    SudokuCell(block = 7, value = 0),
                    SudokuCell(block = 7, value = 0),
                    SudokuCell(block = 7, value = 0),
                    SudokuCell(block = 8, value = 0),
                    SudokuCell(block = 8, value = 0),
                    SudokuCell(block = 8, value = 0),

                    SudokuCell(block = 6, value = 0),
                    SudokuCell(block = 6, value = 0),
                    SudokuCell(block = 6, value = 0),
                    SudokuCell(block = 7, value = 0),
                    SudokuCell(block = 7, value = 0),
                    SudokuCell(block = 7, value = 0),
                    SudokuCell(block = 8, value = 0),
                    SudokuCell(block = 8, value = 0),
                    SudokuCell(block = 8, value = 0),

                    SudokuCell(block = 6, value = 0),
                    SudokuCell(block = 6, value = 0),
                    SudokuCell(block = 6, value = 0),
                    SudokuCell(block = 7, value = 0),
                    SudokuCell(block = 7, value = 0),
                    SudokuCell(block = 7, value = 0),
                    SudokuCell(block = 8, value = 0),
                    SudokuCell(block = 8, value = 0),
                    SudokuCell(block = 8, value = 0),
                ),
            )
        const val json = """
    {
        "length": 9,
		"cells": [
			{"block": 0,"value": 0},
			{"block": 0,"value": 0},
			{"block": 0,"value": 0},
			{"block": 1,"value": 0},
			{"block": 1,"value": 0},
			{"block": 1,"value": 0},
			{"block": 2,"value": 1},
			{"block": 2,"value": 2},
			{"block": 2,"value": 3},

			{"block": 0,"value": 0},
			{"block": 0,"value": 0},
			{"block": 0,"value": 9},
			{"block": 1,"value": 0},
			{"block": 1,"value": 0},
			{"block": 1,"value": 0},
			{"block": 2,"value": 0},
			{"block": 2,"value": 0},
			{"block": 2,"value": 0},

			{"block": 0,"value": 0},
			{"block": 0,"value": 0},
			{"block": 0,"value": 0},
			{"block": 1,"value": 0},
			{"block": 1,"value": 0},
			{"block": 1,"value": 9},
			{"block": 2,"value": 0},
			{"block": 2,"value": 0},
			{"block": 2,"value": 0},

			{"block": 3,"value": 0},
			{"block": 3,"value": 0},
			{"block": 3,"value": 0},
			{"block": 4,"value": 0},
			{"block": 4,"value": 0},
			{"block": 4,"value": 0},
			{"block": 5,"value": 0},
			{"block": 5,"value": 0},
			{"block": 5,"value": 0},

			{"block": 3,"value": 0},
			{"block": 3,"value": 0},
			{"block": 3,"value": 0},
			{"block": 4,"value": 0},
			{"block": 4,"value": 0},
			{"block": 4,"value": 0},
			{"block": 5,"value": 0},
			{"block": 5,"value": 0},
			{"block": 5,"value": 0},

			{"block": 3,"value": 0},
			{"block": 3,"value": 0},
			{"block": 3,"value": 0},
			{"block": 4,"value": 0},
			{"block": 4,"value": 0},
			{"block": 4,"value": 0},
			{"block": 5,"value": 0},
			{"block": 5,"value": 0},
			{"block": 5,"value": 0},

			{"block": 6,"value": 0},
			{"block": 6,"value": 0},
			{"block": 6,"value": 0},
			{"block": 7,"value": 0},
			{"block": 7,"value": 0},
			{"block": 7,"value": 0},
			{"block": 8,"value": 0},
			{"block": 8,"value": 0},
			{"block": 8,"value": 0},

			{"block": 6,"value": 0},
			{"block": 6,"value": 0},
			{"block": 6,"value": 0},
			{"block": 7,"value": 0},
			{"block": 7,"value": 0},
			{"block": 7,"value": 0},
			{"block": 8,"value": 0},
			{"block": 8,"value": 0},
			{"block": 8,"value": 0},

			{"block": 6,"value": 0},
			{"block": 6,"value": 0},
			{"block": 6,"value": 0},
			{"block": 7,"value": 0},
			{"block": 7,"value": 0},
			{"block": 7,"value": 0},
			{"block": 8,"value": 0},
			{"block": 8,"value": 0},
			{"block": 8,"value": 0}
        ]
	}
"""
    }

    /*
    length 9x9 with 1 solutions:
    .........
    ..41.26..
 	.3..5..2.
 	.2..1..3.
 	..65.41..
 	.8..7..4.
 	.7..2..6.
 	..14.35..
 	.........

 	Solution-1:
 	6,1,2,3,4,7,8,5,9,
    8,5,4,1,9,2,6,7,3,
    9,3,7,6,5,8,4,2,1,
    4,2,5,8,1,9,7,3,6,
    7,9,6,5,3,4,1,8,2,
    1,8,3,2,7,6,9,4,5,
    5,7,8,9,2,1,3,6,4,
    2,6,1,4,8,3,5,9,7,
    3,4,9,7,6,5,2,1,8,
     */
    object Length_9_1 {
        val data: SudokuTerminal
            get() = SudokuTerminal(
                length = 9,
                cells = arrayOf(
                    SudokuCell(block = 0, value = 0),
                    SudokuCell(block = 0, value = 0),
                    SudokuCell(block = 0, value = 0),
                    SudokuCell(block = 1, value = 0),
                    SudokuCell(block = 1, value = 0),
                    SudokuCell(block = 1, value = 0),
                    SudokuCell(block = 2, value = 0),
                    SudokuCell(block = 2, value = 0),
                    SudokuCell(block = 2, value = 0),

                    SudokuCell(block = 0, value = 0),
                    SudokuCell(block = 0, value = 0),
                    SudokuCell(block = 0, value = 4),
                    SudokuCell(block = 1, value = 1),
                    SudokuCell(block = 1, value = 0),
                    SudokuCell(block = 1, value = 2),
                    SudokuCell(block = 2, value = 6),
                    SudokuCell(block = 2, value = 0),
                    SudokuCell(block = 2, value = 0),

                    SudokuCell(block = 0, value = 0),
                    SudokuCell(block = 0, value = 3),
                    SudokuCell(block = 0, value = 0),
                    SudokuCell(block = 1, value = 0),
                    SudokuCell(block = 1, value = 5),
                    SudokuCell(block = 1, value = 0),
                    SudokuCell(block = 2, value = 0),
                    SudokuCell(block = 2, value = 2),
                    SudokuCell(block = 2, value = 0),

                    SudokuCell(block = 3, value = 0),
                    SudokuCell(block = 3, value = 2),
                    SudokuCell(block = 3, value = 0),
                    SudokuCell(block = 4, value = 0),
                    SudokuCell(block = 4, value = 1),
                    SudokuCell(block = 4, value = 0),
                    SudokuCell(block = 5, value = 0),
                    SudokuCell(block = 5, value = 3),
                    SudokuCell(block = 5, value = 0),

                    SudokuCell(block = 3, value = 0),
                    SudokuCell(block = 3, value = 0),
                    SudokuCell(block = 3, value = 6),
                    SudokuCell(block = 4, value = 5),
                    SudokuCell(block = 4, value = 0),
                    SudokuCell(block = 4, value = 4),
                    SudokuCell(block = 5, value = 1),
                    SudokuCell(block = 5, value = 0),
                    SudokuCell(block = 5, value = 0),

                    SudokuCell(block = 3, value = 0),
                    SudokuCell(block = 3, value = 8),
                    SudokuCell(block = 3, value = 0),
                    SudokuCell(block = 4, value = 0),
                    SudokuCell(block = 4, value = 7),
                    SudokuCell(block = 4, value = 0),
                    SudokuCell(block = 5, value = 0),
                    SudokuCell(block = 5, value = 4),
                    SudokuCell(block = 5, value = 0),

                    SudokuCell(block = 6, value = 0),
                    SudokuCell(block = 6, value = 7),
                    SudokuCell(block = 6, value = 0),
                    SudokuCell(block = 7, value = 0),
                    SudokuCell(block = 7, value = 2),
                    SudokuCell(block = 7, value = 0),
                    SudokuCell(block = 8, value = 0),
                    SudokuCell(block = 8, value = 6),
                    SudokuCell(block = 8, value = 0),

                    SudokuCell(block = 6, value = 0),
                    SudokuCell(block = 6, value = 0),
                    SudokuCell(block = 6, value = 1),
                    SudokuCell(block = 7, value = 4),
                    SudokuCell(block = 7, value = 0),
                    SudokuCell(block = 7, value = 3),
                    SudokuCell(block = 8, value = 5),
                    SudokuCell(block = 8, value = 0),
                    SudokuCell(block = 8, value = 0),

                    SudokuCell(block = 6, value = 0),
                    SudokuCell(block = 6, value = 0),
                    SudokuCell(block = 6, value = 0),
                    SudokuCell(block = 7, value = 0),
                    SudokuCell(block = 7, value = 0),
                    SudokuCell(block = 7, value = 0),
                    SudokuCell(block = 8, value = 0),
                    SudokuCell(block = 8, value = 0),
                    SudokuCell(block = 8, value = 0),
                ),
            )
        const val json = """
    {
        "length": 9,
		"cells": [
			{"block": 0,"value": 0},
			{"block": 0,"value": 0},
			{"block": 0,"value": 0},
			{"block": 1,"value": 0},
			{"block": 1,"value": 0},
			{"block": 1,"value": 0},
			{"block": 2,"value": 0},
			{"block": 2,"value": 0},
			{"block": 2,"value": 0},

			{"block": 0,"value": 0},
			{"block": 0,"value": 0},
			{"block": 0,"value": 4},
			{"block": 1,"value": 1},
			{"block": 1,"value": 0},
			{"block": 1,"value": 2},
			{"block": 2,"value": 6},
			{"block": 2,"value": 0},
			{"block": 2,"value": 0},

			{"block": 0,"value": 0},
			{"block": 0,"value": 3},
			{"block": 0,"value": 0},
			{"block": 1,"value": 0},
			{"block": 1,"value": 5},
			{"block": 1,"value": 0},
			{"block": 2,"value": 0},
			{"block": 2,"value": 2},
			{"block": 2,"value": 0},

			{"block": 3,"value": 0},
			{"block": 3,"value": 2},
			{"block": 3,"value": 0},
			{"block": 4,"value": 0},
			{"block": 4,"value": 1},
			{"block": 4,"value": 0},
			{"block": 5,"value": 0},
			{"block": 5,"value": 3},
			{"block": 5,"value": 0},

			{"block": 3,"value": 0},
			{"block": 3,"value": 0},
			{"block": 3,"value": 6},
			{"block": 4,"value": 5},
			{"block": 4,"value": 0},
			{"block": 4,"value": 4},
			{"block": 5,"value": 1},
			{"block": 5,"value": 0},
			{"block": 5,"value": 0},

			{"block": 3,"value": 0},
			{"block": 3,"value": 8},
			{"block": 3,"value": 0},
			{"block": 4,"value": 0},
			{"block": 4,"value": 7},
			{"block": 4,"value": 0},
			{"block": 5,"value": 0},
			{"block": 5,"value": 4},
			{"block": 5,"value": 0},

			{"block": 6,"value": 0},
			{"block": 6,"value": 7},
			{"block": 6,"value": 0},
			{"block": 7,"value": 0},
			{"block": 7,"value": 2},
			{"block": 7,"value": 0},
			{"block": 8,"value": 0},
			{"block": 8,"value": 6},
			{"block": 8,"value": 0},

			{"block": 6,"value": 0},
			{"block": 6,"value": 0},
			{"block": 6,"value": 1},
			{"block": 7,"value": 4},
			{"block": 7,"value": 0},
			{"block": 7,"value": 3},
			{"block": 8,"value": 5},
			{"block": 8,"value": 0},
			{"block": 8,"value": 0},

			{"block": 6,"value": 0},
			{"block": 6,"value": 0},
			{"block": 6,"value": 0},
			{"block": 7,"value": 0},
			{"block": 7,"value": 0},
			{"block": 7,"value": 0},
			{"block": 8,"value": 0},
			{"block": 8,"value": 0},
			{"block": 8,"value": 0}
        ]
	}
"""
    }

    /*
    length 9x9 with 2 solutions:
    ..3456789
 	456789123
 	789123456
 	..4365897
 	365897214
 	897214365
 	531642978
 	642978531
 	978531642

 	Solution-1:
    1,2,3,4,5,6,7,8,9,
    4,5,6,7,8,9,1,2,3,
    7,8,9,1,2,3,4,5,6,
    2,1,4,3,6,5,8,9,7,
    3,6,5,8,9,7,2,1,4,
    8,9,7,2,1,4,3,6,5,
    5,3,1,6,4,2,9,7,8,
    6,4,2,9,7,8,5,3,1,
    9,7,8,5,3,1,6,4,2,

    Solution-2:
    2,1,3,4,5,6,7,8,9,
    4,5,6,7,8,9,1,2,3,
    7,8,9,1,2,3,4,5,6,
    1,2,4,3,6,5,8,9,7,
    3,6,5,8,9,7,2,1,4,
    8,9,7,2,1,4,3,6,5,
    5,3,1,6,4,2,9,7,8,
    6,4,2,9,7,8,5,3,1,
    9,7,8,5,3,1,6,4,2,
     */
    object Length_9_2 {
        val data: SudokuTerminal
            get() = SudokuTerminal(
                length = 9,
                cells = arrayOf(
                    SudokuCell(block = 0, value = 0),
                    SudokuCell(block = 0, value = 0),
                    SudokuCell(block = 0, value = 3),
                    SudokuCell(block = 1, value = 4),
                    SudokuCell(block = 1, value = 5),
                    SudokuCell(block = 1, value = 6),
                    SudokuCell(block = 2, value = 7),
                    SudokuCell(block = 2, value = 8),
                    SudokuCell(block = 2, value = 9),

                    SudokuCell(block = 0, value = 4),
                    SudokuCell(block = 0, value = 5),
                    SudokuCell(block = 0, value = 6),
                    SudokuCell(block = 1, value = 7),
                    SudokuCell(block = 1, value = 8),
                    SudokuCell(block = 1, value = 9),
                    SudokuCell(block = 2, value = 1),
                    SudokuCell(block = 2, value = 2),
                    SudokuCell(block = 2, value = 3),

                    SudokuCell(block = 0, value = 7),
                    SudokuCell(block = 0, value = 8),
                    SudokuCell(block = 0, value = 9),
                    SudokuCell(block = 1, value = 1),
                    SudokuCell(block = 1, value = 2),
                    SudokuCell(block = 1, value = 3),
                    SudokuCell(block = 2, value = 4),
                    SudokuCell(block = 2, value = 5),
                    SudokuCell(block = 2, value = 6),

                    SudokuCell(block = 3, value = 0),
                    SudokuCell(block = 3, value = 0),
                    SudokuCell(block = 3, value = 4),
                    SudokuCell(block = 4, value = 3),
                    SudokuCell(block = 4, value = 6),
                    SudokuCell(block = 4, value = 5),
                    SudokuCell(block = 5, value = 8),
                    SudokuCell(block = 5, value = 9),
                    SudokuCell(block = 5, value = 7),

                    SudokuCell(block = 3, value = 3),
                    SudokuCell(block = 3, value = 6),
                    SudokuCell(block = 3, value = 5),
                    SudokuCell(block = 4, value = 8),
                    SudokuCell(block = 4, value = 9),
                    SudokuCell(block = 4, value = 7),
                    SudokuCell(block = 5, value = 2),
                    SudokuCell(block = 5, value = 1),
                    SudokuCell(block = 5, value = 4),

                    SudokuCell(block = 3, value = 8),
                    SudokuCell(block = 3, value = 9),
                    SudokuCell(block = 3, value = 7),
                    SudokuCell(block = 4, value = 2),
                    SudokuCell(block = 4, value = 1),
                    SudokuCell(block = 4, value = 4),
                    SudokuCell(block = 5, value = 3),
                    SudokuCell(block = 5, value = 6),
                    SudokuCell(block = 5, value = 5),

                    SudokuCell(block = 6, value = 5),
                    SudokuCell(block = 6, value = 3),
                    SudokuCell(block = 6, value = 1),
                    SudokuCell(block = 7, value = 6),
                    SudokuCell(block = 7, value = 4),
                    SudokuCell(block = 7, value = 2),
                    SudokuCell(block = 8, value = 9),
                    SudokuCell(block = 8, value = 7),
                    SudokuCell(block = 8, value = 8),

                    SudokuCell(block = 6, value = 6),
                    SudokuCell(block = 6, value = 4),
                    SudokuCell(block = 6, value = 2),
                    SudokuCell(block = 7, value = 9),
                    SudokuCell(block = 7, value = 7),
                    SudokuCell(block = 7, value = 8),
                    SudokuCell(block = 8, value = 5),
                    SudokuCell(block = 8, value = 3),
                    SudokuCell(block = 8, value = 1),

                    SudokuCell(block = 6, value = 9),
                    SudokuCell(block = 6, value = 7),
                    SudokuCell(block = 6, value = 8),
                    SudokuCell(block = 7, value = 5),
                    SudokuCell(block = 7, value = 3),
                    SudokuCell(block = 7, value = 1),
                    SudokuCell(block = 8, value = 6),
                    SudokuCell(block = 8, value = 4),
                    SudokuCell(block = 8, value = 2),
                ),
            )
        const val json = """
    {
        "length": 9,
		"cells": [
			{"block": 0,"value": 0},
			{"block": 0,"value": 0},
			{"block": 0,"value": 3},
			{"block": 1,"value": 4},
			{"block": 1,"value": 5},
			{"block": 1,"value": 6},
			{"block": 2,"value": 7},
			{"block": 2,"value": 8},
			{"block": 2,"value": 9},

			{"block": 0,"value": 4},
			{"block": 0,"value": 5},
			{"block": 0,"value": 6},
			{"block": 1,"value": 7},
			{"block": 1,"value": 8},
			{"block": 1,"value": 9},
			{"block": 2,"value": 1},
			{"block": 2,"value": 2},
			{"block": 2,"value": 3},

			{"block": 0,"value": 7},
			{"block": 0,"value": 8},
			{"block": 0,"value": 9},
			{"block": 1,"value": 1},
			{"block": 1,"value": 2},
			{"block": 1,"value": 3},
			{"block": 2,"value": 4},
			{"block": 2,"value": 5},
			{"block": 2,"value": 6},

			{"block": 3,"value": 0},
			{"block": 3,"value": 0},
			{"block": 3,"value": 4},
			{"block": 4,"value": 3},
			{"block": 4,"value": 6},
			{"block": 4,"value": 5},
			{"block": 5,"value": 8},
			{"block": 5,"value": 9},
			{"block": 5,"value": 7},

			{"block": 3,"value": 3},
			{"block": 3,"value": 6},
			{"block": 3,"value": 5},
			{"block": 4,"value": 8},
			{"block": 4,"value": 9},
			{"block": 4,"value": 7},
			{"block": 5,"value": 2},
			{"block": 5,"value": 1},
			{"block": 5,"value": 4},

			{"block": 3,"value": 8},
			{"block": 3,"value": 9},
			{"block": 3,"value": 7},
			{"block": 4,"value": 2},
			{"block": 4,"value": 1},
			{"block": 4,"value": 4},
			{"block": 5,"value": 3},
			{"block": 5,"value": 6},
			{"block": 5,"value": 5},

			{"block": 6,"value": 5},
			{"block": 6,"value": 3},
			{"block": 6,"value": 1},
			{"block": 7,"value": 6},
			{"block": 7,"value": 4},
			{"block": 7,"value": 2},
			{"block": 8,"value": 9},
			{"block": 8,"value": 7},
			{"block": 8,"value": 8},

			{"block": 6,"value": 6},
			{"block": 6,"value": 4},
			{"block": 6,"value": 2},
			{"block": 7,"value": 9},
			{"block": 7,"value": 7},
			{"block": 7,"value": 8},
			{"block": 8,"value": 5},
			{"block": 8,"value": 3},
			{"block": 8,"value": 1},

			{"block": 6,"value": 9},
			{"block": 6,"value": 7},
			{"block": 6,"value": 8},
			{"block": 7,"value": 5},
			{"block": 7,"value": 3},
			{"block": 7,"value": 1},
			{"block": 8,"value": 6},
			{"block": 8,"value": 4},
			{"block": 8,"value": 2}
        ]
	}
"""
    }

    /*
    length 9x9 with 188 solutions:
    ....7.94.
 	".7..9...5
 	"3....5.7.
 	"..74..1..
 	"463.8....
 	".....7.8.
 	"8..7.....
 	"7......28
 	".5..68...
     */
    object Length_9_188 {
        val data: SudokuTerminal
            get() = SudokuTerminal(
                length = 9,
                cells = arrayOf(
                    SudokuCell(block = 0, value = 0),
                    SudokuCell(block = 0, value = 0),
                    SudokuCell(block = 0, value = 0),
                    SudokuCell(block = 1, value = 0),
                    SudokuCell(block = 1, value = 7),
                    SudokuCell(block = 1, value = 0),
                    SudokuCell(block = 2, value = 9),
                    SudokuCell(block = 2, value = 4),
                    SudokuCell(block = 2, value = 0),

                    SudokuCell(block = 0, value = 0),
                    SudokuCell(block = 0, value = 7),
                    SudokuCell(block = 0, value = 0),
                    SudokuCell(block = 1, value = 0),
                    SudokuCell(block = 1, value = 9),
                    SudokuCell(block = 1, value = 0),
                    SudokuCell(block = 2, value = 0),
                    SudokuCell(block = 2, value = 0),
                    SudokuCell(block = 2, value = 5),

                    SudokuCell(block = 0, value = 3),
                    SudokuCell(block = 0, value = 0),
                    SudokuCell(block = 0, value = 0),
                    SudokuCell(block = 1, value = 0),
                    SudokuCell(block = 1, value = 0),
                    SudokuCell(block = 1, value = 5),
                    SudokuCell(block = 2, value = 0),
                    SudokuCell(block = 2, value = 7),
                    SudokuCell(block = 2, value = 0),

                    SudokuCell(block = 3, value = 0),
                    SudokuCell(block = 3, value = 0),
                    SudokuCell(block = 3, value = 7),
                    SudokuCell(block = 4, value = 4),
                    SudokuCell(block = 4, value = 0),
                    SudokuCell(block = 4, value = 0),
                    SudokuCell(block = 5, value = 1),
                    SudokuCell(block = 5, value = 0),
                    SudokuCell(block = 5, value = 0),

                    SudokuCell(block = 3, value = 4),
                    SudokuCell(block = 3, value = 6),
                    SudokuCell(block = 3, value = 3),
                    SudokuCell(block = 4, value = 0),
                    SudokuCell(block = 4, value = 8),
                    SudokuCell(block = 4, value = 0),
                    SudokuCell(block = 5, value = 0),
                    SudokuCell(block = 5, value = 0),
                    SudokuCell(block = 5, value = 0),

                    SudokuCell(block = 3, value = 0),
                    SudokuCell(block = 3, value = 0),
                    SudokuCell(block = 3, value = 0),
                    SudokuCell(block = 4, value = 0),
                    SudokuCell(block = 4, value = 0),
                    SudokuCell(block = 4, value = 7),
                    SudokuCell(block = 5, value = 0),
                    SudokuCell(block = 5, value = 8),
                    SudokuCell(block = 5, value = 0),

                    SudokuCell(block = 6, value = 8),
                    SudokuCell(block = 6, value = 0),
                    SudokuCell(block = 6, value = 0),
                    SudokuCell(block = 7, value = 7),
                    SudokuCell(block = 7, value = 0),
                    SudokuCell(block = 7, value = 0),
                    SudokuCell(block = 8, value = 0),
                    SudokuCell(block = 8, value = 0),
                    SudokuCell(block = 8, value = 0),

                    SudokuCell(block = 6, value = 7),
                    SudokuCell(block = 6, value = 0),
                    SudokuCell(block = 6, value = 0),
                    SudokuCell(block = 7, value = 0),
                    SudokuCell(block = 7, value = 0),
                    SudokuCell(block = 7, value = 0),
                    SudokuCell(block = 8, value = 0),
                    SudokuCell(block = 8, value = 2),
                    SudokuCell(block = 8, value = 8),

                    SudokuCell(block = 6, value = 0),
                    SudokuCell(block = 6, value = 5),
                    SudokuCell(block = 6, value = 0),
                    SudokuCell(block = 7, value = 0),
                    SudokuCell(block = 7, value = 6),
                    SudokuCell(block = 7, value = 8),
                    SudokuCell(block = 8, value = 0),
                    SudokuCell(block = 8, value = 0),
                    SudokuCell(block = 8, value = 0),
                ),
            )
        const val json = """
    {
        "length": 9,
		"cells": [
			{"block": 0,"value": 0},
			{"block": 0,"value": 0},
			{"block": 0,"value": 0},
			{"block": 1,"value": 0},
			{"block": 1,"value": 7},
			{"block": 1,"value": 0},
			{"block": 2,"value": 9},
			{"block": 2,"value": 4},
			{"block": 2,"value": 0},

			{"block": 0,"value": 0},
			{"block": 0,"value": 7},
			{"block": 0,"value": 0},
			{"block": 1,"value": 0},
			{"block": 1,"value": 9},
			{"block": 1,"value": 0},
			{"block": 2,"value": 0},
			{"block": 2,"value": 0},
			{"block": 2,"value": 5},

			{"block": 0,"value": 3},
			{"block": 0,"value": 0},
			{"block": 0,"value": 0},
			{"block": 1,"value": 0},
			{"block": 1,"value": 0},
			{"block": 1,"value": 5},
			{"block": 2,"value": 0},
			{"block": 2,"value": 7},
			{"block": 2,"value": 0},

			{"block": 3,"value": 0},
			{"block": 3,"value": 0},
			{"block": 3,"value": 7},
			{"block": 4,"value": 4},
			{"block": 4,"value": 0},
			{"block": 4,"value": 0},
			{"block": 5,"value": 1},
			{"block": 5,"value": 0},
			{"block": 5,"value": 0},

			{"block": 3,"value": 4},
			{"block": 3,"value": 6},
			{"block": 3,"value": 3},
			{"block": 4,"value": 0},
			{"block": 4,"value": 8},
			{"block": 4,"value": 0},
			{"block": 5,"value": 0},
			{"block": 5,"value": 0},
			{"block": 5,"value": 0},

			{"block": 3,"value": 0},
			{"block": 3,"value": 0},
			{"block": 3,"value": 0},
			{"block": 4,"value": 0},
			{"block": 4,"value": 0},
			{"block": 4,"value": 7},
			{"block": 5,"value": 0},
			{"block": 5,"value": 8},
			{"block": 5,"value": 0},

			{"block": 6,"value": 8},
			{"block": 6,"value": 0},
			{"block": 6,"value": 0},
			{"block": 7,"value": 7},
			{"block": 7,"value": 0},
			{"block": 7,"value": 0},
			{"block": 8,"value": 0},
			{"block": 8,"value": 0},
			{"block": 8,"value": 0},

			{"block": 6,"value": 7},
			{"block": 6,"value": 0},
			{"block": 6,"value": 0},
			{"block": 7,"value": 0},
			{"block": 7,"value": 0},
			{"block": 7,"value": 0},
			{"block": 8,"value": 0},
			{"block": 8,"value": 2},
			{"block": 8,"value": 8},

			{"block": 6,"value": 0},
			{"block": 6,"value": 5},
			{"block": 6,"value": 0},
			{"block": 7,"value": 0},
			{"block": 7,"value": 6},
			{"block": 7,"value": 8},
			{"block": 8,"value": 0},
			{"block": 8,"value": 0},
			{"block": 8,"value": 0}
        ]
	}
"""
    }
}

