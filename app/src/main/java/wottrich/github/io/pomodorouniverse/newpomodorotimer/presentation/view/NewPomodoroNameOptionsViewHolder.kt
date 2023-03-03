package wottrich.github.io.pomodorouniverse.newpomodorotimer.presentation.view

import android.util.TypedValue
import androidx.recyclerview.widget.RecyclerView
import wottrich.github.io.pomodorouniverse.R
import wottrich.github.io.pomodorouniverse.databinding.ItemNewPomodoroNameOptionsBinding
import wottrich.github.io.pomodorouniverse.newpomodorotimer.presentation.model.OptionChipUiModel

class NewPomodoroNameOptionsViewHolder(
    private val binding: ItemNewPomodoroNameOptionsBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(option: OptionChipUiModel, onItemClick: (OptionChipUiModel) -> Unit) {
        setupChipName(option.name)
        setupSelectableBackground(option.isSelected)
        binding.root.setOnClickListener {
            onItemClick(option)
        }
    }

    private fun setupChipName(name: String) {
        binding.chipName.text = name
    }

    private fun setupSelectableBackground(isSelected: Boolean) {
        binding.apply {
            if (isSelected) {
                chipContainer.setBackgroundResource(R.drawable.component_selected_chip_shape)
                chipName.setTextColor(getAttrColor(com.google.android.material.R.attr.colorOnPrimary))
            } else {
                chipContainer.setBackgroundResource(R.drawable.component_chip_shape)
                chipName.setTextColor(getAttrColor(com.google.android.material.R.attr.colorOnBackground))
            }
        }
    }

    private fun getAttrColor(attrId: Int): Int {
        val typedValue = TypedValue()
        val theme = itemView.context.theme
        theme.resolveAttribute(attrId, typedValue, true)
        return typedValue.data
    }
}