package com.example.currencyconverter.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.currencyconverter.R
import com.example.currencyconverter.domain.Currency
import java.math.BigDecimal
import java.util.Locale

class CurrencyListAdapter(private val items: ArrayList<Currency>, private val click: OnItemClick): RecyclerView.Adapter<CurrencyListAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val charCode: TextView
        val value: TextView
        val previous: TextView
        val image: ImageView

        init {
            charCode = view.findViewById(R.id.charCode)
            value = view.findViewById(R.id.value)
            previous = view.findViewById(R.id.previous)
            image = view.findViewById(R.id.imageView)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.currency_list_item, parent, false)
        return ViewHolder(view)
    }

    fun setNewItems(newItems: List<Currency>){
        val diffCallback = CurrencyListCallback(items, newItems)
        val diffCourses = DiffUtil.calculateDiff(diffCallback)
        items.clear()
        items.addAll(newItems)
        diffCourses.dispatchUpdatesTo(this)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.charCode.text = items[position].charCode
        holder.value.text = "%.4f".format(Locale.ROOT, items[position].value / BigDecimal(items[position].nominal))
        holder.previous.text = "%.4f".format(Locale.ROOT, items[position].previous / BigDecimal(items[position].nominal))
        if (items[position].value > items[position].previous){
            holder.image.setImageResource(R.drawable.baseline_arrow_upward_24)
        }
        else if (items[position].value < items[position].previous){
            holder.image.setImageResource(R.drawable.baseline_arrow_downward_24)
        }
        else {
            holder.image.setImageResource(R.drawable.baseline_arrow_left_24)
        }
        holder.itemView.setOnClickListener{ click.onClick(items[position],position) }
    }

    fun interface OnItemClick{
        fun onClick(item: Currency,position: Int)
    }

}