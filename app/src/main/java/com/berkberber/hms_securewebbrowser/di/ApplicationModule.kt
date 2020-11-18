package com.berkberber.hms_securewebbrowser.di

import org.koin.android.ext.koin.androidApplication
import org.koin.core.qualifier.named
import org.koin.dsl.module

val applicationModule = module {
    single(named("appContext")){ androidApplication().applicationContext }
}