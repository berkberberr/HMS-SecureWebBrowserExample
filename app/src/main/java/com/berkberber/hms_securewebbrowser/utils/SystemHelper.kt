package com.berkberber.hms_securewebbrowser.utils

import android.content.Context
import android.content.pm.ApplicationInfo
import android.graphics.drawable.Drawable
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.qualifier.named

class SystemHelper: KoinComponent {
    private val appContext: Context by inject(named("appContext"))

    /**
     * Getting application information by package name
     * @param packageName: Package name of the app that we want to get information about
     * @return ApplicationInfo class to get app icons, app names and etc. by package name
     */
    private fun getAppByPackageName(packageName: String): ApplicationInfo{
        return appContext.packageManager.getApplicationInfo(packageName, 0)
    }

    /**
     * Getting application icon by package name
     * @param packageName: Package name of the app which we want to get icon
     * @return Icon of the application as drawable
     */
    fun getAppIconByPackageName(packageName: String): Drawable{
        val app = getAppByPackageName(packageName)
        return appContext.packageManager.getApplicationIcon(app)
    }

    /**
     * Getting application name by package name
     * @param packageName: Package name of the app which we want to get name
     * @return Name of the application as drawable
     */
    fun getAppNameByPackageName(packageName: String): String{
        val app = getAppByPackageName(packageName)
        return appContext.packageManager.getApplicationLabel(app).toString()
    }
}