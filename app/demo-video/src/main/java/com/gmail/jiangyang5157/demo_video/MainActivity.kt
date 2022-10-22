package com.gmail.jiangyang5157.demo_video

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.gmail.jiangyang5157.video.VideoPlayerController
import com.gmail.jiangyang5157.video.VideoPlayerController.Companion.STATE_COMPLETED
import com.gmail.jiangyang5157.video.VideoPlayerController.Companion.STATE_ERROR
import com.gmail.jiangyang5157.video.VideoPlayerController.Companion.STATE_IDLE
import com.gmail.jiangyang5157.video.VideoPlayerController.Companion.STATE_PAUSED
import com.gmail.jiangyang5157.video.VideoPlayerController.Companion.STATE_PLAYING
import com.gmail.jiangyang5157.video.VideoPlayerController.Companion.STATE_PREPARED
import com.gmail.jiangyang5157.video.VideoPlayerController.Companion.STATE_PREPARING
import com.gmail.jiangyang5157.video.VideoTextureView
import com.squareup.picasso.Picasso
import java.util.*
import kotlin.math.roundToInt

class MainActivity : AppCompatActivity() {

    private lateinit var videoPlayerController: VideoPlayerController
    private lateinit var videoTextureView: VideoTextureView

    private lateinit var ivThumbnail: ImageView
    private lateinit var pbLoading: ProgressBar
    private lateinit var sbProgress: SeekBar
    private lateinit var tvDuration: TextView
    private lateinit var btnPrimary: Button
    private lateinit var layoutVideoOverlay: ViewGroup

    private var timer: Timer? = null
    private var timeTask: TimerTask? = null

    private val thumbnailUrl =
        "https://storage.googleapis.com/gweb-uniblog-publish-prod/images/HeroHomepage_2880x1200.max-1000x1000.jpg"
    private val videoUrl = "https://www.rmp-streaming.com/media/big-buck-bunny-360p.mp4"

    private val handlerThread = HandlerThread("HandleVideoOverlay").also {
        it.start()
    }
    private val handler = Handler(handlerThread.looper)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        videoTextureView = findViewById(R.id.videoTextureView)
        videoPlayerController = VideoPlayerController(videoTextureView)
        videoPlayerController.stateChangedListener = stateChangedListener
        videoPlayerController.videoPlayerCallback = videoPlayerCallback
        videoPlayerController.prepare(videoUrl)

        ivThumbnail = findViewById(R.id.ivThumbnail)
        Picasso.get().load(thumbnailUrl).into(ivThumbnail)
        ivThumbnail.visibility = View.VISIBLE

        pbLoading = findViewById(R.id.pbLoading)
        sbProgress = findViewById(R.id.sbProgress)
        tvDuration = findViewById(R.id.tvDuration)
        btnPrimary = findViewById(R.id.btnPrimary)
        layoutVideoOverlay = findViewById(R.id.layoutVideoOverlay)

        btnPrimary.setOnClickListener {
            if (videoPlayerController.isPlaying()) {
                videoPlayerController.pause()
                handler.removeCallbacksAndMessages(null)
            } else {
                videoPlayerController.start()
                handler.removeCallbacksAndMessages(null)
                handler.postDelayed({ hideLayoutVideoOverlay(200) }, 2000)
            }
        }

        videoTextureView.setOnClickListener {
            if (videoPlayerController.isPlaying()) {
                if (layoutVideoOverlay.visibility == View.VISIBLE) {
                    hideLayoutVideoOverlay(50)
                } else {
                    showLayoutVideoOverlay(50)
                    handler.removeCallbacksAndMessages(null)
                    handler.postDelayed({ hideLayoutVideoOverlay(200) }, 2000)
                }
            } else {
                if (layoutVideoOverlay.visibility == View.VISIBLE) {
                    hideLayoutVideoOverlay(50)
                } else {
                    showLayoutVideoOverlay(50)
                }
            }
        }

        sbProgress.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                if (fromUser) {
                    val duration = videoPlayerController.getDuration()
                    val to = ((progress / 100.0) * duration).toLong()
                    videoPlayerController.seekTo(to)
                    updateProgress()
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                handler.removeCallbacksAndMessages(null)
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                if (videoPlayerController.isPlaying()) {
                    handler.postDelayed({ hideLayoutVideoOverlay(200) }, 2000)
                }
            }
        })

        findViewById<Button>(R.id.btnPrepare).setOnClickListener {
            videoPlayerController.prepare(videoUrl)
        }
        findViewById<Button>(R.id.btnPlay).setOnClickListener {
            videoPlayerController.start()
        }
        findViewById<Button>(R.id.btnPause).setOnClickListener {
            videoPlayerController.pause()
        }
    }

    private fun showLayoutVideoOverlay(duration: Long = 0) {
        if (layoutVideoOverlay.visibility == View.VISIBLE) {
            return
        }

        if (duration <= 0) {
            layoutVideoOverlay.visibility = View.VISIBLE
            return
        }

        layoutVideoOverlay.alpha = 0f
        layoutVideoOverlay.visibility = View.VISIBLE
        layoutVideoOverlay.animate()
            .alpha(1f)
            .setDuration(duration)
            .setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {}
            })
    }

    private fun hideLayoutVideoOverlay(duration: Long) {
        if (layoutVideoOverlay.visibility == View.GONE) {
            return
        }

        if (duration <= 0) {
            layoutVideoOverlay.visibility = View.GONE
            return
        }

        layoutVideoOverlay.animate()
            .alpha(0f)
            .setDuration(duration)
            .setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    layoutVideoOverlay.visibility = View.GONE
                    layoutVideoOverlay.alpha = 1f
                }
            })
    }

    private val stateChangedListener = object : VideoPlayerController.StateChangedListener {
        override fun onStateChanged(from: Int, to: Int) {
            when (to) {
                STATE_ERROR -> {
                    Log.d("####", "onStateChanged STATE_ERROR")
                    timer?.cancel()
                    timeTask?.cancel()
                    videoPlayerController.prepare(videoUrl)
                }
                STATE_IDLE -> {
                    Log.d("####", "onStateChanged STATE_IDLE")
                }
                STATE_PREPARING -> {
                    Log.d("####", "onStateChanged STATE_PREPARING")
                    pbLoading.visibility = View.VISIBLE
                    hideLayoutVideoOverlay(0)
                }
                STATE_PREPARED -> {
                    Log.d("####", "onStateChanged STATE_PREPARED")
                    pbLoading.visibility = View.GONE
                    showLayoutVideoOverlay(0)

                    updateProgress()

                    timer?.cancel()
                    timeTask?.cancel()
                    timer = Timer()
                    timeTask = object : TimerTask() {
                        override fun run() {
                            if (videoPlayerController.isPlaying()) {
                                Handler(mainLooper).post { updateProgress() }
                            }
                        }
                    }
                    timer?.schedule(timeTask, 0, 1000)
                }
                STATE_PLAYING -> {
                    Log.d("####", "onStateChanged STATE_PLAYING")
                    btnPrimary.text = "Pause"
                    ivThumbnail.visibility = View.GONE
                }
                STATE_PAUSED -> {
                    Log.d("####", "onStateChanged STATE_PAUSED")
                    btnPrimary.text = "Play"
                }
                STATE_COMPLETED -> {
                    Log.d("####", "onStateChanged STATE_COMPLETED")
                    timer?.cancel()
                    timeTask?.cancel()
                    videoPlayerController.prepare(videoUrl)
                    showLayoutVideoOverlay(50)
                }
            }
        }
    }

    private val videoPlayerCallback = object : VideoPlayerController.VideoPlayerCallback {

        override fun onBufferingUpdate(percent: Int) {
            sbProgress.secondaryProgress = percent
        }

        override fun onVideoSizeChanged(width: Int, height: Int) {
            videoTextureView.layoutParams = videoTextureView.layoutParams.apply {
                this.width = this.height * width / height
            }
        }
    }

    private fun updateProgress() {
        val currentProgress = videoPlayerController.getCurrentPosition()
        val duration = videoPlayerController.getDuration()
        tvDuration.text = "${currentProgress}/${duration}"
        val progressPercent: Int = (currentProgress / duration.toFloat() * 100).roundToInt()
        Log.d("####", "progressPercent=$progressPercent")
        sbProgress.progress = progressPercent
    }

    override fun onResume() {
        super.onResume()
        if (!videoPlayerController.isPlaying()) {
            showLayoutVideoOverlay(0)
        }
    }

    override fun onPause() {
        super.onPause()
        videoPlayerController.pause()
    }

    override fun onDestroy() {
        super.onDestroy()
        videoPlayerController.release()
    }
}