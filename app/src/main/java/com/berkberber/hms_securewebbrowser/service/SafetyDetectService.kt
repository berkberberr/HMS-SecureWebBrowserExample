package com.berkberber.hms_securewebbrowser.service

import android.content.Context
import android.os.Build
import com.berkberber.hms_securewebbrowser.BuildConfig
import com.berkberber.hms_securewebbrowser.data.enums.ErrorType
import com.berkberber.hms_securewebbrowser.data.model.MaliciousApps
import com.berkberber.hms_securewebbrowser.interfaces.IServiceListener
import com.berkberber.hms_securewebbrowser.utils.Constants
import com.berkberber.hms_securewebbrowser.utils.SafetyDetectHelper
import com.huawei.hms.support.api.entity.core.CommonCode
import com.huawei.hms.support.api.entity.safetydetect.MaliciousAppsData
import com.huawei.hms.support.api.safetydetect.SafetyDetect
import com.huawei.hms.support.api.safetydetect.SafetyDetectClient
import org.koin.core.component.KoinComponent
import org.koin.core.qualifier.named
import org.koin.core.component.inject
import java.security.NoSuchAlgorithmException
import java.security.SecureRandom

object SafetyDetectService : KoinComponent {
    private val appContext: Context by inject(named("appContext"))
    private val client: SafetyDetectClient = SafetyDetect.getClient(appContext)

    fun isDeviceSecure(serviceListener: IServiceListener<Boolean>) {
        val nonce = ByteArray(24)
        try {
            val random: SecureRandom = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
                SecureRandom.getInstanceStrong()
            else
                SecureRandom.getInstance(Constants.SAFETY_DETECT_ALGORITHM)
            random.nextBytes(nonce)
        } catch (error: NoSuchAlgorithmException) {
            serviceListener.onError(ErrorType.NO_SUCH_OBJECT)
        }

        checkDeviceSecurity(nonce, serviceListener)
    }

    private fun checkDeviceSecurity(nonce: ByteArray, serviceListener: IServiceListener<Boolean>){
        client.sysIntegrity(nonce, BuildConfig.APP_ID)
            .addOnSuccessListener { sysIntegrityResp ->
                SafetyDetectHelper.getPayloadDetailAsJson(sysIntegrityResp)?.let { jsonObject ->
                    serviceListener.onSuccess(jsonObject.getBoolean(Constants.BASIC_INTEGRITY))
                } ?: kotlin.run {
                    serviceListener.onError(ErrorType.SERVICE_FAILURE)
                }
            }
            .addOnFailureListener {
                serviceListener.onError(ErrorType.SERVICE_FAILURE)
            }
    }


    fun checkMaliciousApps(serviceListener: IServiceListener<List<MaliciousApps>?>){
        client.maliciousAppsList
            .addOnSuccessListener { maliciousAppsListResp ->
                if(maliciousAppsListResp.rtnCode == CommonCode.OK){
                    val maliciousAppsList: List<MaliciousAppsData> = maliciousAppsListResp.maliciousAppsList
                    if(maliciousAppsList.isEmpty())
                        serviceListener.onSuccess(null)
                    else{
                        var maliciousApps = mutableListOf<MaliciousApps>()
                        for(maliciousApp in maliciousAppsList){
                            maliciousApp.apply {
                                maliciousApps.add(MaliciousApps(packageName = apkPackageName,
                                    sha256 = apkSha256,
                                    apkCategory = apkCategory))
                            }
                        }
                        serviceListener.onSuccess(maliciousApps)
                    }
                }
            }
            .addOnFailureListener {
                serviceListener.onError(ErrorType.SERVICE_FAILURE)
            }
    }
}