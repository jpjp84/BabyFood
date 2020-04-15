package com.jp.babyfood.ui.weekcalendar

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.jp.babyfood.data.entity.Day
import com.jp.babyfood.databinding.RowWeekCalendarBinding

class WeekCalendarAdapter(private val viewModel: WeekCalendarViewModel) :
    ListAdapter<Day, WeekCalendarAdapter.CalendarViewHolder>(DayDiffCallback()) {

    companion object {
        const val WEEK_COUNT = 7
        const val COLUMN_COUNT = 6
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalendarViewHolder {
        return CalendarViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: CalendarViewHolder, position: Int) {
        holder.bind(viewModel, getItem(position), position == 15)
    }

    class CalendarViewHolder constructor(private val binding: RowWeekCalendarBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(viewModel: WeekCalendarViewModel, items: Day, isFocus: Boolean) {
            binding.viewModel = viewModel
            binding.day = items
            if (isFocus) {
                binding.root.requestFocus()
            }
        }

        companion object {
            fun from(parent: ViewGroup): CalendarViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = RowWeekCalendarBinding.inflate(layoutInflater, parent, false).apply {
                    resizeChildHeight(
                        this,
                        parent
                    )
                }

                return CalendarViewHolder(binding)
            }

            private fun resizeChildHeight(binding: RowWeekCalendarBinding, parent: ViewGroup) {
                binding.root.layoutParams.width = parent.width / WEEK_COUNT
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
