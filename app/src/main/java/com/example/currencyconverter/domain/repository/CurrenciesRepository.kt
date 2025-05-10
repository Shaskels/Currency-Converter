package com.example.currencyconverter.domain.repository

import com.example.currencyconverter.domain.Currency

interface CurrenciesRepository {
    suspend fun updateCurrencyListInLocal(currencies: List<Currency>)

    suspend fun insertCurrencyListInLocal(currencies: List<Currency>)

    suspend fun getCurrencyListFromLocal(): List<Currency>

    suspend fun getCurrencyListFromRemote(): List<Currency>
}