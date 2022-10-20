package com.gmail.jiangyang5157.demo_router.transition

import android.view.Gravity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.transition.Slide
import com.gmail.jiangyang5157.router.core.Route
import com.gmail.jiangyang5157.router.fragment.transition.FragmentTransition

/**
 * Example of transition, it will be used as default transition in the examples.
 *
 * A router can have multiple transitions registered.
 *
 * e.g.
 * ```
 * if (enterFragment is MyFragment1) {
 *     exitFragment.exitTransition = Fade(Fade.MODE_OUT)
 *     enterFragment.enterTransition = Fade(Fade.MODE_IN)
 * }
 * // if ....
 * ```
 */
class DefaultFragmentTransition : FragmentTransition {

    override fun setup(
        transaction: FragmentTransaction,
        exitFragment: Fragment,
        exitRoute: Route,
        enterFragment: Fragment,
        enterRoute: Route
    ) {
        exitFragment.exitTransition = Slide(Gravity.START)
        enterFragment.enterTransition = Slide(Gravity.END)
    }
}
