package com.gmail.jiangyang5157.kit.data

/**
 * Created by Yang Jiang on July 11, 2019
 *
 * A data wrapper that indicates the data state.
 */
sealed class Resource<out T : Any, out E : Any> {

    /**
     * A ready data with [data], it is a nullable as in some usecase, a null data is acceptable
     */
    data class Completed<T : Any>(val data: T? = null) : Resource<T, Nothing>()

    /**
     * Contains an unexpected failure [error] indicates the data is either null (assume null is unexpected in the usecase),
     * or something went wrong during data preparation
     */
    data class Failed<E : Any>(val error: E? = null) : Resource<Nothing, E>()

    /**
     * Indicates data is not ready, but it can contains a optional pre-populate [data] for the usecase to use.
     */
    data class Loading<T : Any>(val data: T? = null) : Resource<T, Nothing>()
}
