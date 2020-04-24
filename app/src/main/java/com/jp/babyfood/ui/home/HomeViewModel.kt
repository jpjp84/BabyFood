package com.jp.babyfood.ui.home

import android.util.Log
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
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.InternalCoroutinesApi
import java.time.YearMonth
import javax.inject.Inject

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
            createNewMonth(it, yearMonth.value!!.isEmpty())
                .subscribeOn(Schedulers.io())
                .filter { index -> index >= 0 }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { index ->
                        _onChangePageItem.value = index
                    },
                    { t: Throwable? -> Log.e("BF_TAG", "Exception : ", t) },
                    { _yearMonth.notifyDataChange() }
                )
        }
    }

    private fun createNewMonth(newMonths: MutableList<YearMonth>, isInit: Boolean): Flowable<Int> {
        return Flowable.defer {
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

                    if (!isInit) return@defer Flowable.just(0)
                }
            }

            Flowable.just(-1)
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