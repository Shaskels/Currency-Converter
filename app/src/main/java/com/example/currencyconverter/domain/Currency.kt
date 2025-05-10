package com.example.currencyconverter.domain

import java.math.BigDecimal

data class Currency(
    val id: String,
    val charCode: String,
    val nominal: Int,
    val name: String,
    val value: BigDecimal,
    val previous: BigDecimal
)