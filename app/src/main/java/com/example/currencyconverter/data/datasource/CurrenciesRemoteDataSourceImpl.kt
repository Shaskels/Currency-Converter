package com.example.currencyconverter.data.datasource

import com.example.currencyconverter.data.Currency
import javax.inject.Inject

class CurrenciesRemoteDataSourceImpl @Inject constructor(private val currencyService: CurrencyService): CurrenciesRemoteDataSource {
    override suspend fun get(): List<Currency> {

        return currencyService.getCurrencies().valute.values.toList()
    }
}