package com.gmail.jiangyang5157.common.math

/**
 * Created by Yang Jiang on April 22, 2018
 */
data class Point3i(override val x: Int, override val y: Int, override val z: Int) : Point3<Int> {

    constructor() : this(0, 0, 0)
    constructor(int: Int) : this(int, int, int)

    operator fun unaryMinus(): Point3i = Point3i(-x, -y, -z)
}
