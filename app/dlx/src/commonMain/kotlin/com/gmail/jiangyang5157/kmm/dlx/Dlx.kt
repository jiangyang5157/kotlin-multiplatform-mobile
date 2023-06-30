package com.gmail.jiangyang5157.kmm.dlx

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

open class DlxNode(
    var up: DlxNode? = null,
    var down: DlxNode? = null,
    var left: DlxNode? = null,
    var right: DlxNode? = null,
) {

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

inline fun <reified T : DlxNode> DlxNode.up(): T? = up as? T
inline fun <reified T : DlxNode> DlxNode.down(): T? = down as? T
inline fun <reified T : DlxNode> DlxNode.left(): T? = left as? T
inline fun <reified T : DlxNode> DlxNode.right(): T? = right as? T

class DlxCell : DlxNode() {

    // the column this cell belongs to
    lateinit var column: DlxColumn

    // first cell in the row
    lateinit var row: DlxCell

    fun rowCells(): List<DlxCell> {
        val ret = mutableListOf(row)
        var it: DlxCell? = row.right()
        while (it != null && it != row) {
            ret.add(it)
            it = it.right()
        }
        return ret.toList()
    }

    fun rowCellsToString(): String {
        return rowCells().joinToString(", ") { it.toString() }
    }

    override fun toString(): String {
        if (!this::column.isInitialized ||
            !this::row.isInitialized
        ) return "DlxCell(ERROR: member is not Initialized)"
        return "DlxCell(column=${column.index}[${column.size}])"
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

class DlxColumn(
    // index of this column
    val index: Int = -1,
    // size of cells belongs to this column
    var size: Int = 0,
) : DlxNode() {

    override fun toString(): String {
        return "DlxColumn($index[$size])"
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

class Dlx private constructor() {

    private lateinit var columns: Array<DlxColumn>

    /** list of [DlxCell.row] used in [solve] lifecycle */
    private var solution: Array<DlxCell?> = emptyArray()

    fun columnSize(): Int = columns.size

    fun peekColumn(): Array<DlxColumn> = columns.copyOf()

    override fun toString(): String {
        val columnToString = StringBuilder()
        val head: DlxColumn? = if (columns.isEmpty()) null else columns[0]
        var it: DlxColumn? = head?.right()
        while (it != null && it != head) {
            columnToString.append("${it.index}[${it.size}]")
            it = it.right()
            columnToString.append("\n")
        }
        return "Dlx(\ncolumns=\n$columnToString)"
    }

    private fun initialize(size: Int) {
        solution = emptyArray()
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
    fun feed(columnIndexes: Array<Int>) {
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
            if (size < 0) throw IllegalArgumentException()

            val ret = Dlx()
            ret.initialize(size)
            return ret
        }
    }

    fun solve(accept: (row: List<DlxCell>) -> Boolean): Boolean {
        if (columns.isEmpty()) return false

        val head = columns[0]

        var it: DlxColumn = head.right()!!
        if (it == head) return accept(solution.filterNotNull())

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
        solution = solution + null

        val solutionSize = solution.size
        var j = targetColumn.down!!
        while (j != targetColumn) {
            if (ret) {
                break
            }

            solution[solutionSize - 1] = j as DlxCell
            var i: DlxCell = j.right()!!
            while (i != j) {
                i.column.cover()
                i = i.right()!!
            }
            ret = solve(accept)
            j = solution[solutionSize - 1] as DlxCell
            targetColumn = j.column

            i = j.left()!!
            while (i != j) {
                i.column.uncover()
                i = i.left()!!
            }
            j = j.down!!
        }

        solution = solution.dropLast(1).toTypedArray()
        targetColumn.uncover()
        return ret
    }
}