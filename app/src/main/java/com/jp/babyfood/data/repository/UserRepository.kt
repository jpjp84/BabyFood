package com.jp.babyfood.data.repository

import com.jp.babyfood.data.entity.User
import io.reactivex.Flowable

interface UserRepository {

    fun getUser(forceUpdate: Boolean): Flowable<User>

    fun isAdult(): Boolean
}