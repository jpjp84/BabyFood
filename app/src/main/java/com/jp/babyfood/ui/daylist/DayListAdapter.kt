package com.jp.babyfood.ui.daylist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.jp.babyfood.data.entity.Day
import com.jp.babyfood.databinding.RowDaylistBinding

class DayListAdapter(private val viewModel: DayListViewModel) :
    ListAdapter<Day, DayListAdapter.CalendarViewHolder>(DayDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalendarViewHolder {
        return CalendarViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: CalendarViewHolder, position: Int) {
        holder.bind(viewModel, getItem(position))
    }

    class CalendarViewHolder constructor(private val binding: RowDaylistBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(viewModel: DayListViewModel, items: Day) {
            binding.viewModel = viewModel
        }

        companion object {
            fun from(parent: ViewGroup): CalendarViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = RowDaylistBinding.inflate(layoutInflater, parent, false)

                return CalendarViewHolder(binding)
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
