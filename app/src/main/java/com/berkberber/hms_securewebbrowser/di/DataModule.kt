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

    factory<ErrorItem>(named("DeviceNotSecure")) { ErrorItem(
        icon = ContextCompat.getDrawable(get(named("appContext")), R.drawable.ic_device_not_secure)!!,
        title = androidContext().getString(R.string.device_not_secure),
        message = androidContext().getString(R.string.device_not_secure_message)) }

    factory<ErrorItem>(named("MaliciousApps")) { ErrorItem(
        icon = ContextCompat.getDrawable(get(named("appContext")), R.drawable.ic_malicious_apps)!!,
        title = androidContext().getString(R.string.device_not_secure),
        message = androidContext().getString(R.string.malicious_apps_message)) }
}