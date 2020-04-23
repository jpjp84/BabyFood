package com.jp.babyfood.data.datasource.api

import com.jp.babyfood.data.entity.Day
import com.jp.babyfood.data.entity.NetworkResult
import io.reactivex.Flowable
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface FoodAPI {

    @Headers(
        "accept: application/json",
        "content-type: application/json"
    )
    @GET("days")
    fun getDaysByYearMonth(@Query("yearMonth") yearMonth: String): Flowable<NetworkResult<List<Day>>>
}