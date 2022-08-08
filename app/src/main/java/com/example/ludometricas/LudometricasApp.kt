package com.example.ludometricas

import android.app.Application
import com.example.ludometricas.modules.AppModules
import org.koin.core.context.startKoin
import org.koin.android.ext.koin.androidContext

class LudometricasApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@LudometricasApp)
            modules(
                AppModules.getModules()
            )
        }
    }
}