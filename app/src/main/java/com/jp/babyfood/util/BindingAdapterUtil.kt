package com.jp.babyfood.util

import android.text.TextWatcher
import android.widget.AutoCompleteTextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.jp.babyfood.data.entity.Day
import com.jp.babyfood.data.entity.Ingredient
import com.jp.babyfood.ui.calendardetail.IngredientAdapter
import com.jp.babyfood.ui.calendarpage.CalendarAdapter
import com.jp.babyfood.ui.home.HomePagerAdapter
import com.jp.babyfood.ui.weekcalendar.WeekCalendarAdapter


@BindingAdapter("bind_page")
fun bindPage(viewPager: ViewPager2, months: List<String>?) {
    (viewPager.adapter as HomePagerAdapter?)?.setPage(months!!)
}

@BindingAdapter("bind_items")
fun bindItems(recyclerView: RecyclerView, days: List<Day>?) {
    if (recyclerView.adapter is CalendarAdapter) {
        (recyclerView.adapter as CalendarAdapter?)?.submitList(days!!)
    }

    if (recyclerView.adapter is WeekCalendarAdapter) {
        (recyclerView.adapter as WeekCalendarAdapter?)?.submitList(days!!)
    }
}

@BindingAdapter("bind_items")
fun bindIngredient(recyclerView: RecyclerView, ingredients: List<Ingredient>?) {
    (recyclerView.adapter as IngredientAdapter?)?.submitList(ingredients!!)
}

@BindingAdapter("text_watcher")
fun setTextWatcher(editText: AutoCompleteTextView, textWatcher: TextWatcher) {
    editText.addTextChangedListener(textWatcher)
}