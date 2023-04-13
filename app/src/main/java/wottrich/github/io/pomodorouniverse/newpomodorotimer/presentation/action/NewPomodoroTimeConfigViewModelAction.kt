package wottrich.github.io.pomodorouniverse.newpomodorotimer.presentation.action

import wottrich.github.io.pomodorouniverse.newpomodorotimer.presentation.state.SectionTimeConfigType

interface NewPomodoroTimeConfigViewModelAction {
    fun sendAction(action: Action)

    sealed class Action {
        object FetchInitialData : Action()
        data class SectionSelected(val section: SectionTimeConfigType) : Action()
        object HandleContinueButtonClick : Action()
    }
}