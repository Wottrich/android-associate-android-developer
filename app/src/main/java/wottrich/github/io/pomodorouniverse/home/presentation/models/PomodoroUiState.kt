package wottrich.github.io.pomodorouniverse.home.presentation.models

import wottrich.github.io.pomodorouniverse.home.domain.models.PomodoroType

data class PomodoroState(
    val playerStatus: PomodoroPlayerStatus = PomodoroPlayerStatus.STOPPED,
    val type: PomodoroType = PomodoroType.WORK
)

data class PomodoroUiState(
    val pomodoroPlayerState: PomodoroPlayerState = PomodoroPlayerState()
)

data class PomodoroPlayerState(
    val remainingPercentage: Float = 100f,
    val timeFormatted: String = "00:00:00",
    val timeInMillis: Long = 0
)

enum class PomodoroPlayerStatus {
    RUNNING,
    STOPPED,
    PAUSED
}