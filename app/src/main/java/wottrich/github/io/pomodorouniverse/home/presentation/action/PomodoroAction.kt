package wottrich.github.io.pomodorouniverse.home.presentation.action

interface PomodoroAction {
    fun sendAction(action: Action)

    sealed class Action {
        object PlayPomodoro : Action()
        object PausePomodoro : Action()
        object StopPomodoro : Action()
    }
}