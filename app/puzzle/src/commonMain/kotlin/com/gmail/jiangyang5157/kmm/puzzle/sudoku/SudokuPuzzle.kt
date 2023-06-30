package com.gmail.jiangyang5157.kmm.puzzle.sudoku

import com.gmail.jiangyang5157.kmm.puzzle.dlx.Dlx
import com.gmail.jiangyang5157.kmm.puzzle.dlx.DlxCell
import com.gmail.jiangyang5157.kmm.puzzle.dlx.right

data class SudokuPuzzle(
    private val terminal: SudokuTerminal,
) {
    lateinit var dlx: Dlx

    init {
        initialize()
    }

    /*
    Constraints example: 9 x 9 puzzle
    1. Each cell must has a digit: 9 * 9 = 81 constraints in column 1-81
    2. Each row must has [1, 9]: 9 * 9 = 81 constraints in column 82-162
    3. Each column must has [1, 9]: 9 * 9 = 81 constraints in column 163-243
    4. Each block must has [1, 9]: 9 * 9 = 81 constraints in column 244-324
    */
    private fun initialize() {
        val terminalSize = terminal.cells.size
        val terminalLength = terminal.length
        val cellConstraintOffset = 0
        val rowConstraintOffset = cellConstraintOffset + terminalSize
        val columnConstraintOffset = rowConstraintOffset + terminalSize
        val blockConstraintOffset = columnConstraintOffset + terminalSize
        val dlxSize = blockConstraintOffset + terminalSize

        dlx = Dlx(dlxSize)
        for (i in 0 until terminalSize) {
            val cell = terminal.cells[i]
            val cellValue = cell.value
            val rowIndex = terminal.row(i)
            val columnIndex = terminal.column(i)

            if (cellValue in 1..terminalLength) {
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
                for (n in 1..terminalLength) {
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

    fun withUniqueSolution(): Boolean {
        var found = 0
        dlx.solve {
            found++
            found > 1
        }
        return found == 1
    }

    fun solve(accept: (terminal: SudokuTerminal) -> Boolean) {
        dlx.solve { cells ->
            val terminalClone = terminal.copy()
            val terminalLength = terminalClone.length
            cells.forEach { cell ->
                val nodeRowColumnIndex =
                    cell.row.column.index // [cellConstraintOffset + 1, rowConstraintOffset]
                val nodeRowRightColumnIndex =
                    cell.row.right<DlxCell>()!!.column.index // [rowConstraintOffset + 1, columnConstraintOffset]
                val index = nodeRowColumnIndex - 1 // [0, terminalSize - 1]
                val value =
                    (nodeRowRightColumnIndex - 1) % terminalLength + 1 // [0, terminalLength - 1]
                terminalClone.cells[index].value = value
            }
            accept(terminalClone)
        }
    }

    fun first(): SudokuTerminal? {
        var ret: SudokuTerminal? = null
        solve {
            ret = it
            true
        }
        return ret
    }

    companion object {


    }
}