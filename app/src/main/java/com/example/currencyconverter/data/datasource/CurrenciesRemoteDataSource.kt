package com.example.currencyconverter.data.datasource

import com.example.currencyconverter.data.Currency

interface CurrenciesRemoteDataSource {
    suspend fun get() : List<Currency>
}