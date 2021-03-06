package com.jp.babyfood.di.module

import android.app.Application
import com.jp.babyfood.BabyFood
import com.jp.babyfood.util.dispatchers.HomePagerScrollDispatcher
import dagger.Module
import dagger.Provides
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
object AppModule {

    @Qualifier
    @Retention(AnnotationRetention.RUNTIME)
    annotation class RemoteDataSource

    @Qualifier
    @Retention(AnnotationRetention.RUNTIME)
    annotation class LocalDataSource

    @JvmStatic
    @Singleton
    @Provides
    fun provideApplication(context: BabyFood): Application = context

    @JvmStatic
    @Singleton
    @Provides
    fun provideHomePagerScrollDispatcher(): HomePagerScrollDispatcher = HomePagerScrollDispatcher()
}