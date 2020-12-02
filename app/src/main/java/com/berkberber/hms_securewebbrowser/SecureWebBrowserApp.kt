package com.berkberber.hms_securewebbrowser

import android.app.Application
import com.berkberber.hms_securewebbrowser.di.applicationModule
import com.berkberber.hms_securewebbrowser.di.dataModule
import com.berkberber.hms_securewebbrowser.di.viewModelModule
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
                applicationModule,
                viewModelModule,
                dataModule
            )
        }
    }

    private fun setup(){
        setupKoin()
    }
}