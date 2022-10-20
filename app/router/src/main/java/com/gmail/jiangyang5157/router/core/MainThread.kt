package com.gmail.jiangyang5157.router.core

import android.os.Handler
import android.os.Looper
import com.gmail.jiangyang5157.router.error.WrongThreadException

/**
 * Execute the [action] on mainThread, or sent the [action] to [mainThreadHandler] if current thread is not main thread.
 */
internal inline fun <T> mainThread(crossinline action: () -> T) {
    if (isMainThread) {
        action()
    } else {
        mainThreadHandler.post { action() }
    }
}

internal val isMainThread: Boolean
    get() = Looper.getMainLooper() == Looper.myLooper()

internal val mainThreadHandler = Handler(Looper.getMainLooper())

/**
 * Throw [WrongThreadException] when current thread is not main thread
 */
internal fun requireMainThread() {
    if (!isMainThread) {
        throw WrongThreadException(
            "Required main thread. Found: ${Thread.currentThread().id}: ${Thread.currentThread().name}"
        )
    }
}
