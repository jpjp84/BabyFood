package com.jp.todo.data.datasource

import com.jp.todo.data.entity.User
import io.reactivex.Completable
import io.reactivex.Single

interface BaseDataSource {

    fun isCached(): Single<Boolean>

    fun save(it: User): Completable
}