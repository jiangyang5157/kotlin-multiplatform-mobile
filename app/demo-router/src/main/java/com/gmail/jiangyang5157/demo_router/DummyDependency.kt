package com.gmail.jiangyang5157.demo_router

import com.gmail.jiangyang5157.demo_router.fragmentroute.ExampleRoute
import com.gmail.jiangyang5157.demo_router.transition.DefaultFragmentTransition
import com.gmail.jiangyang5157.demo_router.uri.*
import com.gmail.jiangyang5157.kit.data.Key
import com.gmail.jiangyang5157.router.core.MultiRouter
import com.gmail.jiangyang5157.router.core.Route
import com.gmail.jiangyang5157.router.core.Router
import com.gmail.jiangyang5157.router.fragment.FragmentRouter
import com.gmail.jiangyang5157.router.fragment.setup.RouterFragment
import com.gmail.jiangyang5157.router.fragment.setup.RouterFragmentActivity
import com.gmail.jiangyang5157.router.fragment.setup.expectThisToBeAFragment

/**
 * Implemented by FragmentActivity
 */
interface RouterFragmentActivityHost<T : Route> : RouterFragmentActivity {
    val router: FragmentRouter<T>
}

/**
 * Implemented by Fragment, use [router] value from it's [RouterFragmentActivityHost]
 */
interface RouterFragmentGuest<T : Route> : RouterFragment {
    @Suppress("UNCHECKED_CAST")
    override val router: FragmentRouter<T>
        get() = (expectThisToBeAFragment().activity as RouterFragmentActivityHost<T>).router
}

/**
 * Dummy dependency for providing [Router]
 */
object Dependency {

    val fragmentrouteRouter: MultiRouter<String, ExampleRoute> = MultiRouter {
        when (it) {
            "ExampleActivity" -> {
                FragmentRouter {
                    transition {
                        register(DefaultFragmentTransition())
                    }
                }
            }
            else -> {
                throw IllegalArgumentException("ExampleRoute $it is not implemented.")
            }
        }
    }

    val uriRouter: MultiRouter<String, UriRoute> = MultiRouter {
        when (it) {
            "ExampleActivity" -> {
                FragmentRouter {
                    transition {
                        register(DefaultFragmentTransition())
                    }
                    fragment {
                        map(Key("http://example.router.uri/page1")) { ExampleFragment1::class }
                        map(Key("http://example.router.uri/page2")) { ExampleFragment2::class }
                    }
                }
            }
            "ExampleCustomRouteStorageActivity" -> {
                FragmentRouter {
                    transition {
                        register(DefaultFragmentTransition())
                    }
                    fragment {
                        map(Key("http://example.router.uri/page1")) { ExampleFragment1::class }
                        map(Key("http://example.router.uri/page2")) { ExampleFragment2::class }
                    }
                    routeStorage(CustomRouteStorage())
                }
            }
            "ExampleCustomStackStorageActivity" -> {
                FragmentRouter {
                    transition {
                        register(DefaultFragmentTransition())
                    }
                    fragment {
                        map(Key("http://example.router.uri/page1")) { ExampleFragment1::class }
                        map(Key("http://example.router.uri/page2")) { ExampleFragment2::class }
                    }
                    stackStorage(CustomStackStorage())
                }
            }
            else -> {
                throw IllegalArgumentException("UriRoute $it is not implemented.")
            }
        }
    }
}
