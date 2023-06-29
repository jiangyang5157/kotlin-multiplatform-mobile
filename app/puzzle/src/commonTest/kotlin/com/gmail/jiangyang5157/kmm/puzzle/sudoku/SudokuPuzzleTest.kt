package com.gmail.jiangyang5157.kmm.puzzle.sudoku

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import kotlin.test.Test
import kotlin.test.assertEquals

class SudokuPuzzleTest {

    @Test
    fun `SudokuPuzzle Length_1_1 from json`() {
        val data = Json.decodeFromString<SudokuPuzzle>(SudokuTemplate.Length_1_1.json)
        assertEquals(data, SudokuTemplate.Length_1_1.data)
    }

    @Test
    fun `SudokuPuzzle Length_4_3 from json`() {
        val data = Json.decodeFromString<SudokuPuzzle>(SudokuTemplate.Length_4_3.json)
        assertEquals(data, SudokuTemplate.Length_4_3.data)
    }

    @Test
    fun `SudokuPuzzle Length_9_0 from json`() {
        val data = Json.decodeFromString<SudokuPuzzle>(SudokuTemplate.Length_9_0.json)
        assertEquals(data, SudokuTemplate.Length_9_0.data)
    }

    @Test
    fun `SudokuPuzzle Length_9_1 from json`() {
        val data = Json.decodeFromString<SudokuPuzzle>(SudokuTemplate.Length_9_1.json)
        assertEquals(data, SudokuTemplate.Length_9_1.data)
    }

    @Test
    fun `SudokuPuzzle Length_9_2 from json`() {
        val data = Json.decodeFromString<SudokuPuzzle>(SudokuTemplate.Length_9_2.json)
        assertEquals(data, SudokuTemplate.Length_9_2.data)
    }

    @Test
    fun `SudokuPuzzle Length_9_188 from json`() {
        val data = Json.decodeFromString<SudokuPuzzle>(SudokuTemplate.Length_9_188.json)
        assertEquals(data, SudokuTemplate.Length_9_188.data)
    }
}

internal object SudokuTemplate {

    /*
    length 1x1 with 1 solutions:
    .
     */
    object Length_1_1 {
        val data = SudokuPuzzle(
            length = 1,
            cells = arrayOf(
                SudokuCell(block = 0, value = 1),
            )
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
        val data = SudokuPuzzle(
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
            )
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
        val data = SudokuPuzzle(
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
            )
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
        val data = SudokuPuzzle(
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
            )
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
        val data = SudokuPuzzle(
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
            )
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
        val data = SudokuPuzzle(
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
            )
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
        val data = SudokuPuzzle(
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
            )
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

