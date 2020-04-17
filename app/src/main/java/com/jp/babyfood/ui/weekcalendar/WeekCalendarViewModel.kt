package com.jp.babyfood.ui.weekcalendar

import android.text.TextUtils
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.jp.babyfood.data.entity.Day
import com.jp.babyfood.data.entity.Month
import com.jp.babyfood.data.repository.UserRepository
import com.jp.babyfood.ui.base.BaseViewModel
import com.jp.babyfood.util.CalendarUtil
import com.jp.babyfood.util.Event
import javax.inject.Inject

class WeekCalendarViewModel @Inject constructor(
    private val userRepository: UserRepository
) : BaseViewModel() {

    private val _months = MutableLiveData<Month>()
    val months: LiveData<Month> = _months

    private val _days = MutableLiveData<MutableList<Day>>()
    val days: LiveData<MutableList<Day>> = _days

    private val _openCalendarDetailEvent = MutableLiveData<Event<Day>>()
    val openCalendarDetailEvent: LiveData<Event<Day>> = _openCalendarDetailEvent

    fun createCalendar(month: String?) {
        if (TextUtils.isEmpty(month)) {
            return
        }

        _months.value = CalendarUtil.createMonth(Month(1))
        _days.value = CalendarUtil.createWeek(2020, 3, 1)
        _days.value!!.addAll(0, CalendarUtil.createWeek(2020, 2, 25))
        _days.value!!.addAll(CalendarUtil.createWeek(2020, 3, 8))
    }

    fun updateCalendar(isNeedNewPrev: Boolean) {
        if (isNeedNewPrev) {
            _days.value!!.addAll(0, CalendarUtil.createWeek(2020, 2, 18))

            return
        }
        _days.value!!.addAll(CalendarUtil.createWeek(2020, 3, 15))
    }

    fun openCalendarDetail(day: Day) {
        _openCalendarDetailEvent.value = Event(day)
    }
}