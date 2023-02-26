package wottrich.github.io.pomodorouniverse.home.presentation.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import wottrich.github.io.pomodorouniverse.R
import wottrich.github.io.pomodorouniverse.base.PomodoroTimer
import wottrich.github.io.pomodorouniverse.base.StringFormatter
import wottrich.github.io.pomodorouniverse.base.StringResProvider
import wottrich.github.io.pomodorouniverse.base.extensions.asLiveData
import wottrich.github.io.pomodorouniverse.data.NotificationModel
import wottrich.github.io.pomodorouniverse.home.data.PomodoroNotificationChannels
import wottrich.github.io.pomodorouniverse.home.data.PomodoroNotificationManager
import wottrich.github.io.pomodorouniverse.home.domain.models.PomodoroType
import wottrich.github.io.pomodorouniverse.home.presentation.action.PomodoroAction
import wottrich.github.io.pomodorouniverse.home.presentation.models.PomodoroPlayerStatus
import wottrich.github.io.pomodorouniverse.home.presentation.models.PomodoroState
import wottrich.github.io.pomodorouniverse.home.presentation.models.PomodoroUiState
import wottrich.github.io.pomodorouniverse.home.presentation.models.updateDescription

@HiltViewModel
class PomodoroViewModel @Inject constructor(
    private val notificationManager: PomodoroNotificationManager,
    private val stringResProvider: StringResProvider
) : ViewModel(), PomodoroAction {

    private val _uiState = MutableLiveData(PomodoroUiState())
    val uiState = _uiState.asLiveData()

    private val _pomodoroState = MutableLiveData(PomodoroState())
    val pomodoroState = _pomodoroState.asLiveData()

    private var pomodoroType: PomodoroType = PomodoroType.WORK
    private val pomodoroTimer = PomodoroTimer().setPomodoroListeners()

    private val pomodoroTimerNotification = NotificationModel().setNotificationConfiguration()
    private var shouldUpdateNotification = false

    override fun sendAction(action: PomodoroAction.Action) {
        when (action) {
            PomodoroAction.Action.PomodoroButtonClicked -> playPomodoroTimer()
            PomodoroAction.Action.StopPomodoro -> stopPomodoroTimer()
            PomodoroAction.Action.OnPauseLifecycle -> handleOnPauseAction()
            PomodoroAction.Action.OnResumeLifecycle -> handleOnResumeAction()
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

    private fun NotificationModel.setNotificationConfiguration(): NotificationModel {
        return copy(
            content = content.copy(
                title = stringResProvider.getString(R.string.notification_pomodoro_timer_title)
            ),
            configuration = configuration.copy(
                channelName = PomodoroNotificationChannels.POMODORO_TIMER_CHANNEL_ID
            )
        )
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
            val timeFormatted = StringFormatter.formatElapsedTime(currentTime)
            updateNotificationWithTimeFormattedIfNeeded(timeFormatted)
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

    private fun updateNotificationWithTimeFormattedIfNeeded(timeFormatted: String) {
        if (shouldUpdateNotification) {
            notificationManager.updateNotification(
                pomodoroTimerNotification.updateDescription(timeFormatted)
            )
        }
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

    private fun handleOnResumeAction() {
        shouldUpdateNotification = false
        notificationManager.removeNotification(pomodoroTimerNotification.id)
    }

    private fun handleOnPauseAction() {
        val state = checkNotNull(pomodoroState.value)
        if (state.playerStatus == PomodoroPlayerStatus.RUNNING) {
            shouldUpdateNotification = true
            notificationManager.buildNotification(pomodoroTimerNotification)
        }
    }

    companion object {
        private const val POMODORO_WORK_TIME_IN_MILLIS = 1.5e+6.toLong()
        private const val POMODORO_BREAK_TIME_IN_MILLIS = 300000L
    }
}