package com.jp.babyfood.data.datasource.local

import android.content.Context
import android.text.TextUtils
import com.jp.babyfood.data.datasource.FoodDataSource
import com.jp.babyfood.data.entity.Day
import com.jp.babyfood.data.entity.Food
import com.jp.babyfood.util.SharedPrefUtil
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single

class FoodLocalDataSource constructor(
    private val context: Context
) : FoodDataSource {

    override fun getDailyFoods(yearMonth: String): Flowable<List<Day>> {
        return Flowable.defer {
            Flowable.just(SharedPrefUtil.get<List<Day>>(context, "foods"))
        }
    }

    override fun getDailyFoodByDay(day: String): Flowable<Food> {
        TODO("Not yet implemented")
    }

    override fun isCached(): Single<Boolean> {
        return Single.defer {
            Single.just(!TextUtils.isEmpty(SharedPrefUtil.get(context, "foods")))
        }
    }

    override fun save(it: List<Day>): Completable {
        return Completable.defer {
            SharedPrefUtil.save(context, "foods", it)

            Completable.complete()
        }
    }
}