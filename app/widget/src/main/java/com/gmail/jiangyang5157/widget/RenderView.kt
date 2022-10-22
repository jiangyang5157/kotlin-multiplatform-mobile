package com.gmail.jiangyang5157.widget

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.util.Log
import android.view.SurfaceHolder
import android.view.SurfaceView
import com.gmail.jiangyang5157.kit.render.FrameThread
import com.gmail.jiangyang5157.kit.render.Renderable

/**
 * Created by Yang Jiang on July 18, 2017
 */
open class RenderView : SurfaceView, SurfaceHolder.Callback, FrameThread.Callback {

    companion object {
        const val TAG = "RenderView"
        const val FPS = 60
    }

    private var frameThread: FrameThread? = null

    private var renderable: Renderable<Canvas>? = null

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init()
    }

    private fun init() {
        holder.addCallback(this)
    }

    override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {
        Log.d(TAG, "surfaceChanged $width x $height")
        frameThread?.onRefresh()
    }

    override fun surfaceDestroyed(holder: SurfaceHolder) {
        Log.d(TAG, "surfaceDestroyed")
        frameThread?.onUnfocused()
        frameThread?.onStop()
    }

    override fun surfaceCreated(holder: SurfaceHolder) {
        Log.d(TAG, "surfaceCreated")
        if (frameThread == null || frameThread?.state === Thread.State.TERMINATED) {
            frameThread = FrameThread(FPS, this)
        }
        frameThread?.onStart()
        frameThread?.onFocused()
        frameThread?.onPause()
    }

    fun resumeRender() {
        frameThread?.onResume()
    }

    fun pauseRender() {
        frameThread?.onPause()
    }

    fun refreshRender() {
        frameThread?.onRefresh()
    }

    final override fun onFrame() {
        if (holder.surface.isValid) {
            val canvas = holder.lockCanvas(null)
            renderable?.onRender(canvas)
            holder.unlockCanvasAndPost(canvas)
        }
    }

    fun setRenderable(renderable: Renderable<Canvas>?) {
        this.renderable = renderable
    }

    fun getRenderable(): Renderable<Canvas>? = renderable
}
