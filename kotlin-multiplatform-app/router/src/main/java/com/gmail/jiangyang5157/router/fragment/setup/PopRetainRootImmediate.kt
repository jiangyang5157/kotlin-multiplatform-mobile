package com.gmail.jiangyang5157.router.fragment.setup

import com.gmail.jiangyang5157.router.core.Route
import com.gmail.jiangyang5157.router.core.pop
import com.gmail.jiangyang5157.router.error.RouterException
import com.gmail.jiangyang5157.router.fragment.FragmentRouter

interface PopRetainRootImmediate {

    /**
     * Will pop a single element from the routing stack, but will never pop the `root` (the last remaining route)
     *
     * @return
     * - true: If the event was consumed an a route was popped
     * - false: If routing stack has equal or less than one element left and no route was popped
     */
    fun <T : Route> FragmentRouter<T>.popRetainRootImmediate(): Boolean {
        var didPop: Boolean? = null

        routingStackInstruction {
            if (count() <= 1) {
                didPop = false
                this
            } else {
                didPop = true
                pop()
            }
        }

        return didPop ?: throw RouterException(
            "popRetainRootImmediate can only be called in the correct lifecycle state and after router.setup"
        )
    }
}
