package com.jp.babyfood.data.datasource

import com.jp.babyfood.data.entity.User
import io.reactivex.Flowable

interface UserDataSource : BaseDataSource<User> {

    fun getUser(): Flowable<User>
}