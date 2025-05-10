package com.example.currencyconverter.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.math.BigDecimal

@Entity
data class Currency(
    @SerializedName("ID")
    @PrimaryKey
    val id: String,
    @SerializedName("CharCode")
    @ColumnInfo(name="char_code")
    val charCode: String,
    @SerializedName("Nominal")
    @ColumnInfo(name="nominal")
    val nominal: Int,
    @SerializedName("Name")
    @ColumnInfo(name = "name")
    val name: String,
    @SerializedName("Value")
    @ColumnInfo(name="value")
    val value: Double,
    @SerializedName("Previous")
    @ColumnInfo(name = "previous")
    val previous: Double
){
    companion object{
        const val TABLE_NAME = "currency"
    }
}


