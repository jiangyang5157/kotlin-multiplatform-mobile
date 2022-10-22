package com.gmail.jiangyang5157.video

import android.graphics.SurfaceTexture
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.os.Handler
import android.os.HandlerThread
import android.util.Log
import android.view.Surface
import android.view.TextureView

/**
 * Use [MediaPlayer] to play video within [VideoTextureView]
 */
class VideoPlayerController(private var videoTextureView: VideoTextureView) :
    TextureView.SurfaceTextureListener,
    MediaPlayer.OnVideoSizeChangedListener,
    MediaPlayer.OnPreparedListener,
    MediaPlayer.OnCompletionListener,
    MediaPlayer.OnBufferingUpdateListener,
    MediaPlayer.OnErrorListener,
    MediaPlayer.OnInfoListener {

    var stateChangedListener: StateChangedListener? = null

    interface StateChangedListener {
        fun onStateChanged(from: Int, to: Int)
    }

    var videoPlayerCallback: VideoPlayerCallback? = null

    interface VideoPlayerCallback {

        /**
         * Provide percentage of video buffer progress
         */
        fun onBufferingUpdate(percent: Int)

        /**
         * Provide the video size after preparation
         */
        fun onVideoSizeChanged(width: Int, height: Int)
    }

    // UI actions
    private var uiHandler: Handler = Handler(videoTextureView.context.mainLooper)

    // Media player actions
    private var mediaHandlerThread: HandlerThread? = null
    private var mediaHandler: Handler? = null

    private var mediaPlayer: MediaPlayer? = null
    private var surfaceTexture: SurfaceTexture? = null
    private var surface: Surface? = null

    val state: Int get() = _state
    private var _state: Int = STATE_UNKNOWN
        set(value) {
            val current = _state
            if (current != value) {
                field = value
                uiHandler.post { stateChangedListener?.onStateChanged(current, value) }
            }
        }

    init {
        videoTextureView.surfaceTextureListener = this
    }

    private fun buildMediaPlayer(): MediaPlayer {
        return MediaPlayer().apply {
            this.setOnVideoSizeChangedListener(this@VideoPlayerController)
            this.setOnPreparedListener(this@VideoPlayerController)
            this.setOnCompletionListener(this@VideoPlayerController)
            this.setOnBufferingUpdateListener(this@VideoPlayerController)
            this.setOnErrorListener(this@VideoPlayerController)
            this.setOnInfoListener(this@VideoPlayerController)

            this.setScreenOnWhilePlaying(true)
            this.isLooping = false
            this.setAudioAttributes(
                AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_MEDIA)
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .build()
            )
        }
    }

    fun release() {
        val tmpMediaHandlerThread: HandlerThread? = mediaHandlerThread
        val tmpMediaHandler: Handler? = mediaHandler
        val tmpMediaPlayer: MediaPlayer? = mediaPlayer
        tmpMediaHandler?.post {
            tmpMediaPlayer?.setSurface(null)
            tmpMediaPlayer?.release()
            tmpMediaHandlerThread?.quit()
        }
        mediaPlayer = null
        mediaHandler = null
        mediaHandlerThread = null
        _state = STATE_IDLE
    }

    fun prepare(videoUrl: String) {
        // make sure STATE_IDLE
        release()

        mediaHandlerThread = HandlerThread(TAG)
        mediaHandlerThread?.let { handlerThread ->
            handlerThread.start()
            mediaHandler = Handler(handlerThread.looper)
            mediaHandler?.let { handler ->
                handler.post {
                    mediaPlayer = buildMediaPlayer()
                    mediaPlayer?.let { mediaPlayer ->
                        try {
                            mediaPlayer.setDataSource(videoUrl)
                            mediaPlayer.prepareAsync()
                            _state = STATE_PREPARING
                        } catch (e: IllegalStateException) {
                            _state = STATE_ERROR
                            e.printStackTrace()
                        }
                    }
                }
            }
        }
    }

    fun start() {
        mediaHandler?.post {
            try {
                mediaPlayer?.start()
            } catch (e: IllegalStateException) {
                _state = STATE_ERROR
                e.printStackTrace()
            }
            _state = STATE_PLAYING
        }
    }

    fun pause() {
        mediaHandler?.post {
            if (true == mediaPlayer?.isPlaying) {
                try {
                    mediaPlayer?.pause()
                } catch (e: IllegalStateException) {
                    _state = STATE_ERROR
                    e.printStackTrace()
                }
                _state = STATE_PAUSED
            }
        }
    }

    fun isPlaying(): Boolean = state == STATE_PLAYING || state == STATE_BUFFERING_PLAYING

    fun isPaused(): Boolean = state == STATE_PAUSED || state == STATE_BUFFERING_PAUSED

    fun getDuration(): Long = mediaPlayer?.duration?.toLong() ?: 0

    fun getCurrentPosition(): Long = mediaPlayer?.currentPosition?.toLong() ?: 0

    fun seekTo(time: Long) {
        mediaHandler?.post {
            try {
                mediaPlayer?.seekTo(time.toInt())
            } catch (e: IllegalStateException) {
                _state = STATE_ERROR
                e.printStackTrace()
            }
        }
    }

    fun setVolume(leftVolume: Float, rightVolume: Float) {
        mediaHandler?.post { mediaPlayer?.setVolume(leftVolume, rightVolume) }
    }

    override fun onError(mediaPlayer: MediaPlayer, what: Int, extra: Int): Boolean {
        Log.e(TAG, "onError $what $extra")
        _state = STATE_ERROR
        return true
    }

    override fun onInfo(mediaPlayer: MediaPlayer, what: Int, extra: Int): Boolean {
        Log.i(TAG, "onInfo: $what $extra")
        when (what) {
            MediaPlayer.MEDIA_INFO_VIDEO_RENDERING_START -> {
                // MediaPlayer start rendering first frame
                _state = STATE_PLAYING
            }
            MediaPlayer.MEDIA_INFO_BUFFERING_START -> {
                // MediaPlayer need to buffer more data
                _state = if (_state == STATE_PAUSED || _state == STATE_BUFFERING_PAUSED) {
                    STATE_BUFFERING_PAUSED
                } else {
                    STATE_BUFFERING_PLAYING
                }
            }
            MediaPlayer.MEDIA_INFO_BUFFERING_END -> {
                when (_state) {
                    STATE_BUFFERING_PLAYING -> {
                        // After filling the buffer, MediaPlayer resumes playing
                        _state = STATE_PLAYING
                    }
                    STATE_BUFFERING_PAUSED -> {
                        // After filling the buffer, MediaPlayer resumes paused
                        _state = STATE_PAUSED
                    }
                }
            }
            else -> {
                // do nothing
            }
        }
        return false
    }

    override fun onPrepared(mediaPlayer: MediaPlayer) {
        uiHandler.post {
            if (null != surfaceTexture) {
                if (null != surface) {
                    surface?.release()
                    surface = null
                }
                try {
                    surface = Surface(surfaceTexture)
                    mediaPlayer.setSurface(surface)
                } catch (e: Exception) {
                    _state = STATE_ERROR
                    e.printStackTrace()
                }
            }
            _state = STATE_PREPARED
            _state = STATE_PAUSED
        }
    }

    override fun onCompletion(mediaPlayer: MediaPlayer) {
        _state = STATE_COMPLETED
    }

    override fun onBufferingUpdate(mediaPlayer: MediaPlayer, percent: Int) {
        uiHandler.post { videoPlayerCallback?.onBufferingUpdate(percent) }
    }

    override fun onVideoSizeChanged(mediaPlayer: MediaPlayer, width: Int, height: Int) {
        Log.d(TAG, "onVideoSizeChanged $width $height")
        uiHandler.post {
            videoTextureView.setVideoSize(width, height)
            videoPlayerCallback?.onVideoSizeChanged(width, height)
        }
    }

    override fun onSurfaceTextureAvailable(surface: SurfaceTexture, width: Int, height: Int) {
        with(surfaceTexture) {
            if (this == null) {
                surfaceTexture = surface
            } else {
                videoTextureView.setSurfaceTexture(this)
            }
        }
    }

    override fun onSurfaceTextureSizeChanged(surface: SurfaceTexture, width: Int, height: Int) {
        Log.d(TAG, "onSurfaceTextureSizeChanged $width $height")
    }

    override fun onSurfaceTextureUpdated(surface: SurfaceTexture) {}

    /**
     *  If returns true, no rendering should happen inside the surface texture after this method is invoked.
     *  If returns false, the client needs to call SurfaceTexture.release().
     */
    override fun onSurfaceTextureDestroyed(surface: SurfaceTexture): Boolean {
        return null == surfaceTexture
    }

    companion object {
        const val TAG = "VideoPlayerController"

        const val STATE_UNKNOWN = -2
        const val STATE_ERROR = -1
        const val STATE_IDLE = 0 // before prepareAsync()
        const val STATE_PREPARING = 1
        const val STATE_PREPARED = 2
        const val STATE_PLAYING = 3
        const val STATE_PAUSED = 4
        const val STATE_BUFFERING_PLAYING = 5 // filling the buffer data and playing
        const val STATE_BUFFERING_PAUSED = 6 // filling the buffer data and paused
        const val STATE_COMPLETED = 7
    }
}