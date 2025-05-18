package com.example.currencyconverter.domain.usecases

import java.math.BigDecimal
import javax.inject.Inject

class ConvertToCurrency @Inject constructor(){
    operator fun invoke(currency: BigDecimal, money: BigDecimal, nominal: BigDecimal): BigDecimal{
        return (nominal * money) / currency
    }
}