package com.gmail.jiangyang5157.common.utils

data class State(val name: String)

data class Event(val name: String)

data class CurrentStateEvent(val state: State, val event: Event)

class StateTransitionMap {
    private var stateMap = mutableMapOf<CurrentStateEvent, State>()
    private var actionMap = mutableMapOf<CurrentStateEvent, () -> Unit>()

    fun addTransition(current: State, event: Event, next: State) {
        val currentStateEvent = CurrentStateEvent(current, event)
        stateMap[currentStateEvent] = next
    }

    fun addActionableTransition(current: State, event: Event, next: State, action: () -> Unit) {
        val currentStateEvent = CurrentStateEvent(current, event)
        stateMap[currentStateEvent] = next
        actionMap[currentStateEvent] = action
    }

    fun getNextState(currentStateEvent: CurrentStateEvent): State? {
        return when (currentStateEvent) {
            in stateMap -> {
                actionMap[currentStateEvent]?.invoke()
                stateMap[currentStateEvent]
            }
            else -> null
        }
    }

    fun getStates(): List<State> {
        val result = mutableSetOf<State>()
        stateMap.filterKeys { result.add(it.state) }
        stateMap.filterValues { result.add(it) }
        return result.toList()
    }

    fun getEvents(): List<Event> {
        val result = mutableSetOf<Event>()
        stateMap.filterKeys { result.add(it.event) }
        return result.toList()
    }
}

class StateMachine constructor(
    private var current: State,
    private val stateTransitionMap: StateTransitionMap
) {

    fun send(event: Event): Boolean {
        val currentStateEvent = CurrentStateEvent(current, event)
        val nextState = stateTransitionMap.getNextState(currentStateEvent) ?: return false
        println("Received $event\nTransitioning from $current to $nextState")
        current = nextState
        return true
    }

    fun send(events: List<Event>): Boolean {
        for (event in events) {
            val done = send(event)
            if (!done) {
                return false
            }
        }
        return true
    }
}
