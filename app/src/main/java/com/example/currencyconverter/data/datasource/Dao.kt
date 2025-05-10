package com.example.currencyconverter.data.datasource

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.currencyconverter.data.Currency

@Dao
interface Dao {
    @Query("SELECT * FROM ${Currency.TABLE_NAME}")
    suspend fun getAll(): List<Currency>

    @Insert
    suspend fun insertAll(vararg currencies: Currency)

    @Update
    suspend fun updateAll(vararg currencies: Currency)
}