package com.jp.babyfood.data.datasource

import com.jp.babyfood.data.entity.User
import io.reactivex.Completable
import io.reactivex.Single

interface BaseDataSource {

    fun isCached(): Single<Boolean>

    fun save(it: User): Completable
}