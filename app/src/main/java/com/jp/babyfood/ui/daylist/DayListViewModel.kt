package com.jp.babyfood.ui.daylist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.jp.babyfood.data.entity.Day
import com.jp.babyfood.data.repository.UserRepository
import com.jp.babyfood.ui.base.BaseViewModel
import com.jp.babyfood.util.Event
import com.jp.babyfood.util.LogUtil.LOGI
import javax.inject.Inject

class DayListViewModel @Inject constructor(
    private val userRepository: UserRepository
) : BaseViewModel() {

    private val _openCalendarDetailEvent = MutableLiveData<Event<Day>>()
    val openCalendarDetailEvent: LiveData<Event<Day>> = _openCalendarDetailEvent

    fun initDayLists(day: Day) {
        LOGI("BF_TAG", "day : $day")
    }

    fun openCalendarDetail() {
//        _openCalendarDetailEvent.value = Event(Day(1, ""))
    }
}