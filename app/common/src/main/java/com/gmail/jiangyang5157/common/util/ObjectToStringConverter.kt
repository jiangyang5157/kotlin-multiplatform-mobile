package com.gmail.jiangyang5157.common.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.util.Base64
import java.io.*

/**
 * Created by Yang Jiang on July 01, 2017
 */
object ObjectToStringConverter {

    /**
     * @param obj Serializable object
     */
    @Throws(IOException::class)
    fun object2String(obj: Any): String {
        val baos = ByteArrayOutputStream()
        val oos = ObjectOutputStream(baos)
        oos.writeObject(obj)
        val base64 = Base64.encodeToString(baos.toByteArray(), Base64.DEFAULT)
        baos.close()
        oos.close()
        return base64
    }

    @Throws(IOException::class, ClassNotFoundException::class)
    fun string2Object(base64: String): Any? {
        val bais = ByteArrayInputStream(Base64.decode(base64.toByteArray(), Base64.DEFAULT))
        val ois = ObjectInputStream(bais)
        val ret = ois.readObject()
        bais.close()
        ois.close()
        return ret
    }

    /**
     * @param quality (0, 100]
     */
    @Throws(IOException::class)
    fun bitmap2String(bitmap: Bitmap, format: Bitmap.CompressFormat, quality: Int): String {
        val baos = ByteArrayOutputStream()
        bitmap.compress(format, quality, baos)
        val base64 = Base64.encodeToString(baos.toByteArray(), Base64.DEFAULT)
        baos.close()
        return base64
    }

    @Throws(IOException::class)
    fun string2Drawable(context: Context, base64: String): Drawable? {
        val bais = ByteArrayInputStream(Base64.decode(base64.toByteArray(), Base64.DEFAULT))
        val ret = Drawable.createFromResourceStream(context.resources, null, bais, null, null)
        bais.close()
        return ret
    }

    @Throws(IOException::class)
    fun string2Bitmap(context: Context, base64: String): Bitmap? {
        val drawable = string2Drawable(context, base64)

        return if (drawable == null) {
            null
        } else {
            (drawable as BitmapDrawable).bitmap
        }
    }
}
