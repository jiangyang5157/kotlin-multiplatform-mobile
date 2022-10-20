package com.gmail.jiangyang5157.common.data

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Assert.fail
import org.junit.Test
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

class EitherTest {

    @Test
    fun rightShouldReturnCorrectType() {
        val result = Either.Right("ironman")
        assertFalse(result.isLeft)
        assertTrue(result.isRight)
    }

    @Test
    fun leftShouldReturnCorrectType() {
        val result =
            Either.Left(RuntimeException())
        assertTrue(result.isLeft)
        assertFalse(result.isRight)
    }

    @Test
    fun mapRightValue() {
        val rightValue = Either.Right(2)

        assertEquals(Either.Right(12), rightValue.map { it + 10 })
        assertEquals(
            Either.Right(
                Either.Right(11)
            ),
            rightValue.map { Either.Right(11) }
        )
        assertEquals(
            Either.Right(
                Either.Right(
                    13
                )
            ),
            rightValue.map { Either.Right(it + 11) }
        )
        assertEquals(Either.Right(12), rightValue.map { 12 })
    }

    @Test
    fun mapTestrightBiased() {
        val latch = CountDownLatch(1)
        Either.Right("right data").map {
            latch.countDown()
        }
        assertTrue(latch.await(1, TimeUnit.SECONDS))

        Either.Left("left data").map {
            fail()
        }
    }

    @Test
    fun flatMapRightValue() {
        val rightValue = Either.Right(2)

        assertEquals(
            Either.Right(12),
            rightValue.flatMap {
                Either.Right(it + 10)
            }
        )
        assertEquals(
            Either.Right(11),
            rightValue.flatMap {
                Either.Right(11)
            }
        )
    }

    @Test
    fun flatMapTestrightBiased() {
        val latch = CountDownLatch(1)
        Either.Right("right data").flatMap {
            latch.countDown()
            right("flatmap on right data completed")
        }
        assertTrue(latch.await(1, TimeUnit.SECONDS))

        Either.Left("left data").flatMap {
            right("flatmap on left data should never executed")
        }
    }

    @Test
    fun foldValues() {
        val rightValue = Either.Right(2)
        rightValue.fold(
            {
                fail()
            },
            {
                assertEquals(2, it)
            }
        )

        val leftValue =
            Either.Left(RuntimeException())
        leftValue.fold(
            {
                assertTrue(it is RuntimeException)
            },
            {
                fail()
            }
        )
    }
}
