package com.hyunki.statsdontlie2

import android.app.Application
import com.hyunki.statsdontlie2.di.AppComponent
import com.hyunki.statsdontlie2.di.DaggerAppComponent

class BaseApplication : Application() {
    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder().application(this).build()
    }
}