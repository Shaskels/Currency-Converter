package com.example.currencyconverter.presentation

import com.example.currencyconverter.domain.Currency

sealed class CurrencyListState {
    object Loading: CurrencyListState()
    object Error: CurrencyListState()
    data class Success(val currencyList: List<Currency>): CurrencyListState()
}