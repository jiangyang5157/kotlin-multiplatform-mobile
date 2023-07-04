package com.gmail.jiangyang5157.sudoku

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform