package com.gmail.jiangyang5157.kit.render

import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

/**
 * Created by Yang Jiang on June 28, 2017
 */
class FpsMeterTest {

    @Test
    fun test_accept() {
        val fps = 1
        val frameRate = FpsMeter(fps)
        assertTrue(frameRate.accept())
        assertFalse(frameRate.accept())
        Thread.sleep(1000)
        assertTrue(frameRate.accept())
        assertFalse(frameRate.accept())
        Thread.sleep(500)
        assertFalse(frameRate.accept())
        assertFalse(frameRate.accept())
        Thread.sleep(500)
        assertTrue(frameRate.accept())
        assertFalse(frameRate.accept())
        println(frameRate.fpsRealTime)
    }

    @Test
    fun test_accept_invalid_fps() {
        var frameRate = FpsMeter(0)
        assertTrue(frameRate.accept())
        assertTrue(frameRate.accept())
        assertTrue(frameRate.accept())
        assertTrue(frameRate.accept())
        println(frameRate.fpsRealTime)

        frameRate = FpsMeter(-1)
        assertTrue(frameRate.accept())
        assertTrue(frameRate.accept())
        assertTrue(frameRate.accept())
        assertTrue(frameRate.accept())
        println(frameRate.fpsRealTime)
    }

    @Test
    fun test_fpsRealTime() {
        val fps = 2
        val frameRate = FpsMeter(fps)
        assertTrue(frameRate.accept())
        Thread.sleep(500)
        assertTrue(frameRate.accept())
        assertEquals(fps, frameRate.fpsRealTime)
        Thread.sleep(200)
        assertFalse(frameRate.accept())
        assertEquals(fps, frameRate.fpsRealTime)
        Thread.sleep(300)
        assertTrue(frameRate.accept())
        assertEquals(fps, frameRate.fpsRealTime)
    }
}
