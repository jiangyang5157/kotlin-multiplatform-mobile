package com.gmail.jiangyang5157.shared.common.data.dlx

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