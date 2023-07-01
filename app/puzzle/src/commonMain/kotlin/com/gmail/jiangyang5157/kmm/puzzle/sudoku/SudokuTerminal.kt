package com.gmail.jiangyang5157.kmm.puzzle.sudoku

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@Serializable
data class SudokuTerminal(
    // [1, )
    @SerialName("length") val length: Int,
    // left-to-right and up-to-down
    @SerialName("cells") val cells: Array<SudokuCell> = Array(length * length) { SudokuCell() },
) {

    init {
        if (length < 1) throw IllegalArgumentException("length $length is not allow")
        if (cells.size != length * length) throw IllegalArgumentException("cells size should be ${length * length}")
    }

    fun deepCopy() : SudokuTerminal {
        return Json.decodeFromString(Json.encodeToString(this))
    }

    fun index(row: Int, column: Int): Int = row * length + column

    fun cell(row: Int, column: Int): SudokuCell = cells[index(row, column)]

    fun rowIndex(index: Int): Int = index / length

    fun columnIndex(index: Int): Int = index % length

    fun upIndex(index: Int): Int = (index - length).let {
        if (it < 0) -1 else it
    }

    fun downIndex(index: Int): Int = (index + length).let {
        if (it > cells.size - 1) -1 else it
    }

    fun leftIndex(index: Int): Int = (index - 1).let {
        if (it < 0 || rowIndex(it) != rowIndex(index)) -1 else it
    }

    fun rightIndex(index: Int): Int = (index + 1).let {
        if (it > cells.size - 1 || rowIndex(it) != rowIndex(index)) -1 else it
    }

    fun neighbourIndexes(index: Int): List<Int> = listOfNotNull(
        upIndex(index).let { if (it == -1) null else it },
        downIndex(index).let { if (it == -1) null else it },
        leftIndex(index).let { if (it == -1) null else it },
        rightIndex(index).let { if (it == -1) null else it },
    )

    override fun toString(): String {
        val cellString = StringBuilder()
        var index = 0
        for (i in 0 until length) {
            for (j in 0 until length) {
                cellString.append("${cells[index]},")
                index++
            }
            cellString.append("\n")
        }
        return "SudokuTerminal(\nlength=$length,\ncells=\n$cellString)"
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
}

internal fun SudokuTerminal.toValueString(): String {
    val cellsToString = StringBuilder()
    var index = 0
    for (i in 0 until length) {
        for (j in 0 until length) {
            cellsToString.append("${cells[index].toValueString()},")
            index++
        }
        cellsToString.append("\n")
    }
    return "$cellsToString"
}