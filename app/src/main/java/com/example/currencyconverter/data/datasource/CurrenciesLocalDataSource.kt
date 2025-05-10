package com.example.currencyconverter.data.datasource

import com.example.currencyconverter.data.Currency

interface CurrenciesLocalDataSource {
    suspend fun getAll() : List<Currency>
    suspend fun insertAll(currencies: List<Currency>)
    suspend fun updateAll(currencies: List<Currency>)
}