package com.gmail.jiangyang5157.router.fragment.transition

import androidx.fragment.app.Fragment
import com.gmail.jiangyang5157.router.core.Route
import com.gmail.jiangyang5157.router.fragment.FragmentRouterDsl

@FragmentRouterDsl
class FragmentTransitionBuilder {

    private var transition: FragmentTransition = EmptyFragmentTransition

    @FragmentRouterDsl
    fun register(transition: FragmentTransition) {
        this.transition += transition
    }

    @JvmName("registerGeneric")
    @FragmentRouterDsl
    inline fun <reified ExitFragment : Fragment, reified ExitRoute : Route,
            reified EnterFragment : Fragment, reified EnterRoute : Route> register(
        transition: GenericFragmentTransition<ExitFragment, ExitRoute, EnterFragment, EnterRoute>
    ) = register(transition.reified().erased())

    @FragmentRouterDsl
    fun clear() {
        this.transition = EmptyFragmentTransition
    }

    fun build(): FragmentTransition = transition
}
