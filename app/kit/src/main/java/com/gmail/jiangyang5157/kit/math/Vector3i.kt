package com.gmail.jiangyang5157.kit.math

import kotlin.math.acos
import kotlin.math.sqrt

/**
 * Created by Yang Jiang on April 24, 2018
 */
data class Vector3i(override val x: Int, override val y: Int, override val z: Int) : Vector3<Int> {

    constructor() : this(0, 0, 0)
    constructor(int: Int) : this(int, int, int)

    operator fun unaryMinus(): Vector3i = Vector3i(-x, -y, -z)
    operator fun plus(other: Vector3i): Vector3i = Vector3i(x + other.x, y + other.y, z + other.z)
    operator fun plus(int: Int): Vector3i = Vector3i(x + int, y + int, z + int)
    operator fun plus(double: Double): Vector3i =
        Vector3i((x + double).toInt(), (y + double).toInt(), (z + double).toInt())

    operator fun minus(other: Vector3i): Vector3i = Vector3i(x - other.x, y - other.y, z - other.z)
    operator fun minus(int: Int): Vector3i = Vector3i(x - int, y - int, z - int)
    operator fun minus(double: Double): Vector3i =
        Vector3i((x - double).toInt(), (y - double).toInt(), (z - double).toInt())

    operator fun times(other: Vector3i): Vector3i = Vector3i(x * other.x, y * other.y, z * other.z)
    operator fun times(int: Int): Vector3i = Vector3i(x * int, y * int, z * int)
    operator fun times(double: Double): Vector3i =
        Vector3i((x * double).toInt(), (y * double).toInt(), (z * double).toInt())

    operator fun div(other: Vector3i): Vector3i = Vector3i(x / other.x, y / other.y, z / other.z)
    operator fun div(int: Int): Vector3i = Vector3i(x / int, y / int, z / int)
    operator fun div(double: Double): Vector3i =
        Vector3i((x / double).toInt(), (y / double).toInt(), (z / double).toInt())

    override val length: Double
        get() = sqrt(this.dot(this))

    override val normalize: Vector3i
        get() = this / length

    override fun dot(other: Vector3<Int>): Double =
        (x * other.x + y * other.y + z * other.z).toDouble()

    override fun cross(other: Vector3<Int>): Vector3i = Vector3i(
        y * other.z - z * other.y,
        z * other.x - x * other.z,
        x * other.y - y * other.x
    )

    override fun alpha(): Double = Math.atan2(y.toDouble(), x.toDouble())
    override fun delta(): Double = Math.asin(z / length)

    override fun radian(other: Vector3<Int>): Double {
        var r = dot(other) * (1.0 / (length * other.length))

        if (r < -1.0) {
            r = -1.0
        } else if (r > 1.0) {
            r = 1.0
        }
        return acos(r)
    }

    override fun xRotation(radian: Double): Vector3i {
        val sin = Math.sin(radian)
        val cos = Math.cos(radian)
        return Vector3i(
            x,
            (y * cos + z * sin).toInt(),
            (y * -sin + z * cos).toInt()
        )
    }

    override fun yRotation(radian: Double): Vector3i {
        val sin = Math.sin(radian)
        val cos = Math.cos(radian)
        return Vector3i(
            (x * cos - z * sin).toInt(),
            y,
            (x * sin + z * cos).toInt()
        )
    }

    override fun zRotation(radian: Double): Vector3i {
        val sin = Math.sin(radian)
        val cos = Math.cos(radian)
        return Vector3i(
            (x * cos + y * sin).toInt(),
            (x * -sin + y * cos).toInt(),
            z
        )
    }
}
