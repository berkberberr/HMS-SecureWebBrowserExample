package com.berkberber.hms_securewebbrowser.di

import com.berkberber.hms_securewebbrowser.utils.HmsHelper
import com.berkberber.hms_securewebbrowser.utils.SystemHelper
import org.koin.android.ext.koin.androidApplication
import org.koin.core.qualifier.named
import org.koin.dsl.module

val applicationModule = module {
    single(named("appContext")){ androidApplication().applicationContext }

    factory { HmsHelper() }
    factory { SystemHelper() }
}