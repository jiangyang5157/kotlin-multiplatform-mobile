package com.gmail.jiangyang5157.router.fragment.transition

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.gmail.jiangyang5157.router.core.Route
import com.gmail.jiangyang5157.router.fragment.FragmentRoute
import kotlin.reflect.KClass

/**
 * # FragmentTransition
 * Represents a way of hooking into the [FragmentRoute] to setup transitions before the routing of a fragment is executed.
 *
 * @see GenericFragmentTransition
 */
typealias FragmentTransition = GenericFragmentTransition<Fragment, Route, Fragment, Route>

/**
 * Generic version of [FragmentTransition].
 * Allows for constraining the transition by the generic type parameters.
 *
 * @see FragmentTransition
 */
interface GenericFragmentTransition<in ExitFragment : Fragment, in ExitRoute : Route, in EnterFragment : Fragment, in EnterRoute : Route> {

    /**
     * ## Note
     * - The setup method will be called before the the router commits any fragment transaction
     * - The setup method will be called for pushing a new route to the top
     * - The setup method will be called for pop the current top route
     * - The transaction parameter should not be used for anything different than setting up transitions
     */
    fun setup(
        transaction: FragmentTransaction,
        exitFragment: ExitFragment,
        exitRoute: ExitRoute,
        enterFragment: EnterFragment,
        enterRoute: EnterRoute
    )
}

/**
 * - Transitions can be combined/chained to build a new transition which invokes all setup methods
 * ```
 * val loginToRegisterTransition: FragmentTransition = ...
 * val loginToHomeTransition: FragmentTransition = ...
 * val homeToSettingsTransition: FragmentTransition = ...
 * val transitionSet: FragmentTransition = loginToRegisterTransition + loginToHomeTransition + homeToSettingsTransition
 * ```
 */
operator fun FragmentTransition.plus(other: FragmentTransition): FragmentTransition =
    CompositeFragmentTransition(this, other)

private class CompositeFragmentTransition(
    private val first: FragmentTransition,
    private val second: FragmentTransition
) : FragmentTransition {

    override fun setup(
        transaction: FragmentTransaction,
        exitFragment: Fragment,
        exitRoute: Route,
        enterFragment: Fragment,
        enterRoute: Route
    ) {
        first.setup(transaction, exitFragment, exitRoute, enterFragment, enterRoute)
        second.setup(transaction, exitFragment, exitRoute, enterFragment, enterRoute)
    }
}

/**
 * # ReifiedGenericFragmentTransition<A, B, C, D>.erased
 *
 * @return GenericFragmentTransition<Fragment, Route, Fragment, Route>
 */
@PublishedApi
internal fun <ExitFragment : Fragment, ExitRoute : Route, EnterFragment : Fragment, EnterRoute : Route>
ReifiedGenericFragmentTransition<ExitFragment, ExitRoute, EnterFragment, EnterRoute>.erased(): GenericFragmentTransition<Fragment, Route, Fragment, Route> =
    ErasedFragmentTransition(this)

@PublishedApi
internal class ReifiedGenericFragmentTransition<ExitFragment : Fragment, ExitRoute : Route, EnterFragment : Fragment, EnterRoute : Route>(
    transition: GenericFragmentTransition<ExitFragment, ExitRoute, EnterFragment, EnterRoute>,
    val enterFragment: KClass<EnterFragment>,
    val exitFragment: KClass<ExitFragment>,
    val enterRoute: KClass<EnterRoute>,
    val exitRoute: KClass<ExitRoute>
) : GenericFragmentTransition<ExitFragment, ExitRoute, EnterFragment, EnterRoute> by transition

@PublishedApi
internal inline fun <reified ExitFragment : Fragment, reified ExitRoute : Route, reified EnterFragment : Fragment, reified EnterRoute : Route>
GenericFragmentTransition<ExitFragment, ExitRoute, EnterFragment, EnterRoute>.reified() =
    ReifiedGenericFragmentTransition(
        transition = this,
        enterFragment = EnterFragment::class,
        enterRoute = EnterRoute::class,
        exitFragment = ExitFragment::class,
        exitRoute = ExitRoute::class
    )

private class ErasedFragmentTransition<ExitFragment : Fragment, ExitRoute : Route, EnterFragment : Fragment, EnterRoute : Route>(
    private val transition: ReifiedGenericFragmentTransition<ExitFragment, ExitRoute, EnterFragment, EnterRoute>
) : GenericFragmentTransition<Fragment, Route, Fragment, Route> {

    override fun setup(
        transaction: FragmentTransaction,
        exitFragment: Fragment,
        exitRoute: Route,
        enterFragment: Fragment,
        enterRoute: Route
    ) {
        if (transition.enterFragment.java.isInstance(enterFragment) &&
            transition.enterRoute.java.isInstance(enterRoute) &&
            transition.exitFragment.java.isInstance(exitFragment) &&
            transition.exitRoute.java.isInstance(exitRoute)
        ) {
            @Suppress("UNCHECKED_CAST")
            transition.setup(
                transaction = transaction,
                enterFragment = enterFragment as EnterFragment,
                enterRoute = enterRoute as EnterRoute,
                exitFragment = exitFragment as ExitFragment,
                exitRoute = exitRoute as ExitRoute
            )
        }
    }
}
