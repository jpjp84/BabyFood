package com.jp.babyfood.util

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.jp.babyfood.data.entity.Day
import com.jp.babyfood.ui.home.CalendarAdapter

@BindingAdapter("bind_items")
fun bindItems(recyclerView: RecyclerView, days: List<Day>?) {
    (recyclerView.adapter as CalendarAdapter?)?.submitList(days!!)
}