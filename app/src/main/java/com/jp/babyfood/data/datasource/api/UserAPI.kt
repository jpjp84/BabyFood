package com.jp.babyfood.data.datasource.api

import com.jp.babyfood.data.entity.User
import io.reactivex.Flowable
import retrofit2.http.GET
import retrofit2.http.Headers

interface UserAPI {

    @Headers(
        "accept: application/json",
        "content-type: application/json"
    )
    @GET("user.json")
    fun getUser(): Flowable<User>
}