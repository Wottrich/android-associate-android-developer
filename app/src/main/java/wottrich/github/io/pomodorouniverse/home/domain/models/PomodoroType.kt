package wottrich.github.io.pomodorouniverse.home.domain.models

enum class PomodoroType {
    WORK, BREAK, LONG_BREAK;

    fun isWork() = this == WORK
    fun isBreak() = this == BREAK
}