package com.gmail.jiangyang5157.dlx

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform