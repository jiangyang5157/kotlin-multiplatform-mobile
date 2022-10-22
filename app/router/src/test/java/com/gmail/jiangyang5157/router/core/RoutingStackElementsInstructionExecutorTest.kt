package com.gmail.jiangyang5157.router.core

import com.gmail.jiangyang5157.kit.data.Key
import kotlin.test.Test
import kotlin.test.assertEquals

class RoutingStackElementsInstructionExecutorTest {

    private data class RouteImpl1(val id: Int) : Route

    private data class RouteImpl2(val id: String) : Route

    private class ElementImpl<T : Route>(override val route: T, override val key: Key) :
        RoutingStack.Element<T>()

    @Test
    fun size_diffRouteValue_diffKey() {
        val stack = RoutingStack.from(
            RouteImpl1(0),
            RouteImpl1(1)
        )
        assertEquals(2, stack.elements.size)
    }

    @Test
    fun size_diffRouteType_diffKey() {
        val stack = RoutingStack.from(
            RouteImpl1(0),
            RouteImpl2("1")
        )
        assertEquals(2, stack.elements.size)
    }

    @Test
    fun size_sameRouteValue_diffKey() {
        val stack = RoutingStack.from(
            RouteImpl1(0),
            RouteImpl1(0)
        )
        assertEquals(2, stack.elements.size)
    }

    @Test
    fun clear_diffRouteType_diffKey() {
        val stack = RoutingStack.from(
            RouteImpl1(0),
            RouteImpl2("1")
        )
        val newStack = stack.clear()
        assertEquals(2, stack.elements.size)
        assertEquals(0, newStack.elements.size)
    }

    @Test
    fun clearKey_whichKeyIsNotInStack() {
        val stack = RoutingStack.from(
            RouteImpl1(0),
            RouteImpl2("1")
        )
        val newStack = stack.clear(Key()) // 0, "1"
        assertEquals(2, stack.elements.size)
        assertEquals(2, newStack.elements.size)
        assertEquals(RouteImpl1(0), newStack.elements[0].route)
        assertEquals(RouteImpl2("1"), newStack.elements[1].route)
    }

    @Test
    fun clearKey_whichKeyIsInStack() {
        val stack = RoutingStack.from(
            RouteImpl1(0),
            RouteImpl2("1")
        )
        val newStack = stack.clear(stack.first().key) // "1"
        assertEquals(2, stack.elements.size)
        assertEquals(1, newStack.elements.size)
        assertEquals(RouteImpl2("1"), newStack.elements[0].route)
    }

    @Test
    fun clearElement_whichElementIsInStack() {
        val stack = RoutingStack.from(
            RouteImpl1(0),
            RouteImpl2("1")
        )
        val newStack = stack.clear(stack.first()) // "1"
        assertEquals(2, stack.elements.size)
        assertEquals(1, newStack.elements.size)
        assertEquals(RouteImpl2("1"), newStack.elements[0].route)
    }

    @Test
    fun clearRoute_whichRouteValueIsInStack() {
        val stack = RoutingStack.from(
            RouteImpl1(0),
            RouteImpl2("1"),
            RouteImpl1(0),
            RouteImpl1(0)
        )
        val newStack = stack.clear(RouteImpl1(0)) // "1"
        assertEquals(4, stack.elements.size)
        assertEquals(1, newStack.elements.size)
        assertEquals(RouteImpl2("1"), newStack.elements[0].route)
    }

    @Test
    fun pushElement_whichRouteValueIsNotInStack_expectAddToTop() {
        val stack = RoutingStack.from(
            RouteImpl1(0),
            RouteImpl1(1)
        )
        val newStack = stack.push(ElementImpl(RouteImpl1(2), Key())) // 0, 1, 2
        assertEquals(2, stack.elements.size)
        assertEquals(3, newStack.elements.size)
        assertEquals(RouteImpl1(0), newStack.elements[0].route)
        assertEquals(RouteImpl1(1), newStack.elements[1].route)
        assertEquals(RouteImpl1(2), newStack.elements[2].route)
    }

    @Test
    fun pushElement_whichRouteValueIsInStack_expectAddToTop() {
        val stack = RoutingStack.from(
            RouteImpl1(0),
            RouteImpl1(1)
        )
        val newStack = stack.push(ElementImpl(RouteImpl1(1), Key())) // 0, 1, 1
        assertEquals(2, stack.elements.size)
        assertEquals(3, newStack.elements.size)
        assertEquals(RouteImpl1(0), newStack.elements[0].route)
        assertEquals(RouteImpl1(1), newStack.elements[1].route)
        assertEquals(RouteImpl1(1), newStack.elements[2].route)
    }

    @Test
    fun pushElement_whichElementIsInStack_expectRemoveAndAddToTop() {
        val stack = RoutingStack.from(
            RouteImpl1(0),
            RouteImpl1(1)
        )
        val newStack = stack.push(stack.first()) // 1, 0
        assertEquals(2, stack.elements.size)
        assertEquals(2, newStack.elements.size)
        assertEquals(RouteImpl1(1), newStack.elements[0].route)
        assertEquals(RouteImpl1(0), newStack.elements[1].route)
    }

    @Test
    fun pushElement_whichRouteValueAndKeyIsInStack_expectRemoveAddToTop() {
        val stack = RoutingStack.from(
            RouteImpl1(0),
            RouteImpl1(1)
        )
        val newStack = stack.push(ElementImpl(RouteImpl1(0), stack.first().key)) // 1, 0
        assertEquals(2, stack.elements.size)
        assertEquals(2, newStack.elements.size)
        assertEquals(RouteImpl1(1), newStack.elements[0].route)
        assertEquals(RouteImpl1(0), newStack.elements[1].route)
    }

    @Test
    fun pushRoute_whichRouteValueIsNotInStack_expectAddToTop() {
        val stack = RoutingStack.from(
            RouteImpl1(0),
            RouteImpl1(1)
        )
        val newStack = stack.push(RouteImpl1(2)) // 0, 1, 2
        assertEquals(2, stack.elements.size)
        assertEquals(3, newStack.elements.size)
        assertEquals(RouteImpl1(0), newStack.elements[0].route)
        assertEquals(RouteImpl1(1), newStack.elements[1].route)
        assertEquals(RouteImpl1(2), newStack.elements[2].route)
    }

    @Test
    fun pushRoute_whichRouteValueIsInStack_expectAddToTop() {
        val stack = RoutingStack.from(
            RouteImpl1(0),
            RouteImpl1(1)
        )
        val newStack = stack.push(RouteImpl1(1)) // 0, 1, 1
        assertEquals(2, stack.elements.size)
        assertEquals(3, newStack.elements.size)
        assertEquals(RouteImpl1(0), newStack.elements[0].route)
        assertEquals(RouteImpl1(1), newStack.elements[1].route)
        assertEquals(RouteImpl1(1), newStack.elements[2].route)
    }

    @Test
    fun pushRoute_whichRouteIsInStack_expectAddToTop() {
        val stack = RoutingStack.from(
            RouteImpl1(0),
            RouteImpl1(1)
        )
        val newStack = stack.push(stack.first().route) // 0, 1, 0
        assertEquals(2, stack.elements.size)
        assertEquals(3, newStack.elements.size)
        assertEquals(RouteImpl1(0), newStack.elements[0].route)
        assertEquals(RouteImpl1(1), newStack.elements[1].route)
        assertEquals(RouteImpl1(0), newStack.elements[2].route)
    }

    @Test
    fun pushDistinct_whichRouteValueIsNotInStack_expectAddToTop() {
        val stack = RoutingStack.from(
            RouteImpl1(0),
            RouteImpl1(1),
            RouteImpl1(2)
        )
        val newStack = stack.pushDistinct(RouteImpl1(3)) // 0, 1, 2, 3
        assertEquals(4, newStack.elements.size)
        assertEquals(RouteImpl1(0), newStack.elements[0].route)
        assertEquals(RouteImpl1(1), newStack.elements[1].route)
        assertEquals(RouteImpl1(2), newStack.elements[2].route)
        assertEquals(RouteImpl1(3), newStack.elements[3].route)
    }

    @Test
    fun pushDistinct_whichRouteValueIsInStack_expectRemoveAndAddToTop() {
        val stack = RoutingStack.from(
            RouteImpl1(0),
            RouteImpl1(1),
            RouteImpl1(2)
        )
        val newStack = stack.pushDistinct(RouteImpl1(1)) // 0, 2, 1
        assertEquals(3, newStack.elements.size)
        assertEquals(RouteImpl1(0), newStack.elements[0].route)
        assertEquals(RouteImpl1(2), newStack.elements[1].route)
        assertEquals(RouteImpl1(1), newStack.elements[2].route)
    }

    @Test
    fun pushDistinct_whichRouteValueIsInStackMultiTimes_expectRemoveAndAddToTop() {
        val stack = RoutingStack.from(
            RouteImpl1(0),
            RouteImpl1(1),
            RouteImpl1(1),
            RouteImpl1(2)
        )
        assertEquals(4, stack.elements.size)
        val newStack = stack.pushDistinct(RouteImpl1(1)) // 0, 2, 1
        assertEquals(3, newStack.elements.size)
        assertEquals(RouteImpl1(0), newStack.elements[0].route)
        assertEquals(RouteImpl1(2), newStack.elements[1].route)
        assertEquals(RouteImpl1(1), newStack.elements[2].route)
    }

    @Test
    fun pop() {
        val stack = RoutingStack.from(
            RouteImpl1(0),
            RouteImpl1(1),
            RouteImpl1(2)
        )
        val newStack = stack.pop()
        assertEquals(3, stack.elements.size)
        assertEquals(2, newStack.elements.size)
        assertEquals(RouteImpl1(0), newStack.elements[0].route)
        assertEquals(RouteImpl1(1), newStack.elements[1].route)
    }

    @Test
    fun pop_whenEmpty() {
        val stack = RoutingStack.from(
            RouteImpl1(0),
            RouteImpl1(1),
            RouteImpl1(2)
        )
        val newStack = stack
            .pop() // 0, 1
            .pop() // 0
            .pop() //
            .pop() //
        assertEquals(3, stack.elements.size)
        assertEquals(0, newStack.elements.size)
    }

    @Test
    fun popUntilPredicate_routeValue_expectPopUntilFirstMeet() {
        val stack =
            RoutingStack.from(
                RouteImpl1(0),
                RouteImpl1(1),
                RouteImpl1(2),
                RouteImpl1(1),
                RouteImpl1(2)
            )
        val newStack =
            stack.popUntilPredicate { element -> element.route == RouteImpl1(1) } // 0, 1, 2, 1
        assertEquals(5, stack.elements.size)
        assertEquals(4, newStack.elements.size)
        assertEquals(RouteImpl1(0), newStack.elements[0].route)
        assertEquals(RouteImpl1(1), newStack.elements[1].route)
        assertEquals(RouteImpl1(2), newStack.elements[2].route)
        assertEquals(RouteImpl1(1), newStack.elements[3].route)
    }

    @Test
    fun popUntilPredicate_topRouteValue_expectDoNothing() {
        val stack =
            RoutingStack.from(
                RouteImpl1(0),
                RouteImpl1(1),
                RouteImpl1(2),
                RouteImpl1(1),
                RouteImpl1(2)
            )
        val newStack =
            stack.popUntilPredicate { element -> element.route == RouteImpl1(2) } // 0, 1, 2, 1, 2
        assertEquals(5, stack.elements.size)
        assertEquals(5, newStack.elements.size)
        assertEquals(RouteImpl1(0), newStack.elements[0].route)
        assertEquals(RouteImpl1(1), newStack.elements[1].route)
        assertEquals(RouteImpl1(2), newStack.elements[2].route)
        assertEquals(RouteImpl1(1), newStack.elements[3].route)
        assertEquals(RouteImpl1(2), newStack.elements[4].route)
        assertEquals(stack.elements[4].key, newStack.elements[4].key)
    }

    @Test
    fun popUntilPredicate_key_expectPopUntilExact() {
        val stack =
            RoutingStack.from(
                RouteImpl1(0),
                RouteImpl1(1),
                RouteImpl1(2),
                RouteImpl1(1),
                RouteImpl1(2)
            )
        val newStack =
            stack.popUntilPredicate { element -> element.key == stack.elements[1].key } // 0, 1
        assertEquals(5, stack.elements.size)
        assertEquals(2, newStack.elements.size)
        assertEquals(RouteImpl1(0), newStack.elements[0].route)
        assertEquals(RouteImpl1(1), newStack.elements[1].route)
    }

    @Test
    fun popUntilPredicate_notMatch_expectPopAll() {
        val stack =
            RoutingStack.from(
                RouteImpl1(0),
                RouteImpl1(1),
                RouteImpl1(2),
                RouteImpl1(1),
                RouteImpl1(2)
            )
        val newStack = stack.popUntilPredicate { false } //
        assertEquals(5, stack.elements.size)
        assertEquals(0, newStack.elements.size)
    }

    @Test
    fun popUntil_element_expectPopUntilExact() {
        val stack =
            RoutingStack.from(
                RouteImpl1(0),
                RouteImpl1(1),
                RouteImpl1(2),
                RouteImpl1(1),
                RouteImpl1(2)
            )
        val newStack = stack.popUntil(stack.elements[1]) // 0, 1
        assertEquals(5, stack.elements.size)
        assertEquals(2, newStack.elements.size)
        assertEquals(RouteImpl1(0), newStack.elements[0].route)
        assertEquals(RouteImpl1(1), newStack.elements[1].route)
    }

    @Test
    fun popUntil_route_expectPopUntilFirstMeet() {
        val stack =
            RoutingStack.from(
                RouteImpl1(0),
                RouteImpl1(1),
                RouteImpl1(2),
                RouteImpl1(1),
                RouteImpl1(2)
            )
        val newStack = stack.popUntil(stack.elements[1].route) // 0, 1, 2, 1
        assertEquals(5, stack.elements.size)
        assertEquals(4, newStack.elements.size)
        assertEquals(RouteImpl1(0), newStack.elements[0].route)
        assertEquals(RouteImpl1(1), newStack.elements[1].route)
        assertEquals(RouteImpl1(2), newStack.elements[2].route)
        assertEquals(RouteImpl1(1), newStack.elements[3].route)
    }

    @Test
    fun popUntil_routeValue_expectPopUntilFirstMeet() {
        val stack =
            RoutingStack.from(
                RouteImpl1(0),
                RouteImpl1(1),
                RouteImpl1(2),
                RouteImpl1(1),
                RouteImpl1(2)
            )
        val newStack = stack.popUntil(RouteImpl1(1)) // 0, 1, 2, 1
        assertEquals(5, stack.elements.size)
        assertEquals(4, newStack.elements.size)
        assertEquals(RouteImpl1(0), newStack.elements[0].route)
        assertEquals(RouteImpl1(1), newStack.elements[1].route)
        assertEquals(RouteImpl1(2), newStack.elements[2].route)
        assertEquals(RouteImpl1(1), newStack.elements[3].route)
    }

    @Test
    fun popUntil_key_expectPopUntilExact() {
        val stack =
            RoutingStack.from(
                RouteImpl1(0),
                RouteImpl1(1),
                RouteImpl1(2),
                RouteImpl1(1),
                RouteImpl1(2)
            )
        val newStack = stack.popUntil(stack.elements[1].key) // 0, 1
        assertEquals(5, stack.elements.size)
        assertEquals(2, newStack.elements.size)
        assertEquals(RouteImpl1(0), newStack.elements[0].route)
        assertEquals(RouteImpl1(1), newStack.elements[1].route)
    }

    @Test
    fun replaceTopWith_onEmptyStack_expectAddToTop() {
        val stack = RoutingStack.empty<Route>()
        val newStack = stack.replaceTopWith(RouteImpl1(0)) // 0
        assertEquals(1, newStack.elements.size)
        assertEquals(RouteImpl1(0), newStack.elements.first().route)
    }

    @Test
    fun replaceTopWith_onNonEmptyStack_expectReplaceTop() {
        val stack = RoutingStack.from(
            RouteImpl1(0),
            RouteImpl1(1),
            RouteImpl1(2)
        )
        val newStack = stack.replaceTopWith(RouteImpl1(3)) // 0, 1, 3
        assertEquals(3, stack.elements.size)
        assertEquals(RouteImpl1(0), newStack.elements[0].route)
        assertEquals(RouteImpl1(1), newStack.elements[1].route)
        assertEquals(RouteImpl1(3), newStack.elements[2].route)
    }

    @Test
    fun replaceTopWith_whichKeyIsInStack_expectRemoveAndReplaceTop() {
        val stack = RoutingStack.from(
            RouteImpl1(0),
            RouteImpl1(1),
            RouteImpl1(2)
        )
        val newStack =
            stack.replaceTopWith(
                RoutingStack.Element(
                    RouteImpl1(3),
                    key = stack.first().key
                )
            ) // 1, 3
        assertEquals(2, newStack.elements.size)
        assertEquals(RouteImpl1(1), newStack.elements[0].route)
        assertEquals(RouteImpl1(3), newStack.elements[1].route)
    }
}
