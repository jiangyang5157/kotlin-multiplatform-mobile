package com.gmail.jiangyang5157.router.fragment

import androidx.fragment.app.Fragment
import com.gmail.jiangyang5157.router.core.Route
import kotlin.reflect.KClass

interface FragmentRoute : Route {
    val fragment: KClass<out Fragment>
}
