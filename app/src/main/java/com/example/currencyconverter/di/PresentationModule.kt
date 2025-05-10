package com.example.currencyconverter.di

import androidx.lifecycle.ViewModel
import com.example.currencyconverter.presentation.CurrencyViewModel
import dagger.Binds
import dagger.MapKey
import dagger.Module
import dagger.multibindings.IntoMap
import kotlin.reflect.KClass

@Module
interface PresentationModule {

    @Binds
    @IntoMap
    @ViewModelKey(CurrencyViewModel::class)
    @AppScope
    fun bindCurrencyViewModel (currencyViewModel: CurrencyViewModel): ViewModel

}

@MustBeDocumented
@Target(
    AnnotationTarget.FUNCTION,
    AnnotationTarget.PROPERTY_GETTER,
    AnnotationTarget.PROPERTY_SETTER
)
@Retention(AnnotationRetention.RUNTIME)
@MapKey
annotation class ViewModelKey(val value: KClass<out ViewModel>)