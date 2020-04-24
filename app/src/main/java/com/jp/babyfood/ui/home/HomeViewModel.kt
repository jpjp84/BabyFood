package com.jp.babyfood.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.jp.babyfood.data.entity.Day
import com.jp.babyfood.data.repository.FoodRepository
import com.jp.babyfood.ui.base.BaseViewModel
import com.jp.babyfood.util.CalendarUtil
import com.jp.babyfood.util.Event
import com.jp.babyfood.util.dispatchers.HomePagerScrollDispatcher
import com.jp.babyfood.util.notifyDataChange
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import java.time.YearMonth
import javax.inject.Inject

@OptIn(InternalCoroutinesApi::class)
class HomeViewModel @Inject constructor(
    private val foodRepository: FoodRepository
) : BaseViewModel(), HomePagerScrollDispatcher.OnFirstPage {

    private val _months = MutableLiveData<MutableList<YearMonth>>().apply {
        val prevYearMonth = YearMonth.now().minusMonths(1)
        val currentYearMonth = YearMonth.now()

        value = mutableListOf(prevYearMonth, currentYearMonth)
    }
    val months: LiveData<MutableList<YearMonth>> = _months

    private val _yearMonth = MutableLiveData<MutableMap<YearMonth, List<Day>>>().apply {
        value = mutableMapOf()
    }
    val yearMonth: LiveData<MutableMap<YearMonth, List<Day>>> = _yearMonth

    private val _onChangePageItem = MediatorLiveData<Int>()
    val onChangePageItem = _onChangePageItem

    private val _openCalendarDetailEvent = MutableLiveData<Event<Day>>()
    val openCalendarDetailEvent: LiveData<Event<Day>> = _openCalendarDetailEvent

    init {
        _onChangePageItem.addSource(months) {
            viewModelScope.launch {
                createNewMonth(it, yearMonth.value!!.isEmpty()).collect(object :
                    FlowCollector<Int> {
                    override suspend fun emit(value: Int) {
                        _onChangePageItem.value = value
                        _yearMonth.notifyDataChange()
                    }
                })
            }
        }
    }

    private fun createNewMonth(newMonths: MutableList<YearMonth>, isInit: Boolean): Flow<Int> =
        flow {
            yearMonth.value?.let {
                val addedYearMonths: MutableList<YearMonth> =
                    newMonths.minus(it.keys) as MutableList<YearMonth>

                addedYearMonths.map { addedYearMonth ->
                    if (it.containsKey(addedYearMonth)) return@map

                    _yearMonth.value?.put(
                        addedYearMonth,
                        CalendarUtil.createYearMonth(
                            addedYearMonth.year,
                            addedYearMonth.monthValue
                        )
                    )
                    if (!isInit) emit(0)
                }
            }
        }.flowOn(Dispatchers.Default)

    override fun updateMonths() {
        _months.value?.let {
            val prevYearMonth = it[0].minusMonths(1)
            if (it.contains(prevYearMonth)) {
                return
            }

            it.add(0, prevYearMonth)
            _months.notifyDataChange()
        }
    }

    fun openCalendarDetail(day: Day) {
        _openCalendarDetailEvent.value = Event(day)
    }
}