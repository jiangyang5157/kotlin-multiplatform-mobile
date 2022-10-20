package com.gmail.jiangyang5157.router.core

/**
 * [Route] implementation that has [data]
 */
interface DataRoute<T> : Route {
    val data: T
}
