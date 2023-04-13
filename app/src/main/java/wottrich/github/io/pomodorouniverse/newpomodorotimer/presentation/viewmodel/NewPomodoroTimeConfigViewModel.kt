package wottrich.github.io.pomodorouniverse.newpomodorotimer.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import wottrich.github.io.pomodorouniverse.base.extensions.asLiveData
import wottrich.github.io.pomodorouniverse.newpomodorotimer.presentation.action.NewPomodoroTimeConfigViewModelAction
import wottrich.github.io.pomodorouniverse.newpomodorotimer.presentation.state.NewPomodoroTimeConfigViewModelUiState
import wottrich.github.io.pomodorouniverse.newpomodorotimer.presentation.state.SectionTimeConfigType

class NewPomodoroTimeConfigViewModel : ViewModel(), NewPomodoroTimeConfigViewModelAction {

    private val _uiState = MutableLiveData(NewPomodoroTimeConfigViewModelUiState())
    val uiState = _uiState.asLiveData()

    override fun sendAction(action: NewPomodoroTimeConfigViewModelAction.Action) {
        when (action) {
            is NewPomodoroTimeConfigViewModelAction.Action.FetchInitialData -> handleFetchInitialData()
            is NewPomodoroTimeConfigViewModelAction.Action.SectionSelected -> handleSectionSelected(
                action.section
            )
            is NewPomodoroTimeConfigViewModelAction.Action.HandleContinueButtonClick -> handleContinueButtonClick()
        }
    }

    private fun handleFetchInitialData() {

    }

    private fun handleSectionSelected(sectionTimeConfigType: SectionTimeConfigType) {
        val state = checkNotNull(uiState.value)
        _uiState.value = state.copy(
            focusTimeComponent = state.focusTimeComponent.copy(
                isOpened = sectionTimeConfigType == SectionTimeConfigType.FOCUS_TIME
            ),
            breakTimeComponent = state.breakTimeComponent.copy(
                isOpened = sectionTimeConfigType == SectionTimeConfigType.BREAK_TIME
            ),
            longBreakTimeComponent = state.longBreakTimeComponent.copy(
                isOpened = sectionTimeConfigType == SectionTimeConfigType.LONG_BREAK_TIME
            ),
            breakCountComponent = state.breakCountComponent.copy(
                isOpened = sectionTimeConfigType == SectionTimeConfigType.BREAK_COUNT
            )
        )
    }

    private fun handleContinueButtonClick() {

    }
}