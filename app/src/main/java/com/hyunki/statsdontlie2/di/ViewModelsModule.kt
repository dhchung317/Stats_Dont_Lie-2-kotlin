package com.hyunki.statsdontlie2.di

import androidx.lifecycle.ViewModel
import com.hyunki.statsdontlie2.view.MainViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelsModule {
    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    abstract fun bindNewViewModel(viewModel: MainViewModel): ViewModel
}