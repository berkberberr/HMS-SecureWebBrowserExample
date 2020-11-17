package com.berkberber.hms_securewebbrowser

import android.app.Application
import com.berkberber.hms_securewebbrowser.di.applicationModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class SecureWebBrowserApp: Application(){
    override fun onCreate() {
        super.onCreate()

        setup()
    }

    private fun setupKoin(){
        startKoin {
            androidContext(this@SecureWebBrowserApp)
            modules(
                applicationModule
            )
        }
    }

    private fun setup(){
        setupKoin()
    }
}