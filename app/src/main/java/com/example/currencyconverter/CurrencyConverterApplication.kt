package com.example.currencyconverter

import android.app.Application
import com.example.currencyconverter.di.AppComponent
import com.example.currencyconverter.di.DaggerAppComponent

class CurrencyConverterApplication : Application() {

    lateinit var appComponent: AppComponent
        private set

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.factory().create(applicationContext)
    }
}