package com.gmail.jiangyang5157.kmm.puzzle.dlx

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

internal open class DlxNode(
    var up: DlxNode? = null,
    var down: DlxNode? = null,
    var left: DlxNode? = null,
    var right: DlxNode? = null,
) {

    override fun toString(): String {
        return "DlxNode(up=$up, down=$down, left=$left, right=$right)"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is DlxNode) return false
        if (this.up != other.up) return false
        if (this.down != other.down) return false
        if (this.left != other.left) return false
        if (this.right != other.right) return false
        return true
    }

    override fun hashCode(): Int {
        var hashCode = 31
        hashCode += 31 * this.up.hashCode()
        hashCode += 31 * this.down.hashCode()
        hashCode += 31 * this.left.hashCode()
        hashCode += 31 * this.right.hashCode()
        return hashCode
    }
}

internal inline fun <reified T : DlxNode> DlxNode.up(): T? = up as? T
internal inline fun <reified T : DlxNode> DlxNode.down(): T? = down as? T
internal inline fun <reified T : DlxNode> DlxNode.left(): T? = left as? T
internal inline fun <reified T : DlxNode> DlxNode.right(): T? = right as? T

internal class DlxCell : DlxNode() {

    // the column this cell belongs to
    lateinit var column: DlxColumn

    // first cell in the row
    lateinit var row: DlxCell

    override fun toString(): String {
        if (!this::column.isInitialized ||
            !this::row.isInitialized
        ) return "DlxCell(ERROR: member is not Initialized)"
        return "DlxCell(column=$column, row=$row, up=$up, down=$down, left=$left, right=$right)"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is DlxCell) return false
        if (this.column != other.column) return false
        if (this.row != other.row) return false
        if (this.up != other.up) return false
        if (this.down != other.down) return false
        if (this.left != other.left) return false
        if (this.right != other.right) return false
        return true
    }

    override fun hashCode(): Int {
        var hashCode = 31
        hashCode += 31 * this.column.hashCode()
        hashCode += 31 * this.row.hashCode()
        hashCode += 31 * this.up.hashCode()
        hashCode += 31 * this.down.hashCode()
        hashCode += 31 * this.left.hashCode()
        hashCode += 31 * this.right.hashCode()
        return hashCode
    }
}

internal class DlxColumn(
    // index of this column
    val index: Int = -1,
    // size of cells belongs to this column
    var size: Int = 0,
) : DlxNode() {

    override fun toString(): String {
        return "DlxColumn(index=$index, size=$size)"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is DlxColumn) return false
        if (this.index != other.index) return false
        if (this.size != other.size) return false
        if (this.up != other.up) return false
        if (this.down != other.down) return false
        if (this.left != other.left) return false
        if (this.right != other.right) return false
        return true
    }

    override fun hashCode(): Int {
        var hashCode = 31
        hashCode += 31 * this.index.hashCode()
        hashCode += 31 * this.size.hashCode()
        hashCode += 31 * this.up.hashCode()
        hashCode += 31 * this.down.hashCode()
        hashCode += 31 * this.left.hashCode()
        hashCode += 31 * this.right.hashCode()
        return hashCode
    }
}

// remove the column
internal fun DlxColumn.cover() {
    right?.left = left
    left?.right = right

    var i = down
    while (i != null && i != this) {
        var j: DlxCell = i.right()!!
        while (j != i) {
            j.down?.up = j.up
            j.up?.down = j.down
            j.column.size--
            j = j.right()!!
        }
        i = i.down
    }
}

// add the column back
internal fun DlxColumn.uncover() {
    var i = up
    while (i != null && i != this) {
        var j: DlxCell = i.left()!!
        while (j != i) {
            j.down?.up = j
            j.up?.down = j
            j.column.size++
            j = j.left()!!
        }
        i = i.up
    }

    right?.left = this
    left?.right = this
}

internal typealias DlxSolution = MutableList<DlxCell?> // TODO YangJ: ?

internal class Dlx private constructor() {

    private lateinit var solution: DlxSolution
    private lateinit var columns: Array<DlxColumn>

    fun columnSize(): Int {
        return columns.size
    }

    fun peekColumn(): Array<DlxColumn> {
        return columns.copyOf()
    }

    fun peekSolution(): List<DlxCell?> {
        return solution.toList()
    }

    override fun toString(): String {
        if (!this::columns.isInitialized ||
            !this::solution.isInitialized
        ) return "Dlx(ERROR: member is not Initialized)"

        val head: DlxColumn? = if (columns.isEmpty()) null else columns[0]
        var columnsToString = "head=$head"

        var it: DlxColumn? = head?.right()
        while (it != null && it != head) {
            columnsToString += ", $it"
            it = it.right()
        }
        return "Dlx(columns=$columnsToString, solution=$solution)"
    }

    fun reset(size: Int) {
        solution = mutableListOf()
        columns = emptyArray()

        if (size < 0) return

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

    // append row of cells to the bottom by a list of column indexes, note that column 0 is head
    fun feed(columnIndexes: List<Int>) {
        if (columns.isEmpty()) return
        columnIndexes.forEach {
            if (it < 1) return // head is not for feed
            if (it > columns.size - 1) return // contains column index out of bound
        }

        val size = columnIndexes.size
        val cells = (0 until size).map { DlxCell() }

        for (i in 0 until size) {
            val columnIndex = columnIndexes[i]
            val column = columns[columnIndex]
            column.size++

            val cell = cells[i]
            cell.column = column
            cell.row = cells[0]

            cell.up = column.up
            cell.down = column

            val circularShiftLeftI = circularShiftLeft(i, size)
            val circularShiftRightI = circularShiftRight(i, size)
            cell.left = cells[circularShiftLeftI]
            cell.right = cells[circularShiftRightI]

            column.up?.down = cell
            column.up = cell
            cells[circularShiftLeftI].right = cell
            cells[circularShiftRightI].left = cell
        }
    }

    private fun circularShiftLeft(index: Int, length: Int): Int =
        (index - 1).let {
            if (it < 0) length - 1 else it
        }

    private fun circularShiftRight(index: Int, length: Int): Int =
        (index + 1).let {
            if (it >= length) 0 else it
        }

    companion object {

        operator fun invoke(size: Int): Dlx {
            val ret = Dlx()
            ret.reset(size)
            return ret
        }
    }

    fun solve(accept: (DlxSolution) -> Boolean): Boolean {
        if (columns.isEmpty()) return false

        val head = columns[0]

        var it: DlxColumn = head.right()!!
        if (it == head) return accept(solution)

        // find the column has minimum size, it improves overall performance by compare with linear iterator
        var targetColumn = it
        var min = targetColumn.size
        while (it != head) {
            if (it.size < min) {
                targetColumn = it
                min = it.size
            }
            it = it.right()!!
        }

        var ret = false
        targetColumn.cover()
        solution += listOf(null)
        println("#### targetColumn=${targetColumn.index} - $this")

        val oLen = solution.size
        var j = targetColumn.down!!
        while (j != targetColumn) {
            if (ret) {
                break
            }

            solution[oLen - 1] = j as DlxCell
            var i: DlxCell = j.right()!!
            while (i != j) {
                i.column.cover()
                i = i.right()!!
            }
            ret = solve(accept)
            j = solution[oLen - 1] as DlxCell
            targetColumn = j.column

            i = j.left()!!
            while (i != j) {
                i.column.uncover()
                i = i.left()!!
            }
            j = j.down!!
        }

        solution = solution.take(oLen - 1).toMutableList()
        targetColumn.uncover()
        return ret
    }
}