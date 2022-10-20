package com.gmail.jiangyang5157.router.fragment.setup

import com.gmail.jiangyang5157.router.core.Route
import com.gmail.jiangyang5157.router.fragment.FragmentRouter

interface PopRetainRootImmediateOrFinish :
    PopRetainRootImmediate,
    AsFragmentActivity {

    /**
     * Same as [popRetainRootImmediate], but will also finish the current activity if no routes are left to pop
     *
     * @return
     * - true: If a route was popped
     * - false: If no route (except the root) was left to pop and the activity was finished
     *
     * @see popRetainRootImmediate
     */
    fun <T : Route> FragmentRouter<T>.popRetainRootImmediateOrFinish(): Boolean =
        if (!popRetainRootImmediate()) {
            expectThisToBeAFragmentActivity().finish()
            false
        } else {
            true
        }
}
