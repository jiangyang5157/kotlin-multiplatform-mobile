package com.gmail.jiangyang5157.router.fragment.setup

import androidx.fragment.app.Fragment

internal class AsFragmentRouterSetup(fragment: Fragment) :
    FragmentRouterSetup,
    FragmentRouterHost by AsFragmentRouterHost(fragment),
    InvokeRouterOnSaveState by InvokeRouterOnFragmentSaveState(fragment)
