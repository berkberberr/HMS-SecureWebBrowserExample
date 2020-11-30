package com.berkberber.hms_securewebbrowser.data.model

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import com.berkberber.hms_securewebbrowser.R
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.koin.core.qualifier.named

data class MaliciousApps(
    val packageName: String,
    val sha256: String,
    val apkCategory: Int
): KoinComponent{
    private val appContext: Context = get(named("appContext"))

    fun getAppIconAccordingToThreat(): Drawable?{
        return when(apkCategory){
            1 -> ContextCompat.getDrawable(appContext, R.drawable.malicious_app_risk)
            2 -> ContextCompat.getDrawable(appContext, R.drawable.malicious_app_virus)
            else -> null
        }
    }
}