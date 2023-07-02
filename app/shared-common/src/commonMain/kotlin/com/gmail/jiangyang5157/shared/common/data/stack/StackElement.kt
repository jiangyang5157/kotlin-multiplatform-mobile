package com.gmail.jiangyang5157.shared.common.data.stack

import com.gmail.jiangyang5157.shared.common.data.Key

data class StackElement<out T>(
    val value: T,
    val key: Key = Key(),
) {

    override fun toString(): String {
        return "StackElement(${key.value}, $value)"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is StackElement<*>) return false
        if (this.key != other.key) return false
        if (this.value != other.value) return false
        return true
    }

    override fun hashCode(): Int {
        var hashCode = 31
        hashCode += 31 * this.key.hashCode()
        hashCode += 31 * this.value.hashCode()
        return hashCode
    }
}

fun <T> Iterable<T>.toStackElements() =
    this.map { element -> StackElement(element) }

fun <T> T.asStackElement(key: Key = Key()) =
    StackElement(this, key)
