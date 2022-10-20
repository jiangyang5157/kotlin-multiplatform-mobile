package com.gmail.jiangyang5157.router.fragment.setup

import android.app.Activity
import android.app.Application
import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import com.gmail.jiangyang5157.router.core.requireMainThread
import java.lang.ref.WeakReference

internal class InvokeRouterOnActivitySaveState(activity: FragmentActivity) :
    InvokeRouterOnSaveState {

    private val activityReference = WeakReference(activity)

    private val application = activity.application

    private val onSaveInstanceStateCallbacks = mutableListOf<OnSaveStateCallback>()

    private val activityLifecycleCallbacks = object : Application.ActivityLifecycleCallbacks {

        override fun onActivityPaused(activity: Activity) = Unit
        override fun onActivityResumed(activity: Activity) = Unit
        override fun onActivityStarted(activity: Activity) = Unit
        override fun onActivityStopped(activity: Activity) = Unit
        override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) = Unit

        override fun onActivityDestroyed(activity: Activity) {
            if (activity == activityReference.get() || activityReference.get() == null) {
                application.unregisterActivityLifecycleCallbacks(this)
            }
        }

        override fun onActivitySaveInstanceState(
            activity: Activity,
            outState: Bundle
        ) {
            if (activity == activityReference.get()) {
                onSaveInstanceStateCallbacks.forEach { callback ->
                    callback.invoke(outState)
                }
            }
        }
    }

    override fun invokeOnSaveState(callback: OnSaveStateCallback) {
        requireMainThread()
        onSaveInstanceStateCallbacks += callback
    }

    init {
        application.registerActivityLifecycleCallbacks(activityLifecycleCallbacks)
    }
}
