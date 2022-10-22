package com.gmail.jiangyang5157.demo_router.uri

import android.net.Uri
import com.gmail.jiangyang5157.kit.data.Key
import com.gmail.jiangyang5157.router.core.DataRoute
import com.gmail.jiangyang5157.router.core.KeyRoute
import com.gmail.jiangyang5157.router.core.ParcelableRoute
import kotlinx.parcelize.Parcelize

/**
 * KeyRoute.key -> Fragment mapping configuration either by FragmentRouterBuilder()...build() or DSL:
 *
 * ```
 * FragmentRouter {
 *     fragment {
 *         map(Key("LoginRoute")) { LoginFragment::class }
 *         map(Key("HomeRoute")) { HomeFragment::class }
 *         map(Key("SettingsRoute")) { SettingsFragment::class }
 *     }
 * }
 * ```
 *
 * [data] as an associated data attached with route which in this case is an uri string that can holds multiple parameters by `&`
 *
 * ## Note
 * - ParcelableRoute only needed for router with default FragmentRouteStorage (see ParcelableFragmentRouteStorage).
 * - A custom route storage can implement in a way that doesn't require parcelable route.
 */
@Parcelize
class UriRoute(override val data: String) : KeyRoute, DataRoute<String>, ParcelableRoute {

    override val key: Key
        get() = Key(Uri.parse(data).let { "${it.scheme}://${it.authority}${it.path}" })

    fun query(name: String): String? = Uri.parse(data).getQueryParameter(name)
}
