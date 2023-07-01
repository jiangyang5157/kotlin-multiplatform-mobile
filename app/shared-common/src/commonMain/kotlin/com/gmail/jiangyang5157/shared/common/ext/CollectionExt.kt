package com.gmail.jiangyang5157.shared.common.ext

fun <T> Collection<T>?.isNotNullOrEmpty() = !this.isNullOrEmpty()