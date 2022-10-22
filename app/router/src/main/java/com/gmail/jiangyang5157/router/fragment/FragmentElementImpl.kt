package com.gmail.jiangyang5157.router.fragment

import androidx.fragment.app.Fragment
import com.gmail.jiangyang5157.kit.data.Key
import com.gmail.jiangyang5157.router.core.KeyRoute
import com.gmail.jiangyang5157.router.core.Route
import com.gmail.jiangyang5157.router.core.RoutingStack
import com.gmail.jiangyang5157.router.error.RouterException
import com.gmail.jiangyang5157.router.fragment.setup.FragmentContainer
import kotlin.reflect.KClass

internal class FragmentElementImpl<T : Route>(
    private val fragmentRouterConfiguration: FragmentRouterConfiguration<T>,
    private val container: FragmentContainer,
    private val element: RoutingStack.Element<T>,
) : FragmentElement<T>(),
    FragmentRouterConfiguration<T> by fragmentRouterConfiguration {

    class Factory<T : Route>(
        private val fragmentRouterConfiguration: FragmentRouterConfiguration<T>,
        private val container: FragmentContainer
    ) : FragmentElement.Factory<T> {
        override fun invoke(element: RoutingStack.Element<T>): FragmentElement<T> =
            FragmentElementImpl(
                fragmentRouterConfiguration = fragmentRouterConfiguration,
                container = container,
                element = element
            )
    }

    override val key: Key = element.key

    override val route: T = element.route

    override fun createFragment(): Fragment {
        val context = container.activity
        val fragmentFactory = container.fragmentManager.fragmentFactory
        val fragment =
            fragmentFactory.instantiate(context.classLoader, getFragmentClassNameOrThrow())
        fragmentRouteStorage.run { fragment.attach(route) }
        return fragment
    }

    private fun getFragmentClassNameOrThrow(): String =
        getFragmentClassOrThrow().java.canonicalName.orEmpty()

    private fun getFragmentClassOrThrow(): KClass<out Fragment> =
        when (route) {
            is FragmentRoute -> {
                route.fragment
            }
            is KeyRoute -> {
                fragmentMap[route.key] ?: throw RouterException(
                    """
        Missing fragment mapping for key ${route.key}
        Consider implementing `FragmentRoute`, specifying a `FragmentMap` or declaring it via DSL:
        FragmentRouter {
            fragment {
                map(key) { fragmentClass }
            }
        }
                    """.trimIndent()
                )
            }
            else -> {
                throw RouterException(
                    """
        Missing fragment mapping for route $route
        In order to find fragment mapping, the route should be either [FragmentRoute] or [KeyRoute].
                    """.trimIndent()
                )
            }
        }
}
