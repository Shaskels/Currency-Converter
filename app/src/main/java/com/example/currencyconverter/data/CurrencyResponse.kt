package com.example.currencyconverter.data

import com.google.gson.annotations.SerializedName

data class CurrencyResponse (
    @SerializedName("Valute")
    val valute: Map<String, Currency>
)