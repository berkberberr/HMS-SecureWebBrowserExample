package com.berkberber.hms_securewebbrowser.di

import androidx.core.content.ContextCompat
import com.berkberber.hms_securewebbrowser.R
import com.berkberber.hms_securewebbrowser.data.model.ErrorItem
import org.koin.android.ext.koin.androidContext
import org.koin.core.qualifier.named
import org.koin.dsl.module

val dataModule = module {
    factory<ErrorItem>(named("HmsNotAvailable")) { ErrorItem(
        icon = ContextCompat.getDrawable(get(named("appContext")), R.drawable.huawei)!!,
        title = androidContext().getString(R.string.hms_not_available),
        message = androidContext().getString(R.string.download_hms_core)) }
}