package wottrich.github.io.pomodorouniverse.home.presentation.view

import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.core.widget.TextViewCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import wottrich.github.io.pomodorouniverse.R
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
        binding?.newPomodoroButton?.setOnClickListener {
            findNavController().navigate(R.id.navigateToNewPomodoroNav)
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
        val statusRes = when (pomodoroState.type) {
            PomodoroType.WORK -> getWorkPomodoroStatus(pomodoroState.playerStatus)
            PomodoroType.BREAK -> getBreakPomodoroStatus(pomodoroState.playerStatus)
            PomodoroType.LONG_BREAK -> TODO()
        }
        statusRes?.let {
            binding?.pomodoroStatusTextView?.text = getString(it)
        }
    }

    @StringRes
    private fun getWorkPomodoroStatus(playerStatus: PomodoroPlayerStatus): Int? {
        return when (playerStatus) {
            PomodoroPlayerStatus.RUNNING -> R.string.pomodoro_status_running_work_state_label
            PomodoroPlayerStatus.PAUSED -> R.string.pomodoro_status_paused_work_state_label
            PomodoroPlayerStatus.STOPPED -> null
        }
    }

    @StringRes
    private fun getBreakPomodoroStatus(playerStatus: PomodoroPlayerStatus): Int? {
        return when (playerStatus) {
            PomodoroPlayerStatus.RUNNING -> R.string.pomodoro_status_running_break_state_label
            PomodoroPlayerStatus.PAUSED -> R.string.pomodoro_status_paused_break_state_label
            PomodoroPlayerStatus.STOPPED -> null
        }
    }

    private fun setupPomodoroSnackbar(pomodoroState: PomodoroState) {
        val statusRes = when (pomodoroState.type) {
            PomodoroType.WORK -> getWorkPomodoroSnackbarMessage(pomodoroState.playerStatus)
            PomodoroType.BREAK -> getBreakPomodoroSnackbarMessage(pomodoroState.playerStatus)
            PomodoroType.LONG_BREAK -> TODO()
        }
        if (statusRes != null) {
            showSnackbar(getString(statusRes), binding?.pomodoroButton)
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

    @StringRes
    private fun getWorkPomodoroSnackbarMessage(playerStatus: PomodoroPlayerStatus): Int? {
        return when (playerStatus) {
            PomodoroPlayerStatus.RUNNING -> R.string.pomodoro_snackbar_running_work_state_label
            PomodoroPlayerStatus.PAUSED -> R.string.pomodoro_snackbar_paused_work_state_label
            PomodoroPlayerStatus.STOPPED -> null
        }
    }

    @StringRes
    private fun getBreakPomodoroSnackbarMessage(playerStatus: PomodoroPlayerStatus): Int? {
        return when (playerStatus) {
            PomodoroPlayerStatus.RUNNING -> R.string.pomodoro_snackbar_running_break_state_label
            PomodoroPlayerStatus.PAUSED -> R.string.pomodoro_snackbar_paused_break_state_label
            PomodoroPlayerStatus.STOPPED -> null
        }
    }

    private fun setupPomodoroButton(pomodoroState: PomodoroState) {
        val buttonLabelRes = when (pomodoroState.type) {
            PomodoroType.WORK -> getWorkButtonLabelFromPlayerStatus(pomodoroState.playerStatus)
            PomodoroType.BREAK -> getBreakButtonLabelFromPlayerStatus(pomodoroState.playerStatus)
            PomodoroType.LONG_BREAK -> TODO()
        }
        binding?.pomodoroButton?.text = getString(buttonLabelRes)
    }

    @StringRes
    private fun getWorkButtonLabelFromPlayerStatus(playerStatus: PomodoroPlayerStatus): Int {
        return when (playerStatus) {
            PomodoroPlayerStatus.RUNNING -> R.string.pomodoro_button_running_work_state_label
            PomodoroPlayerStatus.STOPPED -> R.string.pomodoro_button_stopped_work_state_label
            PomodoroPlayerStatus.PAUSED -> R.string.pomodoro_button_paused_work_state_label
        }
    }

    @StringRes
    private fun getBreakButtonLabelFromPlayerStatus(playerStatus: PomodoroPlayerStatus): Int {
        return when (playerStatus) {
            PomodoroPlayerStatus.RUNNING -> R.string.pomodoro_button_running_break_state_label
            PomodoroPlayerStatus.STOPPED -> R.string.pomodoro_button_stopped_break_state_label
            PomodoroPlayerStatus.PAUSED -> R.string.pomodoro_button_paused_break_state_label
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