package com.hyunki.statsdontlie

import android.app.Application
import com.hyunki.statsdontlie.di.AppComponent
import com.hyunki.statsdontlie.di.DaggerAppComponent

class BaseApplication : Application() {
    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder().application(this).build()
    }
}