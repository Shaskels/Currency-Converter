package com.example.currencyconverter.data.datasource

import com.example.currencyconverter.data.CurrencyResponse
import retrofit2.http.GET

interface CurrencyService {

    @GET("/daily_json.js")
    suspend fun getCurrencies(): CurrencyResponse
}