package com.hyunki.statsdontlie.di.main

import androidx.lifecycle.ViewModel
import com.hyunki.statsdontlie.di.ViewModelKey
import com.hyunki.statsdontlie.viewmodel.NewViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class MainViewModelsModule {

    @Binds
    @IntoMap
    @ViewModelKey(NewViewModel::class)
    abstract fun bindMainViewModel(viewModel: NewViewModel): ViewModel

//    @Binds
//    @IntoMap
//    @ViewModelKey(ArViewModel::class)
//    abstract fun bindArViewModel(viewModel: ArViewModel): ViewModel
}