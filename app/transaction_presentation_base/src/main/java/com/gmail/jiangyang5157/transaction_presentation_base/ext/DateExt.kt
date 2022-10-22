package com.gmail.jiangyang5157.transaction_presentation_base.ext

import com.gmail.jiangyang5157.kit.utils.RegexUtils.DATE_EEEddMMMyyyy
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

fun Date.isSameDayAs(other: Date?): Boolean {
    return if (other == null) {
        false
    } else {
        try {
            val pattern = DATE_EEEddMMMyyyy
            SimpleDateFormat(pattern, Locale.getDefault()).format(this)
                .equals(SimpleDateFormat(pattern, Locale.getDefault()).format(other))
        } catch (e: ParseException) {
            false
        }
    }
}
