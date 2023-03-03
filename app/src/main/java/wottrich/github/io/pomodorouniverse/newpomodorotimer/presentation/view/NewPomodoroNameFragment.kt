package wottrich.github.io.pomodorouniverse.newpomodorotimer.presentation.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import wottrich.github.io.pomodorouniverse.R
import wottrich.github.io.pomodorouniverse.databinding.FragmentNewPomodoroNameBinding
import wottrich.github.io.pomodorouniverse.newpomodorotimer.presentation.action.NewPomodoroNameViewModelAction
import wottrich.github.io.pomodorouniverse.newpomodorotimer.presentation.effect.NewPomodoroNameViewModelUiEffect
import wottrich.github.io.pomodorouniverse.newpomodorotimer.presentation.model.OptionChipUiModel
import wottrich.github.io.pomodorouniverse.newpomodorotimer.presentation.state.NewPomodoroNameViewModelUiState
import wottrich.github.io.pomodorouniverse.newpomodorotimer.presentation.viewmodel.NewPomodoroNameViewModel

@AndroidEntryPoint
class NewPomodoroNameFragment : Fragment() {

    private var binding: FragmentNewPomodoroNameBinding? = null
    private val viewModel by viewModels<NewPomodoroNameViewModel>()
    private val adapter by lazy {
        NewPomodoroNameOptionsAdapter().also {
            it.setOnClickListener { option ->
                viewModel.sendAction(
                    NewPomodoroNameViewModelAction.Action.HandleOptionSelected(option)
                )
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNewPomodoroNameBinding.inflate(
            inflater,
            container,
            false
        )
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.sendAction(NewPomodoroNameViewModelAction.Action.FetchInitialData)
        setupListeners()
        setupAdapter()
        setupObservers()
    }

    private fun setupListeners() {
        binding?.textInputEditTextPomodoroName?.doAfterTextChanged {
            val text = it?.toString().orEmpty()
            viewModel.sendAction(NewPomodoroNameViewModelAction.Action.TextWatcher(text))
        }
    }

    private fun setupAdapter() {
        binding?.optionsRecyclerView?.apply {
            adapter = this@NewPomodoroNameFragment.adapter
        }
    }

    private fun setupObservers() {
        viewModel.optionsLiveData.observe(viewLifecycleOwner, ::handleOptionsList)
        viewModel.uiState.observe(viewLifecycleOwner, ::handleUiState)
        viewModel.uiEffect.observe(viewLifecycleOwner, ::handleUiEffect)
    }

    private fun handleUiState(state: NewPomodoroNameViewModelUiState) {
        binding?.apply {
            setupTextField(state.textFieldText)
            setupButtonState(state.isButtonEnabled)
        }
    }

    private fun FragmentNewPomodoroNameBinding.setupTextField(text: String) {
        val textField = textInputEditTextPomodoroName
        if (textField.text?.toString() != text) {
            textField.setText(text)
        }
    }

    private fun FragmentNewPomodoroNameBinding.setupButtonState(isEnabled: Boolean) {
        continueButton.isEnabled = isEnabled
    }

    private fun handleUiEffect(effect: NewPomodoroNameViewModelUiEffect) {
        when (effect) {
            is NewPomodoroNameViewModelUiEffect.OnUpdateSelectedItem -> {
                if (effect.oldItem != null) {
                    adapter.notifyItemChanged(effect.oldItem.index, effect.oldItem)
                }
                adapter.notifyItemChanged(effect.newItem.index, effect.newItem)
                binding?.textInputEditTextPomodoroName?.requestFocus()
            }
            is NewPomodoroNameViewModelUiEffect.OnRemoveSelectedItem -> {
                if (effect.item != null) {
                    adapter.notifyItemChanged(effect.item.index, effect.item)
                }
            }
        }
    }

    private fun handleOptionsList(options: List<OptionChipUiModel>) {
        adapter.submitItems(options.toMutableList())
    }

}