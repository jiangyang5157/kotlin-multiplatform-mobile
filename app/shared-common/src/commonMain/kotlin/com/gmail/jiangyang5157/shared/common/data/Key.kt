package com.gmail.jiangyang5157.shared.common.data

import kotlin.random.Random

/**
 * # Key
 * Key objects are used to identify other objects (routes in particular)
 * The [value] of the key will be used for comparison of [Key] objects.
 *
 * ## Note
 * - [Key] sub-implementations cannot override the behaviour of the [equals] or [hashCode] function
 * - [Key] implementations are required to be immutable and not changing over time.
 */
data class Key(val value: String = randomKeyValue()) {

    override fun toString() = "Key($value)"

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Key) return false
        if (value != other.value) return false
        return true
    }

    override fun hashCode() = value.hashCode()

    companion object {

        /**
         * Creates a new 128-bit long random string, which can be used as value for [Key] objects.
         */
        fun randomKeyValue() = Random.nextBytes(16)
            .map { byte -> byte.toInt() and 0xFF }
            .joinToString("") { it.toString(16) }
    }
}
