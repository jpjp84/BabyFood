package com.jp.babyfood.di.module

import androidx.lifecycle.ViewModel
import com.jp.babyfood.di.ViewModelBuilder
import com.jp.babyfood.di.ViewModelKey
import com.jp.babyfood.ui.calendarpage.CalendarPageFragment
import com.jp.babyfood.ui.calendarpage.CalendarPageViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
abstract class CalendarPageModule {

    @ContributesAndroidInjector(
        modules = [
            ViewModelBuilder::class
        ]
    )
    internal abstract fun calendarPageFragment(): CalendarPageFragment


    @Binds
    @IntoMap
    @ViewModelKey(CalendarPageViewModel::class)
    abstract fun bindViewModel(viewModel: CalendarPageViewModel): ViewModel
}