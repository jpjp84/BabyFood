package com.jp.babyfood.data

import com.jp.babyfood.data.datasource.FoodDataSource
import com.jp.babyfood.data.entity.Day
import com.jp.babyfood.data.entity.Food
import com.jp.babyfood.data.repository.FoodRepository
import com.jp.babyfood.di.module.AppModule
import io.reactivex.Flowable
import java.time.YearMonth
import javax.inject.Inject

class FoodRepositoryImpl @Inject constructor(
    @AppModule.LocalDataSource private val foodLocalDataSource: FoodDataSource,
    @AppModule.RemoteDataSource private val foodRemoteDataSource: FoodDataSource
) : FoodRepository {

    private var foods: List<Day>? = null

    override fun getDailyFoods(yearMonth: YearMonth, forceUpdate: Boolean): Flowable<List<Day>> {
        return foodLocalDataSource.isCached()
            .flatMapPublisher {
                if (forceUpdate || !it) {
                    foodRemoteDataSource.getDailyFoods(yearMonth.toString())
                } else {
                    foodLocalDataSource.getDailyFoods(yearMonth.toString())
                }
            }
            .flatMap {
                foods = it
                foodLocalDataSource.save(it).toSingle { it }.toFlowable()
            }
    }

    override fun getDailyFoodById(id: String, forceUpdate: Boolean): Flowable<Food> {
        return foodLocalDataSource.isCached()
            .flatMapPublisher {
                if (forceUpdate || !it) {
                    foodRemoteDataSource.getDailyFoodById(id)
                } else {
                    foodLocalDataSource.getDailyFoodById(id)
                }
            }
//            .flatMap {
//                foodLocalDataSource.save(it).toSingle { it }.toFlowable()
//            }
    }
}