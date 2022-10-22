package com.gmail.jiangyang5157.kit.math

/**
 * Created by Yang Jiang on April 25, 2018
 */
interface Point3<out N : Number> : Point {
    val x: N
    val y: N
    val z: N
}
