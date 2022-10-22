package com.gmail.jiangyang5157.kit.math

/**
 * Created by Yang Jiang on April 25, 2018
 */
interface Vector3<N : Number> : Vector {
    val x: N
    val y: N
    val z: N

    val length: Double
    val normalize: Vector3<N>

    fun dot(other: Vector3<N>): Double
    fun cross(other: Vector3<N>): Vector3<N>

    fun alpha(): Double
    fun delta(): Double

    fun radian(other: Vector3<N>): Double
    fun xRotation(radian: Double): Vector3<N>
    fun yRotation(radian: Double): Vector3<N>
    fun zRotation(radian: Double): Vector3<N>
}
