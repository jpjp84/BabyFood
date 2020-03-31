package com.jp.todo.di.module

import android.content.Context
import com.jp.todo.data.UserRepositoryImpl
import com.jp.todo.data.datasource.UserDataSource
import com.jp.todo.data.datasource.local.UserLocalDataSource
import com.jp.todo.data.datasource.remote.UserRemoteDataSource
import com.jp.todo.data.repository.UserRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = [RepositoryBindModule::class])
object DataModule {

    @JvmStatic
    @Singleton
    @Provides
    @AppModule.LocalDataSource
    fun bindLocalUserDataSource(context: Context): UserDataSource {
        return UserLocalDataSource(context)
    }

    @JvmStatic
    @Singleton
    @Provides
    @AppModule.RemoteDataSource
    fun bindRemoteUserDataSource(): UserDataSource {
        return UserRemoteDataSource()
    }
}

@Module
abstract class RepositoryBindModule {

    @Singleton
    @Binds
    abstract fun bindRepository(userRepository: UserRepositoryImpl): UserRepository
}