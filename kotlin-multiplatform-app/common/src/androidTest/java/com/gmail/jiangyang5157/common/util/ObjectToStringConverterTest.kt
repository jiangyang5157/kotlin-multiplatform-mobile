package com.gmail.jiangyang5157.common.util

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import java.io.Serializable

/**
 * Created by Yang Jiang on July 01, 2017
 */
class ObjectToStringConverterTest {

    @Test
    fun objectToStringToObject() {
        val testClassOriginal = TestClass(1, "a")
        val testClassString = ObjectToStringConverter.object2String(testClassOriginal)
        println("testClassString: $testClassString")
        val testClassResult = ObjectToStringConverter.string2Object(testClassString) as TestClass
        assertEquals(testClassOriginal, testClassResult)
        assertTrue(testClassOriginal == testClassResult)
        assertTrue(testClassOriginal !== testClassResult)
    }
}

class TestClass(var i: Int, var s: String) : Serializable {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other?.javaClass != javaClass) return false
        other as TestClass
        if (i != other.i) return false
        if (s != other.s) return false
        return true
    }

    override fun hashCode(): Int {
        var result = i
        result = 31 * result + s.hashCode()
        return result
    }
}
