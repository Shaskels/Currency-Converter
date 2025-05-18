package com.example.currencyconverter.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.currencyconverter.R
import com.example.currencyconverter.presentation.UpdateWorker
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction().apply {
                add(R.id.fragment_container_up, CurrencyFragment.newInstance())
                commit()
            }
            supportFragmentManager.beginTransaction().apply {
                add(R.id.fragment_container_down, ConverterFragment.newInstance())
                commit()
            }
        }
        val constraints =
            Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build()
        val updateWorkerRequest =
            PeriodicWorkRequestBuilder<UpdateWorker>(12, TimeUnit.HOURS).setConstraints(constraints)
                .addTag("update").build()

        WorkManager.getInstance(applicationContext).enqueue(updateWorkerRequest)
    }
}