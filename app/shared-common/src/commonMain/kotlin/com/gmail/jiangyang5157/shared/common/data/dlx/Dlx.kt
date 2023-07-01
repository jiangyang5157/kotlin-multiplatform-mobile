package com.gmail.jiangyang5157.shared.common.data.dlx

/*
Dancing Links (Algorithm X) data struct.

                |           |           |           |
- column 0 - column 1 - column 2 - ......... - column i -
                |           |           |           |
           -  Cell(row)-  Cell     -  Cell     -  Cell     -
                |           |           |           |
                                   -  Cell(row)-  Cell     -
                                        |           |
                       -  Cell(row)-  Cell     -  Cell     -
                            |           |           |
                       -  Cell(row)-  Cell     -  Cell     -
                            |           |           |
*/
class Dlx(size: Int) {

    val columns: Array<DlxColumn>

    init {
        // column 0 as head
        val correctSize = size + 1
        columns = Array(correctSize) { i -> DlxColumn(index = i) }

        // index: 0
        var it = columns[0]
        var prev = columns[correctSize - 1]
        it.up = it
        it.down = it
        // head.left = tail
        it.left = prev
        // tail.right = head
        prev.right = it
        prev = it

        // index: [1, correctSize]
        for (i in 1 until correctSize) {
            it = columns[i]
            it.up = it
            it.down = it
            it.left = prev
            prev.right = it
            prev = it
        }
    }

    override fun toString(): String {
        val columnString = StringBuilder()
        val head: DlxColumn? = if (columns.isEmpty()) null else columns[0]
        var it: DlxColumn? = head?.right()
        while (it != null && it != head) {
            columnString.append("${it.index},")
            it = it.right()
            columnString.append("\n")
        }
        return "Dlx(\n$columnString)"
    }

    // append row of cells to the bottom by a list of column indexes, note that column 0 is head
    fun feed(columnIndexes: Array<Int>) {
        columnIndexes.forEach {
            if (it < 1) throw IllegalArgumentException("index $it is not allow")
            if (it > columns.size - 1) throw IllegalArgumentException("index $it out of bound")
        }

        val columnIndexSize = columnIndexes.size
        val cells = (0 until columnIndexSize).map { DlxCell() }
        for (i in 0 until columnIndexSize) {
            val columnIndex = columnIndexes[i]
            val column = columns[columnIndex]
            column.size++

            val cell = cells[i]
            cell.column = column
            cell.row = cells[0]

            cell.up = column.up
            cell.down = column

            val leftIndex = leftIndex(i, columnIndexSize)
            val rightIndex = rightIndex(i, columnIndexSize)
            cell.left = cells[leftIndex]
            cell.right = cells[rightIndex]

            column.up!!.down = cell
            column.up = cell
            cells[leftIndex].right = cell
            cells[rightIndex].left = cell
        }
    }

    private fun leftIndex(index: Int, length: Int): Int =
        (index - 1).let {
            if (it < 0) length - 1 else it
        }

    private fun rightIndex(index: Int, length: Int): Int =
        (index + 1).let {
            if (it >= length) 0 else it
        }

    /* list of [DlxCell.row] used in [solve] lifecycle, holding solutions */
    private var tmp: Array<DlxCell?> = emptyArray()
    fun solve(accept: (row: List<DlxCell>) -> Boolean): Boolean {
        val head = columns[0]

        var it: DlxColumn = head.right()!!
        if (it == head) {
            // found solution, return a copy of [DlxCell.row] list
            return accept(tmp.filterNotNull())
        }

        // find the column with minimum size, it improves overall performance by compare with linear iterator
        var targetColumn = it
        var min = targetColumn.size
        while (it != head) {
            if (it.size < min) {
                min = it.size
                targetColumn = it
            }
            it = it.right()!!
        }

        var ret = false
        targetColumn.cover()

        tmp += null
        val solutionSize = tmp.size
        var j = targetColumn.down!!
        while (j != targetColumn) {
            if (ret) {
                break
            }

            tmp[solutionSize - 1] = j as DlxCell
            var i: DlxCell = j.right()!!
            while (i != j) {
                i.column.cover()
                i = i.right()!!
            }

            ret = solve(accept)

            j = tmp[solutionSize - 1] as DlxCell
            targetColumn = j.column
            i = j.left()!!
            while (i != j) {
                i.column.uncover()
                i = i.left()!!
            }
            j = j.down!!
        }
        tmp = tmp.dropLast(1).toTypedArray()

        targetColumn.uncover()
        return ret
    }
}