package com.gmail.jiangyang5157.demo_compose_canvas.render

fun Int.hasFlag(flag: Int): Boolean = flag and this == flag
fun Int.withFlag(flag: Int): Int = this or flag
fun Int.minusFlag(flag: Int): Int = this and flag.inv()

fun Long.hasFlag(flag: Long): Boolean = flag and this == flag
fun Long.withFlag(flag: Long): Long = this or flag
fun Long.minusFlag(flag: Long): Long = this and flag.inv()

object DrawGravity {
    val Left = 1
    val Top = 1 shl 1
    val Right = 1 shl 2
    val Bottom = 1 shl 3

    val CenterVertical = Top or Bottom
    val CenterHorizontal = Left or Right
    val Center = CenterVertical or CenterHorizontal
}

object DrawOrientation {
    val Vertical = 1
    val Horizontal = 1 shl 1
}