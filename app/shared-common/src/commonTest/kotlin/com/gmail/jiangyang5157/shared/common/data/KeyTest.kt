package com.gmail.jiangyang5157.shared.common.data

import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class KeyTest {

    @Test
    fun equals_nonEqualKey() {
        val key1 = Key()
        val key2 = Key()
        assertFalse { key1 == key2 }
    }

    @Test
    fun equals_equalKey() {
        val key1 = Key("1")
        val key2 = Key("1")
        assertTrue { key1 == key2 }
    }

    @Test
    fun randomKeyValue_notSame() {
        val randomKeyValue1 = Key.randomKeyValue()
        val randomKeyValue2 = Key.randomKeyValue()
        assertFalse { randomKeyValue1 == randomKeyValue2 }
    }
}
