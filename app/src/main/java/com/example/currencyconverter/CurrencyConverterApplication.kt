package com.example.currencyconverter

import android.app.Application
import androidx.work.Configuration
import com.example.currencyconverter.di.AppComponent
import com.example.currencyconverter.di.DaggerAppComponent
import com.example.currencyconverter.di.WorkerProvider

class CurrencyConverterApplication : Application(), Configuration.Provider {

    lateinit var appComponent: AppComponent
        private set

    override val workManagerConfiguration: Configuration
        get() = Configuration.Builder().setMinimumLoggingLevel(android.util.Log.INFO)
            .setWorkerFactory(WorkerProvider(appComponent)).build()

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.factory().create(applicationContext)
    }
}