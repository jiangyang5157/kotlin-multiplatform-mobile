package com.gmail.jiangyang5157.kmm.puzzle.sudoku

import com.gmail.jiangyang5157.kmm.puzzle.dlx.Dlx
import com.gmail.jiangyang5157.kmm.puzzle.dlx.DlxCell
import com.gmail.jiangyang5157.kmm.puzzle.dlx.right
import kotlin.math.sqrt

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

        fun withUniqueSolution(
            length: Int,
            minSubGiven: Int,
            minTotalGiven: Int,
        ): SudokuTerminal {
            var round = 0
            while (true) {
                // it will be unlikely need a seconder round
                round++
                println("SudokuPuzzle.withUniqueSolution round=$round")

                // initialize blank terminal with block and value unassigned
                val terminal = SudokuTerminal(length)

                val terminalLength = terminal.length
                val terminalSize = terminal.cells.size

                // block length, eg: block length is 3 in a length=9 (9x9) puzzle
                val blockLength = sqrt(terminalLength.toFloat()).toInt()

                // feed terminal block
                for (i in 0 until terminalSize) {
                    val row = terminal.row(i)
                    val column = terminal.column(i)
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
                SudokuPuzzle(terminal).first()?.let { first ->
                    // dig out values one by one and make sure it still with unique solution in the process
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
                                    val cell = first.cell(row, column)
                                    val valueBackup = cell.value
                                    cell.value = 0
                                    if (SudokuPuzzle(first).withUniqueSolution()) {
//                                        println("#### clear the value")
                                        remainRowGiven[row]--
                                        remainColumnGiven[column]--
                                        remainTotalGiven--
                                    } else {
//                                        println("#### revert the value")
                                        cell.value = valueBackup
                                    }
                                }
                            }
                        }
                    }

                    return terminal
                }
            }
        }
    }
}