package com.jp.babyfood.data.datasource.remote

import com.jp.babyfood.data.datasource.FoodDataSource
import com.jp.babyfood.data.datasource.api.FoodAPI
import com.jp.babyfood.data.entity.Day
import com.jp.babyfood.data.entity.Food
import com.jp.babyfood.util.NetworkUtil
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class FoodRemoteDataSource : FoodDataSource {

    override fun getDailyFoods(yearMonth: String): Flowable<List<Day>> {
        return NetworkUtil.getAPI(FoodAPI::class.java).getDaysByYearMonth(yearMonth)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map { response -> response.result ?: throw Exception(response.error) }
    }

    override fun getDailyFoodByDay(day: String): Flowable<Food> {
        TODO("Not yet implemented")
    }

    override fun isCached(): Single<Boolean> {
        throw UnsupportedOperationException()
    }

    override fun save(it: List<Day>): Completable {
        throw UnsupportedOperationException()
    }
}