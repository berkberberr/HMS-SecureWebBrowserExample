package com.berkberber.hms_securewebbrowser.data.model

import android.content.Context
import android.graphics.drawable.Drawable
import com.berkberber.hms_securewebbrowser.R
import com.berkberber.hms_securewebbrowser.utils.SystemHelper
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.koin.core.qualifier.named

data class MaliciousApps(
    val packageName: String,
    val sha256: String,
    val apkCategory: Int
): KoinComponent{
    private val appContext: Context = get(named("appContext"))
    private val systemHelper: SystemHelper = get()

    fun getAppIcon(): Drawable = systemHelper.getAppIconByPackageName(packageName)
    fun getAppName(): String = systemHelper.getAppNameByPackageName(packageName)

    fun getThreatDescription(): String {
        return when(apkCategory){
            1 -> appContext.getString(R.string.risky_app_description)
            2 -> appContext.getString(R.string.virus_app_description)
            else -> ""
        }
    }
}