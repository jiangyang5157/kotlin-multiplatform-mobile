package com.gmail.jiangyang5157.shared.puzzle.sudoku

import com.gmail.jiangyang5157.shared.common.data.graph.Edge
import com.gmail.jiangyang5157.shared.common.data.graph.Graph
import com.gmail.jiangyang5157.shared.common.data.graph.Node

data class SudokuGraphNode(
    val index: Int,
    val cell: SudokuCell,
) : Node<Int>(index)


data class SudokuGraph(
    val terminal: SudokuTerminal,
) : Graph<Int>() {

    init {
        val terminalSize = terminal.cells.size
        for (i in 0 until terminalSize) {
            if (node(i) == null) {
                val terminalCell = terminal.cells[i]
                addNode(SudokuGraphNode(i, terminalCell))
            }
            terminal.neighbourIndexes(i).forEach { neighbourIndex ->
                if (node(neighbourIndex) == null) {
                    val neighbourCell = terminal.cells[i]
                    addNode(SudokuGraphNode(neighbourIndex, neighbourCell))
                }
                link(i, neighbourIndex)
            }
        }
    }

    fun link(src: Int, tgt: Int) {
        addEdge(src, tgt, Edge(0.0))
    }

    fun unlink(src: Int, tgt: Int) {
        deleteEdge(src, tgt)
    }

    fun srcNeighbourIndexes(index: Int): List<Int> {
        return sources(index)?.let { sources ->
            listOfNotNull(
                terminal.upIndex(index)
                    .let { if (it == -1 || sources[it] == null) null else it },
                terminal.downIndex(index)
                    .let { if (it == -1 || sources[it] == null) null else it },
                terminal.leftIndex(index)
                    .let { if (it == -1 || sources[it] == null) null else it },
                terminal.rightIndex(index)
                    .let { if (it == -1 || sources[it] == null) null else it },
            )
        } ?: emptyList()
    }

    fun tgtNeighbourIndexes(index: Int): List<Int> {
        return targets(index)?.let { targets ->
            listOfNotNull(
                terminal.upIndex(index)
                    .let { if (it == -1 || targets[it] == null) null else it },
                terminal.downIndex(index)
                    .let { if (it == -1 || targets[it] == null) null else it },
                terminal.leftIndex(index)
                    .let { if (it == -1 || targets[it] == null) null else it },
                terminal.rightIndex(index)
                    .let { if (it == -1 || targets[it] == null) null else it },
            )
        } ?: emptyList()
    }
}