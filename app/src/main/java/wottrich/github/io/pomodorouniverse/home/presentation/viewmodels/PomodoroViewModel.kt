package wottrich.github.io.pomodorouniverse.home.presentation.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.Locale
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import wottrich.github.io.pomodorouniverse.base.PomodoroTimer
import wottrich.github.io.pomodorouniverse.base.extensions.asLiveData
import wottrich.github.io.pomodorouniverse.home.domain.models.PomodoroType
import wottrich.github.io.pomodorouniverse.home.presentation.action.PomodoroAction
import wottrich.github.io.pomodorouniverse.home.presentation.models.PomodoroPlayerStatus
import wottrich.github.io.pomodorouniverse.home.presentation.models.PomodoroState
import wottrich.github.io.pomodorouniverse.home.presentation.models.PomodoroUiState

@HiltViewModel
class PomodoroViewModel @Inject constructor() : ViewModel(), PomodoroAction {

    private val _uiState = MutableLiveData(PomodoroUiState())
    val uiState = _uiState.asLiveData()

    private val _pomodoroState = MutableLiveData(PomodoroState())
    val pomodoroState = _pomodoroState.asLiveData()

    private var pomodoroType: PomodoroType = PomodoroType.WORK
    private val pomodoroTimer = PomodoroTimer().setPomodoroListeners()

    override fun sendAction(action: PomodoroAction.Action) {
        when (action) {
            PomodoroAction.Action.PomodoroButtonClicked -> playPomodoroTimer()
            PomodoroAction.Action.StopPomodoro -> stopPomodoroTimer()
        }
    }

    private fun playPomodoroTimer() {
        val currentState = checkNotNull(pomodoroState.value)
        when (currentState.playerStatus) {
            PomodoroPlayerStatus.RUNNING -> pausePomodoroTimer()
            PomodoroPlayerStatus.STOPPED -> startPomodoroTimer()
            PomodoroPlayerStatus.PAUSED -> startPomodoroTimer()
        }
    }

    private fun startPomodoroTimer() {
        pomodoroTimer.setPomodoroTotalTime(getPomodoroTimerByPomodoroType())
        pomodoroTimer.start()
    }

    private fun pausePomodoroTimer() {
        pomodoroTimer.pause()
    }

    private fun stopPomodoroTimer() {
        pomodoroTimer.stop()
    }

    private fun PomodoroTimer.setPomodoroListeners(): PomodoroTimer {
        onCirclePercentageChange = { animatedValue ->
            val state = checkNotNull(uiState.value)
            _uiState.value =
                state.copy(
                    pomodoroPlayerState = state.pomodoroPlayerState.copy(
                        remainingPercentage = animatedValue
                    )
                )
        }
        onClockTimeChange = { currentTime ->
            val timeFormatted = formatElapsedTime(currentTime)
            val state = checkNotNull(uiState.value)
            _uiState.value =
                state.copy(
                    pomodoroPlayerState = state.pomodoroPlayerState.copy(
                        timeFormatted = timeFormatted,
                        timeInMillis = currentTime
                    )
                )
        }
        onClockTimeFinished = {
            changePomodoroType()
        }
        onPlay = {
            updatePlayerStatus(PomodoroPlayerStatus.RUNNING)
        }
        onPause = {
            updatePlayerStatus(PomodoroPlayerStatus.PAUSED)
        }
        onStop = {
            updatePlayerStatus(PomodoroPlayerStatus.STOPPED)
        }
        return this
    }

    private fun updatePlayerStatus(playerStatus: PomodoroPlayerStatus) {
        val state = checkNotNull(pomodoroState.value)
        _pomodoroState.value = state.copy(playerStatus = playerStatus)
    }

    private fun changePomodoroType() {
        val nextPomodoroType = when (pomodoroType) {
            PomodoroType.WORK -> PomodoroType.BREAK
            PomodoroType.BREAK -> PomodoroType.WORK
        }
        pomodoroType = nextPomodoroType
        _pomodoroState.value = pomodoroState.value?.copy(type = pomodoroType)
    }

    private fun getPomodoroTimerByPomodoroType(): Long {
        return when (pomodoroType) {
            PomodoroType.WORK -> POMODORO_WORK_TIME_IN_MILLIS
            PomodoroType.BREAK -> POMODORO_BREAK_TIME_IN_MILLIS
        }
    }

    private fun formatElapsedTime(time: Long): String {
        return String.format(
            Locale.getDefault(), "%02d:%02d:%02d",
            TimeUnit.MILLISECONDS.toHours(time) % TimeUnit.DAYS.toHours(1),
            TimeUnit.MILLISECONDS.toMinutes(time) % TimeUnit.HOURS.toMinutes(1),
            TimeUnit.MILLISECONDS.toSeconds(time) % TimeUnit.MINUTES.toSeconds(1)
        )
    }

    companion object {
        private const val POMODORO_WORK_TIME_IN_MILLIS = 1.5e+6.toLong()
        private const val POMODORO_BREAK_TIME_IN_MILLIS = 300000L
    }
}