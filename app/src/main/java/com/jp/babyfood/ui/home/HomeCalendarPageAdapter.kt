package com.jp.babyfood.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.jp.babyfood.databinding.RowCalendarPageBinding
import java.time.YearMonth

class HomeCalendarPageAdapter(private val viewModel: HomeViewModel) :
    ListAdapter<YearMonth, HomeCalendarPageAdapter.CalendarPageViewHolder>(MonthDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalendarPageViewHolder {
        return CalendarPageViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: CalendarPageViewHolder, position: Int) {
        holder.bind(viewModel, getItem(position))
    }

    class CalendarPageViewHolder constructor(private val binding: RowCalendarPageBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private lateinit var adapter: CalendarAdapter

        fun bind(viewModel: HomeViewModel, yearMonth: YearMonth) {
            binding.viewModel = viewModel
            adapter = CalendarAdapter(viewModel)
            binding.calendarPageView.adapter = adapter
            adapter.submitList(viewModel.getDaysByYearMonth(yearMonth))
        }

        companion object {
            fun from(parent: ViewGroup): CalendarPageViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = RowCalendarPageBinding.inflate(layoutInflater, parent, false)

                return CalendarPageViewHolder(binding)
            }
        }
    }
}

class MonthDiffCallback : DiffUtil.ItemCallback<YearMonth>() {
    override fun areItemsTheSame(oldItem: YearMonth, newItem: YearMonth): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: YearMonth, newItem: YearMonth): Boolean {
        return oldItem == newItem
    }
}
