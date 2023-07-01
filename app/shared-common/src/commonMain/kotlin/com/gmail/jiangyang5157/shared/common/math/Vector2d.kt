package com.gmail.jiangyang5157.shared.common.math

import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt

/**
 * Created by Yang Jiang on June 26, 2017
 */
data class Vector2d(override val x: Double, override val y: Double) : Vector2<Double> {

    constructor() : this(0.0, 0.0)
    constructor(double: Double) : this(double, double)

    operator fun unaryMinus(): Vector2d = Vector2d(-x, -y)
    operator fun plus(other: Vector2d): Vector2d = Vector2d(x + other.x, y + other.y)
    operator fun plus(int: Int): Vector2d = Vector2d(x + int, y + int)
    operator fun plus(double: Double): Vector2d = Vector2d(x + double, y + double)
    operator fun minus(other: Vector2d): Vector2d = Vector2d(x - other.x, y - other.y)
    operator fun minus(int: Int): Vector2d = Vector2d(x - int, y - int)
    operator fun minus(double: Double): Vector2d = Vector2d(x - double, y - double)
    operator fun times(other: Vector2d): Vector2d = Vector2d(x * other.x, y * other.y)
    operator fun times(int: Int): Vector2d = Vector2d(x * int, y * int)
    operator fun times(double: Double): Vector2d = Vector2d(x * double, y * double)
    operator fun div(other: Vector2d): Vector2d = Vector2d(x / other.x, y / other.y)
    operator fun div(int: Int): Vector2d = Vector2d(x / int, y / int)
    operator fun div(double: Double): Vector2d = Vector2d(x / double, y / double)

    override val length: Double
        get() = sqrt(this.dot(this))

    override val normalize: Vector2d
        get() = this / length

    override fun dot(other: Vector2<Double>): Double = x * other.x + y * other.y
    override fun cross(other: Vector2<Double>): Double = x * other.y + y * other.x

    override fun alpha(): Double = atan2(y, x)

    override fun rotate(radian: Double): Vector2d {
        val cosRadian = cos(radian)
        val sinRadian = sin(radian)
        return Vector2d(
            x * cosRadian - y * sinRadian,
            x * sinRadian + y * cosRadian
        )
    }
}
