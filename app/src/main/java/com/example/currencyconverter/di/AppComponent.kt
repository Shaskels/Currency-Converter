package com.example.currencyconverter.di

import android.content.Context
import com.example.currencyconverter.ui.CurrencyAppWidget
import com.example.currencyconverter.domain.repository.CurrenciesRepository
import com.example.currencyconverter.domain.usecases.UpdateCurrencies
import com.example.currencyconverter.presentation.ViewModelFactory
import com.example.currencyconverter.ui.ConverterFragment
import com.example.currencyconverter.ui.CurrencyFragment
import dagger.BindsInstance
import dagger.Component
import javax.inject.Scope


@Component(modules = [RemoteModule::class, DataModule::class, PresentationModule::class, RoomModule::class])
@AppScope
interface AppComponent {

    val currenciesRepository: CurrenciesRepository

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): AppComponent
    }

    fun viewModelsFactory() : ViewModelFactory
    fun injectCurrencyFragment(fragment: CurrencyFragment)
    fun injectConverterFragment(fragment: ConverterFragment)
    fun injectWidget(widget: CurrencyAppWidget)
}

@Scope
annotation class AppScope