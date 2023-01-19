package wottrich.github.io.pomodorouniverse.home.presentation.models

data class PomodoroPlayerState(
    val remainingPercentage: Float = 100f,
    val timeFormatted: String = POMODORO_PLAYER_STATE_TIME_FORMATTED_DEFAULT,
    val timeInMillis: Long = 0
) {
    companion object {
        private const val POMODORO_PLAYER_STATE_TIME_FORMATTED_DEFAULT = "00:00:00"
    }
}