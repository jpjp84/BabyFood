package com.jp.babyfood.ui.calendarpage

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.jp.babyfood.data.entity.Day
import com.jp.babyfood.databinding.RowCalendarBinding
import com.jp.babyfood.util.view.OnItemClickListener

class CalendarAdapter(
    private val viewModel: CalendarPageViewModel,
    private val clickListener: OnItemClickListener<Day>
) : ListAdapter<Day, CalendarAdapter.CalendarViewHolder>(
    DayDiffCallback()
) {

    companion object {
        const val COLUMN_COUNT = 5
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalendarViewHolder {
        return CalendarViewHolder.from(
            parent
        )
    }

    override fun onBindViewHolder(holder: CalendarViewHolder, position: Int) {
        holder.bind(viewModel, getItem(position), clickListener)
    }

    class CalendarViewHolder constructor(private val binding: RowCalendarBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(viewModel: CalendarPageViewModel, items: Day, clickListener: OnItemClickListener<Day>) {
            binding.viewModel = viewModel
            binding.day = items
            binding.root.setOnClickListener { clickListener.onItemClick(items) }
        }

        companion object {
            fun from(parent: ViewGroup): CalendarViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = RowCalendarBinding.inflate(layoutInflater, parent, false).apply {
                    resizeChildHeight(
                        this,
                        parent
                    )
                }

                return CalendarViewHolder(
                    binding
                )
            }

            private fun resizeChildHeight(binding: RowCalendarBinding, parent: ViewGroup) {
                binding.root.layoutParams.height = parent.height / COLUMN_COUNT
            }
        }
    }
}

class DayDiffCallback : DiffUtil.ItemCallback<Day>() {
    override fun areItemsTheSame(oldItem: Day, newItem: Day): Boolean {
        return oldItem.date == newItem.date
    }

    override fun areContentsTheSame(oldItem: Day, newItem: Day): Boolean {
        return oldItem == newItem
    }
}
