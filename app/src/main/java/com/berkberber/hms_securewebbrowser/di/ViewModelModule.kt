package com.berkberber.hms_securewebbrowser.di

import com.berkberber.hms_securewebbrowser.ui.splash.SplashViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { SplashViewModel() }
}