package com.jp.babyfood.util

import android.text.TextWatcher
import android.widget.AutoCompleteTextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.jp.babyfood.data.entity.Day
import com.jp.babyfood.ui.calendarpage.CalendarAdapter
import com.jp.babyfood.ui.home.HomePagerAdapter


@BindingAdapter("bind_page")
fun bindPage(viewPager: ViewPager2, months: List<String>?) {
    (viewPager.adapter as HomePagerAdapter?)?.setPage(months!!)
}

@BindingAdapter("bind_items")
fun bindItems(recyclerView: RecyclerView, days: List<Day>?) {
    (recyclerView.adapter as CalendarAdapter?)?.submitList(days!!)
}

@BindingAdapter("text_watcher")
fun setTextWatcher(editText: AutoCompleteTextView, textWatcher: TextWatcher) {
    editText.addTextChangedListener(textWatcher)
}