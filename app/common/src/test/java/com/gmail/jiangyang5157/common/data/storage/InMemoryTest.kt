package com.gmail.jiangyang5157.common.data.storage

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert
import org.junit.Assert.fail
import org.junit.Before
import org.junit.Test

class InMemoryTest {

    private lateinit var storage: InMemory

    @Before
    fun before() {
        storage = InMemory()
    }

    @After
    fun after() {
        storage.clear()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun storage_start_with_0_size_for_each_test() = runTest {
        Assert.assertEquals(0, storage.entities().first().size)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun entities_returns_map_of_key_values_() = runTest {
        storage.put("string", "s1")
        storage.put("boolean", true)
        storage.put("int", 1)
        storage.put("long", 1L)
        storage.put("float", 1f)
        storage.put("double", 1.0)

        Assert.assertEquals(6, storage.entities().first().size)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun get_will_not_emit_for_non_existent_key() = runTest {
        Assert.assertEquals(0, storage.get<Any>("unknown").count())
        Assert.assertEquals(null, storage.get<Any>("unknown").firstOrNull())
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun put_would_override_previous_value() = runTest {
        storage.put("int", 1)
        storage.put("int", 2)
        storage.put("string", "s1")
        storage.put("string", "s2")

        Assert.assertEquals(1, storage.get<Int>("int").count())
        Assert.assertEquals(1, storage.get<String>("string").count())
        Assert.assertEquals(2, storage.get<Int>("int").first())
        Assert.assertEquals("s2", storage.get<String>("string").first())
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun get_returns_reified_value() = runTest {
        storage.put("string", "s1")
        storage.put("boolean", true)
        storage.put("int", 1)
        storage.put("long", 1L)
        storage.put("float", 1f)
        storage.put("double", 1.0)

        Assert.assertEquals("s1", storage.get<String>("string").first())
        Assert.assertEquals(true, storage.get<Boolean>("boolean").first())
        Assert.assertEquals(1, storage.get<Int>("int").first())
        Assert.assertEquals(1L, storage.get<Long>("long").first())
        Assert.assertEquals(1f, storage.get<Float>("float").first())
        Assert.assertEquals(1.0, storage.get<Double>("double").first(), 0.01)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun get_returns_data_class() = runTest {
        storage.put("Data", Data("class1"))

        Assert.assertEquals(Data("class1"), storage.get<Data>("Data").first())
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun get_cast_to_different_type_would_throw_ClassCastException() = runTest {
        storage.put("string", "s1")

        try {
            storage.get<Int>("string").first()
            fail()
        } catch (e: Exception) {
            if (e !is ClassCastException) {
                fail()
            }
        }
    }

    data class Data(
        val name: String = "data class",
    )
}
