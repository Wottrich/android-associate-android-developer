package wottrich.github.io.pomodorouniverse.newpomodorotimer.presentation.model

data class OptionChipUiModel(
    val index: Int,
    val name: String,
    val tag: OptionChipTag,
    val isSelected: Boolean = false
)

enum class OptionChipTag {
    WORK_FOCUS,
    RUNNING,
    READING_HABIT;
}