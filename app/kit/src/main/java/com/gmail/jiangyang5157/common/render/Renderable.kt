package com.gmail.jiangyang5157.common.render

/**
 * Created by Yang Jiang on April 22, 2018
 */
interface Renderable<in T> {

    fun onRender(t: T)
}
