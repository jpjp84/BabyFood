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
import com.jp.babyfood.util.dispatchers.HomePagerScrollDispatcher
import com.jp.babyfood.util.merge
import com.jp.babyfood.util.notifyDataChange
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.time.YearMonth
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    private val foodRepository: FoodRepository
) : BaseViewModel(), HomePagerScrollDispatcher.OnFirstPage {

    private val _yearMonths = MutableLiveData<MutableList<YearMonth>>().apply {
        val prevYearMonth = YearMonth.now().minusMonths(1)
        val currentYearMonth = YearMonth.now()

        value = mutableListOf(prevYearMonth, currentYearMonth)
    }
    val yearMonths: LiveData<MutableList<YearMonth>> = _yearMonths

    private val _daysByYearMonth = MutableLiveData<MutableMap<YearMonth, Days>>(mutableMapOf())
    val daysByYearMonth: LiveData<MutableMap<YearMonth, Days>> = _daysByYearMonth

    private val _onLoadedDays = MediatorLiveData<Pair<Int, Days>>()
    val onLoadedDays = _onLoadedDays

    private val _openCalendarDetailEvent = MutableLiveData<Event<Day>>()
    val openCalendarDetailEvent: LiveData<Event<Day>> = _openCalendarDetailEvent

    init {
        _onLoadedDays.addSource(yearMonths) {
            loadDays(getDefaultMonths())
        }
    }

    private fun loadDays(getDefaultMonths: () -> MutableList<YearMonth>?) = viewModelScope.launch {
        val defaultMonthsJob = async { getDefaultMonths() }

        defaultMonthsJob.await()?.apply { map { getDaysByYearMonth(it) } }
    }

    private fun getDefaultMonths(): () -> MutableList<YearMonth>? {
        return {
            daysByYearMonth.value?.let {
                val addedYearMonths: MutableList<YearMonth> =
                    yearMonths.value?.minus(it.keys) as MutableList<YearMonth>

                addedYearMonths.map { addedYearMonth ->
                    if (it.containsKey(addedYearMonth)) return@map

                    _daysByYearMonth.value?.put(
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
    }

    private fun getDaysByYearMonth(yearMonth: YearMonth) {
        addDisposable(
            foodRepository.getDailyFoods(yearMonth, true)
                .subscribe(
                    { replaceNewDays(yearMonth, it) },
                    { t -> Log.e("BF_TAG", "Throwable : ", t) }
                )
        )
    }

    private fun replaceNewDays(yearMonth: YearMonth, updatedDays: Days) {
        val currentMonths = _daysByYearMonth.value?.get(yearMonth)
        currentMonths?.merge(updatedDays)?.let {
            _daysByYearMonth.value?.set(yearMonth, it)
            _daysByYearMonth.notifyDataChange()
            _onLoadedDays.value = Pair(yearMonths.value!!.indexOf(yearMonth), it)
        }
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