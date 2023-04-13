package wottrich.github.io.pomodorouniverse.newpomodorotimer.presentation.state

import wottrich.github.io.pomodorouniverse.R

data class NewPomodoroTimeConfigViewModelUiState(
    val focusTimeComponent: TimeConfigurationCardUiModel = TimeConfigurationCardUiModel(SectionTimeConfigType.FOCUS_TIME),
    val breakTimeComponent: TimeConfigurationCardUiModel = TimeConfigurationCardUiModel(SectionTimeConfigType.BREAK_TIME),
    val longBreakTimeComponent: TimeConfigurationCardUiModel = TimeConfigurationCardUiModel(SectionTimeConfigType.LONG_BREAK_TIME),
    val breakCountComponent: TimeConfigurationCardUiModel = TimeConfigurationCardUiModel(SectionTimeConfigType.BREAK_COUNT, timeFormat = R.string.breaks_format),
    val isLongBreakTimeEnabled: Boolean = false
)

data class TimeConfigurationCardUiModel(
    val section: SectionTimeConfigType,
    val timeResumed: Int = 0,
    val timeFormat: Int = R.string.minutes_format,
    val isOpened: Boolean = false
)