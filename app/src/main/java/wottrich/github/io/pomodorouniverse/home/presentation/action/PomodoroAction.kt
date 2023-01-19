package wottrich.github.io.pomodorouniverse.home.presentation.action

interface PomodoroAction {
    fun sendAction(action: Action)

    sealed class Action {
        object OnPauseLifecycle : Action()
        object OnResumeLifecycle : Action()
        object PomodoroButtonClicked : Action()
        object StopPomodoro : Action()
    }
}