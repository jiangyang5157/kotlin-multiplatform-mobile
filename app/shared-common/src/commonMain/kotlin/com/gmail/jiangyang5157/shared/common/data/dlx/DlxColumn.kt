package com.gmail.jiangyang5157.shared.common.data.dlx

class DlxColumn(
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

// unlink the column
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

// link the column
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