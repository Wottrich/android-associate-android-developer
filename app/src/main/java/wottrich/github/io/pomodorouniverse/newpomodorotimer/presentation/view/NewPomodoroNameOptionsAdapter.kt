package wottrich.github.io.pomodorouniverse.newpomodorotimer.presentation.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import wottrich.github.io.pomodorouniverse.databinding.ItemNewPomodoroNameOptionsBinding
import wottrich.github.io.pomodorouniverse.newpomodorotimer.presentation.model.OptionChipUiModel

class NewPomodoroNameOptionsAdapter : RecyclerView.Adapter<NewPomodoroNameOptionsViewHolder>() {

    private var items: MutableList<OptionChipUiModel> = mutableListOf()
    private var onClickListener: ((OptionChipUiModel) -> Unit)? = null

    fun submitItems(newItems: MutableList<OptionChipUiModel>) {
        this.items = newItems
        notifyItemRangeInserted(0, newItems.size)
    }

    fun setOnClickListener(onClickListener: (OptionChipUiModel) -> Unit) {
        this.onClickListener = onClickListener
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): NewPomodoroNameOptionsViewHolder {
        val itemBinding = ItemNewPomodoroNameOptionsBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return NewPomodoroNameOptionsViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: NewPomodoroNameOptionsViewHolder, position: Int) {
        holder.bind(getItem(position)) {
            onClickListener?.invoke(it)
        }
    }

    override fun onBindViewHolder(
        holder: NewPomodoroNameOptionsViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        if (payloads.isNotEmpty()) {
            val item = payloads[0] as OptionChipUiModel
            items[position] = item
        }
        super.onBindViewHolder(holder, position, payloads)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    private fun getItem(position: Int): OptionChipUiModel {
        return items[position]
    }
}