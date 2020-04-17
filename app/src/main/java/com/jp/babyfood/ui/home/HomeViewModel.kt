package com.jp.babyfood.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.jp.babyfood.data.entity.Day
import com.jp.babyfood.data.repository.UserRepository
import com.jp.babyfood.ui.base.BaseViewModel
import com.jp.babyfood.util.CalendarUtil
import com.jp.babyfood.util.Event
import java.time.YearMonth
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    private val userRepository: UserRepository
) : BaseViewModel() {

    private val _months = MutableLiveData<MutableList<YearMonth>>()
    val months: LiveData<MutableList<YearMonth>> = _months

    private val _yearMonth = MutableLiveData<MutableMap<String, List<Day>>>(mutableMapOf())
    val yearMonth: LiveData<MutableMap<String, List<Day>>> = _yearMonth

    fun updateCalendar() {
        val prevYearMonth = YearMonth.now().minusMonths(1)
        val currentYearMonth = YearMonth.now()
        _months.value = mutableListOf(prevYearMonth, currentYearMonth)

        _months.value?.map {
            val year = it.year
            val month = it.monthValue
            _yearMonth.value?.put("${year}${month}", CalendarUtil.createYearMonth(year, month))
        }
    }

    fun loadCalendar(): Boolean {
        val prevYearMonth = YearMonth.now().minusMonths((_months.value!!.size).toLong())
        if (_yearMonth.value!!.containsKey("${prevYearMonth.year}${prevYearMonth.monthValue}")) {
            return false
        }

        _months.value?.add(prevYearMonth)
        val newYearMonth: MutableMap<String, List<Day>> = mutableMapOf()
        newYearMonth.putAll(_yearMonth.value!!)
        newYearMonth["${prevYearMonth.year}${prevYearMonth.monthValue}"] =
            CalendarUtil.createYearMonth(prevYearMonth.year, prevYearMonth.monthValue)

        _yearMonth.value = newYearMonth
        return true
    }

    private val _openCalendarDetailEvent = MutableLiveData<Event<Day>>()
    val openCalendarDetailEvent: LiveData<Event<Day>> = _openCalendarDetailEvent

    fun openCalendarDetail(day: Day) {
        _openCalendarDetailEvent.value = Event(day)
    }
}