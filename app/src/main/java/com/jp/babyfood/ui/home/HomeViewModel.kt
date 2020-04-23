package com.jp.babyfood.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.jp.babyfood.data.entity.Day
import com.jp.babyfood.data.repository.FoodRepository
import com.jp.babyfood.ui.base.BaseViewModel
import com.jp.babyfood.util.CalendarUtil
import com.jp.babyfood.util.Event
import com.jp.babyfood.util.dispatchers.HomePagerScrollDispatcher
import com.jp.babyfood.util.notifyDataChange
import java.time.YearMonth
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    private val foodRepository: FoodRepository
) : BaseViewModel(), HomePagerScrollDispatcher.OnFirstPage {

    private val _months = MutableLiveData<MutableList<YearMonth>>().apply {
        value = initMonth()
    }
    val months: LiveData<MutableList<YearMonth>> = _months

    private val _yearMonth: MutableMap<String, List<Day>> = mutableMapOf()

    private val _openCalendarDetailEvent = MutableLiveData<Event<Day>>()
    val openCalendarDetailEvent: LiveData<Event<Day>> = _openCalendarDetailEvent

    private fun initMonth(): MutableList<YearMonth> {
        val prevYearMonth = YearMonth.now().minusMonths(1)
        val currentYearMonth = YearMonth.now()

        return mutableListOf(prevYearMonth, currentYearMonth)
    }

    override fun updateMonths(): Int? {
        return _months.value?.let {
            val prevYearMonth = it[0].minusMonths(1)
            if (it.contains(prevYearMonth)) {
                return@let null
            }

            it.add(0, prevYearMonth)
            _months.notifyDataChange()
            return@let 0
        }
    }

    fun getDaysByYearMonth(yearMonth: YearMonth): List<Day>? =
        _yearMonth[yearMonth.toString()] ?: CalendarUtil.createYearMonth(
            yearMonth.year,
            yearMonth.monthValue
        )

    fun openCalendarDetail(day: Day) {
        _openCalendarDetailEvent.value = Event(day)
    }
}