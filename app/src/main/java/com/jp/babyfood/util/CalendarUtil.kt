package com.jp.babyfood.util

import android.content.Context
import android.icu.util.Calendar
import com.jp.babyfood.R
import com.jp.babyfood.data.entity.Day
import com.jp.babyfood.data.entity.Days
import java.text.SimpleDateFormat
import java.time.YearMonth
import java.util.*


typealias YearMonths = MutableList<YearMonth>
typealias DaysByYearMonths = MutableMap<YearMonth, Days>

object CalendarUtil {
    val currentDay: String = SimpleDateFormat("yyyyMMdd", Locale.KOREA).format(Date())

    fun createSimpleYearMonth(yearMonth: YearMonth): List<Day> {
        return createSimpleYearMonth(yearMonth.year, yearMonth.monthValue)
    }

    private fun createSimpleYearMonth(
        year: Int = YearMonth.now().year,
        month: Int = YearMonth.now().monthValue
    ): List<Day> {
        val calendar: Calendar = Calendar.getInstance()
        calendar.set(year, month - 1, 1, 0, 0, 0)
        calendar.add(Calendar.DAY_OF_MONTH, Calendar.SUNDAY - calendar.get(Calendar.DAY_OF_WEEK))
        val days = mutableListOf<Day>()

        while (calendar.get(Calendar.MONTH) <= month - 1) {
            val isInMonth = month - 1 == calendar[Calendar.MONTH] && year == calendar[Calendar.YEAR]

            val key = "${calendar[Calendar.YEAR]}${String.format(
                "%02d%02d",
                calendar[Calendar.MONTH] + 1,
                calendar[Calendar.DATE]
            )}"
            days.add(Day(key, mutableListOf(), isInMonth))
            calendar.add(Calendar.DATE, 1)
        }

        return days.toList()
    }

    fun createYearMonth(yearMonth: YearMonth): List<Day> {
        return createYearMonth(yearMonth.year, yearMonth.monthValue)
    }

    fun createYearMonth(
        year: Int = YearMonth.now().year,
        month: Int = YearMonth.now().monthValue
    ): List<Day> {
        val calendar: Calendar = Calendar.getInstance()
        calendar.set(year, month - 1, 1, 0, 0, 0)
        calendar.add(Calendar.DAY_OF_MONTH, Calendar.SUNDAY - calendar.get(Calendar.DAY_OF_WEEK))

        return List(42) {
            calendar.add(Calendar.DATE, if (it == 0) 0 else 1)
            val isInMonth = month - 1 == calendar[Calendar.MONTH] && year == calendar[Calendar.YEAR]

            val key = "${calendar[Calendar.YEAR]}${String.format(
                "%02d%02d",
                calendar[Calendar.MONTH] + 1,
                calendar[Calendar.DATE]
            )}"
            Day(key, mutableListOf(), isInMonth)
        }
    }

    val createDefaultDays: (YearMonths) -> (DaysByYearMonths) -> YearMonths? =
        { yearMonths ->
            { daysByYearMonth ->
                val addedMonths = yearMonths.minus(daysByYearMonth.keys) as MutableList<YearMonth>
                addedMonths.apply {
                    map { addedYearMonth ->
                        if (daysByYearMonth.containsKey(addedYearMonth)) return@map

                        daysByYearMonth[addedYearMonth] = createYearMonth(
                            addedYearMonth.year,
                            addedYearMonth.monthValue
                        )
                    }
                }
            }
        }


    fun getLocaleMMDDByDay(context: Context, day: Day): String {
        if (isCurrentDay(day)) {
            return context.resources.getString(R.string.date_today)
        }
        return context.resources.getString(
            R.string.date_mm_dd,
            day.date.substring(4..5),
            day.date.substring(6)
        )
    }

    fun isCurrentDay(day: Day): Boolean {
        return day.date == currentDay
    }
}