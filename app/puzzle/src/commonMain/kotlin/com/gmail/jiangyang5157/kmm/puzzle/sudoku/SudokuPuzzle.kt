package com.gmail.jiangyang5157.kmm.puzzle.sudoku

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SudokuPuzzle(
    @SerialName("length")
    val length: Int, // edge length indicating the puzzle terminal size: length x length
    @SerialName("cells")
    val cells: List<SudokuCell>, // left-to-right and up-to-down
)

@Serializable
data class SudokuCell(
    @SerialName("block")
    val block: Int,
    @SerialName("value")
    val value: Int,
)

