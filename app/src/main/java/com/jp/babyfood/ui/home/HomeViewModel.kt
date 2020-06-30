package com.jp.babyfood.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.jp.babyfood.data.entity.Day
import com.jp.babyfood.data.entity.Days
import com.jp.babyfood.data.repository.FoodRepository
import com.jp.babyfood.ui.base.BaseViewModel
import com.jp.babyfood.util.*
import com.jp.babyfood.util.dispatchers.HomePagerScrollDispatcher
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.time.YearMonth
import java.util.*
import javax.inject.Inject


class HomeViewModel @Inject constructor(
    private val foodRepository: FoodRepository
) : BaseViewModel(), HomePagerScrollDispatcher.OnChangePage {

    companion object {
        const val INSERTED_POSITION = 0
    }

    private val _selectedMonth = MutableLiveData<YearMonth>(YearMonth.now())
    val selectedMonth: LiveData<YearMonth> = _selectedMonth

    private val _yearMonthMap = MutableLiveData<SortedMap<YearMonth, List<Day>>>().apply {
        val prevYearMonth = YearMonth.now().minusMonths(1)
        val currentYearMonth = YearMonth.now()
        val afterYearMonth = YearMonth.now().plusMonths(1)

        value = linkedMapOf(
            prevYearMonth to CalendarUtil.createYearMonth(prevYearMonth),
            currentYearMonth to CalendarUtil.createYearMonth(currentYearMonth),
            afterYearMonth to CalendarUtil.createYearMonth(afterYearMonth)
        ).toSortedMap()
    }
    val yearMonthMap: LiveData<SortedMap<YearMonth, List<Day>>> = _yearMonthMap

    private val _yearMonths = MutableLiveData<MutableList<YearMonth>>().apply {
        val prevYearMonth = YearMonth.now().minusMonths(1)
        val currentYearMonth = YearMonth.now()
        val afterYearMonth = YearMonth.now().plusMonths(1)

        value = mutableListOf(prevYearMonth, currentYearMonth, afterYearMonth)
    }
    val yearMonths: LiveData<MutableList<YearMonth>> = _yearMonths

    private val _daysByYearMonth = MutableLiveData<MutableMap<YearMonth, Days>>(mutableMapOf())
    val daysByYearMonth: LiveData<MutableMap<YearMonth, Days>> = _daysByYearMonth

    private val _onUpdateSavedDays = MediatorLiveData<Event<Pair<Int, Days>>>()
    val onUpdateSavedDays = _onUpdateSavedDays

    private val _insertedNewPage = MutableLiveData<Event<Int>>()
    val addNewPage = _insertedNewPage

    private val _openCalendarDetailEvent = MutableLiveData<Event<Day>>()
    val openCalendarDetailEvent: LiveData<Event<Day>> = _openCalendarDetailEvent

    private val _openSelectMonthDialog = MutableLiveData<Event<Boolean>>()
    val openSelectMonthDialog: LiveData<Event<Boolean>> = _openSelectMonthDialog

    override fun onChangePage(position: Int) {
        _yearMonthMap.value?.let {
            _selectedMonth.value = it.keys.toList()[position]
        }
    }
    override fun onFirstPage() {
        _yearMonthMap.value?.let {
            val prevYearMonth = it.keys.first().minusMonths(1)
            if (it.contains(prevYearMonth)) {
                return
            }

            it[prevYearMonth] = prevYearMonth?.let { it1 -> CalendarUtil.createYearMonth(it1) }
            _insertedNewPage.value = Event(INSERTED_POSITION)
        }
    }

    override fun onLastPage() {
        _yearMonthMap.value?.let {
            val nextYearMonth = it.keys.last().plusMonths(1)
            if (it.contains(nextYearMonth)) {
                return
            }

            it[nextYearMonth] = nextYearMonth?.let { it1 -> CalendarUtil.createYearMonth(it1) }
            _insertedNewPage.value = Event(it.size - 1)
        }
    }

    private fun loadDays(getNewMonths: (DaysByYearMonths) -> YearMonths?) =
        viewModelScope.launch {
            val getNewMonthsJob = async { daysByYearMonth.value?.let { getNewMonths(it) } }
            getNewMonthsJob.await()?.apply { map { getDaysByYearMonth(it) } }
        }

    private fun getDaysByYearMonth(yearMonth: YearMonth) {
//        addDisposable(
//            foodRepository.getDailyFoods(yearMonth, false)
//                .subscribe(
//                    { replaceNewDays(yearMonth, it) },
//                    { t -> Log.e("BF_TAG", "Throwable : ", t) }
//                )
//        )
    }

    private fun replaceNewDays(yearMonth: YearMonth, updatedDays: Days) {
        val currentMonths = _daysByYearMonth.value?.get(yearMonth)
        currentMonths?.merge(updatedDays)?.let {
            _daysByYearMonth.value?.set(yearMonth, it)
            _onUpdateSavedDays.value = Event(Pair(yearMonths.value!!.indexOf(yearMonth), it))
        }
    }

    fun openSelectMonthDialog() {
        _openSelectMonthDialog.value = Event(true)
    }

    fun openCalendarDetail(day: Day) {
        _openCalendarDetailEvent.value = Event(day)
    }
}