package com.jp.babyfood.ui.daylist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.jp.babyfood.data.entity.Food
import com.jp.babyfood.databinding.RowDaylistBinding

class DayListAdapter(private val viewModel: DayListViewModel) :
    ListAdapter<Food, DayListAdapter.CalendarViewHolder>(FoodDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalendarViewHolder {
        return CalendarViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: CalendarViewHolder, position: Int) {
        holder.bind(viewModel, getItem(position))
    }

    class CalendarViewHolder constructor(private val binding: RowDaylistBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(viewModel: DayListViewModel, item: Food) {
            binding.viewModel = viewModel
            binding.food = item
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

class FoodDiffCallback : DiffUtil.ItemCallback<Food>() {
    override fun areItemsTheSame(oldItem: Food, newItem: Food): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Food, newItem: Food): Boolean {
        return oldItem == newItem
    }
}
