package com.gmail.jiangyang5157.kit.utils

import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

/**
 * Created by Yang Jiang on June 27, 2017
 */
class UrlUtilsTest {

    @Test
    fun test_contains() {
        assertTrue(UrlUtils.contains("hhttp://developer.android.com/index.html"))
        assertTrue(UrlUtils.contains("http://developer.android.com/index.html"))
        assertTrue(UrlUtils.contains("https://developer.android.com/index.html"))
        assertTrue(UrlUtils.contains("asd//####https://developer.android.com/index.html"))
        assertTrue(UrlUtils.contains("https://de-ve-lo..per.android.com/index.html"))
        assertTrue(UrlUtils.contains("https://developer.android.com/in-dex.html"))
        assertTrue(UrlUtils.contains("https://developer.android.com/in dex.html"))
        assertTrue(UrlUtils.contains("https://developer.android.com/in#dex.html"))
        assertTrue(UrlUtils.contains("(https://developer.android.com/index.html)"))
        assertTrue(UrlUtils.contains("https://developer.an::droid.com/index.html)"))
        assertTrue(UrlUtils.contains("https://dev#eloper.an::droid.com/index.html)"))
        assertTrue(UrlUtils.contains("http://developer.android.com/index.html/"))
        assertTrue(UrlUtils.contains("http://developer.android.com/index.html///"))
        assertTrue(UrlUtils.contains("http://developer.android.com/index.html///asdasd-asd.asd--asd//"))
        assertTrue(UrlUtils.contains("Indonesia<br>BaliIsForMe.com (http://www.BaliIsForMe.com)"))

        assertFalse(UrlUtils.contains(""))
        assertFalse(UrlUtils.contains("https://"))
        assertFalse(UrlUtils.contains("developer.android.com/index.html"))
        assertFalse(UrlUtils.contains("https:// developer.android.com/index.html)"))
    }

    @Test
    fun test_extractFirst() {
        assertEquals(
            UrlUtils.extractFirst("hhttp://developer.android.com/index.html"),
            "http://developer.android.com/index.html"
        )
        assertEquals(
            UrlUtils.extractFirst("http://developer.android.com/index.html"),
            "http://developer.android.com/index.html"
        )
        assertEquals(
            UrlUtils.extractFirst("https://developer.android.com/index.html"),
            "https://developer.android.com/index.html"
        )
        assertEquals(
            UrlUtils.extractFirst("asd//####https://developer.android.com/index.html"),
            "https://developer.android.com/index.html"
        )
        assertEquals(
            UrlUtils.extractFirst("https://de-ve-lo..per.android.com/index.html"),
            "https://de-ve-lo..per.android.com/index.html"
        )
        assertEquals(
            UrlUtils.extractFirst("https://developer.android.com/in-dex.html"),
            "https://developer.android.com/in-dex.html"
        )
        assertEquals(
            UrlUtils.extractFirst("https://developer.android.com/in dex.html"),
            "https://developer.android.com/in"
        )
        assertEquals(
            UrlUtils.extractFirst("https://developer.android.com/in#dex.html"),
            "https://developer.android.com/in#dex.html"
        )
        assertEquals(
            UrlUtils.extractFirst("(https://developer.android.com/index.html)"),
            "https://developer.android.com/index.html"
        )
        assertEquals(
            UrlUtils.extractFirst("https://developer.an::droid.com/index.html)"),
            "https://developer.an::droid.com/index.html"
        )
        assertEquals(
            UrlUtils.extractFirst("https://dev#eloper.an::droid.com/index.html)"),
            "https://dev"
        )
        assertEquals(
            UrlUtils.extractFirst("http://developer.android.com/index.html/"),
            "http://developer.android.com/index.html"
        )
        assertEquals(
            UrlUtils.extractFirst("http://developer.android.com/index.html///"),
            "http://developer.android.com/index.html"
        )
        assertEquals(
            UrlUtils.extractFirst("http://developer.android.com/index.html///asdasd-asd.asd--asd//"),
            "http://developer.android.com/index.html"
        )
        assertEquals(
            UrlUtils.extractFirst("Indonesia<br>BaliIsForMe.com (http://www.BaliIsForMe.com)"),
            "http://www.BaliIsForMe.com"
        )

        assertEquals(UrlUtils.extractFirst(""), null)
        assertEquals(UrlUtils.extractFirst("https://"), null)
        assertEquals(UrlUtils.extractFirst("developer.android.com/index.html"), null)
        assertEquals(UrlUtils.extractFirst("https:// developer.android.com/index.html)"), null)
    }
}
