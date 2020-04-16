package com.jp.babyfood.util

import android.icu.util.Calendar
import com.jp.babyfood.data.entity.Day
import com.jp.babyfood.data.entity.Month


object CalendarUtil {

    fun createMonth(month: Month): Month {
        val calendar: Calendar = Calendar.getInstance()
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), 1)

        val startWeek = calendar.get(Calendar.DAY_OF_WEEK)
        val endDayOfMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)

        val dayList = List(42) {
            val date = it - startWeek + 2
            if (date < 1 || date > endDayOfMonth) {
                return@List Day(0, "")
            }
            Day(date, "")
        }

        month.days.addAll(dayList)
        return month
    }

    fun createWeek(year: Int, month: Int, day: Int): MutableList<Day> {
        val calendar = Calendar.getInstance()
        calendar.set(year, month, day)

        calendar.add(Calendar.DAY_OF_MONTH, Calendar.SUNDAY - calendar.get(Calendar.DAY_OF_WEEK));

        val weeks = mutableListOf(Day(calendar[Calendar.DAY_OF_MONTH], ""))
        MutableList(6) {
            calendar.add(Calendar.DAY_OF_MONTH, 1)
            weeks.add(Day(calendar[Calendar.DAY_OF_MONTH], ""))
        }

        return weeks
    }
}