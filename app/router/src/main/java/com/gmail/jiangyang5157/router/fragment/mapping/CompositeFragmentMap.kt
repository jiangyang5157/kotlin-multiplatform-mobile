package com.gmail.jiangyang5157.router.fragment.mapping

import androidx.fragment.app.Fragment
import com.gmail.jiangyang5157.kit.data.Key
import kotlin.reflect.KClass

internal class CompositeFragmentMap(
    private val first: FragmentMap,
    private val second: FragmentMap,
) : FragmentMap {

    override fun get(key: Key): KClass<out Fragment>? = first[key] ?: second[key]
}

operator fun FragmentMap.plus(other: FragmentMap): FragmentMap =
    CompositeFragmentMap(
        this,
        other
    )
