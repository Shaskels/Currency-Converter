package com.example.currencyconverter.di

import android.content.Context
import androidx.room.Room
import com.example.currencyconverter.data.datasource.CurrencyDatabase
import com.example.currencyconverter.data.datasource.Dao
import dagger.Module
import dagger.Provides

@Module
class RoomModule {

    @Provides
    @AppScope
    fun providesRoomDatabase(context: Context): CurrencyDatabase{
        return Room.databaseBuilder(context, CurrencyDatabase::class.java, "currency_database").build()
    }

    @Provides
    @AppScope
    fun providesCurrencyDao(database: CurrencyDatabase):Dao{
        return database.currencyDao()
    }
}