package com.example.genesis.top

import android.app.Application
import com.example.genesis.di.apiModule
import com.example.genesis.di.countryModule
import org.koin.core.context.startKoin

// Custom Application class to initialize Koin for dependency injection
class MyApp : Application() {

    // Called when the application is starting, before any activity, service, or receiver objects have been created
    override fun onCreate() {
        super.onCreate()

        // Initialize Koin for dependency injection
        startKoin {
            // Specify the modules to be used by Koin
            modules(apiModule, countryModule)
        }
    }
}
