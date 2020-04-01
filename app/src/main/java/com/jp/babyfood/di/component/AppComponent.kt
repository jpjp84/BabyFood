package com.jp.babyfood.di.component

import android.content.Context
import com.jp.babyfood.BabyFood
import com.jp.babyfood.di.module.AppModule
import com.jp.babyfood.di.module.DataModule
import com.jp.babyfood.di.module.MainModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AppModule::class,
        AndroidSupportInjectionModule::class,
        DataModule::class,
        MainModule::class
    ]
)
interface AppComponent : AndroidInjector<BabyFood> {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance applicationContext: Context): AppComponent
    }

}