package com.gmail.jiangyang5157.router.fragment.setup

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import com.gmail.jiangyang5157.router.core.requireMainThread
import com.gmail.jiangyang5157.router.fragment.FragmentRouter

internal class FragmentContainerLifecycleImpl(
    private val router: FragmentRouter<*>,
    private val attachEvent: Lifecycle.Event,
    private val detachEvent: Lifecycle.Event
) : FragmentContainerLifecycle {

    data class Factory(
        val attachEvent: Lifecycle.Event,
        val detachEvent: Lifecycle.Event
    ) : FragmentContainerLifecycle.Factory {

        override fun invoke(router: FragmentRouter<*>): FragmentContainerLifecycle =
            FragmentContainerLifecycleImpl(
                router = router,
                attachEvent = attachEvent,
                detachEvent = detachEvent
            )
    }

    private var observer: LifecycleEventObserver? = null

    override fun setup(lifecycle: Lifecycle, container: FragmentContainer) {
        requireMainThread()
        val observer = this.observer
        if (observer != null) {
            lifecycle.removeObserver(observer)
        }

        val newObserver = Observer(container)
        this.observer = newObserver
        lifecycle.addObserver(newObserver)
    }

    inner class Observer(private val container: FragmentContainer) : LifecycleEventObserver {

        override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
            if (observer != this) {
                source.lifecycle.removeObserver(this)
                return
            }

            if (event == attachEvent) {
                router.attachContainer(container)
            }

            if (event == detachEvent) {
                router.detachContainer()
            }
        }
    }
}
