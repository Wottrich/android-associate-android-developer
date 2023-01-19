package wottrich.github.io.pomodorouniverse.home.presentation.models

import wottrich.github.io.pomodorouniverse.home.domain.models.PomodoroType

data class PomodoroState(
    val playerStatus: PomodoroPlayerStatus = PomodoroPlayerStatus.STOPPED,
    val type: PomodoroType = PomodoroType.WORK
)