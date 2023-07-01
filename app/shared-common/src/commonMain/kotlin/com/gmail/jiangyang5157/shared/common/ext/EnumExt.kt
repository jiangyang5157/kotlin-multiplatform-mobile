package com.gmail.jiangyang5157.shared.common.ext

interface EnumWithKey<T : Enum<T>, K> {
    val T.key: K
}

/**
 * Example usage:
 *
 * ```
 * enum class MyEnum(val id: String) {
 *      Type1("id 1"),
 *      Type2("id 1");
 *
 *      // eg: val myEnumWithId2 = MyEnum.getByKey("id 2")
 *      companion object : EnumWithKey<MyEnum, String> {
 *          override val MyEnum.key: String
 *              get() = id
 *      }
 * }
 * ```
 */
inline fun <reified T : Enum<T>, K> EnumWithKey<T, K>.getByKey(key: K): T? {
    return enumValues<T>().find { it.key == key }
}
