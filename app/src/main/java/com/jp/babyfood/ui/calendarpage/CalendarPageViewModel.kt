package com.jp.babyfood.ui.calendarpage

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

class CalendarPageViewModel @Inject constructor(
    private val userRepository: UserRepository
) : BaseViewModel() {

    private val _months = MutableLiveData<Month>()
    val months: LiveData<Month> = _months

    private val _openCalendarDetailEvent = MutableLiveData<Event<Day>>()
    val openCalendarDetailEvent: LiveData<Event<Day>> = _openCalendarDetailEvent

    fun updateCalendar(month: String?) {
        if (TextUtils.isEmpty(month)) {
            return
        }

        _months.value = CalendarUtil.createMonth(Month(1, mutableListOf()))
    }

    fun openCalendarDetail(day: Day) {
        _openCalendarDetailEvent.value = Event(day)
    }
}