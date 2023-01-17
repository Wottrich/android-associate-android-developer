package wottrich.github.io.pomodorouniverse.home.presentation.view

import android.os.Bundle
import android.os.CountDownTimer
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.core.widget.TextViewCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import java.util.Locale
import java.util.concurrent.TimeUnit
import wottrich.github.io.pomodorouniverse.databinding.FragmentPomodoroBinding
import wottrich.github.io.pomodorouniverse.home.domain.models.PomodoroType
import wottrich.github.io.pomodorouniverse.home.presentation.action.PomodoroAction
import wottrich.github.io.pomodorouniverse.home.presentation.models.PomodoroPlayerState
import wottrich.github.io.pomodorouniverse.home.presentation.models.PomodoroPlayerStatus
import wottrich.github.io.pomodorouniverse.home.presentation.models.PomodoroUiState
import wottrich.github.io.pomodorouniverse.home.presentation.viewmodels.PomodoroViewModel

@AndroidEntryPoint
class PomodoroFragment : Fragment() {

    private var binding: FragmentPomodoroBinding? = null

    private var pomodoroTimer: CountDownTimer? = null

    private val viewModel: PomodoroViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPomodoroBinding.inflate(
            inflater,
            container,
            false
        )
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListeners()
        setupObservers()
        setupAutoSizeTextSupportApiLevel()
    }

    private fun setupListeners() {
        binding?.playPomodoroButton?.setOnClickListener {
            viewModel.sendAction(PomodoroAction.Action.PlayPomodoro)
            setupPauseButton()
        }
        binding?.pausePomodoroButton?.setOnClickListener {
            viewModel.sendAction(PomodoroAction.Action.PausePomodoro)
            setupPlayButton()
        }
    }

    private fun setupObservers() {
        viewModel.uiState.observe(viewLifecycleOwner, ::handleUiState)
    }

    private fun handleUiState(state: PomodoroUiState) {
        val playerState = state.pomodoroPlayerState
        binding?.pomodoroTimerTextView?.text = playerState.timeFormatted
        setupPomodoroStatusTextView(playerState)
        binding?.circleProgressBar?.progress = playerState.remainingPercentage
    }

    private fun setupPomodoroStatusTextView(playerState: PomodoroPlayerState) {
        val status = when (playerState.playerStatus) {
            PomodoroPlayerStatus.RUNNING -> {
                when (playerState.type) {
                    PomodoroType.WORK -> "Você está trabalhando..."
                    PomodoroType.BREAK -> "Aproveite seu intervalo!"
                }
            }
            PomodoroPlayerStatus.STOPPED -> {
                setupPlayButton()
                when (playerState.type) {
                    PomodoroType.WORK -> "Começar a trabalhar"
                    PomodoroType.BREAK -> "Começar intervalo"
                }
            }
            PomodoroPlayerStatus.PAUSED -> {
                when (playerState.type) {
                    PomodoroType.WORK -> "Você pausou seu trabalho"
                    PomodoroType.BREAK -> "Você pausou seu intervalo"
                }
            }
        }
        binding?.pomodoroStatusTextView?.text = status
    }

    private fun setupAutoSizeTextSupportApiLevel() {
        binding?.pomodoroTimerTextView?.let {
            TextViewCompat.setAutoSizeTextTypeUniformWithConfiguration(
                it,
                10, 28, 1,
                TypedValue.COMPLEX_UNIT_DIP
            )
        }
    }

    private fun setupPlayButton() {
        binding?.pausePomodoroButton?.isVisible = false
        binding?.playPomodoroButton?.isVisible = true
    }

    private fun setupPauseButton() {
        binding?.pausePomodoroButton?.isVisible = true
        binding?.playPomodoroButton?.isVisible = false
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

}