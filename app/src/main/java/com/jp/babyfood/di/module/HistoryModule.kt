package com.jp.babyfood.di.module

import androidx.lifecycle.ViewModel
import com.jp.babyfood.di.ViewModelBuilder
import com.jp.babyfood.di.ViewModelKey
import com.jp.babyfood.ui.history.HistoryFragment
import com.jp.babyfood.ui.history.HistoryViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
abstract class HistoryModule {

    @ContributesAndroidInjector(
        modules = [
            ViewModelBuilder::class
        ]
    )
    internal abstract fun historyFragment(): HistoryFragment


    @Binds
    @IntoMap
    @ViewModelKey(HistoryViewModel::class)
    abstract fun bindViewModel(viewModel: HistoryViewModel): ViewModel
}