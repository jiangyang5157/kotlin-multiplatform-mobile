package com.gmail.jiangyang5157.router.fragment.setup

import android.os.Bundle

typealias OnSaveStateCallback = (outState: Bundle) -> Unit

internal interface InvokeRouterOnSaveState {

    fun invokeOnSaveState(callback: OnSaveStateCallback)
}
