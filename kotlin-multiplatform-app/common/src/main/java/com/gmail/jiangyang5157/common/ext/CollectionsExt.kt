package com.gmail.jiangyang5157.common.ext

fun Collection<Any>?.isNotNullOrEmpty() = this != null && this.isNotEmpty()