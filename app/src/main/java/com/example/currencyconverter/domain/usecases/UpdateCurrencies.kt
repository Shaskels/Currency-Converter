package com.example.currencyconverter.domain.usecases

import com.example.currencyconverter.domain.Currency
import com.example.currencyconverter.domain.repository.CurrenciesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UpdateCurrencies @Inject constructor(private val currenciesRepository: CurrenciesRepository) {
    suspend operator fun invoke(): List<Currency> {
        return withContext(Dispatchers.IO)
        {
            val result = currenciesRepository.getCurrencyListFromRemote()
            currenciesRepository.updateCurrencyListInLocal(result)
            return@withContext result
        }
    }
}