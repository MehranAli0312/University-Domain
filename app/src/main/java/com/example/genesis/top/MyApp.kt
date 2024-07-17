package com.example.genesis.top

import android.app.Application
import com.example.genesis.di.apiModule
import com.example.genesis.di.countryModule
import org.koin.core.context.startKoin

class MyApp : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            /** Retrofit **/
            modules(apiModule, countryModule)
        }
    }
}