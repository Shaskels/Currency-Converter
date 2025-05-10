package com.example.currencyconverter.di

import com.example.currencyconverter.data.datasource.CurrenciesLocalDataSource
import com.example.currencyconverter.data.datasource.CurrenciesLocalDataSourceImpl
import com.example.currencyconverter.data.datasource.CurrenciesRemoteDataSource
import com.example.currencyconverter.data.datasource.CurrenciesRemoteDataSourceImpl
import com.example.currencyconverter.data.repository.CurrenciesRepositoryImpl
import com.example.currencyconverter.domain.repository.CurrenciesRepository
import dagger.Binds
import dagger.Module
import javax.inject.Qualifier

@Module
interface DataModule {
    @Binds
    @RemoteDataSource
    fun bindRemoteDataSource(currenciesRemoteDataSource: CurrenciesRemoteDataSourceImpl): CurrenciesRemoteDataSource

    @Binds
    @LocalDataSource
    fun bindLocalDataSource(currenciesLocalDataSource: CurrenciesLocalDataSourceImpl): CurrenciesLocalDataSource

    @Binds
    fun bindCurrenciesRepository(currenciesRepositoryImpl: CurrenciesRepositoryImpl): CurrenciesRepository

}

@Qualifier
annotation class RemoteDataSource

@Qualifier
annotation class LocalDataSource