package com.jp.babyfood.util

import android.graphics.Color
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.text.TextWatcher
import android.view.View
import android.view.ViewGroup
import android.widget.AutoCompleteTextView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.jp.babyfood.data.entity.Day
import com.jp.babyfood.data.entity.Food
import com.jp.babyfood.data.entity.Ingredient
import com.jp.babyfood.ui.calendardetail.IngredientAdapter
import com.jp.babyfood.ui.daylist.DayListAdapter
import com.jp.babyfood.ui.home.CalendarAdapter
import com.jp.babyfood.ui.home.HomeCalendarPageAdapter
import java.time.YearMonth


@BindingAdapter("bind_page")
fun bindPage(recyclerView: RecyclerView, months: List<YearMonth>?) {
    (recyclerView.adapter as HomeCalendarPageAdapter?)?.submitList(months!!)
}

@BindingAdapter("bind_items")
fun bindItems(recyclerView: RecyclerView, days: List<Day>?) {
    days?.let {
        (recyclerView.adapter as CalendarAdapter?)?.submitList(it.toMutableList())
    }
}

@BindingAdapter("bind_items")
fun bindFoodItems(recyclerView: RecyclerView, foods: List<Food>?) {
    foods?.let {
        (recyclerView.adapter as DayListAdapter?)?.submitList(it)
    }
}

@BindingAdapter("textColorByBgColor")
fun textColorByBgColor(textView: TextView, color: String) {
    val bgColor = color.substring(1).toInt(16)
    val floatArray = floatArrayOf(0f, 0f, 0f)
    val textColorString: String? = Color.colorToHSV(bgColor, floatArray).let {
        if (floatArray[2] > 0.3) "000000" else "ffffff"
    }

    textView.setTextColor(Color.parseColor("#${textColorString}"))
}

@BindingAdapter(value = ["backgroundColor", "backgroundDrawable"])
fun backgroundColorWithBorder(
    layout: ViewGroup,
    backgroundColor: Int,
    backgroundDrawable: Drawable
) {
    layout.background = (backgroundDrawable as GradientDrawable).apply {
        setColor(backgroundColor)
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

@BindingAdapter("visible")
fun setVisible(view: View, visible: Boolean) {
    view.visibility = if(visible) View.VISIBLE else View.GONE
}