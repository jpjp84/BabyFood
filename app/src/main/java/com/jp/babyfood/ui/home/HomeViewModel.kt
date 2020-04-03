package com.jp.babyfood.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.jp.babyfood.data.entity.Day
import com.jp.babyfood.data.entity.Month
import com.jp.babyfood.data.repository.UserRepository
import com.jp.babyfood.ui.base.BaseViewModel
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    private val userRepository: UserRepository
) : BaseViewModel() {

    private val _months = MutableLiveData<List<Month>>()
    val months: LiveData<List<Month>> = _months

    private val _days = MutableLiveData<List<Day>>()
    val days: LiveData<List<Day>> = _days

    fun updateCalendar() {
        _days.value = List(35) { Day(it, "test") }
        _months.value = arrayListOf(Month(1, _days.value))
    }
}