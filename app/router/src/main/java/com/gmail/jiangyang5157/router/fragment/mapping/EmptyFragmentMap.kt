package com.gmail.jiangyang5157.router.fragment.mapping

import androidx.fragment.app.Fragment
import com.gmail.jiangyang5157.common.data.Key
import kotlin.reflect.KClass

class EmptyFragmentMap : FragmentMap {

    override fun get(key: Key): KClass<out Fragment>? = null
}
