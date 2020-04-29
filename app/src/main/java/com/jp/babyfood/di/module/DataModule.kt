package com.jp.babyfood.di.module

import android.content.Context
import com.jp.babyfood.data.FoodRepositoryImpl
import com.jp.babyfood.data.UserRepositoryImpl
import com.jp.babyfood.data.datasource.FoodDataSource
import com.jp.babyfood.data.datasource.UserDataSource
import com.jp.babyfood.data.datasource.local.FoodLocalDataSource
import com.jp.babyfood.data.datasource.local.UserLocalDataSource
import com.jp.babyfood.data.datasource.remote.FoodRemoteDataSource
import com.jp.babyfood.data.datasource.remote.UserRemoteDataSource
import com.jp.babyfood.data.repository.FoodRepository
import com.jp.babyfood.data.repository.UserRepository
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

    @JvmStatic
    @Singleton
    @Provides
    @AppModule.LocalDataSource
    fun bindLocalFoodDataSource(context: Context): FoodDataSource {
        return FoodLocalDataSource(context)
    }

    @JvmStatic
    @Singleton
    @Provides
    @AppModule.RemoteDataSource
    fun bindRemoteFoodDataSource(): FoodDataSource {
        return FoodRemoteDataSource()
    }
}

@Module
abstract class RepositoryBindModule {

    @Singleton
    @Binds
    abstract fun bindUserRepository(userRepository: UserRepositoryImpl): UserRepository

    @Singleton
    @Binds
    abstract fun bindFoodRepository(userRepository: FoodRepositoryImpl): FoodRepository
}