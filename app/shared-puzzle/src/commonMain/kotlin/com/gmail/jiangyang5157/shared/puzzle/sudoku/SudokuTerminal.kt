package com.gmail.jiangyang5157.shared.puzzle.sudoku

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlin.math.sqrt

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


//                        g := NewGraph(t)
//                        // unlink between blocks
//                        for i := 0; i < t.E; i++ {
//                            for j := 0; j < t.E; j++ {
//                            if i < t.E-1 && (i+1)%square == 0 {
//                                top, bottom := Index2Id(t.Index(i, j)), Index2Id(t.Index(i+1, j))
//                                unlink(g, top, bottom)
//                                unlink(g, bottom, top)
//                            }
//                            if j < t.E-1 && (j+1)%square == 0 {
//                                left, right := Index2Id(t.Index(i, j)), Index2Id(t.Index(i, j+1))
//                                unlink(g, left, right)
//                                unlink(g, right, left)
//                            }
//                        }
//                        }
//
//                        attempts := len(t.C) / square
//                        for i := 0; i < attempts; i++ {
//                            if !swap(t, g) {
//                                i--
//                            }
//                        }
//
//                        func swap(t *TerminalJson, g graph.Graph) bool {
//
//                            // Gen random aIndex and bIndex
//                            aIndex, bIndex := -1, -1
//                            aIndex = rand.Intn(len(t.C))
//                            aNbs := t.Neighbours(aIndex)
//                            aNbs = disorderDigits(aNbs)
//                            for _, aNb := range aNbs {
//                            if t.C[aNb].B != t.C[aIndex].B {
//                                random := rand.Intn(t.E)
//                                traversal.Dfs(g, Index2Id(aNb), func(nd graph.Node) bool {
//                                    bIndex = Id2Index(nd.Id())
//                                    random--
//                                    return random < 0
//                                })
//                                break
//                            }
//                        }
//                            if aIndex == -1 || bIndex == -1 {
//                                return false
//                            }
//
//                            // Swap aIndex and bIndex
//                            aBlock, bBlock := t.C[aIndex].B, t.C[bIndex].B
//                            aIndexId, bIndexId := Index2Id(aIndex), Index2Id(bIndex)
//                            bNbs := t.Neighbours(bIndex)
//
//                            t.C[aIndex].B, t.C[bIndex].B = bBlock, aBlock
//                            for _, aNb := range aNbs {
//                            aNbId := Index2Id(aNb)
//                            if t.C[aNb].B == aBlock {
//                                unlink(g, aIndexId, aNbId)
//                                unlink(g, aNbId, aIndexId)
//                            }
//                            if t.C[aNb].B == bBlock {
//                                link(g, aIndexId, aNbId)
//                                link(g, aNbId, aIndexId)
//                            }
//                        }
//                            for _, bNb := range bNbs {
//                            bNbId := Index2Id(bNb)
//                            if t.C[bNb].B == bBlock {
//                                unlink(g, bIndexId, bNbId)
//                                unlink(g, bNbId, bIndexId)
//                            }
//                            if t.C[bNb].B == aBlock {
//                                link(g, bIndexId, bNbId)
//                                link(g, bNbId, bIndexId)
//                            }
//                        }
//
//                            // Validate
//                            aValidation, bValidation := 0, 0
//                            traversal.Dfs(g, aIndexId, func(nd graph.Node) bool {
//                                bValidation++
//                                return false
//                            })
//                            traversal.Dfs(g, bIndexId, func(nd graph.Node) bool {
//                                aValidation++
//                                return false
//                            })
//
//                            if aValidation != t.E || bValidation != t.E {
//
//                                // Undo swap
//                                t.C[aIndex].B, t.C[bIndex].B = aBlock, bBlock
//                                for _, aNb := range aNbs {
//                                aNbId := Index2Id(aNb)
//                                if t.C[aNb].B == aBlock {
//                                    link(g, aIndexId, aNbId)
//                                    link(g, aNbId, aIndexId)
//                                }
//                                if t.C[aNb].B == bBlock {
//                                    unlink(g, aIndexId, aNbId)
//                                    unlink(g, aNbId, aIndexId)
//                                }
//                            }
//                                for _, bNb := range bNbs {
//                                bNbId := Index2Id(bNb)
//                                if t.C[bNb].B == bBlock {
//                                    link(g, bIndexId, bNbId)
//                                    link(g, bNbId, bIndexId)
//                                }
//                                if t.C[bNb].B == aBlock {
//                                    unlink(g, bIndexId, bNbId)
//                                    unlink(g, bNbId, bIndexId)
//                                }
//                            }
//                                return false
//                            }
//
//                            return true
//                        }
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