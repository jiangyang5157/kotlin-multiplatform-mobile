package com.gmail.jiangyang5157.shared.common.data

data class Element<out T>(
    val value: T,
    val key: Key = Key(),
) {

    override fun toString(): String {
        return "Element($key, $value)"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Element<*>) return false
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

/**
 * Convert values to Elements with unique Keys
 */
fun <T> Iterable<T>.toElements() = this.map { element -> Element(element) }

fun <T> T.toElement(key: Key = Key()) = Element(this, key)