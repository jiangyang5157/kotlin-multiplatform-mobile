package com.gmail.jiangyang5157.common.utils

/**
 * Created by Yang Jiang on April 26, 2018
 */
class BitFlag {

    private var status = 0L

    fun getStatus() = status

    fun set(bit: Long) {
        status = status or bit
    }

    fun erase(bit: Long) {
        status = status and bit.inv()
    }

    fun toggle(bit: Long) {
        status = status xor bit
    }

    fun check(bit: Long): Boolean {
        return status and bit == bit
    }
}
