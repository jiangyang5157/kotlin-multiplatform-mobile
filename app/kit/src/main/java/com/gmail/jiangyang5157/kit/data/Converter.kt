package com.gmail.jiangyang5157.kit.data

interface Converter<A, B> {
    fun forward(a: A?): B?
    fun backward(b: B?): A?
}
