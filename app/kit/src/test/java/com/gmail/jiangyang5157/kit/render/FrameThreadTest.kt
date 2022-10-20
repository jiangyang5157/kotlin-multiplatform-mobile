package com.gmail.jiangyang5157.kit.render

import org.junit.Assert
import org.junit.Test
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import kotlin.test.assertFalse
import kotlin.test.assertNotEquals
import kotlin.test.assertTrue

/**
 * Created by Yang Jiang on August 06, 2017
 */
class FrameThreadTest {

    @Test
    fun test_start_focused_resume() {
        var count = 0
        val signal = CountDownLatch(2)
        val thread = FrameThread(
            60,
            object : FrameThread.Callback {
                override fun onFrame() {
                    count++
                    signal.countDown()
                }
            }
        )

        thread.onStart()
        thread.onFocused()
        thread.onResume()

        signal.await(2, TimeUnit.SECONDS)
        Assert.assertTrue("count=$count", count > 0)
    }

    @Test
    fun test_start_unfocused_resume() {
        var count = 0
        val signal = CountDownLatch(2)
        val thread = FrameThread(
            60,
            object : FrameThread.Callback {
                override fun onFrame() {
                    count++
                    signal.countDown()
                }
            }
        )

        thread.onStart()
        thread.onUnfocused()
        thread.onResume()

        signal.await(2, TimeUnit.SECONDS)
        Assert.assertTrue("count=$count", count == 0)
    }

//    /**
//     * #### TODO: Unstable
//     */
//    @Test
//    fun test_start_focused_refresh() {
//        var count = 0
//        val signal = CountDownLatch(2)
//        val thread = FrameThread(60, object : FrameThread.Callback {
//            override fun onFrame() {
//                count++
//                signal.countDown()
//            }
//        })
//
//        thread.onStart()
//        thread.onFocused()
//        thread.onRefresh()
//
//        signal.await(2, TimeUnit.SECONDS)
//        Assert.assertTrue("count=$count", count > 0)
//    }

//    /**
//     * #### TODO: Unstable
//     */
//    @Test
//    fun test_start_unfocused_refresh() {
//        var count = 0
//        val signal = CountDownLatch(2)
//        val thread = FrameThread(60, object : FrameThread.Callback {
//            override fun onFrame() {
//                count++
//                signal.countDown()
//            }
//        })
//
//        thread.onStart()
//        thread.onUnfocused()
//        thread.onRefresh()
//
//        signal.await(2, TimeUnit.SECONDS)
//        Assert.assertTrue("count=$count", count > 0)
//    }

    @Test
    fun test_status_start() {
        var count = 0
        val signal = CountDownLatch(2)
        val thread = FrameThread(
            60,
            object : FrameThread.Callback {
                override fun onFrame() {
                    count++
                    signal.countDown()
                }
            }
        )
        assertNotEquals(FrameThread.STATUS_RUNNING, thread.getStatus())
        assertNotEquals(FrameThread.STATUS_PAUSED, thread.getStatus())
        assertNotEquals(FrameThread.STATUS_FOCUSED, thread.getStatus())
        assertNotEquals(FrameThread.STATUS_REFRESH, thread.getStatus())
        assertFalse(thread.checkStatus(FrameThread.STATUS_RUNNING))
        assertFalse(thread.checkStatus(FrameThread.STATUS_PAUSED))
        assertFalse(thread.checkStatus(FrameThread.STATUS_FOCUSED))
        assertFalse(thread.checkStatus(FrameThread.STATUS_REFRESH))

        thread.onStart()
        assertTrue(thread.checkStatus(FrameThread.STATUS_RUNNING))

        signal.await(2, TimeUnit.SECONDS)
        Assert.assertTrue("count=$count", count == 0)
    }
}
