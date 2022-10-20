package com.gmail.jiangyang5157.kit.utils

import org.junit.Test
import java.util.regex.Pattern
import kotlin.test.assertFalse
import kotlin.test.assertTrue

/**
 * Created by Yang Jiang on January 26, 2019
 */
class RegexUtilsTest {

    @Test
    fun test_amount() {
        val valid = listOf(
            "123456789",
            "-123456789",
            "1234567890.1234567890",
            "-1234567890.1234567890",
            "123456789.",
            "-123456789.",
            ".123456789",
            "-.123456789",
            "0.123456789",
            "-0.123456789",
            "0",
            "-0",
            ".0",
            "-.0",
            "+1"
        )
        val invalid = listOf(
            ".",
            "-.",
            "-",
            "+",
            "-.",
            ".",
            "..",
            "1.1.1",
            "..1"
        )

        val pattern = Pattern.compile(RegexUtils.AMOUNT)
        valid.forEach {
            assertTrue { pattern.matcher(it).matches() }
        }
        invalid.forEach {
            assertFalse { pattern.matcher(it).matches() }
        }
    }

    @Test
    fun test_url() {
        val valid = listOf(
            "http://developer.android.com/index.html"
        )
        val invalid = listOf(
            "hhttp://developer.android.com/index.html",
            "developer.android.com/index.html",
            "https://",
            "",
            "https:// developer.android.com/index.html)"
        )

        val pattern = Pattern.compile(RegexUtils.URL)
        valid.forEach {
            assertTrue { pattern.matcher(it).matches() }
        }
        invalid.forEach {
            assertFalse { pattern.matcher(it).matches() }
        }
    }
}
