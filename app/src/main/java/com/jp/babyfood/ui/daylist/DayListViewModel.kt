package com.jp.babyfood.ui.daylist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.jp.babyfood.data.entity.Day
import com.jp.babyfood.data.entity.Food
import com.jp.babyfood.data.repository.UserRepository
import com.jp.babyfood.ui.base.BaseViewModel
import com.jp.babyfood.util.Event
import javax.inject.Inject

class DayListViewModel @Inject constructor(
    private val userRepository: UserRepository
) : BaseViewModel() {

    private val _day = MutableLiveData<Day>()
    val day: LiveData<Day> = _day

    private val _openCalendarDetailEvent = MutableLiveData<Event<Food?>>()
    val openCalendarDetailEvent: LiveData<Event<Food?>> = _openCalendarDetailEvent

    fun initDayLists(initDay: Day) {
        _day.value = initDay
    }

    fun openCalendarDetail() {
        _openCalendarDetailEvent.value = Event(Food())
    }
}