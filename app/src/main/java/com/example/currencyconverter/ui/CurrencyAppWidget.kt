package com.example.currencyconverter.ui

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.RemoteViews
import com.example.currencyconverter.CurrencyConverterApplication
import com.example.currencyconverter.R
import com.example.currencyconverter.domain.Currency
import com.example.currencyconverter.domain.usecases.GetCurrencies
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.math.BigDecimal
import java.util.Locale
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

/**
 * Implementation of App Widget functionality.
 */
class CurrencyAppWidget : AppWidgetProvider() {

    @Inject
    lateinit var getCurrencies: GetCurrencies

    override fun onReceive(context: Context?, intent: Intent?) {
        (context?.applicationContext as CurrencyConverterApplication).appComponent.injectWidget(this)
        super.onReceive(context, intent)
        Log.i("Worker", "receive")
    }

    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        // There may be multiple widgets active, so update all of them
        Log.i("Worker", "Update")
        for (appWidgetId in appWidgetIds) {
            goAsync(Dispatchers.IO) {
                val views = RemoteViews(context.packageName, R.layout.currency_app_widget)
                val currencies = getCurrencies()
                val usd = currencies.find { it.charCode == "USD" }
                val eur = currencies.find { it.charCode == "EUR" }
                if (usd != null)
                    changeView(views, usd, R.id.value, R.id.previous, R.id.imageView)
                if (eur != null)
                    changeView(views, eur, R.id.value2, R.id.previous2, R.id.imageView2)
                appWidgetManager.updateAppWidget(appWidgetId, views)
            }
        }
    }

    private fun changeView(
        views: RemoteViews,
        currency: Currency,
        valueId: Int,
        previousId: Int,
        imageId: Int
    ) {
        views.setTextViewText(
            valueId,
            "%.4f".format(Locale.ROOT, currency.value / BigDecimal(currency.nominal))
        )
        views.setTextViewText(
            previousId,
            "%.4f".format(Locale.ROOT, currency.previous / BigDecimal(currency.nominal))
        )
        if (currency.value > currency.previous) {
            views.setImageViewResource(imageId, R.drawable.baseline_arrow_upward_24)
        } else if (currency.value < currency.previous) {
            views.setImageViewResource(imageId, R.drawable.baseline_arrow_downward_24)
        } else {
            views.setImageViewResource(imageId, R.drawable.baseline_arrow_left_24)
        }
    }
}

@OptIn(DelicateCoroutinesApi::class)
fun BroadcastReceiver.goAsync(
    context: CoroutineContext = EmptyCoroutineContext,
    block: suspend CoroutineScope.(BroadcastReceiver.PendingResult) -> Unit
) {
    val pendingResult = goAsync()
    GlobalScope.launch(context) {
        try {
            block(pendingResult)
        } catch (e: Exception) {
            Log.e("Widget", e.message.toString())
        }
    }.invokeOnCompletion { pendingResult.finish() }
}