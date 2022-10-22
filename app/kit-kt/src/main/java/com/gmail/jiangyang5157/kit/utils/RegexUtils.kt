package com.gmail.jiangyang5157.kit.utils

/**
 * Created by Yang Jiang on June 27, 2017
 */
object RegexUtils {

    // Tue, 15 Nov 1994 12:45:26 GMT
    const val DATE_HTTP_EDMYHMSZ: String = "EEE, dd MMM yyyy HH:mm:ss zzz"

    // Nov 15, 1994 12:45:26
    const val DATE_MDYHMS: String = "MMM dd, yyyy HH:mm:ss"

    // 19941115124526
    const val DATE_YMDHMS: String = "yyyyMMddHHmmss"

    // 15/11/1994
    const val DATE_DMY: String = "dd/MM/yyyy"

    // 1994/11/15
    const val DATE_YMD: String = "yyyy/MM/dd"

    /**
     * Accurate regex that captures the four parts of the IP Address
     */
    const val IP_ADDRESS: String = "^(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$"

    /**
     * Accurate regex that captures the Port
     */
    const val IP_PORT: String = "^([\\d]{1,5})$"

    const val URL: String = "(https?:\\/\\/)+[\\w.:\\-]+(/[\\w.:\\-~!@#$%&+=|;?,]+)*"

    const val AMOUNT: String = "^(\\-|\\+)?([0-9]+|[0-9]+\\.[0-9]*|[0-9]*\\.[0-9]+){1}$"
}
