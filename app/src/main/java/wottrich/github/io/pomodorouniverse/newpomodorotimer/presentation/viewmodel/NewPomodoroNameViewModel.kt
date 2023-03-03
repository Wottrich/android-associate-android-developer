package wottrich.github.io.pomodorouniverse.newpomodorotimer.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import wottrich.github.io.pomodorouniverse.base.extensions.asLiveData
import wottrich.github.io.pomodorouniverse.livedata.MutableSingleLiveEvent
import wottrich.github.io.pomodorouniverse.newpomodorotimer.presentation.action.NewPomodoroNameViewModelAction
import wottrich.github.io.pomodorouniverse.newpomodorotimer.presentation.effect.NewPomodoroNameViewModelUiEffect
import wottrich.github.io.pomodorouniverse.newpomodorotimer.presentation.model.OptionChipTag
import wottrich.github.io.pomodorouniverse.newpomodorotimer.presentation.model.OptionChipUiModel
import wottrich.github.io.pomodorouniverse.newpomodorotimer.presentation.state.NewPomodoroNameViewModelUiState

class NewPomodoroNameViewModel : ViewModel(), NewPomodoroNameViewModelAction {

    private val _optionsLiveData = MutableLiveData<List<OptionChipUiModel>>(listOf())
    val optionsLiveData = _optionsLiveData.asLiveData()

    private val _uiState = MutableLiveData(NewPomodoroNameViewModelUiState())
    val uiState = _uiState.asLiveData()

    private val _uiEffect = MutableSingleLiveEvent<NewPomodoroNameViewModelUiEffect>()
    val uiEffect = _uiEffect.asSingleLiveEvent()

    private var selectedOption: OptionChipUiModel? = null

    override fun sendAction(action: NewPomodoroNameViewModelAction.Action) {
        when (action) {
            NewPomodoroNameViewModelAction.Action.FetchInitialData -> fetchInitialData()
            NewPomodoroNameViewModelAction.Action.HandleContinueButtonClick -> handleContinueButtonClick()
            is NewPomodoroNameViewModelAction.Action.HandleOptionSelected -> handleOptionSelected(action.option)
            is NewPomodoroNameViewModelAction.Action.TextWatcher -> onTextWatcher(action.typedText)
        }
    }

    private fun fetchInitialData() {
        _optionsLiveData.postValue(
            listOf(
                OptionChipUiModel(
                    index = 0,
                    name = "Foco nos estudos",
                    tag = OptionChipTag.WORK_FOCUS
                ),
                OptionChipUiModel(
                    index = 1,
                    name = "Timer para corrida",
                    tag = OptionChipTag.RUNNING
                ),
                OptionChipUiModel(
                    index = 2,
                    name = "Foco no trabalho",
                    tag = OptionChipTag.WORK_FOCUS
                ),
                OptionChipUiModel(
                    index = 3,
                    name = "Criando o hábito de leitura",
                    tag = OptionChipTag.READING_HABIT
                )
            )
        )
    }

    private fun handleOptionSelected(newSelectedOption: OptionChipUiModel) {
        val currentSelectedOption = selectedOption
        if (currentSelectedOption != newSelectedOption) {
            val newItem = newSelectedOption.copy(isSelected = true)
            _uiEffect.postValue(
                NewPomodoroNameViewModelUiEffect.OnUpdateSelectedItem(
                    oldItem = currentSelectedOption?.copy(isSelected = false),
                    newItem = newItem
                )
            )
            selectedOption = newItem
            updateTypedText(newSelectedOption.name)
        } else {
            removeSelectedItem()
        }
    }

    private fun handleContinueButtonClick() {
    }

    private fun onTextWatcher(typedText: String) {
        updateTypedText(typedText)
        if (selectedOption != null && typedText != selectedOption?.name) {
            removeSelectedItem()
        }
    }

    private fun updateTypedText(text: String) {
        _uiState.postValue(
            getState().copy(
                textFieldText = text,
                isButtonEnabled = text.isNotEmpty()
            )
        )
    }

    private fun removeSelectedItem() {
        _uiEffect.postValue(
            NewPomodoroNameViewModelUiEffect.OnRemoveSelectedItem(
                item = selectedOption?.copy(isSelected = false)
            )
        )
        selectedOption = null
    }

    private fun getState(): NewPomodoroNameViewModelUiState {
        return checkNotNull(uiState.value)
    }

}