package com.gmail.jiangyang5157.router.fragment.setup

import androidx.lifecycle.Lifecycle
import com.gmail.jiangyang5157.router.fragment.FragmentRouterDsl

@FragmentRouterDsl
class FragmentContainerLifecycleFactoryBuilder {

    @FragmentRouterDsl
    var attachOn: Lifecycle.Event = Lifecycle.Event.ON_RESUME

    @FragmentRouterDsl
    var detachOn: Lifecycle.Event = Lifecycle.Event.ON_PAUSE

    internal fun build(): FragmentContainerLifecycle.Factory =
        FragmentContainerLifecycleImpl.Factory(
            attachEvent = attachOn,
            detachEvent = detachOn
        )
}
