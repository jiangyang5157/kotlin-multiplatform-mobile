package com.gmail.jiangyang5157.common.math

import kotlin.math.acos
import kotlin.math.sqrt

/**
 * Created by Yang Jiang on June 27, 2017
 */
data class Vector3d(override val x: Double, override val y: Double, override val z: Double) : Vector3<Double> {

    constructor() : this(0.0, 0.0, 0.0)
    constructor(double: Double) : this(double, double, double)

    operator fun unaryMinus(): Vector3d = Vector3d(-x, -y, -z)
    operator fun plus(other: Vector3d): Vector3d = Vector3d(x + other.x, y + other.y, z + other.z)
    operator fun plus(int: Int): Vector3d = Vector3d(x + int, y + int, z + int)
    operator fun plus(double: Double): Vector3d = Vector3d(x + double, y + double, z + double)
    operator fun minus(other: Vector3d): Vector3d = Vector3d(x - other.x, y - other.y, z - other.z)
    operator fun minus(int: Int): Vector3d = Vector3d(x - int, y - int, z - int)
    operator fun minus(double: Double): Vector3d = Vector3d(x - double, y - double, z - double)
    operator fun times(other: Vector3d): Vector3d = Vector3d(x * other.x, y * other.y, z * other.z)
    operator fun times(int: Int): Vector3d = Vector3d(x * int, y * int, z * int)
    operator fun times(double: Double): Vector3d = Vector3d(x * double, y * double, z * double)
    operator fun div(other: Vector3d): Vector3d = Vector3d(x / other.x, y / other.y, z / other.z)
    operator fun div(int: Int): Vector3d = Vector3d(x / int, y / int, z / int)
    operator fun div(double: Double): Vector3d = Vector3d(x / double, y / double, z / double)

    override val length: Double
        get() = sqrt(this.dot(this))

    override val normalize: Vector3d
        get() = this / length

    override fun dot(other: Vector3<Double>): Double = x * other.x + y * other.y + z * other.z
    override fun cross(other: Vector3<Double>): Vector3d = Vector3d(
        y * other.z - z * other.y,
        z * other.x - x * other.z,
        x * other.y - y * other.x
    )

    override fun alpha(): Double = Math.atan2(y, x)
    override fun delta(): Double = Math.asin(z / length)

    override fun radian(other: Vector3<Double>): Double {
        var r = dot(other) * (1.0 / (length * other.length))

        if (r < -1.0) {
            r = -1.0
        } else if (r > 1.0) {
            r = 1.0
        }
        return acos(r)
    }

    override fun xRotation(radian: Double): Vector3d {
        val sin = Math.sin(radian)
        val cos = Math.cos(radian)
        return Vector3d(
            x,
            y * cos + z * sin,
            y * -sin + z * cos
        )
    }

    override fun yRotation(radian: Double): Vector3d {
        val sin = Math.sin(radian)
        val cos = Math.cos(radian)
        return Vector3d(
            x * cos - z * sin,
            y,
            x * sin + z * cos
        )
    }

    override fun zRotation(radian: Double): Vector3d {
        val sin = Math.sin(radian)
        val cos = Math.cos(radian)
        return Vector3d(
            x * cos + y * sin,
            x * -sin + y * cos,
            z
        )
    }
}
