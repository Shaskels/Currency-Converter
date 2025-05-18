package com.example.currencyconverter.presentation

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.currencyconverter.domain.usecases.UpdateCurrencies
import javax.inject.Inject

class UpdateWorker @Inject constructor(
    context: Context,
    workerParameters: WorkerParameters,
) : CoroutineWorker(context, workerParameters) {

    @Inject
    lateinit var updateCurrencies: UpdateCurrencies
  
    override suspend fun doWork(): Result {
        try {
            updateCurrencies()
        } catch (e: Exception){
            Result.retry()
        }
        return Result.success()
    }

}