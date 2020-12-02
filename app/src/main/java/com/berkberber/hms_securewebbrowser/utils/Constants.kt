package com.berkberber.hms_securewebbrowser.utils

import android.content.Context
import com.berkberber.hms_securewebbrowser.R
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.koin.core.qualifier.named

object Constants: KoinComponent{
    private val context: Context = get(named("appContext"))

    // Data
    val ERROR_ITEM = context.getString(R.string.error_item)
    val MALICIOUS_APPS = context.getString(R.string.malicious_apps)

    // String value for service
    const val BASIC_INTEGRITY = "basicIntegrity"
    const val SAFETY_DETECT_ALGORITHM = "SHA1PRNG"
}