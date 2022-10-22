package com.gmail.jiangyang5157.kit.ext

fun Collection<Any>?.isNotNullOrEmpty() = this != null && this.isNotEmpty()