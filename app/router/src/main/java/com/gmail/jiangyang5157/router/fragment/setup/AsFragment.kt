package com.gmail.jiangyang5157.router.fragment.setup

import androidx.fragment.app.Fragment
import com.gmail.jiangyang5157.router.error.RouterException

interface AsFragment

fun AsFragment.isFragment() = this is Fragment

fun AsFragment.expectThisToBeAFragment() =
    this as? Fragment ?: throw RouterException(
        "Expect ${this::class.java.simpleName} is an androidx.fragment.app.Fragment"
    )
