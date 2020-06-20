package com.hyunki.statsdontlie.di

import android.app.Application
import com.hyunki.statsdontlie.BaseApplication
import com.hyunki.statsdontlie.view.MainActivity
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Component(
        modules = [
        AppModule::class
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