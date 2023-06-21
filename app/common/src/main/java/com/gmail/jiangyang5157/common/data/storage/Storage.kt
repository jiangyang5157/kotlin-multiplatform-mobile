package com.gmail.jiangyang5157.common.data.storage

import kotlinx.coroutines.flow.Flow
import kotlin.reflect.KClass

interface Storage {

    fun put(key: String, value: Any)

    fun remove(key: String)

    fun remove(where: (key: String, value: Any) -> Boolean)

    fun clear()

    fun <T : Any> get(key: String, clazz: KClass<T>): Flow<T>

    fun get(where: (key: String, value: Any) -> Boolean): Flow<List<Any>>

    fun entities(): Flow<Map<String, Any>>
}

inline fun <reified T : Any> Storage.get(key: String): Flow<T> = get(key, T::class)