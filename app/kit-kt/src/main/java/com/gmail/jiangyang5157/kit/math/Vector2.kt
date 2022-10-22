package com.gmail.jiangyang5157.kit.math

/**
 * Created by Yang Jiang on April 25, 2018
 */
interface Vector2<N : Number> : Vector {
    val x: N
    val y: N

    val length: Double
    val normalize: Vector2<N>

    fun dot(other: Vector2<N>): Double
    fun cross(other: Vector2<N>): Double

    fun alpha(): Double
    fun rotate(radian: Double): Vector2<N>
}
