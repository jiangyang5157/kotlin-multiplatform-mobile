package com.gmail.jiangyang5157.transaction_presentation_base.ext

import com.gmail.jiangyang5157.kit.data.finance.Money

// GST 15%

fun Money.addGst() = this * 1.15

fun Money.includedGst() = this * 3 / 23
