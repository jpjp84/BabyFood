package com.jp.babyfood.util

import android.text.TextWatcher
import android.widget.AutoCompleteTextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.jp.babyfood.data.entity.Day
import com.jp.babyfood.data.entity.Ingredient
import com.jp.babyfood.ui.calendardetail.IngredientAdapter
import com.jp.babyfood.ui.calendarpage.CalendarAdapter
import com.jp.babyfood.ui.home.HomeCalendarPageAdapter
import com.jp.babyfood.ui.weekcalendar.WeekCalendarAdapter


@BindingAdapter("bind_page")
fun bindPage(recyclerView: RecyclerView, months: Map<String, List<Day>>?) {
//    (viewPager.adapter as HomePagerAdapter?)?.setPage(months!!)
    (recyclerView.adapter as HomeCalendarPageAdapter?)?.submitMap(months!!)
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