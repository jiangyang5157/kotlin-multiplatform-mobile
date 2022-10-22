package com.gmail.jiangyang5157.transaction_presentation_base.ext

import com.gmail.jiangyang5157.transaction_domain_kt.entity.REGEX_DATE_EEE_dd_MMM_yyyy
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun Date.isSameDayAs(other: Date?): Boolean {
    return if (other == null) {
        false
    } else {
        try {
            val pattern = REGEX_DATE_EEE_dd_MMM_yyyy
            SimpleDateFormat(pattern, Locale.getDefault()).format(this)
                .equals(SimpleDateFormat(pattern, Locale.getDefault()).format(other))
        } catch (e: ParseException) {
            false
        }
    }
}
