package com.gmail.jiangyang5157.kit.utils

import org.junit.Test
import kotlin.test.assertEquals

/**
 * Created by Yang Jiang on June 28, 2017
 */
class IoUtilsTest {

    @Test
    fun test_read_all() {
        val endWithLineSeparator = "1234567890\nasdasd\n\n\t\tHello, World!\n\n"
        assertEquals(endWithLineSeparator, IoUtils.read(endWithLineSeparator.byteInputStream()))
    }

    @Test
    fun test_read_lines() {
        val content = "1234567890\nasdasd\n\n\t\tHello, World!\n\n"
        val body = StringBuilder()
        IoUtils.read(
            content.byteInputStream(),
            object : IoUtils.OnReadingListener {
                override fun onReadLine(line: CharSequence?): Boolean {
                    return if (line == null) {
                        false
                    } else {
                        body.append(line).append(System.getProperty("line.separator"))
                        true
                    }
                }
            }
        )
        assertEquals(content, body.toString())
    }
}
