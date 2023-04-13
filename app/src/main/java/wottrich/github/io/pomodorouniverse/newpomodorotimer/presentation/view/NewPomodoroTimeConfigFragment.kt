package wottrich.github.io.pomodorouniverse.newpomodorotimer.presentation.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import wottrich.github.io.pomodorouniverse.databinding.FragmentNewPomodoroTimeConfigBinding
import wottrich.github.io.pomodorouniverse.newpomodorotimer.presentation.action.NewPomodoroTimeConfigViewModelAction
import wottrich.github.io.pomodorouniverse.newpomodorotimer.presentation.state.NewPomodoroTimeConfigViewModelUiState
import wottrich.github.io.pomodorouniverse.newpomodorotimer.presentation.viewmodel.NewPomodoroTimeConfigViewModel

@AndroidEntryPoint
class NewPomodoroTimeConfigFragment : Fragment() {

    private var binding: FragmentNewPomodoroTimeConfigBinding? = null
    private val viewModel: NewPomodoroTimeConfigViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNewPomodoroTimeConfigBinding.inflate(
            inflater,
            container,
            false
        )
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObservers()
    }

    private fun setupObservers() {
        viewModel.uiState.observe(viewLifecycleOwner, ::handleUiState)
    }

    private fun handleUiState(state: NewPomodoroTimeConfigViewModelUiState) {
        binding?.apply {
            focusTimeComponent.setupCard(
                state.focusTimeComponent,
                onItemClick = {
                    viewModel.sendAction(
                        NewPomodoroTimeConfigViewModelAction.Action.SectionSelected(it)
                    )
                },
                onLeftButtonClick = { },
                onRightButtonClick = { }
            )
            breakTimeComponent.setupCard(
                state.breakTimeComponent,
                onItemClick = {
                    viewModel.sendAction(
                        NewPomodoroTimeConfigViewModelAction.Action.SectionSelected(it)
                    )
                },
                onLeftButtonClick = { },
                onRightButtonClick = { }
            )
            longBreakTimeComponent.setupCard(
                state.longBreakTimeComponent,
                onItemClick = {
                    viewModel.sendAction(
                        NewPomodoroTimeConfigViewModelAction.Action.SectionSelected(it)
                    )
                },
                onLeftButtonClick = { },
                onRightButtonClick = { }
            )
            longBreakIntervalComponent.setupCard(
                state.breakCountComponent,
                onItemClick = {
                    viewModel.sendAction(
                        NewPomodoroTimeConfigViewModelAction.Action.SectionSelected(it)
                    )
                },
                onLeftButtonClick = { },
                onRightButtonClick = { }
            )
        }
    }

    override fun onDestroy() {
        binding = null
        super.onDestroy()
    }

}