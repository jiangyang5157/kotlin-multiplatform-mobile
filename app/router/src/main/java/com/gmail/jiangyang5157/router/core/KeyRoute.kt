package com.gmail.jiangyang5157.router.core

import com.gmail.jiangyang5157.common.data.Key

/**
 * [Route] implementation that has [key]
 */
interface KeyRoute : Route {
    val key: Key
}
