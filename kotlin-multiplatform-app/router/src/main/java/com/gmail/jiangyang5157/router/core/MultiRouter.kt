package com.gmail.jiangyang5157.router.core

import java.util.concurrent.locks.ReentrantLock
import kotlin.concurrent.withLock

/**
 * # MultiRouter
 * It basically stores (and creates) references to [Router]s for specified [K]s.
 *
 * ## Note
 * This class is fully thread safe on the JVM.
 *
 * @param factory: Function that creates a router for a specific key.
 * - This [factory] will only be called once for each key and the resulting [Router] will be stored in a [MutableMap]
 */
class MultiRouter<K, T : Route>(private val factory: (key: K) -> Router<T>) {

    private val routers = mutableMapOf<K, Router<T>>()

    private val lock = ReentrantLock()

    operator fun get(key: K): Router<T> = lock.withLock {
        routers.getOrPut(key, { factory(key) })
    }
}
