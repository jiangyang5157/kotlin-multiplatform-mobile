package com.gmail.jiangyang5157.common.ext

import android.os.Environment
import android.os.Environment.MEDIA_MOUNTED
import android.os.Environment.MEDIA_MOUNTED_READ_ONLY

/**
 * Created by Yang Jiang on April 24, 2018
 */

inline val Environment.isExternalStorageWritable
    get() = Environment.getExternalStorageState() == MEDIA_MOUNTED

inline val Environment.isExternalStorageReadable
    get() = Environment.getExternalStorageState() == MEDIA_MOUNTED_READ_ONLY || isExternalStorageWritable
