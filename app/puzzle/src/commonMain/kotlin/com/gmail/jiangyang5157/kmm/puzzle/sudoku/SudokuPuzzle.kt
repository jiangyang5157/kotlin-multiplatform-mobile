package com.gmail.jiangyang5157.kmm.puzzle.sudoku

import com.gmail.jiangyang5157.kmm.puzzle.dlx.Dlx
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SudokuPuzzle(
    @SerialName("length")
    val length: Int, // edge length indicating the puzzle terminal size: length x length
    @SerialName("cells")
    val cells: Array<SudokuCell>, // left-to-right and up-to-down
) {
    companion object {

//        operator fun invoke(length: Int): SudokuPuzzle {
//            return SudokuPuzzle(
//                length = length,
//                cells = em
//            )
//        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false
        other as SudokuPuzzle
        if (length != other.length) return false
        if (!cells.contentEquals(other.cells)) return false
        return true
    }

    override fun hashCode(): Int {
        var result = length
        result = 31 * result + cells.contentHashCode()
        return result
    }
}

@Serializable
data class SudokuCell(
    @SerialName("block")
    val block: Int,
    @SerialName("value")
    val value: Int,
)

