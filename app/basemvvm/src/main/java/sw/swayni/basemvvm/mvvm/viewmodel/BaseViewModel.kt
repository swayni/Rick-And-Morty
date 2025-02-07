package sw.swayni.basemvvm.mvvm.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavDirections
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import sw.swayni.basemvvm.mvvm.core.Event
import sw.swayni.basemvvm.mvvm.enums.NavigationAction
import kotlin.properties.Delegates

abstract class BaseViewModel <ViewState : BaseViewState, ViewAction: BaseAction>(initialState: ViewState) : ViewModel() {

    private val _uiStateFlow = MutableStateFlow(initialState)
    val uiStateFlow = _uiStateFlow.asStateFlow()

    private val _navigation = MutableSharedFlow<Event<NavigationAction>>()
    val navigation = _navigation.asSharedFlow()

    protected var state by Delegates.observable(initialState) { _, old, new ->
        viewModelScope.launch {
            if (new != old) {
                _uiStateFlow.emit(new)
            }
        }
    }

    fun navigate(navDirections: NavDirections) {
        viewModelScope.launch {
            _navigation.emit(Event(NavigationAction.ToDirection(navDirections)))
        }
    }

    fun navigateBack() {
        viewModelScope.launch {
            _navigation.emit(Event(NavigationAction.Back))
        }
    }

    fun sendAction(viewAction: ViewAction) {
        state = onReduceState(viewAction)
    }

    fun loadData() {
        onLoadData()
    }

    protected abstract fun cleanState(): ViewState
    protected open fun onLoadData() {}

    protected abstract fun onReduceState(viewAction: ViewAction): ViewState
}