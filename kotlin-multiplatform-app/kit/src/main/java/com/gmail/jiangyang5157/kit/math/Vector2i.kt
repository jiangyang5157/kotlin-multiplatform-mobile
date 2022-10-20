package com.gmail.jiangyang5157.kit.math

/**
 * Created by Yang Jiang on April 24, 2018
 */
data class Vector2i(override val x: Int, override val y: Int) : Vector2<Int> {

    constructor() : this(0, 0)
    constructor(int: Int) : this(int, int)

    operator fun unaryMinus(): Vector2i = Vector2i(-x, -y)
    operator fun plus(other: Vector2i): Vector2i = Vector2i(x + other.x, y + other.y)
    operator fun plus(int: Int): Vector2i = Vector2i(x + int, y + int)
    operator fun plus(double: Double): Vector2i = Vector2i((x + double).toInt(), (y + double).toInt())
    operator fun minus(other: Vector2i): Vector2i = Vector2i(x - other.x, y - other.y)
    operator fun minus(int: Int): Vector2i = Vector2i(x - int, y - int)
    operator fun minus(double: Double): Vector2i = Vector2i((x - double).toInt(), (y - double).toInt())
    operator fun times(other: Vector2i): Vector2i = Vector2i(x * other.x, y * other.y)
    operator fun times(int: Int): Vector2i = Vector2i(x * int, y * int)
    operator fun times(double: Double): Vector2i = Vector2i((x * double).toInt(), (y * double).toInt())
    operator fun div(other: Vector2i): Vector2i = Vector2i(x / other.x, y / other.y)
    operator fun div(int: Int): Vector2i = Vector2i(x / int, y / int)
    operator fun div(double: Double): Vector2i = Vector2i((x / double).toInt(), (y / double).toInt())

    override val length: Double
        get() = Math.sqrt(this.dot(this))

    override val normalize: Vector2i
        get() = this / length

    override fun dot(other: Vector2<Int>): Double = (x * other.x + y * other.y).toDouble()
    override fun cross(other: Vector2<Int>): Double = (x * other.y + y * other.x).toDouble()

    override fun alpha(): Double = Math.atan2(y.toDouble(), x.toDouble())

    override fun rotate(radian: Double): Vector2i {
        val cosRadian = Math.cos(radian)
        val sinRadian = Math.sin(radian)
        return Vector2i(
            (x * cosRadian - y * sinRadian).toInt(),
            (x * sinRadian + y * cosRadian).toInt()
        )
    }
}
