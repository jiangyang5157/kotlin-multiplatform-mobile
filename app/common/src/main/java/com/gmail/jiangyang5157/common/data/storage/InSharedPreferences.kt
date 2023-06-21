package com.gmail.jiangyang5157.common.data.storage

import android.content.SharedPreferences
import androidx.core.content.edit
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlin.reflect.KClass
import kotlin.reflect.cast
import java.io.Serializable

class InSharedPreferences(
    private val storage: SharedPreferences,
) : Storage {

    override fun put(key: String, value: Any) {
        storage.edit {
            when (value) {
                is String -> putString(key, value)
                is Boolean -> putString(key, value.toString())
                is Int -> putString(key, value.toString())
                is Long -> putString(key, value.toString())
                is Float -> putString(key, value.toString())
                is Double -> putString(key, value.toString())
                else -> {
                    if (value is Serializable) {
                        putString(key, Gson().toJson(value))
                    } else {
                        putString(key, value.toString())
                    }
                }
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

    override fun get(where: (key: String, value: Any) -> Boolean): Flow<List<Any>> {
        return flow {
            val entriesFilter = storage.all.entries.filter {
                it.value?.let { value -> where(it.key, value) } ?: false // ignore null values
            }

            @Suppress("UNCHECKED_CAST")
            val valueList = entriesFilter.map { it.value } as List<Any>
            emit(valueList)
        }
    }

    override fun <T : Any> get(key: String, clazz: KClass<T>): Flow<T> {
        return flow {
            storage.all[key]?.run {
                try {
                    when (clazz) {
                        String::class -> storage.getString(key, null)
                        Boolean::class -> storage.getString(key, null)?.toBoolean()
                        Int::class -> storage.getString(key, null)?.toInt()
                        Long::class -> storage.getString(key, null)?.toLong()
                        Float::class -> storage.getString(key, null)?.toFloat()
                        Double::class -> storage.getString(key, null)?.toDouble()
                        else -> {
                            storage.getString(key, null)
                                ?.let { Gson().fromJson(it, clazz.javaObjectType) }
                        }
                    }
                } catch (e: NumberFormatException) {
                    storage.getString(key, null)
                } catch (e: JsonSyntaxException) {
                    storage.getString(key, null)
                }?.run {
                    emit(clazz.cast(this))
                }
            }
        }
    }

    override fun entities(): Flow<Map<String, Any>> {
        @Suppress("UNCHECKED_CAST")
        return flow {
            val valueMap = storage.all
                .filterNot { it.value != null } // ignore null values
                .toMap() as Map<String, Any>
            emit(valueMap)
        }
    }
}

