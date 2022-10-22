package com.gmail.jiangyang5157.common.util

import android.os.Environment
import android.os.StatFs
import java.io.File
import java.io.IOException
import java.math.BigDecimal

/**
 * Created by Yang Jiang on July 01, 2017
 */
object StorageUtils {

    const val UNIT_STORAGE: Int = 1024

    const val BYTE_SYMBOL: String = "BYTE"

    const val KB_SYMBOL: String = "KB"

    const val MB_SYMBOL: String = "MB"

    const val GB_SYMBOL: String = "GB"

    /**
     * Return true if external storage is available for read and write
     */
    val isExternalStorageWritable: Boolean =
        Environment.MEDIA_MOUNTED == Environment.getExternalStorageState()

    /**
     *  Return true if external storage is available to at least read
     */
    val isExternalStorageReadable: Boolean
        get() {
            val state = Environment.getExternalStorageState()
            return Environment.MEDIA_MOUNTED == state || Environment.MEDIA_MOUNTED_READ_ONLY == state
        }

    val sdacrdFile: File = Environment.getExternalStorageDirectory()

    @Throws(IOException::class)
    fun chmod777(filePath: String) {
        Runtime.getRuntime().exec("chmod 777 $filePath")
    }

    private fun getSize(fileLength: Long, symbol: String): Double {
        var ret = 0.0
        when (symbol) {
            BYTE_SYMBOL -> ret = fileLength.toFloat().toDouble()
            KB_SYMBOL -> ret = (fileLength.toFloat() / UNIT_STORAGE).toDouble()
            MB_SYMBOL -> ret = (fileLength.toFloat() / (UNIT_STORAGE * UNIT_STORAGE)).toDouble()
            GB_SYMBOL -> ret =
                (fileLength.toFloat() / (UNIT_STORAGE * UNIT_STORAGE * UNIT_STORAGE)).toDouble()
        }
        val scale = 2
        val roundingMode = BigDecimal.ROUND_UP
        var bigDecimal = BigDecimal(ret)
        bigDecimal = bigDecimal.setScale(scale, roundingMode)
        ret = bigDecimal.toDouble()
        return ret
    }

    fun getAvailableSize(file: File, symbol: String): Double {
        val statFs = StatFs(file.path)
        val blockSize = statFs.blockSizeLong
        val availableBlocks = statFs.availableBlocksLong
        val available = availableBlocks * blockSize
        return getSize(available, symbol)
    }

    fun getSize(file: File, symbol: String): Double = getSize(file.length(), symbol)
}
