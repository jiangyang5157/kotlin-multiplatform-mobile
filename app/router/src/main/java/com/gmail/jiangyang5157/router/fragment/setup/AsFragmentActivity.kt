package com.gmail.jiangyang5157.router.fragment.setup

import androidx.fragment.app.FragmentActivity
import com.gmail.jiangyang5157.router.error.RouterException

interface AsFragmentActivity

fun AsFragment.isFragmentActivity() = this is FragmentActivity

fun AsFragmentActivity.expectThisToBeAFragmentActivity() =
    this as? FragmentActivity ?: throw RouterException(
        "Expect ${this::class.java.simpleName} is an androidx.fragment.app.FragmentActivity"
    )
