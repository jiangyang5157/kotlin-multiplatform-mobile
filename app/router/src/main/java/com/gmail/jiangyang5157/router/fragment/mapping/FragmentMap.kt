package com.gmail.jiangyang5157.router.fragment.mapping

import androidx.fragment.app.Fragment
import com.gmail.jiangyang5157.kit.data.Key
import com.gmail.jiangyang5157.router.core.KeyRoute
import com.gmail.jiangyang5157.router.fragment.FragmentElementImpl
import com.gmail.jiangyang5157.router.fragment.FragmentRoute
import kotlin.reflect.KClass

/**
 * # FragmentMap
 * Definition of which fragment should be displayed for a certain route
 *
 * In order to find fragment mapping, the route should be either [FragmentRoute] or [KeyRoute].
 * @see FragmentElementImpl
 */
interface FragmentMap {

    /**
     * @return
     * - The class of the fragment that should be displayed for the [key]
     * - `null` if this map does not contain any information about the given [key]
     */
    operator fun get(key: Key): KClass<out Fragment>?
}
