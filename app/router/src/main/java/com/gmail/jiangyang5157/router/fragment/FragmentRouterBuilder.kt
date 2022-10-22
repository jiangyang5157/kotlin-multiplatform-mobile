package com.gmail.jiangyang5157.router.fragment

import android.os.Parcelable
import androidx.lifecycle.Lifecycle
import com.gmail.jiangyang5157.router.core.*
import com.gmail.jiangyang5157.router.error.RouterException
import com.gmail.jiangyang5157.router.fragment.mapping.EmptyFragmentMap
import com.gmail.jiangyang5157.router.fragment.mapping.FragmentMap
import com.gmail.jiangyang5157.router.fragment.mapping.FragmentMapBuilder
import com.gmail.jiangyang5157.router.fragment.mapping.plus
import com.gmail.jiangyang5157.router.fragment.setup.*
import com.gmail.jiangyang5157.router.fragment.transition.EmptyFragmentTransition
import com.gmail.jiangyang5157.router.fragment.transition.FragmentTransition
import com.gmail.jiangyang5157.router.fragment.transition.FragmentTransitionBuilder
import com.gmail.jiangyang5157.router.fragment.transition.plus
import kotlin.reflect.KClass

@FragmentRouterDsl
class FragmentRouterBuilder<T : Route>(private val type: KClass<T>) {

    private val typeIsParcelable = Parcelable::class.java.isAssignableFrom(type.java)

    private var fragmentMap: FragmentMap = EmptyFragmentMap()

    private var fragmentRouteStorage: FragmentRouteStorage<T>? =
        when {
            typeIsParcelable -> ParcelableFragmentRouteStorage.createUnsafe()
            else -> null
        }

    private var routingStackStorage: RoutingStackStorage<T>? =
        when {
            typeIsParcelable -> ParcelableRoutingStackStorage.createUnsafe()
            else -> null
        }

    private var fragmentTransition: FragmentTransition = EmptyFragmentTransition

    private var fragmentStackPatcher: FragmentStackPatcher = FragmentStackPatcherImpl

    private var fragmentContainerLifecycleFactory: FragmentContainerLifecycle.Factory =
        FragmentContainerLifecycleImpl.Factory(
            attachEvent = Lifecycle.Event.ON_RESUME,
            detachEvent = Lifecycle.Event.ON_PAUSE
        )

    private var routingStackInstruction: RoutingStackInstruction<T> = emptyRouterInstruction()

    /**
     * Allows for configuration of the <key, fragmentClass> map
     *
     * @see FragmentMap
     */
    @FragmentRouterDsl
    fun fragment(init: FragmentMapBuilder.() -> Unit) {
        this.fragmentMap += FragmentMapBuilder()
            .also(init).build()
    }

    /**
     * Specify a custom [FragmentRouteStorage], it is required to build [FragmentRouter]
     *
     * ## Default:
     * [ParcelableFragmentRouteStorage] will be used as default if the base route type [T] is [Parcelable]
     *
     * @see FragmentRouteStorage
     */
    @FragmentRouterDsl
    fun routeStorage(storage: FragmentRouteStorage<T>) {
        this.fragmentRouteStorage = storage
    }

    /**
     * Specify a custom [RoutingStackStorage], it is required to build [FragmentRouter]
     *
     * ## Default:
     * [ParcelableRoutingStackStorage] will be used as default if the base route type [T] is [Parcelable]
     *
     * @see RoutingStackStorage
     */
    @FragmentRouterDsl
    fun stackStorage(storage: RoutingStackStorage<T>) {
        this.routingStackStorage = storage
    }

    /**
     * Allows for configuration of [FragmentTransition]s which will be used for the router
     *
     * ## Default:
     * No transition will be used.
     *
     * @see FragmentTransition
     */
    @FragmentRouterDsl
    fun transition(init: FragmentTransitionBuilder.() -> Unit) {
        this.fragmentTransition += FragmentTransitionBuilder()
            .also(init).build()
    }

    /**
     * Specify a custom [FragmentStackPatcher]
     *
     * ## Default:
     * [FragmentStackPatcherImpl] will be used as default
     *
     * @see FragmentStackPatcher
     */
    @FragmentRouterDsl
    fun stackPatcher(patcher: FragmentStackPatcher) {
        this.fragmentStackPatcher = patcher
    }

    /**
     * Allows for configuration of the [Lifecycle.Event]s that shall be used to attach/detach the fragment container.
     *
     * ## Default:
     * - attach on [Lifecycle.Event.ON_RESUME]
     * - detach on [Lifecycle.Event.ON_PAUSE]
     */
    @FragmentRouterDsl
    fun containerLifecycle(init: FragmentContainerLifecycleFactoryBuilder.() -> Unit) {
        this.fragmentContainerLifecycleFactory = FragmentContainerLifecycleFactoryBuilder()
            .also(init).build()
    }

    /**
     * An initialization of [RoutingStack] by given [RoutingStackInstruction]
     *
     * ## Default:
     * Do nothing.
     *
     * @see RoutingStackInstruction
     */
    @FragmentRouterDsl
    fun stackInitialization(instruction: RoutingStackInstruction<T>) {
        this.routingStackInstruction += instruction
    }

    fun build(): FragmentRouter<T> =
        FragmentRouter(
            fragmentMap = fragmentMap,
            fragmentRouteStorage = requireFragmentRouteStorage(),
            routingStackStorage = requireSaveRoutingStack(),
            fragmentTransition = fragmentTransition,
            fragmentStackPatcher = fragmentStackPatcher,
            fragmentContainerLifecycleFactory = fragmentContainerLifecycleFactory,
            routingStackInstruction = routingStackInstruction
        )

    private fun requireFragmentRouteStorage(): FragmentRouteStorage<T> =
        fragmentRouteStorage ?: throw RouterException(
            """
                Missing `FragmentRouteStorage`
                Either specify one with

                    FragmentRouter {
                        ...
                        fragmentRouteStorage(MyFragmentRouteStorage())
                    }

                Or let ${type.java.simpleName} implement `Parcelable` to use the default implementation
            """.trimIndent()
        )

    private fun requireSaveRoutingStack(): RoutingStackStorage<T> =
        routingStackStorage ?: throw RouterException(
            """
                Missing `SaveRoutingStack`
                Either specify one with

                    FragmentRouter {
                        ...
                        saveRoutingStack(MySaveRoutingStack())
                    }

                Or let ${type.java.simpleName} implement `Parcelable` to use the default implementation
            """.trimIndent()
        )
}
