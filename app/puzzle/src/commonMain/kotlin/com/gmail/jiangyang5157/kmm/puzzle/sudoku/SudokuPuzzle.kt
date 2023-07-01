package com.gmail.jiangyang5157.kmm.puzzle.sudoku

import com.gmail.jiangyang5157.shared.common.data.dlx.Dlx
import com.gmail.jiangyang5157.shared.common.data.dlx.DlxCell
import com.gmail.jiangyang5157.shared.common.data.dlx.right
import kotlin.math.sqrt

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

    companion object {

        /**
         * Build SudokuTerminal has unique solution
         */
        fun buildTerminal(
            length: Int,
            minSubGiven: Int,
            minTotalGiven: Int,
        ): SudokuTerminal {
            if (length < 1) throw IllegalArgumentException("length $length is not allow")
            if (minSubGiven < 1) throw IllegalArgumentException("minSubGiven $minSubGiven is not allow")
            if (minTotalGiven < 1) throw IllegalArgumentException("minTotalGiven $minTotalGiven is not allow")

            var round = 0
            while (true) {
                // it will be unlikely need a seconder round
                round++
                println("#### SudokuPuzzle.buildTerminal round=$round")

                // initialize blank terminal with block and value unassigned
                val terminal = SudokuTerminal(length)
                val terminalLength = terminal.length
                val terminalSize = terminal.cells.size
                // block length, eg: block length is 3 in a length=9 (9x9) puzzle
                val blockLength = sqrt(terminalLength.toFloat()).toInt()

                // feed terminal blocks
                for (i in 0 until terminalSize) {
                    val row = terminal.rowIndex(i)
                    val column = terminal.columnIndex(i)
                    val cell = terminal.cells[i]
                    cell.block = (row / blockLength) * blockLength + column / blockLength
                }

                // feed random values into diagonal block of terminal
                val blockState = mutableMapOf<Int, MutableMap<Int, Boolean>>()
                val tmp = IntArray(terminalLength) { i -> i + 1 }
                for (i in 0 until terminalLength step blockLength + 1) {
                    tmp.shuffle()
                    for (j in 0 until terminalLength) {
                        val k = (i / blockLength) * blockLength
                        val row = j / blockLength + k
                        val column = j % blockLength + k
                        val index = terminal.index(row, column)
                        val cell = terminal.cells[index]
                        val cellBlock = cell.block

                        if (!blockState.containsKey(cellBlock)) {
                            blockState[cellBlock] = mutableMapOf()
                        }
                        if (blockState[cellBlock]?.get(tmp[j]) != null) {
                            continue
                        }

                        cell.value = tmp[j]
                        blockState[cellBlock]?.set(tmp[j], true)
                    }
                }

                // build a completed terminal by using the first solution, it will be unlikely 0 solution here
                SudokuPuzzle(terminal).solve()?.let { result ->
                    // dig out values one by one and make sure it still has unique solution in the process
                    var remainTotalGiven = terminalSize
                    val remainRowGiven = IntArray(terminalLength) { terminalLength }
                    val remainColumnGiven = IntArray(terminalLength) { terminalLength }

                    val rowIndexes = IntArray(terminalLength) { i -> i }
                    val columnIndexes = IntArray(terminalLength) { i -> i }
                    for (i in 0 until terminalLength) {
                        val row = rowIndexes[i]
                        columnIndexes.shuffle()
                        for (j in 0 until terminalLength) {
                            val column = columnIndexes[j]
                            when {
                                remainRowGiven[row] <= minSubGiven -> continue
                                remainColumnGiven[column] <= minSubGiven -> continue
                                remainTotalGiven <= minTotalGiven -> continue
                                else -> {
                                    val resultCell = result.cell(row, column)
                                    val resultValueBackup = resultCell.value
                                    resultCell.value = 0
                                    if (SudokuPuzzle(result).hasUniqueSolution()) {
//                                        println("#### clear the value")
                                        remainRowGiven[row]--
                                        remainColumnGiven[column]--
                                        remainTotalGiven--
                                    } else {
//                                        ("#### revert the value")
                                        resultCell.value = resultValueBackup
                                    }
                                }
                            }
                        }
                    }

                    return result
                }
            }
        }
    }
}