package wottrich.github.io.pomodorouniverse.newpomodorotimer.presentation.action

import wottrich.github.io.pomodorouniverse.newpomodorotimer.presentation.model.OptionChipUiModel

interface NewPomodoroNameViewModelAction {
    fun sendAction(action: Action)

    sealed class Action {
        object FetchInitialData : Action()
        data class HandleOptionSelected(val option: OptionChipUiModel) : Action()
        data class TextWatcher(val typedText: String) : Action()
        object HandleContinueButtonClick : Action()
    }
}