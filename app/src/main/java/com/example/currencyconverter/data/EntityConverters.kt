package com.example.currencyconverter.data

import java.math.BigDecimal

fun Currency.toDomainCurrency() = com.example.currencyconverter.domain.Currency(
    id,
    charCode,
    nominal,
    name,
    BigDecimal(value),
    BigDecimal(previous),
)

fun com.example.currencyconverter.domain.Currency.toDataCurrency() = Currency(
    id,
    charCode,
    nominal,
    name,
    value.toDouble(),
    previous.toDouble(),
)