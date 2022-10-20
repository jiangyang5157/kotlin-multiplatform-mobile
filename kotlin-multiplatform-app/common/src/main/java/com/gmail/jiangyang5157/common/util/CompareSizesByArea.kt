package com.gmail.jiangyang5157.common.util

import android.os.Build
import android.util.Size
import androidx.annotation.RequiresApi
import java.lang.Long.signum

/**
 * Created by Yang Jiang on April 30, 2018
 */
class CompareSizesByArea : Comparator<Size> {

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun compare(lhs: Size, rhs: Size) =
        signum(lhs.width.toLong() * lhs.height - rhs.width.toLong() * rhs.height)
}
