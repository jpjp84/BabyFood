package com.jp.babyfood.data.repository

import com.jp.babyfood.data.entity.Day
import io.reactivex.Flowable
import java.time.YearMonth

interface FoodRepository {

    fun getDailyFoods(yearMonth: YearMonth, forceUpdate: Boolean): Flowable<List<Day>>
}