package com.jp.babyfood.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.jp.babyfood.data.entity.Day
import com.jp.babyfood.data.entity.Days
import com.jp.babyfood.data.repository.FoodRepository
import com.jp.babyfood.ui.base.BaseViewModel
import com.jp.babyfood.util.CalendarUtil
import com.jp.babyfood.util.Event
import com.jp.babyfood.util.LogUtil.LOGI
import com.jp.babyfood.util.dispatchers.HomePagerScrollDispatcher
import com.jp.babyfood.util.merge
import com.jp.babyfood.util.notifyDataChange
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.time.YearMonth
import javax.inject.Inject

@OptIn(InternalCoroutinesApi::class)
class HomeViewModel @Inject constructor(
    private val foodRepository: FoodRepository
) : BaseViewModel(), HomePagerScrollDispatcher.OnFirstPage {

    private val _yearMonths = MutableLiveData<MutableList<YearMonth>>().apply {
        val prevYearMonth = YearMonth.now().minusMonths(1)
        val currentYearMonth = YearMonth.now()

        value = mutableListOf(prevYearMonth, currentYearMonth)
    }
    val yearMonths: LiveData<MutableList<YearMonth>> = _yearMonths

    private val _daysMap = MutableLiveData<MutableMap<YearMonth, Days>>(mutableMapOf())
    val daysMap: LiveData<MutableMap<YearMonth, Days>> = _daysMap

    private val _onChangePageItem = MediatorLiveData<Int>()
    val onChangePageItem = _onChangePageItem

    private val _openCalendarDetailEvent = MutableLiveData<Event<Day>>()
    val openCalendarDetailEvent: LiveData<Event<Day>> = _openCalendarDetailEvent

    init {
        _onChangePageItem.addSource(yearMonths) { loadDays(it) }
    }

    private fun loadDays(newMonths: MutableList<YearMonth>) = viewModelScope.launch {
        val isInit = _daysMap.value!!.isEmpty()
        val createMonthJob = async { createDefaultDays(newMonths) }

        createMonthJob.await()?.let { newYearMonths ->
            if (!isInit) {
                _onChangePageItem.value = 0
                _daysMap.notifyDataChange()
            }

            newYearMonths.map { updateDays(it) }
        }
    }

    private fun createDefaultDays(newMonths: MutableList<YearMonth>): MutableList<YearMonth>? {
        return daysMap.value?.let {
            val addedYearMonths: MutableList<YearMonth> =
                newMonths.minus(it.keys) as MutableList<YearMonth>

            addedYearMonths.map { addedYearMonth ->
                if (it.containsKey(addedYearMonth)) return@map

                _daysMap.value?.put(
                    addedYearMonth,
                    CalendarUtil.createYearMonth(
                        addedYearMonth.year,
                        addedYearMonth.monthValue
                    )
                )
            }
            return@let addedYearMonths
        }
    }

    private fun updateDays(yearMonth: YearMonth) {
        LOGI("BF_TAG", "Success : $yearMonth")
        addDisposable(
            foodRepository.getDailyFoods(yearMonth, true)
                .subscribe(
                    { replaceDays(yearMonth, it) },
                    { t -> Log.e("BF_TAG", "Throwable : ", t) }
                )
        )
    }

    private fun replaceDays(yearMonth: YearMonth, updatedDays: Days) {
        val currentMonths = _daysMap.value?.get(yearMonth)
        val result = currentMonths?.merge(updatedDays)
    }

    override fun updateMonths() {
        _yearMonths.value?.let {
            val prevYearMonth = it[0].minusMonths(1)
            if (it.contains(prevYearMonth)) {
                return
            }

            it.add(0, prevYearMonth)
            _yearMonths.notifyDataChange()
        }
    }

    fun openCalendarDetail(day: Day) {
        _openCalendarDetailEvent.value = Event(day)
    }
}