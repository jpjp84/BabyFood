package com.jp.babyfood.data.datasource.remote

import com.jp.babyfood.data.datasource.FoodDataSource
import com.jp.babyfood.data.datasource.api.FoodAPI
import com.jp.babyfood.data.entity.Day
import com.jp.babyfood.data.entity.Food
import com.jp.babyfood.data.entity.Ingredient
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

    override fun getDailyFoodById(id: String): Flowable<Food> {
        val ingredients = mutableListOf(
            Ingredient("쌀미음", 10),
            Ingredient("소고기", 10),
            Ingredient("브로콜리", 10)
        )
        return Flowable.defer {
            Flowable.just(Food(id, "이유식1", ingredients, color = "#332332"))
        }
    }

    override fun isCached(): Single<Boolean> {
        throw UnsupportedOperationException()
    }

    override fun save(it: List<Day>): Completable {
        throw UnsupportedOperationException()
    }
}