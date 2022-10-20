package com.gmail.jiangyang5157.common.util

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.BitmapFactory.decodeResource
import android.graphics.Canvas
import android.graphics.Matrix
import androidx.core.graphics.drawable.RoundedBitmapDrawable
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory
import java.io.BufferedOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.net.URL

/**
 * Created by Yang Jiang on July 01, 2017
 */
object BitmapUtils {

    fun load(resources: Resources, resId: Int): Bitmap = decodeResource(resources, resId)

    fun load(url: URL): Bitmap? {
        return try {
            BitmapFactory.decodeStream(url.openConnection().getInputStream())
        } catch (e: IOException) {
            null
        }
    }

    /**
     * @param sampleSize dst.width = src.width/sampleSize; dst.high = src.high/sampleSize
     */
    @Throws(IOException::class)
    fun load(filePath: String?, sampleSize: Int = 1): Bitmap? {
        if (filePath == null) {
            return null
        }
        val options = BitmapFactory.Options()
        options.inSampleSize = sampleSize
        return BitmapFactory.decodeFile(filePath, options)
    }

    /**
     * @param quality [0, 100]
     */
    @Throws(IOException::class)
    fun save(src: Bitmap, file: File, format: Bitmap.CompressFormat, quality: Int) {
        val bos = BufferedOutputStream(FileOutputStream(file))
        src.compress(format, quality, bos)
        bos.flush()
        bos.close()
    }

    fun roundDrawable(resources: Resources, bitmap: Bitmap): RoundedBitmapDrawable {
        val bitmapWidth = bitmap.width
        val bitmapHeight = bitmap.height

        val bitmapSquare = Math.min(bitmapWidth, bitmapHeight)
        val bitmapRadius = bitmapSquare / 2

        val roundBitmap = Bitmap.createBitmap(bitmapSquare, bitmapSquare, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(roundBitmap)
        canvas.drawBitmap(bitmap, (bitmapSquare - bitmapWidth).toFloat(), (bitmapSquare - bitmapHeight).toFloat(), null)

        val roundBitmapDrawable = RoundedBitmapDrawableFactory.create(resources, roundBitmap)
        roundBitmapDrawable.cornerRadius = bitmapRadius.toFloat()
        roundBitmapDrawable.setAntiAlias(true)
        return roundBitmapDrawable
    }

    fun verticalReverse(src: Bitmap): Bitmap {
        return Bitmap.createScaledBitmap(src, src.width, -src.height, true)
    }

    fun horizontalReverse(src: Bitmap): Bitmap {
        return Bitmap.createScaledBitmap(src, -src.width, src.height, true)
    }

    fun diagonalReverse(src: Bitmap): Bitmap {
        return Bitmap.createScaledBitmap(src, -src.width, -src.height, true)
    }

    fun rotate(src: Bitmap, degrees: Float): Bitmap {
        val imgWidth = src.width
        val imgHeight = src.height
        val matrix = Matrix()
        matrix.postScale(imgWidth.toFloat(), imgHeight.toFloat())
        matrix.setRotate(degrees)
        return Bitmap.createBitmap(src, 0, 0, imgWidth, imgHeight, matrix, true)
    }

    fun scale(src: Bitmap, newWidth: Int, newHeight: Int): Bitmap {
        val oldWidth = src.width
        val oldHeight = src.height
        val scaleWidth = (newWidth / oldWidth).toFloat()
        val scaleHeight = (newHeight / oldHeight).toFloat()
        val matrix = Matrix()
        matrix.postScale(scaleWidth, scaleHeight)
        return Bitmap.createBitmap(src, 0, 0, oldWidth, oldHeight, matrix, true)
    }

    fun scale(src: Bitmap, scale: Float): Bitmap {
        val oldWidth = src.width
        val oldHeight = src.height
        val matrix = Matrix()
        matrix.postScale(scale, scale)
        return Bitmap.createBitmap(src, 0, 0, oldWidth, oldHeight, matrix, true)
    }
}
