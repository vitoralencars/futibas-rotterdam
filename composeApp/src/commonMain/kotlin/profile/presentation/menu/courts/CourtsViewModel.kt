package profile.presentation.menu.courts

import androidx.lifecycle.viewModelScope
import common.base.BaseViewModel
import kotlinx.coroutines.launch
import profile.domain.usecase.GetCourtsUseCase

class CourtsViewModel(
    private val getCourtsUseCase: GetCourtsUseCase,
) : BaseViewModel<CourtsScreenState, CourtsSideEffect>(
    initialState = CourtsScreenState(emptyList())
) {

    init {
        viewModelScope.launch {
            val courts = getCourtsUseCase()
            updateState {
                CourtsScreenState(courts = courts)
            }
        }
    }

    fun handleCourtTapped(url: String) {
        viewModelScope.launch {
            emitSideEffect(CourtsSideEffect.OpenGoogleMaps(url))
        }
    }
}
