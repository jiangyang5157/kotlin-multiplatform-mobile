package com.gmail.jiangyang5157.kit.utils

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by Yang Jiang on July 01, 2017
 */
object DateBuilder {

    fun buildStringDate(template: String): String = buildDateFormat(template).format(Date())

    fun buildStringDate(milliseconds: Long, template: String): String =
        buildDateFormat(template).format(Date(milliseconds))

    fun buildLongDate(): Long = Date().time

    @Throws(NullPointerException::class, IllegalArgumentException::class, ParseException::class)
    fun buildLongDate(stringDate: String, template: String): Long =
        buildDateFormat(template).parse(stringDate).time

    fun buildDateFormat(template: String, locale: Locale = Locale.getDefault()): SimpleDateFormat =
        SimpleDateFormat(template, locale)
}
