package com.gmail.jiangyang5157.demo_router.fragmentroute

import androidx.fragment.app.Fragment
import com.gmail.jiangyang5157.router.core.DataRoute
import com.gmail.jiangyang5157.router.core.ParcelableRoute
import com.gmail.jiangyang5157.router.fragment.FragmentRoute
import kotlinx.parcelize.Parcelize
import kotlin.reflect.KClass

/**
 * FragmentRoute.fragment indicates associated Fragment
 *
 * [data] as an associated data attached with route
 */
interface ExampleRoute : FragmentRoute, DataRoute<String>, ParcelableRoute

@Parcelize
class ExampleRoute1(override val data: String) : ExampleRoute {
    override val fragment: KClass<out Fragment>
        get() = ExampleFragment1::class
}

@Parcelize
class ExampleRoute2(override val data: String) : ExampleRoute {
    override val fragment: KClass<out Fragment>
        get() = ExampleFragment2::class
}
