package com.example.currencyconverter.presentation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.currencyconverter.domain.ConverterMode
import com.example.currencyconverter.domain.Currency
import com.example.currencyconverter.domain.usecases.GetCurrencies
import com.example.currencyconverter.domain.usecases.UpdateCurrencies
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.math.BigDecimal
import javax.inject.Inject

class CurrencyViewModel @Inject constructor(
    private val getCurrencies: GetCurrencies,
    private val updateCurrencies: UpdateCurrencies
) : ViewModel() {

    private val _currencyListState = MutableLiveData<CurrencyListState>()
    val currencyListState: LiveData<CurrencyListState> get() = _currencyListState

    private val _selectedItem = MutableStateFlow<Currency?>(null)
    val selectedItem: StateFlow<Currency?> = _selectedItem

    var mode = ConverterMode.TO_RUBLE
    fun getCurrenciesList() {
        viewModelScope.launch {
            _currencyListState.value = CurrencyListState.Loading
            try {
                val result = getCurrencies()
                _currencyListState.value = CurrencyListState.Success(result)
            } catch (e: Exception) {
                _currencyListState.value = CurrencyListState.Error
                Log.e("Currency", e.message.toString())
            }
        }
    }

    fun updateCurrenciesList() {
        viewModelScope.launch {
            _currencyListState.value = CurrencyListState.Loading
            try {
                val result = updateCurrencies()
                _currencyListState.value = CurrencyListState.Success(result)
            } catch (e: Exception) {
                _currencyListState.value = CurrencyListState.Error
                Log.e("Currency", e.message.toString())
            }
        }
    }

    fun updateClickedItem(item: Currency, position: Int) {
        viewModelScope.launch {
            _selectedItem.value = item
        }
    }

    fun convertToRubles(money: BigDecimal): BigDecimal {
        return if (_selectedItem.value != null)
            money * (_selectedItem.value!!.value / BigDecimal(_selectedItem.value!!.nominal))
        else BigDecimal(0)
    }

    fun convertToCurrency(money: BigDecimal): BigDecimal {
        return if (_selectedItem.value != null)
            money * (BigDecimal(_selectedItem.value!!.nominal) / _selectedItem.value!!.value)
        else BigDecimal(0)
    }
}