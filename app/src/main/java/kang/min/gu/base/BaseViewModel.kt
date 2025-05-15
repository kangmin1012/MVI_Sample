package kang.min.gu.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.runningFold
import kotlinx.coroutines.flow.stateIn

abstract class BaseViewModel<State : Reducer.ViewState, Event : Reducer.ViewEvent, Effect : Reducer.ViewEffect>(
    initialState: State,
    private val reducer: Reducer<State, Event, Effect>
) : ViewModel() {

    private val _event = Channel<Event>(capacity = Channel.CONFLATED)

    val state = _event.receiveAsFlow()
        .runningFold(initialState, ::reduceState)
        .stateIn(viewModelScope, SharingStarted.Eagerly, initialState)

    private val _effects = Channel<Effect>(capacity = Channel.CONFLATED)
    val effect = _effects.receiveAsFlow()

    private fun reduceState(state: State, event: Event): State {
        val (newState, effect) = reducer.reduce(state, event)

        effect?.let {
            _effects.trySend(effect)
        }

        return newState
    }

    fun sendEvent(event: Event) {
        _event.trySend(event)
    }

    override fun onCleared() {
        super.onCleared()
        _event.cancel()
        _effects.cancel()
    }
}