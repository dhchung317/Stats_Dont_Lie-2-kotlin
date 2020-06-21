package com.hyunki.statsdontlie2.di

import androidx.lifecycle.ViewModel
import com.hyunki.statsdontlie2.view.NewViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelsModule {
    @Binds
    @IntoMap
    @ViewModelKey(NewViewModel::class)
    abstract fun bindNewViewModel(viewModel: NewViewModel): ViewModel
}