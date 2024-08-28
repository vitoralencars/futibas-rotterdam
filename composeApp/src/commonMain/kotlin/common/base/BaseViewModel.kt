package common.base

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update

open class BaseViewModel<T, SE>(initialState: T) : ViewModel() {
    private val _state = MutableStateFlow(initialState)
    val state: StateFlow<T> get() = _state

    private val _sideEffect = Channel<SE>()
    val sideEffect = _sideEffect.receiveAsFlow()

    protected fun setState(newState: T) {
        _state.value = newState
    }

    protected fun updateState(update: (T) -> T) {
        _state.update(update)
    }

    protected suspend fun emitSideEffect(sideEffect: SE) {
        _sideEffect.send(sideEffect)
    }

    open fun onViewResumed() {}
}
