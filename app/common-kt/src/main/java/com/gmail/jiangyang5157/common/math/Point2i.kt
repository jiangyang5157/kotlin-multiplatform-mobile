package com.gmail.jiangyang5157.common.math

/**
 * Created by Yang Jiang on April 22, 2018
 */
data class Point2i(override val x: Int, override val y: Int) : Point2<Int> {

    constructor() : this(0, 0)
    constructor(int: Int) : this(int, int)

    operator fun unaryMinus(): Point2i = Point2i(-x, -y)
}
