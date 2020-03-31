package com.jp.todo.data.datasource

import com.jp.todo.data.entity.User
import io.reactivex.Flowable

interface UserDataSource : BaseDataSource {

    fun getUser(): Flowable<User>
}