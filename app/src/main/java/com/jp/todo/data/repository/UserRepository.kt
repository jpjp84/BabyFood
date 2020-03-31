package com.jp.todo.data.repository

import com.jp.todo.data.entity.User
import io.reactivex.Flowable

interface UserRepository {

    fun getUser(forceUpdate: Boolean): Flowable<User>

    fun isAdult(): Boolean
}