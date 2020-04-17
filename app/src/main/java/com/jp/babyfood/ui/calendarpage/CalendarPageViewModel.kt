package com.jp.babyfood.ui.calendarpage

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.jp.babyfood.data.entity.Day
import com.jp.babyfood.data.repository.UserRepository
import com.jp.babyfood.ui.base.BaseViewModel
import com.jp.babyfood.util.CalendarUtil
import com.jp.babyfood.util.Event
import javax.inject.Inject

class CalendarPageViewModel @Inject constructor(
    private val userRepository: UserRepository
) : BaseViewModel() {

    private val _months = MutableLiveData<List<Day>>().apply { value = emptyList() }
    val months: LiveData<List<Day>> = _months

    private val _openCalendarDetailEvent = MutableLiveData<Event<Day>>()
    val openCalendarDetailEvent: LiveData<Event<Day>> = _openCalendarDetailEvent

    fun updateCalendar(yearMonth: String?, days: List<Day>) {
        if (yearMonth.isNullOrEmpty()) {
            return
        }

        _months.value = days
    }
    fun updateCalendar() {

        _months.value = CalendarUtil.createYearMonth()
    }

    fun openCalendarDetail(day: Day) {
        _openCalendarDetailEvent.value = Event(day)
    }
}