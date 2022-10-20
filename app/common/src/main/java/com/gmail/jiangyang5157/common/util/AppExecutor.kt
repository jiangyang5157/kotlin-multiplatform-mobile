package com.gmail.jiangyang5157.common.util

import java.util.concurrent.Executor
import java.util.concurrent.Executors
import javax.inject.Inject

/**
 * Created by Yang Jiang on July 11, 2019
 */
class AppExecutor @Inject constructor(
    val mainThread: Executor = MainThreadExecutor(),
    val diskIO: Executor = Executors.newSingleThreadExecutor(),
    val networkIO: Executor = Executors.newFixedThreadPool(2)
)
