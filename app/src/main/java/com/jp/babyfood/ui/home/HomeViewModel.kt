package com.jp.babyfood.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
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

    private val _yearMonth = MutableLiveData<MutableMap<YearMonth, List<Day>>>().apply {
        value = mutableMapOf()
    }
    val yearMonth: LiveData<MutableMap<YearMonth, List<Day>>> = _yearMonth

    private val _addMonth = MediatorLiveData<Boolean>()
    val addMonth = _addMonth

    private val _openCalendarDetailEvent = MutableLiveData<Event<Day>>()
    val openCalendarDetailEvent: LiveData<Event<Day>> = _openCalendarDetailEvent

    private fun initMonth(): MutableList<YearMonth> {
        val prevYearMonth = YearMonth.now().minusMonths(1)
        val currentYearMonth = YearMonth.now()

        return mutableListOf(prevYearMonth, currentYearMonth)
    }

    init {
        addMonth.addSource(months) {
            addMonth.value = true
            val addedYearMonth: MutableList<YearMonth> =
                it.minus(yearMonth.value?.keys!!) as MutableList<YearMonth>

            addedYearMonth.toList().map { newYearMonth ->
                val new =
                    _yearMonth.value?.get(newYearMonth) ?: CalendarUtil.createYearMonth(
                        newYearMonth.year,
                        newYearMonth.monthValue
                    )
                _yearMonth.value?.put(newYearMonth, new)
            }

            _yearMonth.notifyDataChange()
        }
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

    fun openCalendarDetail(day: Day) {
        _openCalendarDetailEvent.value = Event(day)
    }
}