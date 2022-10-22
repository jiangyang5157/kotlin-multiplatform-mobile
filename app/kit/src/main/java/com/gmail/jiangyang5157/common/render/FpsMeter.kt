package com.gmail.jiangyang5157.common.render

import com.gmail.jiangyang5157.common.utils.TimeUtils
import kotlin.math.roundToInt

/**
 * Created by Yang Jiang on June 27, 2017
 */
class FpsMeter(fps: Int = 0) {

    // nano per frame
    private val npf: Long = if (fps <= 0) 0 else TimeUtils.NANO_IN_SECOND / fps

    private var lastTime: Long = 0

    private var npfRealTime: Long = 0

    val fpsRealTime: Int
        get() = (TimeUtils.NANO_IN_SECOND / npfRealTime.toDouble()).roundToInt()

    fun accept(): Boolean {
        var ret = false

        val thisTime = System.nanoTime()
        val elapsedTime = thisTime - lastTime
        if (npf <= 0 || elapsedTime >= npf) {
            lastTime = thisTime
            npfRealTime = elapsedTime
            ret = true
        }

        return ret
    }
}
