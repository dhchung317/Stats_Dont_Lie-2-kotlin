package com.hyunki.statsdontlie2.di

import android.app.Application
import com.hyunki.statsdontlie2.BaseApplication
import com.hyunki.statsdontlie2.localdb.di.BDLDatabaseModule
import com.hyunki.statsdontlie2.view.MainActivity
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Component(
        modules = [
        AppModule::class,
        BDLDatabaseModule::class,
        ViewModelFactoryModule::class,
        ViewModelsModule::class
        ]
)

@Singleton
interface AppComponent {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }

    fun inject(baseApplication: BaseApplication)

    fun inject(mainActivity: MainActivity)

}