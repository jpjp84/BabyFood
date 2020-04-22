package com.jp.babyfood.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.jp.babyfood.data.entity.Day
import com.jp.babyfood.data.repository.UserRepository
import com.jp.babyfood.ui.base.BaseViewModel
import com.jp.babyfood.util.CalendarUtil
import com.jp.babyfood.util.Event
import com.jp.babyfood.util.dispatchers.HomePagerScrollDispatcher
import com.jp.babyfood.util.notifyDataChange
import java.time.YearMonth
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    private val userRepository: UserRepository
) : BaseViewModel(), HomePagerScrollDispatcher.OnFirstPage {

    private val _months = MutableLiveData<MutableList<YearMonth>>(mutableListOf())
    val months: LiveData<MutableList<YearMonth>> = _months

    private val _yearMonth: MutableMap<String, List<Day>> = mutableMapOf()

    private val _openCalendarDetailEvent = MutableLiveData<Event<Day>>()
    val openCalendarDetailEvent: LiveData<Event<Day>> = _openCalendarDetailEvent

    fun initMonth() {
        val prevYearMonth = YearMonth.now().minusMonths(1)
        val currentYearMonth = YearMonth.now()

        _months.value = mutableListOf(prevYearMonth, currentYearMonth)
    }

    override fun onUpdate() {
        _months.value?.let { yearMonths ->
            val prevYearMonth = yearMonths[0].minusMonths(1)
            if (yearMonths.contains(prevYearMonth)) {
                return
            }

            yearMonths.add(0, prevYearMonth)
            _months.notifyDataChange()
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