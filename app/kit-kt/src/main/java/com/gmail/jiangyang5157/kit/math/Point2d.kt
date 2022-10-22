package com.gmail.jiangyang5157.kit.math

/**
 * Created by Yang Jiang on June 26, 2017
 */
data class Point2d(override val x: Double, override val y: Double) : Point2<Double> {

    constructor() : this(0.0, 0.0)
    constructor(double: Double) : this(double, double)

    operator fun unaryMinus(): Point2d = Point2d(-x, -y)
}
