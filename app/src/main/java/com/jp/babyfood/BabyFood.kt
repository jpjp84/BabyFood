package com.jp.babyfood

import androidx.multidex.MultiDex
import com.jp.babyfood.di.component.DaggerAppComponent
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication


open class BabyFood : DaggerApplication() {
    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent.factory().create(applicationContext)
    }

    override fun onCreate() {
        super.onCreate()
        MultiDex.install(this)
        Logger.addLogAdapter(AndroidLogAdapter())
    }
}