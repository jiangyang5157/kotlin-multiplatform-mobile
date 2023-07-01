package com.gmail.jiangyang5157.shared.puzzle.sudoku

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

internal fun SudokuCell.toValueString(): String {
    return "$value"
}

@Serializable
data class SudokuCell(
    @SerialName("block") var block: Int = -1, // [0, )
    @SerialName("value") var value: Int = 0, // 0: none
) {

    override fun toString(): String {
        return "$value[$block]"
    }
}