package com.berkberber.hms_securewebbrowser.utils

import android.content.Context
import com.huawei.hms.api.ConnectionResult
import com.huawei.hms.api.HuaweiApiAvailability
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.qualifier.named

class HmsHelper: KoinComponent{
    private val appContext: Context by inject(named("appContext"))

    fun isHmsAvailable(): Boolean {
        val isAvailable = HuaweiApiAvailability.getInstance().isHuaweiMobileNoticeAvailable(appContext)
        return (ConnectionResult.SUCCESS == isAvailable)
    }
}