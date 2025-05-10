package com.example.currencyconverter.presentation

import androidx.recyclerview.widget.DiffUtil
import com.example.currencyconverter.domain.Currency

class CurrencyListCallback(
    private val oldList: List<Currency>,
    private val newList: List<Currency>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].charCode == newList[newItemPosition].charCode &&
                oldList[oldItemPosition].name == newList[newItemPosition].name &&
                oldList[oldItemPosition].value == newList[newItemPosition].value &&
                oldList[oldItemPosition].previous == newList[newItemPosition].previous
    }

}