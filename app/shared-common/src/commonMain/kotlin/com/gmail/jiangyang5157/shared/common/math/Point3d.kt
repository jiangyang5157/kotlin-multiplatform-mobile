package com.gmail.jiangyang5157.shared.common.math

/**
 * Created by Yang Jiang on June 26, 2017
 */
data class Point3d(override val x: Double, override val y: Double, override val z: Double) :
    Point3<Double> {

    constructor() : this(0.0, 0.0, 0.0)
    constructor(double: Double) : this(double, double, double)

    operator fun unaryMinus(): Point3d = Point3d(-x, -y, -z)
}
