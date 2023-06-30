package com.gmail.jiangyang5157.kmm.puzzle.sudoku

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SudokuTerminal(
    @SerialName("length") val length: Int, // [1, ), edge length indicating the terminal size: length x length
    @SerialName("cells") val cells: Array<SudokuCell>, // left-to-right and up-to-down, size = length * length
) {

    fun index(row: Int, column: Int): Int = row * length + column
    fun cell(row: Int, column: Int): SudokuCell = cells[index(row, column)]

    fun row(index: Int): Int = index / length

    fun column(index: Int): Int = index % length

    fun up(index: Int): Int = (index - length).let {
        if (it < 0) -1 else it
    }

    fun down(index: Int): Int = (index + length).let {
        if (it > cells.size - 1) -1 else it
    }

    fun left(index: Int): Int = (index - 1).let {
        if (it < 0 || row(it) != row(index)) -1 else it
    }

    fun right(index: Int): Int = (index + 1).let {
        if (it > cells.size - 1 || row(it) != row(index)) -1 else it
    }

    fun neighbours(index: Int): List<Int> = listOfNotNull(
        up(index).let { if (it == -1) null else it },
        down(index).let { if (it == -1) null else it },
        left(index).let { if (it == -1) null else it },
        right(index).let { if (it == -1) null else it },
    )

    override fun toString(): String {
        val cellsToString = StringBuilder()
        var index = 0
        for (i in 0 until length) {
            for (j in 0 until length) {
                cellsToString.append("${cells[index].valueToString()},")
                index++
            }
            cellsToString.append("\n")
        }
        return "SudokuTerminal(\nlength=$length,\ncells=\n$cellsToString)"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false
        other as SudokuTerminal
        if (length != other.length) return false
        if (!cells.contentEquals(other.cells)) return false
        return true
    }

    override fun hashCode(): Int {
        var result = length
        result = 31 * result + cells.contentHashCode()
        return result
    }

    companion object {

        operator fun invoke(length: Int): SudokuTerminal {
            if (length < 1) throw IllegalArgumentException("length should bigger than 1")
            return SudokuTerminal(
                length = length,
                cells = Array(length * length) {
                    SudokuCell(
                        block = -1,
                        value = 0,
                    )
                },
            )
        }
    }
}

@Serializable
data class SudokuCell(
    @SerialName("block") val block: Int = -1, // [0, )
    @SerialName("value") var value: Int = 0, // 0: none
) {

    fun valueToString(): String {
        return "$value"
    }

    override fun toString(): String {
        return "$value[$block]"
    }
}