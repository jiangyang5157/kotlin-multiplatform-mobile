package com.gmail.jiangyang5157.kmm.puzzle.sudoku

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import kotlin.test.Test
import kotlin.test.assertEquals

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
    fun `SudokuTerminal test row`() {
        assertEquals(0, SudokuTerminalTemplate.Length_9_0.data.row(0))
        assertEquals(0, SudokuTerminalTemplate.Length_9_0.data.row(8))
        assertEquals(1, SudokuTerminalTemplate.Length_9_0.data.row(9))
        assertEquals(7, SudokuTerminalTemplate.Length_9_0.data.row(71))
        assertEquals(8, SudokuTerminalTemplate.Length_9_0.data.row(72))
        assertEquals(8, SudokuTerminalTemplate.Length_9_0.data.row(80))
    }

    @Test
    fun `SudokuTerminal test column`() {
        assertEquals(0, SudokuTerminalTemplate.Length_9_0.data.column(0))
        assertEquals(8, SudokuTerminalTemplate.Length_9_0.data.column(8))
        assertEquals(0, SudokuTerminalTemplate.Length_9_0.data.column(9))
        assertEquals(8, SudokuTerminalTemplate.Length_9_0.data.column(71))
        assertEquals(0, SudokuTerminalTemplate.Length_9_0.data.column(72))
        assertEquals(8, SudokuTerminalTemplate.Length_9_0.data.column(80))
    }

    @Test
    fun `SudokuTerminal test up`() {
        assertEquals(-1, SudokuTerminalTemplate.Length_9_0.data.up(0))
        assertEquals(-1, SudokuTerminalTemplate.Length_9_0.data.up(1))
        assertEquals(-1, SudokuTerminalTemplate.Length_9_0.data.up(2))
        assertEquals(-1, SudokuTerminalTemplate.Length_9_0.data.up(3))
        assertEquals(-1, SudokuTerminalTemplate.Length_9_0.data.up(4))
        assertEquals(-1, SudokuTerminalTemplate.Length_9_0.data.up(5))
        assertEquals(-1, SudokuTerminalTemplate.Length_9_0.data.up(6))
        assertEquals(-1, SudokuTerminalTemplate.Length_9_0.data.up(7))
        assertEquals(-1, SudokuTerminalTemplate.Length_9_0.data.up(8))

        assertEquals(0, SudokuTerminalTemplate.Length_9_0.data.up(9))
        assertEquals(8, SudokuTerminalTemplate.Length_9_0.data.up(17))

        assertEquals(63, SudokuTerminalTemplate.Length_9_0.data.up(72))
        assertEquals(71, SudokuTerminalTemplate.Length_9_0.data.up(80))
    }

    @Test
    fun `SudokuTerminal test down`() {
        assertEquals(9, SudokuTerminalTemplate.Length_9_0.data.down(0))
        assertEquals(17, SudokuTerminalTemplate.Length_9_0.data.down(8))

        assertEquals(79, SudokuTerminalTemplate.Length_9_0.data.down(70))
        assertEquals(80, SudokuTerminalTemplate.Length_9_0.data.down(71))

        assertEquals(-1, SudokuTerminalTemplate.Length_9_0.data.down(72))
        assertEquals(-1, SudokuTerminalTemplate.Length_9_0.data.down(73))
        assertEquals(-1, SudokuTerminalTemplate.Length_9_0.data.down(74))
        assertEquals(-1, SudokuTerminalTemplate.Length_9_0.data.down(75))
        assertEquals(-1, SudokuTerminalTemplate.Length_9_0.data.down(76))
        assertEquals(-1, SudokuTerminalTemplate.Length_9_0.data.down(77))
        assertEquals(-1, SudokuTerminalTemplate.Length_9_0.data.down(78))
        assertEquals(-1, SudokuTerminalTemplate.Length_9_0.data.down(79))
        assertEquals(-1, SudokuTerminalTemplate.Length_9_0.data.down(80))
    }

    @Test
    fun `SudokuTerminal test left`() {
        assertEquals(-1, SudokuTerminalTemplate.Length_9_0.data.left(0))
        assertEquals(-1, SudokuTerminalTemplate.Length_9_0.data.left(9))
        assertEquals(-1, SudokuTerminalTemplate.Length_9_0.data.left(18))
        assertEquals(-1, SudokuTerminalTemplate.Length_9_0.data.left(27))
        assertEquals(-1, SudokuTerminalTemplate.Length_9_0.data.left(36))
        assertEquals(-1, SudokuTerminalTemplate.Length_9_0.data.left(45))
        assertEquals(-1, SudokuTerminalTemplate.Length_9_0.data.left(54))
        assertEquals(-1, SudokuTerminalTemplate.Length_9_0.data.left(63))
        assertEquals(-1, SudokuTerminalTemplate.Length_9_0.data.left(72))

        assertEquals(0, SudokuTerminalTemplate.Length_9_0.data.left(1))
        assertEquals(9, SudokuTerminalTemplate.Length_9_0.data.left(10))
        assertEquals(18, SudokuTerminalTemplate.Length_9_0.data.left(19))
        assertEquals(27, SudokuTerminalTemplate.Length_9_0.data.left(28))
        assertEquals(36, SudokuTerminalTemplate.Length_9_0.data.left(37))
        assertEquals(45, SudokuTerminalTemplate.Length_9_0.data.left(46))
        assertEquals(54, SudokuTerminalTemplate.Length_9_0.data.left(55))
        assertEquals(63, SudokuTerminalTemplate.Length_9_0.data.left(64))
        assertEquals(72, SudokuTerminalTemplate.Length_9_0.data.left(73))
    }

    @Test
    fun `SudokuTerminal test right`() {
        assertEquals(8, SudokuTerminalTemplate.Length_9_0.data.right(7))
        assertEquals(17, SudokuTerminalTemplate.Length_9_0.data.right(16))
        assertEquals(26, SudokuTerminalTemplate.Length_9_0.data.right(25))
        assertEquals(35, SudokuTerminalTemplate.Length_9_0.data.right(34))
        assertEquals(44, SudokuTerminalTemplate.Length_9_0.data.right(43))
        assertEquals(53, SudokuTerminalTemplate.Length_9_0.data.right(52))
        assertEquals(62, SudokuTerminalTemplate.Length_9_0.data.right(61))
        assertEquals(71, SudokuTerminalTemplate.Length_9_0.data.right(70))
        assertEquals(80, SudokuTerminalTemplate.Length_9_0.data.right(79))

        assertEquals(-1, SudokuTerminalTemplate.Length_9_0.data.right(8))
        assertEquals(-1, SudokuTerminalTemplate.Length_9_0.data.right(17))
        assertEquals(-1, SudokuTerminalTemplate.Length_9_0.data.right(26))
        assertEquals(-1, SudokuTerminalTemplate.Length_9_0.data.right(35))
        assertEquals(-1, SudokuTerminalTemplate.Length_9_0.data.right(44))
        assertEquals(-1, SudokuTerminalTemplate.Length_9_0.data.right(53))
        assertEquals(-1, SudokuTerminalTemplate.Length_9_0.data.right(62))
        assertEquals(-1, SudokuTerminalTemplate.Length_9_0.data.right(71))
        assertEquals(-1, SudokuTerminalTemplate.Length_9_0.data.right(80))
    }

    @Test
    fun `SudokuTerminal test neighbours`() {
        assertEquals(listOf(9, 1), SudokuTerminalTemplate.Length_9_0.data.neighbours(0))
        assertEquals(listOf(10, 0, 2), SudokuTerminalTemplate.Length_9_0.data.neighbours(1))
        assertEquals(listOf(0, 18, 10), SudokuTerminalTemplate.Length_9_0.data.neighbours(9))

        assertEquals(listOf(17, 7), SudokuTerminalTemplate.Length_9_0.data.neighbours(8))

        assertEquals(listOf(31, 49, 39, 41), SudokuTerminalTemplate.Length_9_0.data.neighbours(40))

        assertEquals(listOf(63, 73), SudokuTerminalTemplate.Length_9_0.data.neighbours(72))

        assertEquals(listOf(62, 80, 70), SudokuTerminalTemplate.Length_9_0.data.neighbours(71))
        assertEquals(listOf(70, 78, 80), SudokuTerminalTemplate.Length_9_0.data.neighbours(79))
        assertEquals(listOf(71, 79), SudokuTerminalTemplate.Length_9_0.data.neighbours(80))
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
}

internal object SudokuTerminalTemplate {

    /*
    length 1x1 with 1 solutions:
    .
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
        val data = SudokuTerminal(
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
     */
    object Length_4_3 {
        val data = SudokuTerminal(
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
        val data = SudokuTerminal(
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
     */
    object Length_9_1 {
        val data = SudokuTerminal(
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
     */
    object Length_9_2 {
        val data = SudokuTerminal(
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
        val data = SudokuTerminal(
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

