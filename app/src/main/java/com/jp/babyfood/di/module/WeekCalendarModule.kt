package com.jp.babyfood.di.module

import androidx.lifecycle.ViewModel
import com.jp.babyfood.di.ViewModelBuilder
import com.jp.babyfood.di.ViewModelKey
import com.jp.babyfood.ui.weekcalendar.WeekCalendarFragment
import com.jp.babyfood.ui.weekcalendar.WeekCalendarViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
abstract class WeekCalendarModule {

    @ContributesAndroidInjector(
        modules = [
            ViewModelBuilder::class
        ]
    )
    internal abstract fun weekCalendarFragment(): WeekCalendarFragment


    @Binds
    @IntoMap
    @ViewModelKey(WeekCalendarViewModel::class)
    abstract fun bindViewModel(viewModel: WeekCalendarViewModel): ViewModel
}