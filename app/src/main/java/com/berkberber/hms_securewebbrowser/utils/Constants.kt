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
}