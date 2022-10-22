package com.gmail.jiangyang5157.kit.render

/**
 * Created by Yang Jiang on July 16, 2017
 */
class FrameThread(fps: Int, private val callback: Callback) : Thread() {

    companion object {
        const val STATUS_RUNNING = 1 shl 0
        const val STATUS_PAUSED = 1 shl 1
        const val STATUS_FOCUSED = 1 shl 2
        const val STATUS_REFRESH = 1 shl 3
    }

    interface Callback {

        fun onFrame()
    }

    private val fpsMeter = FpsMeter(fps)

    private val lock = java.lang.Object()

    private var status = 0

    fun getStatus() = status

    private fun on(status: Int) {
        this.status = this.status or status
    }

    private fun off(status: Int) {
        this.status = this.status and status.inv()
    }

    fun checkStatus(status: Int): Boolean {
        return this.status and status == status
    }

    override fun run() {
        while (true) {
            while (checkStatus(STATUS_RUNNING) && (checkStatus(STATUS_PAUSED) || !checkStatus(STATUS_FOCUSED))) {
                if (checkStatus(STATUS_REFRESH)) {
                    off(STATUS_REFRESH)
                    break
                }

                synchronized(lock) {
                    lock.wait()
                }
            }

            if (!checkStatus(STATUS_RUNNING)) {
                break
            }

            if (!fpsMeter.accept()) {
                continue
            }

            synchronized(lock) {
                callback.onFrame()
            }
        }
    }

    fun onStart() {
        synchronized(lock) {
            on(STATUS_RUNNING)
            start()
        }
    }

    fun onStop() {
        synchronized(lock) {
            off(STATUS_RUNNING)
            lock.notify()
        }

        var retry = true
        while (retry) {
            join()
            retry = false
        }
    }

    fun onPause() {
        synchronized(lock) {
            on(STATUS_PAUSED)
        }
    }

    fun onResume() {
        synchronized(lock) {
            off(STATUS_PAUSED)
            lock.notify()
        }
    }

    fun onRefresh() {
        synchronized(lock) {
            on(STATUS_REFRESH)
            lock.notify()
        }
    }

    fun onFocused() {
        synchronized(lock) {
            on(STATUS_FOCUSED)
            lock.notify()
        }
    }

    fun onUnfocused() {
        synchronized(lock) {
            off(STATUS_FOCUSED)
        }
    }
}
