package com.hyunki.statsdontlie.di

import androidx.lifecycle.ViewModelProvider
import com.hyunki.statsdontlie.viewmodel.ViewModelProviderFactory
import dagger.Binds
import dagger.Module

@Module
abstract class ViewModelFactoryModule {
    @Binds
    abstract fun bindViewModelProviderFactory(viewModelProviderFactory: ViewModelProviderFactory): ViewModelProvider.Factory
}
