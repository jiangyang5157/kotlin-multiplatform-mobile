package com.gmail.jiangyang5157.router.fragment.mapping

import androidx.fragment.app.Fragment
import com.gmail.jiangyang5157.kit.data.Key
import kotlin.reflect.KClass

@PublishedApi
internal class LambdaFragmentMap<T : Key, R : T>(
    private val key: T,
    private val lambda: R.() -> KClass<out Fragment>?
) : FragmentMap {

    override fun get(key: Key): KClass<out Fragment>? {
        return if (key == this.key) {
            @Suppress("UNCHECKED_CAST")
            lambda(key as R)
        } else {
            null
        }
    }
}
