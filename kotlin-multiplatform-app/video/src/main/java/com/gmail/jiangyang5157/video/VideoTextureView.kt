package com.gmail.jiangyang5157.video

import android.content.Context
import android.util.AttributeSet
import android.view.TextureView

class VideoTextureView : TextureView {

    private var videoWidth: Int = 0
    private var videoHeight: Int = 0

    @JvmOverloads
    constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0,
        defStyleRes: Int = 0
    ) : super(context, attrs, defStyleAttr, defStyleRes)

    fun setVideoSize(videoWidth: Int, videoHeight: Int) {
        if (this.videoWidth != videoWidth || this.videoHeight != videoHeight) {
            this.videoWidth = videoWidth
            this.videoHeight = videoHeight
            requestLayout()
        }
    }

    override fun setRotation(rotation: Float) {
        if (rotation != getRotation()) {
            super.setRotation(rotation)
            requestLayout()
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
//        Log.d("VideoTextureView", "onMeasure(${MeasureSpec.toString(widthMeasureSpec)}, ${MeasureSpec.toString(heightMeasureSpec)})")

        val rotated = rotation.let { it == 90f || it == 270f }
        val videoWidthMeasureSpec = if (rotated) heightMeasureSpec else widthMeasureSpec
        val videoHeightMeasureSpec = if (rotated) widthMeasureSpec else heightMeasureSpec

        /**
         * Below from
         * https://android.googlesource.com/platform/frameworks/base/+/master/core/java/android/widget/VideoView.java#onMeasure
         */
        var width = getDefaultSize(videoWidth, videoWidthMeasureSpec)
        var height = getDefaultSize(videoHeight, videoHeightMeasureSpec)
        if (videoWidth > 0 && videoHeight > 0) {
            val widthSpecMode = MeasureSpec.getMode(videoWidthMeasureSpec)
            val widthSpecSize = MeasureSpec.getSize(videoWidthMeasureSpec)
            val heightSpecMode = MeasureSpec.getMode(videoHeightMeasureSpec)
            val heightSpecSize = MeasureSpec.getSize(videoHeightMeasureSpec)
            if (widthSpecMode == MeasureSpec.EXACTLY && heightSpecMode == MeasureSpec.EXACTLY) {
                // the size is fixed
                width = widthSpecSize
                height = heightSpecSize
                // for compatibility, we adjust size based on aspect ratio
                if (videoWidth * height < width * videoHeight) {
                    //Log.i("@@@", "image too wide, correcting");
                    width = height * videoWidth / videoHeight
                } else if (videoWidth * height > width * videoHeight) {
                    //Log.i("@@@", "image too tall, correcting");
                    height = width * videoHeight / videoWidth
                }
            } else if (widthSpecMode == MeasureSpec.EXACTLY) {
                // only the width is fixed, adjust the height to match aspect ratio if possible
                width = widthSpecSize
                height = width * videoHeight / videoWidth
                if (heightSpecMode == MeasureSpec.AT_MOST && height > heightSpecSize) {
                    // couldn't match aspect ratio within the constraints
                    height = heightSpecSize
                }
            } else if (heightSpecMode == MeasureSpec.EXACTLY) {
                // only the height is fixed, adjust the width to match aspect ratio if possible
                height = heightSpecSize
                width = height * videoWidth / videoHeight
                if (widthSpecMode == MeasureSpec.AT_MOST && width > widthSpecSize) {
                    // couldn't match aspect ratio within the constraints
                    width = widthSpecSize
                }
            } else {
                // neither the width nor the height are fixed, try to use actual video size
                width = videoWidth
                height = videoHeight
                if (heightSpecMode == MeasureSpec.AT_MOST && height > heightSpecSize) {
                    // too tall, decrease both width and height
                    height = heightSpecSize
                    width = height * videoWidth / videoHeight
                }
                if (widthSpecMode == MeasureSpec.AT_MOST && width > widthSpecSize) {
                    // too wide, decrease both width and height
                    width = widthSpecSize
                    height = width * videoHeight / videoWidth
                }
            }
        } else {
            // no size yet, just adopt the given spec sizes
        }
        setMeasuredDimension(width, height)
    }
}