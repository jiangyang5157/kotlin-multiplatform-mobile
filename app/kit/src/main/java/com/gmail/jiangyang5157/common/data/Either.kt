package com.gmail.jiangyang5157.common.data

/**
 * Represents a value of one of two possible types (a disjoint union).
 * Instances of [Either] are either an instance of [Left] or [Right].
 * Functional Programming Convention dictates that [Left] is used for "failure"
 * and [Right] is used for "success".
 *
 * @see Left
 * @see Right
 */
sealed class Either<out L, out R> {

    /**
     * Represents the left side of [Either] class which by convention is a "Failure".
     */
    data class Left<out L>(val data: L) : Either<L, Nothing>()

    /**
     * Represents the right side of [Either] class which by convention is a "Success".
     */
    data class Right<out R>(val data: R) : Either<Nothing, R>()

    val isLeft
        get() = this is Left<L>

    val isRight
        get() = this is Right<R>
}

fun <L> left(a: L) = Either.Left(a)

fun <R> right(b: R) = Either.Right(b)

/**
 * Applies fnL if this is a Left or fnR if this is a Right.
 */
fun <T, L, R> Either<L, R>.fold(fnL: (L) -> T, fnR: (R) -> T): T =
    when (this) {
        is Either.Left -> fnL(data)
        is Either.Right -> fnR(data)
    }

/**
 * Right-biased map() FP convention which means that Right is assumed to be the default case
 * to operate on. If it is Left, operations like map, flatMap, ... return the Left value unchanged.
 */
fun <T, L, R> Either<L, R>.map(fn: (R) -> (T)): Either<L, T> = this.flatMap(fn.c(::right))

private fun <A, B, C> ((A) -> B).c(f: (B) -> C): (A) -> C = {
    f(this(it))
}

/**
 * Right-biased flatMap() FP convention which means that Right is assumed to be the default case
 * to operate on. If it is Left, operations like map, flatMap, ... return the Left value unchanged.
 */
fun <T, L, R> Either<L, R>.flatMap(fn: (R) -> Either<L, T>): Either<L, T> =
    when (this) {
        is Either.Left -> Either.Left(
            data
        )
        is Either.Right -> fn(data)
    }
