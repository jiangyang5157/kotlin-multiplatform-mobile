package com.gmail.jiangyang5157.common.ext

import android.os.Bundle
import androidx.fragment.app.Fragment

/**
 * Created by Yang Jiang on July 16, 2017
 */
inline fun <reified T : Fragment> instance(args: Bundle? = null): Fragment {
    return T::class.java.newInstance().apply {
        arguments = args
    }
}
