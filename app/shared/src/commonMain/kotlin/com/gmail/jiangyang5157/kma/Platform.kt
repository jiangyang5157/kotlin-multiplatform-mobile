package com.gmail.jiangyang5157.kma

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform