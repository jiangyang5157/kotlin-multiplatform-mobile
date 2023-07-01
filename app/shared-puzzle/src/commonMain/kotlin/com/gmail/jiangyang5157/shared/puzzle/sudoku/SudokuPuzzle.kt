package com.gmail.jiangyang5157.shared.puzzle.sudoku

import com.gmail.jiangyang5157.shared.common.data.dlx.Dlx
import com.gmail.jiangyang5157.shared.common.data.dlx.DlxCell
import com.gmail.jiangyang5157.shared.common.data.dlx.right

data class SudokuPuzzle(
    val terminal: SudokuTerminal,
) {
    private val dlx: Dlx

    init {/*
        Init dlx and feed with constraints.

        Constraints example: 9 x 9 puzzle
        1. Each cell must has a digit: 9 * 9 = 81 constraints in column 1-81
        2. Each row must has [1, 9]: 9 * 9 = 81 constraints in column 82-162
        3. Each column must has [1, 9]: 9 * 9 = 81 constraints in column 163-243
        4. Each block must has [1, 9]: 9 * 9 = 81 constraints in column 244-324
        */
        val terminalLength = terminal.length
        val terminalSize = terminal.cells.size
        val cellConstraintOffset = 0
        val rowConstraintOffset = cellConstraintOffset + terminalSize
        val columnConstraintOffset = rowConstraintOffset + terminalSize
        val blockConstraintOffset = columnConstraintOffset + terminalSize
        val dlxSize = blockConstraintOffset + terminalSize
        dlx = Dlx(dlxSize)

        for (i in 0 until terminalSize) {
            val cell = terminal.cells[i]
            val cellValue = cell.value
            val rowIndex = terminal.rowIndex(i)
            val columnIndex = terminal.columnIndex(i)

            if (cellValue in 1..terminalLength) {
                // has value, feed constraints on this value
                dlx.feed(
                    arrayOf(
                        cellConstraintOffset + i + 1,
                        rowConstraintOffset + rowIndex * terminalLength + cellValue,
                        columnConstraintOffset + columnIndex * terminalLength + cellValue,
                        blockConstraintOffset + cell.block * terminalLength + cellValue,
                    )
                )
            } else {
                // no value, feed constraints on all possible values'
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

    /**
     * Solve puzzle without impacting original terminal
     * @param accept given a completed solved terminal, it returns true to stop finding next solution
     */
    fun solve(accept: (terminal: SudokuTerminal) -> Boolean) {
        dlx.solve { cells ->
            val terminalClone = terminal.deepCopy()
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

    /**
     * Solve puzzle and return first found solution
     */
    fun solve(): SudokuTerminal? {
        var ret: SudokuTerminal? = null
        solve {
            ret = it
            true
        }
        return ret
    }

    /**
     * Return true if this puzzle has unique solution
     */
    fun hasUniqueSolution(): Boolean {
        var found = 0
        dlx.solve {
            found++
            found > 1// break when 2 solution found
        }
        return found == 1
    }
}