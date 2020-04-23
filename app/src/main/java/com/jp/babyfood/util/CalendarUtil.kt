package com.jp.babyfood.util

import android.icu.util.Calendar
import com.jp.babyfood.data.entity.Day
import java.time.YearMonth


object CalendarUtil {

    fun createYearMonth(year: String, month: String): List<Day> {
        return createYearMonth(year.toInt(), month.toInt())
    }

    fun createYearMonth(
        year: Int = YearMonth.now().year,
        month: Int = YearMonth.now().monthValue
    ): List<Day> {
        val calendar: Calendar = Calendar.getInstance()
        calendar.set(year, month - 1, 1)
        calendar.add(Calendar.DAY_OF_MONTH, Calendar.SUNDAY - calendar.get(Calendar.DAY_OF_WEEK))

        return List(42) {
            calendar.add(Calendar.DATE, if (it == 0) 0 else 1)
            val isInMonth = month - 1 == calendar[Calendar.MONTH] && year == calendar[Calendar.YEAR]
            Day(calendar.timeInMillis, mutableListOf(), !isInMonth)
        }
    }
}