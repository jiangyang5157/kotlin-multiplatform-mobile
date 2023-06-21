package com.gmail.jiangyang5157.common.data.storage

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlin.reflect.KClass
import kotlin.reflect.cast

class InMemory : Storage {

    private val storage = HashMap<String, Any>()

    override fun put(key: String, value: Any) {
        storage[key] = value
    }

    override fun remove(key: String) {
        storage.remove(key)
    }

    override fun remove(where: (key: String, value: Any) -> Boolean) {
        storage.entries.removeIf { where(it.key, it.value) }
    }

    override fun clear() {
        storage.clear()
    }

    override fun get(where: (key: String, value: Any) -> Boolean): Flow<List<Any>> {
        return flow {
            val entriesFilter = storage.entries.filter { where(it.key, it.value) }
            val valueList = entriesFilter.map { it.value }
            emit(valueList)
        }
    }

    override fun <T : Any> get(key: String, clazz: KClass<T>): Flow<T> {
        return flow {
            storage[key]?.run { emit(clazz.cast(this)) }
        }
    }

    override fun entities(): Flow<Map<String, Any>> {
        return flow {
            val valueMap = storage.toMap()
            emit(valueMap)
        }
    }
}