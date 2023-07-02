package com.gmail.jiangyang5157.shared.puzzle.sudoku

import com.gmail.jiangyang5157.shared.common.data.graph.dfs
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlin.math.sqrt
import kotlin.random.Random

@Serializable
data class SudokuTerminal(
    // [1, )
    @SerialName("length") val length: Int,
    // left-to-right and up-to-down
    @SerialName("cells") val cells: Array<SudokuCell> = Array(length * length) { SudokuCell() },
) {

    init {
        if (length < 1) throw IllegalArgumentException("length $length less than 1 is not allow")
        if (length != sqrt(length.toFloat()).toInt()
                .let { it * it }
        ) throw IllegalArgumentException("length $length is not allow, try 4, 9, 16")
        if (length > 16) throw IllegalArgumentException("length $length bigger than 16 is not allow, due to performance concerns when solving the puzzle: length=4 (~90ms), length=9 (~100ms), length=16 (~1000ms)")
        if (cells.size != length * length) throw IllegalArgumentException("cells size should be ${length * length}")
    }

    fun deepCopy(): SudokuTerminal {
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

    companion object {

        /**
         * Build SudokuTerminal has unique solution
         */
        fun withUniqueSolution(
            blockMode: SudokuBlockMode,
            length: Int,
            minTotalGiven: Int = when (length) {
                9 -> 17// The minimum number of “given” cells to solve a Sudoku is 17. This was proven in 2012 by a research team from Ireland.
                else -> 0
            },
            minSubGiven: Int = 0,
        ): SudokuTerminal {
            if (length < 1) throw IllegalArgumentException("length $length less then 1 is not allow")
            if (minTotalGiven < 0) throw IllegalArgumentException("minTotalGiven $minTotalGiven less than 0 is not allow")
            if (minSubGiven < 0) throw IllegalArgumentException("minSubGiven $minSubGiven less than 0 is not allow")

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

                when (blockMode) {
                    SudokuBlockMode.Square -> {
                        // do nothing
                    }

                    SudokuBlockMode.Irregular -> {
                        val graph = SudokuGraph(terminal)

                        // unlink different blocks
                        for (i in 0 until terminalLength) {
                            for (j in 0 until terminalLength) {
                                if (i < terminalLength - 1 && (i + 1) % blockLength == 0) {
                                    val top = terminal.index(i, j)
                                    val bottom = terminal.index(i + 1, j)
                                    graph.unlink(top, bottom)
                                    graph.unlink(bottom, top)
                                }
                                if (j < terminalLength - 1 && (j + 1) % blockLength == 0) {
                                    val left = terminal.index(i, j)
                                    val right = terminal.index(i, j + 1)
                                    graph.unlink(left, right)
                                    graph.unlink(right, left)
                                }
                            }
                        }

                        fun swap(): Boolean {
                            // generate random aIndex and bIndex
                            var aIndex = -1
                            var bIndex = -1
                            aIndex = Random.nextInt(terminalSize)

                            val aNeighbourIndexes = terminal.neighbourIndexes(aIndex)
                            aNeighbourIndexes.shuffled()

                            for (aNeighbourIndex in aNeighbourIndexes) {
                                if (terminal.cells[aNeighbourIndex].block != terminal.cells[aIndex].block) {
                                    var random = Random.nextInt(terminalLength)
                                    graph.dfs(aNeighbourIndex) { node ->
                                        bIndex = node.id
                                        random--
                                        random < 0
                                    }
                                }

                                if (aIndex == -1 || bIndex == -1) return false
                            }

                            // Swap aIndex and bIndex
                            val aBlock = terminal.cells[aIndex].block
                            val bBlock = terminal.cells[bIndex].block
                            val bNeighbourIndexes = terminal.neighbourIndexes(bIndex)

                            terminal.cells[aIndex].block = bBlock
                            terminal.cells[bIndex].block = aBlock

                            for (aNeighbourIndex in aNeighbourIndexes) {
                                if (terminal.cells[aNeighbourIndex].block == aBlock) {
                                    graph.unlink(aIndex, aNeighbourIndex)
                                    graph.unlink(aNeighbourIndex, aIndex)
                                }
                                if (terminal.cells[aNeighbourIndex].block == bBlock) {
                                    graph.link(aIndex, aNeighbourIndex)
                                    graph.link(aNeighbourIndex, aIndex)
                                }
                            }
                            for (bNeighbourIndex in bNeighbourIndexes) {
                                if (terminal.cells[bNeighbourIndex].block == aBlock) {
                                    graph.unlink(bIndex, bNeighbourIndex)
                                    graph.unlink(bNeighbourIndex, bIndex)
                                }
                                if (terminal.cells[bNeighbourIndex].block == bBlock) {
                                    graph.link(bIndex, bNeighbourIndex)
                                    graph.link(bNeighbourIndex, bIndex)
                                }
                            }

                            // Validate
                            var aValidation = 0
                            var bValidation = 0
                            graph.dfs(aIndex) {
                                bValidation++
                                false
                            }
                            graph.dfs(bIndex) {
                                aValidation++
                                false
                            }
                            if (aValidation != terminalLength || bValidation != terminalLength) {

                                // Undo swap
                                terminal.cells[aIndex].block = aBlock
                                terminal.cells[bIndex].block = bBlock

                                for (aNeighbourIndex in aNeighbourIndexes) {
                                    if (terminal.cells[aNeighbourIndex].block == aBlock) {
                                        graph.link(aIndex, aNeighbourIndex)
                                        graph.link(aNeighbourIndex, aIndex)
                                    }
                                    if (terminal.cells[aNeighbourIndex].block == bBlock) {
                                        graph.unlink(aIndex, aNeighbourIndex)
                                        graph.unlink(aNeighbourIndex, aIndex)
                                    }
                                }
                                for (bNeighbourIndex in bNeighbourIndexes) {
                                    if (terminal.cells[bNeighbourIndex].block == aBlock) {
                                        graph.link(bIndex, bNeighbourIndex)
                                        graph.link(bNeighbourIndex, bIndex)
                                    }
                                    if (terminal.cells[bNeighbourIndex].block == bBlock) {
                                        graph.unlink(bIndex, bNeighbourIndex)
                                        graph.unlink(bNeighbourIndex, bIndex)
                                    }
                                }

                                return false
                            }

                            return true
                        }

                        val swapAttempts = terminalSize / blockLength
                        var swapIndex = 0
                        while (swapIndex < swapAttempts) {
                            if (swap()) {
                                swapIndex++
                            }
                        }
                    }
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

                    val indexes = IntArray(terminalSize) { i -> i }
                    indexes.shuffle()
//                    println("#### indexes=${indexes.joinToString(",")}")
                    for (i in 0 until terminalSize) {
                        val index = indexes[i]
                        val rowIndex = result.rowIndex(index)
                        val columnIndex = result.columnIndex(index)
                        when {
                            remainTotalGiven <= minTotalGiven -> continue
                            remainRowGiven[rowIndex] <= minSubGiven &&
                                    remainColumnGiven[columnIndex] <= minSubGiven -> continue

                            else -> {
                                val resultCell = result.cells[index]
                                val resultValueBackup = resultCell.value
                                resultCell.value = 0
                                if (SudokuPuzzle(result).hasUniqueSolution()) {
//                                    println("#### clear the value")
                                    remainTotalGiven--
                                    remainRowGiven[rowIndex]--
                                    remainColumnGiven[columnIndex]--
                                } else {
//                                    println("#### revert the value")
                                    resultCell.value = resultValueBackup
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

fun SudokuTerminal.solve(): SudokuTerminal? {
    return SudokuPuzzle(this).solve()
}