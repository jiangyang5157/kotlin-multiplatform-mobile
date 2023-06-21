package com.gmail.jiangyang5157.common.data.storage

import android.content.SharedPreferences
import androidx.core.content.edit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlin.reflect.KClass
import kotlin.reflect.cast

class InSharedPreferences(
    private val storage: SharedPreferences,
) : Storage {

    override fun put(key: String, value: Any) {
        storage.edit {
            when (value) {
                is Serializable -> putString(key, Json.encodeToString(value))
                is String -> putString(key, value)
                is Boolean -> putString(key, value.toString())
                is Int -> putString(key, value.toString())
                is Long -> putString(key, value.toString())
                is Float -> putString(key, value.toString())
                else -> throw UnsupportedOperationException()
            }
        }
    }

    override fun remove(key: String) {
        storage.edit { remove(key) }
    }

    override fun remove(where: (key: String, value: Any) -> Boolean) {
        storage.edit { remove { key, value -> where(key, value) } }
    }

    override fun clear() {
        storage.edit { clear() }
    }

    @Suppress("UNCHECKED_CAST")
    override fun get(where: (key: String, value: Any) -> Boolean): Flow<List<Any>> {
        return flow {
            val entriesFilter = storage.all.entries.filter {
                it.value?.let { value -> where(it.key, value) } ?: false // ignore null values
            }
            val valueList = entriesFilter.map { it.value } as List<Any>
            emit(valueList)
        }
    }

    override fun <T : Any> get(key: String, clazz: KClass<T>): Flow<T> {
        return flow {
            storage.all[key]?.run {
                when (clazz) {
                    Serializable::class -> storage.getString(key, null)
                        ?.let { Json.decodeFromString(it) }
                    String::class -> storage.getString(key, null)
                    Boolean::class -> storage.getString(key, null)?.toBoolean()
                    Int::class -> storage.getString(key, null)?.toInt()
                    Long::class -> storage.getString(key, null)?.toLong()
                    Float::class -> storage.getString(key, null)?.toFloat()
                    else -> throw UnsupportedOperationException()
                }?.run {
                    emit(clazz.cast(this))
                }
            }
        }
    }

    @Suppress("UNCHECKED_CAST")
    override fun entities(): Flow<Map<String, Any>> {
        return flow {
            val valueMap = storage.all
                .filterNot { it.value != null } // ignore null values
                .toMap() as Map<String, Any>
            emit(valueMap)
        }
    }
}

