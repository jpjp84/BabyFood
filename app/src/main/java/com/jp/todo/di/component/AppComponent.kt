package com.jp.todo.di.component

import android.content.Context
import com.jp.todo.Todo
import com.jp.todo.di.module.AppModule
import com.jp.todo.di.module.DataModule
import com.jp.todo.di.module.MainModule
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
interface AppComponent : AndroidInjector<Todo> {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance applicationContext: Context): AppComponent
    }

}