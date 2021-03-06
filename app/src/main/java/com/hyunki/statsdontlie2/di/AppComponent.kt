package com.hyunki.statsdontlie2.di

import android.app.Application
import com.hyunki.statsdontlie2.BaseApplication
import com.hyunki.statsdontlie2.localdb.di.DatabaseModule
import com.hyunki.statsdontlie2.view.MainActivity
import com.hyunki.statsdontlie2.view.fragments.game.GameFragment
import com.hyunki.statsdontlie2.view.fragments.menu.MenuFragment
import com.hyunki.statsdontlie2.view.fragments.result.ResultFragment
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Component(
        modules = [
        AppModule::class,
        DatabaseModule::class,
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
    fun inject(gameFragment: GameFragment)
    fun inject(menuFragment: MenuFragment)
    fun inject(resultFragment: ResultFragment)
}