package com.example.currencyconverter.data.repository

import com.example.currencyconverter.data.datasource.CurrenciesLocalDataSource
import com.example.currencyconverter.data.datasource.CurrenciesRemoteDataSource
import com.example.currencyconverter.data.toDataCurrency
import com.example.currencyconverter.data.toDomainCurrency
import com.example.currencyconverter.di.LocalDataSource
import com.example.currencyconverter.di.RemoteDataSource
import com.example.currencyconverter.domain.Currency
import com.example.currencyconverter.domain.repository.CurrenciesRepository

import javax.inject.Inject

class CurrenciesRepositoryImpl @Inject constructor(
    @LocalDataSource private val currenciesLocalDataSource: CurrenciesLocalDataSource,
    @RemoteDataSource private val currenciesRemoteDataSource: CurrenciesRemoteDataSource
) : CurrenciesRepository {
    override suspend fun updateCurrencyListInLocal(currencies: List<Currency>) {
        currenciesLocalDataSource.updateAll(currencies.map {
            it.toDataCurrency()
        })
    }

    override suspend fun insertCurrencyListInLocal(currencies: List<Currency>) {
        currenciesLocalDataSource.insertAll(currencies.map {
            it.toDataCurrency()
        })
    }

    override suspend fun getCurrencyListFromLocal(): List<Currency> {
        return currenciesLocalDataSource.getAll().map { it.toDomainCurrency() }
    }

    override suspend fun getCurrencyListFromRemote(): List<Currency> {
        return currenciesRemoteDataSource.get().map { it.toDomainCurrency() }
    }
}