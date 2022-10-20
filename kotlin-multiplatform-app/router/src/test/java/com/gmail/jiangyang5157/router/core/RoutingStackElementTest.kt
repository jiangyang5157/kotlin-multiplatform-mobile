package com.gmail.jiangyang5157.router.core

import com.gmail.jiangyang5157.kit.data.Key
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotEquals
import kotlin.test.assertTrue

class RoutingStackElementTest {

    private data class RouteImpl1(val id: Int) : Route

    private data class RouteImpl2(val id: String) : Route

    private class ElementImpl1<T : Route>(override val route: T, override val key: Key) :
        RoutingStack.Element<T>()

    private data class ElementImpl2<T : Route>(override val route: T, override val key: Key) :
        RoutingStack.Element<T>()

    @Test
    fun equals_sameElement_sameRouteType_sameRouteValue_sameKey_expectEqual() {
        val i1 = ElementImpl1(RouteImpl1(0), Key("key"))
        val i2 = ElementImpl1(RouteImpl1(0), Key("key"))
        assertTrue(i1 == i2)
        assertEquals(i1.hashCode(), i2.hashCode())
    }

    @Test
    fun equals_diffElement_sameRouteType_sameRouteValue_sameKey_expectEqual() {
        val i1 = ElementImpl1(RouteImpl1(0), Key("key"))
        val i2 = ElementImpl2(RouteImpl1(0), Key("key"))
        assertTrue(i1 == i2)
        assertEquals(i1.hashCode(), i2.hashCode())
    }

    @Test
    fun equals_sameElement_sameRouteType_sameRouteValue_diffKey_expectNotEqual() {
        val i1 = ElementImpl1(RouteImpl1(0), Key())
        val i2 = ElementImpl1(RouteImpl1(0), Key())
        assertFalse(i1 == i2)
        assertNotEquals(i1.hashCode(), i2.hashCode())
    }

    @Test
    fun equals_diffElement_sameRouteType_sameRouteValue_diffKey_expectNotEqual() {
        val i1 = ElementImpl1(RouteImpl1(0), Key())
        val i2 = ElementImpl2(RouteImpl1(0), Key())
        assertFalse(i1 == i2)
        assertNotEquals(i1.hashCode(), i2.hashCode())
    }

    @Test
    fun equals_sameElement_sameRouteType_diffRouteValue_sameKey_expectNotEqual() {
        val i1 = ElementImpl1(RouteImpl1(0), Key("key"))
        val i2 = ElementImpl1(RouteImpl1(1), Key("key"))
        assertFalse(i1 == i2)
        assertNotEquals(i1.hashCode(), i2.hashCode())
    }

    @Test
    fun equals_diffElement_sameRouteType_diffRouteValue_sameKey_expectNotEqual() {
        val i1 = ElementImpl1(RouteImpl1(0), Key("key"))
        val i2 = ElementImpl2(RouteImpl1(1), Key("key"))
        assertFalse(i1 == i2)
        assertNotEquals(i1.hashCode(), i2.hashCode())
    }

    @Test
    fun equals_sameElement_diffRouteType_sameKey_expectNotEqual() {
        val i1 = ElementImpl1(RouteImpl1(0), Key("key"))
        val i2 = ElementImpl1(RouteImpl2("0"), Key("key"))
        assertFalse(i1 == i2)
        assertNotEquals(i1.hashCode(), i2.hashCode())
    }

    @Test
    fun equals_diffElement_diffRouteType_sameKey_expectNotEqual() {
        val i1 = ElementImpl1(RouteImpl1(0), Key("key"))
        val i2 = ElementImpl2(RouteImpl2("0"), Key("key"))
        assertFalse(i1 == i2)
        assertNotEquals(i1.hashCode(), i2.hashCode())
    }
}
