package wottrich.github.io.pomodorouniverse.newpomodorotimer.presentation.effect

import wottrich.github.io.pomodorouniverse.newpomodorotimer.presentation.model.OptionChipUiModel

sealed class NewPomodoroNameViewModelUiEffect {
    data class OnUpdateSelectedItem(
        val oldItem: OptionChipUiModel?,
        val newItem: OptionChipUiModel
    ) : NewPomodoroNameViewModelUiEffect()
    data class OnRemoveSelectedItem(
        val item: OptionChipUiModel?
    ) : NewPomodoroNameViewModelUiEffect()
}