package wottrich.github.io.pomodorouniverse.home.presentation.models

import wottrich.github.io.pomodorouniverse.home.domain.models.PomodoroType

data class PomodoroUiState(
    val pomodoroPlayerState: PomodoroPlayerState = PomodoroPlayerState()
)

data class PomodoroPlayerState(
    val remainingPercentage: Float = 100f,
    val timeFormatted: String = "",
    val timeInMillis: Long = 0,
    val playerStatus: PomodoroPlayerStatus = PomodoroPlayerStatus.STOPPED,
    val type: PomodoroType = PomodoroType.WORK
)

enum class PomodoroPlayerStatus {
    RUNNING,
    STOPPED,
    PAUSED
}