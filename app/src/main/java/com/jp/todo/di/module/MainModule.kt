package com.jp.todo.di.module

import androidx.lifecycle.ViewModel
import com.jp.todo.di.ViewModelBuilder
import com.jp.todo.di.ViewModelKey
import com.jp.todo.ui.main.MainActivity
import com.jp.todo.ui.main.MainViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
abstract class MainModule {

    @ContributesAndroidInjector(
        modules = [
            ViewModelBuilder::class
        ]
    )
    internal abstract fun mainActivity(): MainActivity


    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    abstract fun bindViewModel(viewModel: MainViewModel): ViewModel
}