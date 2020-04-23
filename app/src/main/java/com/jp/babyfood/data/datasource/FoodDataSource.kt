package com.jp.babyfood.data.datasource

import com.jp.babyfood.data.entity.Day
import com.jp.babyfood.data.entity.Food
import io.reactivex.Flowable

interface FoodDataSource : BaseDataSource<List<Day>> {

    fun getDailyFoods(yearMonth: String): Flowable<List<Day>>

    fun getDailyFoodByDay(day: String): Flowable<Food>
}