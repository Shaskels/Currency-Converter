package com.example.currencyconverter.data.repository

import com.example.currencyconverter.data.datasource.CurrenciesLocalDataSource
import com.example.currencyconverter.data.datasource.CurrenciesRemoteDataSource
import com.example.currencyconverter.di.LocalDataSource
import com.example.currencyconverter.di.RemoteDataSource
import com.example.currencyconverter.domain.Currency
import com.example.currencyconverter.domain.repository.CurrenciesRepository
import java.math.BigDecimal
import javax.inject.Inject

class CurrenciesRepositoryImpl @Inject constructor(
    @LocalDataSource private val currenciesLocalDataSource: CurrenciesLocalDataSource,
    @RemoteDataSource private val currenciesRemoteDataSource: CurrenciesRemoteDataSource
): CurrenciesRepository {
    override suspend fun updateCurrencyListInLocal(currencies: List<Currency>) {
        currenciesLocalDataSource.updateAll(currencies.map {
            com.example.currencyconverter.data.Currency(it.id, it.charCode, it.nominal, it.name, it.value.toDouble(), it.previous.toDouble()) })
    }

    override suspend fun insertCurrencyListInLocal(currencies: List<Currency>) {
        currenciesLocalDataSource.insertAll(currencies.map {
            com.example.currencyconverter.data.Currency(it.id, it.charCode, it.nominal, it.name, it.value.toDouble(), it.previous.toDouble()) })
    }

    override suspend fun getCurrencyListFromLocal(): List<Currency> {
        return currenciesLocalDataSource.getAll().map { Currency(it.id, it.charCode, it.nominal, it.name,BigDecimal(it.value), BigDecimal(it.previous)) }
    }

    override suspend fun getCurrencyListFromRemote(): List<Currency> {
        return currenciesRemoteDataSource.get().map { Currency(it.id, it.charCode, it.nominal, it.name, BigDecimal(it.value), BigDecimal(it.previous)) }
    }
}