package wottrich.github.io.pomodorouniverse.base

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.os.CountDownTimer
import android.view.animation.LinearInterpolator
import wottrich.github.io.pomodorouniverse.home.presentation.models.PomodoroPlayerStatus

class PomodoroTimer {

    private var pomodoroTotalTime: Long
    private var pomodoroCurrentTime: Long

    constructor() : super() {
        pomodoroTotalTime = 0
        pomodoroCurrentTime = 0
    }

    constructor(pomodoroTotalTime: Long) : super() {
        this.pomodoroTotalTime = pomodoroTotalTime
        this.pomodoroCurrentTime = pomodoroTotalTime
    }

    var onCirclePercentageChange: (remainPercentage: Float) -> Unit = {}
    var onClockTimeChange: (currentTime: Long) -> Unit = {}
    var onClockTimeFinished: () -> Unit = {}
    var onPlay: () -> Unit = {}
    var onPause: () -> Unit = {}
    var onStop: () -> Unit = {}

    var pomodoroPlayerStatus: PomodoroPlayerStatus = PomodoroPlayerStatus.STOPPED
        private set

    private var clockTimer: CountDownTimer? = null
    private var animatorCircle: ValueAnimator? = null

    fun start() {
        when (pomodoroPlayerStatus) {
            PomodoroPlayerStatus.STOPPED -> {
                pomodoroCurrentTime = pomodoroTotalTime
                setupCountDownTimer(pomodoroTotalTime)
                setupObjectAnimator()
                setupAnimatorChangeListener()
                animatorCircle?.start()
                clockTimer?.start()
            }
            PomodoroPlayerStatus.PAUSED -> {
                setupCountDownTimer(pomodoroCurrentTime)
                animatorCircle?.resume()
                clockTimer?.start()
            }
            PomodoroPlayerStatus.RUNNING -> Unit
        }
        pomodoroPlayerStatus = PomodoroPlayerStatus.RUNNING
        onPlay()
    }

    fun pause() {
        animatorCircle?.pause()
        clockTimer?.cancel()
        pomodoroPlayerStatus = PomodoroPlayerStatus.PAUSED
        onPause()
    }

    fun stop() {
        animatorCircle?.cancel()
        animatorCircle?.removeAllUpdateListeners()
        clockTimer?.cancel()
        animatorCircle = null
        clockTimer = null
        pomodoroCurrentTime = 0L
        pomodoroPlayerStatus = PomodoroPlayerStatus.STOPPED
        onStop()
    }

    fun setPomodoroTotalTime(pomodoroTotalTime: Long) {
        this.pomodoroTotalTime = pomodoroTotalTime
    }

    private fun setupAnimatorChangeListener() {
        animatorCircle?.addUpdateListener {
            onCirclePercentageChange(it.animatedValue as Float)
        }
    }

    private fun setupCountDownTimer(millisUntilFinished: Long) {
        clockTimer = object : CountDownTimer(millisUntilFinished, COUNT_DOWN_INTERVAL_IN_MILLIS) {
            override fun onTick(millisUntilFinished: Long) {
                pomodoroCurrentTime = millisUntilFinished
                onClockTimeChange(millisUntilFinished)
            }

            override fun onFinish() {
                stop()
                this@PomodoroTimer.onClockTimeFinished()
            }
        }
    }

    private fun setupObjectAnimator() {
        animatorCircle = ObjectAnimator.ofFloat(0f, 100f)
            .apply {
                duration = pomodoroTotalTime
                interpolator = LinearInterpolator()
            }
    }

    companion object {
        private const val COUNT_DOWN_INTERVAL_IN_MILLIS = 100L
    }
}