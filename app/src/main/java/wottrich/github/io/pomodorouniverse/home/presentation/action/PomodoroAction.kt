package wottrich.github.io.pomodorouniverse.home.presentation.action

interface PomodoroAction {
    fun sendAction(action: Action)

    sealed class Action {
        object PomodoroButtonClicked : Action()
        object StopPomodoro : Action()
    }
}