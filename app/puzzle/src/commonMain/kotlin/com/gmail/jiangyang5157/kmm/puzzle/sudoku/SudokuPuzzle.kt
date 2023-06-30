package com.gmail.jiangyang5157.kmm.puzzle.sudoku

import com.gmail.jiangyang5157.kmm.puzzle.dlx.Dlx
import com.gmail.jiangyang5157.kmm.puzzle.dlx.DlxCell
import com.gmail.jiangyang5157.kmm.puzzle.dlx.right

data class SudokuPuzzle(
    val terminal: SudokuTerminal,
) {
    lateinit var dlx: Dlx

    init {
        reset()
    }

    fun reset() {
        val terminalSize = terminal.cells.size
        val terminalLength = terminal.length
        val cellConstraintOffset = 0
        val rowConstraintOffset = cellConstraintOffset + terminalSize
        val columnConstraintOffset = cellConstraintOffset + terminalSize
        val blockConstraintOffset = cellConstraintOffset + terminalSize
        val dlxSize = blockConstraintOffset + terminalSize

        dlx = Dlx(dlxSize)
        for (i in 0 until terminalSize) {
            val cell = terminal.cells[i]
            val cellValue = cell.value
            val rowIndex = terminal.row(i)
            val columnIndex = terminal.column(i)

            if (cellValue in 1 until terminalLength) {
                // has value
                dlx.feed(
                    arrayOf(
                        cellConstraintOffset + i + 1,
                        rowConstraintOffset + rowIndex * terminalLength + cellValue,
                        columnConstraintOffset + columnIndex * terminalLength + cellValue,
                        blockConstraintOffset + cell.block * terminalLength + cellValue,
                    )
                )
            } else {
                // no value, consider all possible values
                for (n in 1 until terminalLength) {
                    dlx.feed(
                        arrayOf(
                            cellConstraintOffset + i + 1,
                            rowConstraintOffset + rowIndex * terminalLength + n,
                            columnConstraintOffset + columnIndex * terminalLength + n,
                            blockConstraintOffset + cell.block * terminalLength + n,
                        )
                    )
                }
            }
        }
    }

    fun solve(accept: (terminal: SudokuTerminal) -> Boolean) {
        dlx.solve { cells ->
            println("#### solve=$cells")
            val terminalClone = terminal.copy()
            val terminalLength = terminalClone.length
            cells.forEach { cell ->
                val nodeRowColumnIndex =
                    cell.row.column.index // [cellConstraintOffset + 1, rowConstraintOffset]
                val nodeRowRightColumnIndex =
                    cell.row.right<DlxCell>()!!.column.index // [rowConstraintOffset + 1, columnConstraintOffset]
                val index = nodeRowColumnIndex - 1 // [0, terminalSize - 1]
                val value = nodeRowRightColumnIndex % terminalLength + 1 // [0, terminalLength - 1]
                terminalClone.cells[index].value = value

            }
            accept(terminalClone)
        }
    }

    fun hasUniqueSolution(): Boolean {
        var found = 0
        dlx.solve {
            println("#### hasUniqueSolution=$it")
            found++
            found > 1
        }
        return found == 1
    }
}