package wottrich.github.io.pomodorouniverse.home.presentation.view

import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.TextViewCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import wottrich.github.io.pomodorouniverse.databinding.FragmentPomodoroBinding
import wottrich.github.io.pomodorouniverse.home.domain.models.PomodoroType
import wottrich.github.io.pomodorouniverse.home.presentation.action.PomodoroAction
import wottrich.github.io.pomodorouniverse.home.presentation.models.PomodoroPlayerStatus
import wottrich.github.io.pomodorouniverse.home.presentation.models.PomodoroState
import wottrich.github.io.pomodorouniverse.home.presentation.models.PomodoroUiState
import wottrich.github.io.pomodorouniverse.home.presentation.viewmodels.PomodoroViewModel

@AndroidEntryPoint
class PomodoroFragment : Fragment() {

    private var binding: FragmentPomodoroBinding? = null
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
        binding?.pomodoroButton?.setOnClickListener {
            viewModel.sendAction(PomodoroAction.Action.PomodoroButtonClicked)
        }
    }

    private fun setupObservers() {
        viewModel.uiState.observe(viewLifecycleOwner, ::handleUiState)
        viewModel.pomodoroState.observe(viewLifecycleOwner, ::handlePomodoroState)
    }

    private fun handlePomodoroState(state: PomodoroState) {
        setupPomodoroSnackbar(state)
        setupPomodoroButton(state)
        setupPomodoroStatus(state)
    }

    private fun handleUiState(state: PomodoroUiState) {
        val playerState = state.pomodoroPlayerState
        binding?.pomodoroTimerTextView?.text = playerState.timeFormatted
        binding?.circleProgressBar?.progress = playerState.remainingPercentage
    }

    private fun setupPomodoroStatus(pomodoroState: PomodoroState) {
        val status = when (pomodoroState.type) {
            PomodoroType.WORK -> getWorkPomodoroStatus(pomodoroState.playerStatus)
            PomodoroType.BREAK -> getBreakPomodoroStatus(pomodoroState.playerStatus)
        }
        binding?.pomodoroStatusTextView?.text = status
    }

    private fun getWorkPomodoroStatus(playerStatus: PomodoroPlayerStatus): String? {
        return when (playerStatus) {
            PomodoroPlayerStatus.RUNNING -> "Foque no trabalho!"
            PomodoroPlayerStatus.PAUSED -> "Tome o tempo que for preciso para voltar \uD83D\uDE0A"
            PomodoroPlayerStatus.STOPPED -> null
        }
    }

    private fun getBreakPomodoroStatus(playerStatus: PomodoroPlayerStatus): String? {
        return when (playerStatus) {
            PomodoroPlayerStatus.RUNNING -> "Seu intervalo é sagrado!"
            PomodoroPlayerStatus.PAUSED -> "Tome o tempo que for preciso para voltar \uD83D\uDE0A"
            PomodoroPlayerStatus.STOPPED -> null
        }
    }

    private fun setupPomodoroSnackbar(pomodoroState: PomodoroState) {
        val status = when (pomodoroState.type) {
            PomodoroType.WORK -> getWorkPomodoroSnackbarMessage(pomodoroState.playerStatus)
            PomodoroType.BREAK -> getBreakPomodoroSnackbarMessage(pomodoroState.playerStatus)
        }
        if (status != null) {
            showSnackbar(status, binding?.pomodoroButton)
        }
    }

    private fun showSnackbar(status: String, anchorView: View?) {
        binding?.root?.let {
            Snackbar.make(it, status, Snackbar.LENGTH_LONG).apply {
                anchorView?.let {
                    this.anchorView = anchorView
                }
            }.show()
        }
    }

    private fun getWorkPomodoroSnackbarMessage(playerStatus: PomodoroPlayerStatus): String? {
        return when (playerStatus) {
            PomodoroPlayerStatus.RUNNING -> "Você está trabalhando..."
            PomodoroPlayerStatus.PAUSED -> "Você pausou seu trabalho"
            PomodoroPlayerStatus.STOPPED -> null
        }
    }

    private fun getBreakPomodoroSnackbarMessage(playerStatus: PomodoroPlayerStatus): String? {
        return when (playerStatus) {
            PomodoroPlayerStatus.RUNNING -> "Aproveite seu intervalo!"
            PomodoroPlayerStatus.PAUSED -> "Você pausou seu intervalo"
            PomodoroPlayerStatus.STOPPED -> null
        }
    }

    private fun setupPomodoroButton(pomodoroState: PomodoroState) {
        val buttonLabel = when (pomodoroState.type) {
            PomodoroType.WORK -> getWorkButtonLabelFromPlayerStatus(pomodoroState.playerStatus)
            PomodoroType.BREAK -> getBreakButtonLabelFromPlayerStatus(pomodoroState.playerStatus)
        }
        binding?.pomodoroButton?.text = buttonLabel
    }

    private fun getWorkButtonLabelFromPlayerStatus(playerStatus: PomodoroPlayerStatus): String {
        return when (playerStatus) {
            PomodoroPlayerStatus.RUNNING -> "Pausar"
            PomodoroPlayerStatus.STOPPED -> "Começar a trabalhar"
            PomodoroPlayerStatus.PAUSED -> "Continuar"
        }
    }

    private fun getBreakButtonLabelFromPlayerStatus(playerStatus: PomodoroPlayerStatus): String {
        return when (playerStatus) {
            PomodoroPlayerStatus.RUNNING -> "Pausar"
            PomodoroPlayerStatus.STOPPED -> "Começar o intervalo"
            PomodoroPlayerStatus.PAUSED -> "Continuar"
        }
    }

    private fun setupAutoSizeTextSupportApiLevel() {
        binding?.pomodoroTimerTextView?.let {
            TextViewCompat.setAutoSizeTextTypeUniformWithConfiguration(
                it,
                10, 28, 1,
                TypedValue.COMPLEX_UNIT_DIP
            )
        }
        binding?.pomodoroStatusTextView?.let {
            TextViewCompat.setAutoSizeTextTypeUniformWithConfiguration(
                it,
                10, 28, 1,
                TypedValue.COMPLEX_UNIT_DIP
            )
        }
    }

    override fun onResume() {
        viewModel.sendAction(PomodoroAction.Action.OnResumeLifecycle)
        super.onResume()
    }

    override fun onPause() {
        viewModel.sendAction(PomodoroAction.Action.OnPauseLifecycle)
        super.onPause()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

}