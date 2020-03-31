package com.jp.todo.data

import com.jp.todo.data.datasource.UserDataSource
import com.jp.todo.data.entity.User
import com.jp.todo.data.repository.UserRepository
import com.jp.todo.di.module.AppModule
import io.reactivex.Flowable
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    @AppModule.LocalDataSource private val userLocalDataSource: UserDataSource,
    @AppModule.RemoteDataSource private val userRemoteDataSource: UserDataSource
) : UserRepository {

    private var user: User? = null

    override fun getUser(forceUpdate: Boolean): Flowable<User> {
        return userLocalDataSource.isCached()
            .flatMapPublisher {
                if (forceUpdate || !it) {
                    userRemoteDataSource.getUser()
                } else {
                    userLocalDataSource.getUser()
                }
            }
            .flatMap {
                user = it
                userLocalDataSource.save(it).toSingle { it }.toFlowable()
            }
    }

    override fun isAdult(): Boolean {
        return user?.age!! > 19
    }
}