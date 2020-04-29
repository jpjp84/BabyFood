package com.jp.babyfood.di.module

import androidx.lifecycle.ViewModel
import com.jp.babyfood.di.ViewModelBuilder
import com.jp.babyfood.di.ViewModelKey
import com.jp.babyfood.ui.daylist.DayListFragment
import com.jp.babyfood.ui.daylist.DayListViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
abstract class DayListModule {

    @ContributesAndroidInjector(
        modules = [
            ViewModelBuilder::class
        ]
    )
    internal abstract fun dayListFragment(): DayListFragment


    @Binds
    @IntoMap
    @ViewModelKey(DayListViewModel::class)
    abstract fun bindViewModel(viewModel: DayListViewModel): ViewModel
}