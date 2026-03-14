package com.devbub.cycleforecast

import android.app.Application
import com.devbub.cycleforecast.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class WeatherBikingApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@WeatherBikingApp)
            modules(appModule)
        }
    }
}