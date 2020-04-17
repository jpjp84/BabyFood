package com.jp.babyfood.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.jp.babyfood.data.entity.Day
import com.jp.babyfood.databinding.RowCalendarPageBinding
import com.jp.babyfood.util.LogUtil

class HomeCalendarPageAdapter(private val viewModel: HomeViewModel) :
    ListAdapter<String, HomeCalendarPageAdapter.CalendarPageViewHolder>(MonthDiffCallback()) {

    private val months = mutableMapOf<String, List<Day>>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalendarPageViewHolder {
        return CalendarPageViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: CalendarPageViewHolder, position: Int) {
        LogUtil.LOGI("BF_TAG", "onBindViewHolder -> ${months.size}")
        months[getItem(position)]?.let { holder.bind(viewModel, it) }
    }

    fun submitMap(months: Map<String, List<Day>>) {
        this.months.clear()
        this.months.putAll(months)
        submitList(months.keys.toList())

        LogUtil.LOGI("BF_TAG", "submit map -> ${months.size}")
    }

    class CalendarPageViewHolder constructor(private val binding: RowCalendarPageBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private lateinit var adapter: CalendarAdapter

        fun bind(viewModel: HomeViewModel, items: List<Day>) {
            binding.viewModel = viewModel
            adapter = CalendarAdapter(viewModel)
            binding.calendarPageView.adapter = adapter
            adapter.submitList(items)
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

class MonthDiffCallback : DiffUtil.ItemCallback<String>() {
    override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem == newItem
    }
}
