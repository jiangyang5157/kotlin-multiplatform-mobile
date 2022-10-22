package com.gmail.jiangyang5157.common.utils

import java.io.BufferedInputStream
import java.io.BufferedOutputStream
import java.io.BufferedReader
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader

/**
 * Created by Yang Jiang on June 27, 2017
 */
object IoUtils {

    interface OnReadingListener {

        /**
         * Return false to stop
         */
        fun onReadLine(line: CharSequence?): Boolean
    }

    fun lineSeparator(): String {
        return System.getProperty("line.separator")
    }

    @Throws(IOException::class)
    fun read(inputStream: InputStream, onReadingListener: OnReadingListener) {
        val reader = BufferedReader(InputStreamReader(inputStream))
        var valid = true
        while (valid) {
            valid = onReadingListener.onReadLine(reader.readLine())
        }

        reader.close()
    }

    @Throws(IOException::class)
    fun read(inputStream: InputStream): CharSequence {
        val body = StringBuilder()
        read(
            inputStream,
            object : OnReadingListener {
                override fun onReadLine(line: CharSequence?): Boolean {
                    return if (line == null) {
                        false
                    } else {
                        body.append(line).append(lineSeparator())
                        true
                    }
                }
            }
        )

        return body.toString()
    }

    @Throws(IOException::class)
    fun write(inputStream: InputStream, dst: File) {
        dst.parentFile.mkdirs()

        val BUFFER_SIZE = 1024
        val buffer = ByteArray(BUFFER_SIZE)
        val fileOutputStream = FileOutputStream(dst)
        val bufferedOutputStream = BufferedOutputStream(fileOutputStream, BUFFER_SIZE)

        do {
            val length = inputStream.read(buffer, 0, BUFFER_SIZE)
            if (length != -1) {
                bufferedOutputStream.write(buffer, 0, length)
            } else {
                break
            }
        } while (true)

        bufferedOutputStream.flush()
        bufferedOutputStream.close()
        fileOutputStream.flush()
        fileOutputStream.close()
    }

    @Throws(IOException::class)
    fun copy(src: File, dst: File) {
        write(FileInputStream(src), dst)
    }

    @Throws(IOException::class)
    fun unzip(inputStream: InputStream, dst: File, replace: Boolean) {
        val bufferedInputStream = BufferedInputStream(inputStream)
        val zipInputStream = java.util.zip.ZipInputStream(bufferedInputStream)

        do {
            val zipEntry = zipInputStream.nextEntry
            if (zipEntry != null) {
                val file = File(dst.absolutePath + File.separator + zipEntry.name)

                if (zipEntry.isDirectory) {
                    file.mkdirs()
                } else if (file.isDirectory || replace || !file.exists()) {
                    write(zipInputStream, file)
                }
            } else {
                break
            }
        } while (true)

        zipInputStream.close()
        bufferedInputStream.close()
    }
}
