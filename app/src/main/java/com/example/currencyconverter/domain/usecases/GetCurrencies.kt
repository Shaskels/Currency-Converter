package com.example.currencyconverter.domain.usecases

import com.example.currencyconverter.domain.Currency
import com.example.currencyconverter.domain.repository.CurrenciesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetCurrencies @Inject constructor(private val repository: CurrenciesRepository) {
    suspend operator fun invoke(): List<Currency> {
        return withContext(Dispatchers.IO) {
            var result = repository.getCurrencyListFromLocal()
            if (result.isEmpty()) {
                result = repository.getCurrencyListFromRemote()
                repository.insertCurrencyListInLocal(result)
            }
            return@withContext result
        }
    }
}