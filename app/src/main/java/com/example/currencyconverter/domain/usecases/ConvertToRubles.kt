package com.example.currencyconverter.domain.usecases

import java.math.BigDecimal
import javax.inject.Inject

class ConvertToRubles @Inject constructor(){
    operator fun invoke(currency: BigDecimal, money: BigDecimal, nominal: BigDecimal): BigDecimal{
        return money * (currency / nominal)
    }
}