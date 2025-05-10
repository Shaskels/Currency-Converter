package com.example.currencyconverter.di

import android.content.Context
import androidx.work.WorkerParameters
import com.example.currencyconverter.presentation.UpdateWorker
import dagger.BindsInstance
import dagger.Component
import javax.inject.Scope

@Component(dependencies = [AppComponent::class])
@WorkerScope
interface WorkerComponent {
    @Component.Factory
    interface Factory {
        fun create(
            appComponent: AppComponent,
            @BindsInstance context: Context,
            @BindsInstance workerParameters: WorkerParameters,
        ): WorkerComponent
    }

    fun injectUpdateWorker(): UpdateWorker
}

@Scope
annotation class WorkerScope