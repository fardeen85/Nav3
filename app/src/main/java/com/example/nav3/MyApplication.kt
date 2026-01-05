package com.example.nav3

import android.app.Application
import com.example.nav3.di.appmodule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MyApplication)
            modules(appmodule)
        }
    }
}