package com.gmail.jiangyang5157.shared.common.data.dlx

class DlxCell : DlxNode() {

    // the column this cell belongs to
    lateinit var column: DlxColumn

    // first cell in the row
    lateinit var row: DlxCell

    override fun toString(): String {
        return "DlxCell(${column.index})"
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

fun DlxCell.rowCells(): List<DlxCell> {
    val ret = mutableListOf(row)
    var it: DlxCell? = row.right()
    while (it != null && it != row) {
        ret.add(it)
        it = it.right()
    }
    return ret.toList()
}

fun List<DlxCell>.toColumnString(): String {
    return joinToString(",") { "${it.column.index}" }
}