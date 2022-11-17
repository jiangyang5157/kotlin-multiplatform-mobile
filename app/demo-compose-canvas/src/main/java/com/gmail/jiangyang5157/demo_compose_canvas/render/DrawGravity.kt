package com.gmail.jiangyang5157.demo_compose_canvas.render

inline fun Int.hasFlag(flag: Int): Boolean = flag and this == flag
inline fun Int.withFlag(flag: Int): Int = this or flag
inline fun Int.minusFlag(flag: Int): Int = this and flag.inv()

object DrawGravity {
    val NO_GRAVITY = 0
    val LEFT = 1
    val TOP = 1 shl 1
    val RIGHT = 1 shl 2
    val BOTTOM = 1 shl 3

    val CENTER_VERTICAL = TOP or BOTTOM
    val CENTER_HORIZONTAL = LEFT or RIGHT
    val CENTER = CENTER_VERTICAL or CENTER_HORIZONTAL
}