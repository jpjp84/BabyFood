package com.jp.babyfood.data.datasource.remote

import com.jp.babyfood.data.datasource.UserDataSource
import com.jp.babyfood.data.datasource.api.UserAPI
import com.jp.babyfood.data.entity.User
import com.jp.babyfood.util.NetworkUtil
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class UserRemoteDataSource: UserDataSource {

    override fun getUser(): Flowable<User> {
        return NetworkUtil.getAPI(UserAPI::class.java).getUser()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    override fun isCached(): Single<Boolean> {
        throw UnsupportedOperationException()
    }

    override fun save(it: User): Completable {
        throw UnsupportedOperationException()
    }
}