package com.example.currencyconverter.di

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters

class WorkerProvider(private val appComponent: AppComponent) : WorkerFactory() {
    override fun createWorker(
        appContext: Context,
        workerClassName: String,
        workerParameters: WorkerParameters
    ): ListenableWorker {
        val daggerWorkerComponent = DaggerWorkerComponent.factory().create(appComponent, appContext, workerParameters)
        return daggerWorkerComponent.injectUpdateWorker()
    }
}