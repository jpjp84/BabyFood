package com.jp.babyfood.di.module

import androidx.lifecycle.ViewModel
import com.jp.babyfood.di.ViewModelBuilder
import com.jp.babyfood.di.ViewModelKey
import com.jp.babyfood.ui.calendardetail.CalendarDetailFragment
import com.jp.babyfood.ui.calendardetail.CalendarDetailViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
abstract class CalendarDetailModule {

    @ContributesAndroidInjector(
        modules = [
            ViewModelBuilder::class
        ]
    )
    internal abstract fun calendarDetailFragment(): CalendarDetailFragment


    @Binds
    @IntoMap
    @ViewModelKey(CalendarDetailViewModel::class)
    abstract fun bindViewModel(viewModel: CalendarDetailViewModel): ViewModel
}