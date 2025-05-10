package com.example.currencyconverter.data.datasource

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.currencyconverter.data.Currency

@Database(entities = [Currency::class], version = 1)
abstract class CurrencyDatabase: RoomDatabase() {
    abstract fun currencyDao(): Dao
}