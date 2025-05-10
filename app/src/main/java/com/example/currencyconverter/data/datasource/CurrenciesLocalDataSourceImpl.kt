package com.example.currencyconverter.data.datasource

import com.example.currencyconverter.data.Currency
import javax.inject.Inject

class CurrenciesLocalDataSourceImpl @Inject constructor(private val dao: Dao): CurrenciesLocalDataSource {

    override suspend fun getAll(): List<Currency> {
        return dao.getAll()
    }

    override suspend fun insertAll(currencies: List<Currency>) {
        dao.insertAll(*currencies.toTypedArray())
    }

    override suspend fun updateAll(currencies: List<Currency>) {
        dao.updateAll(*currencies.toTypedArray())
    }
}