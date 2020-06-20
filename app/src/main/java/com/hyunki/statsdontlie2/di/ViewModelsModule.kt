package com.hyunki.statsdontlie2.di

import androidx.lifecycle.ViewModel
import com.hyunki.statsdontlie2.di.ViewModelKey
import com.hyunki.statsdontlie2.viewmodel.NewViewModel
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