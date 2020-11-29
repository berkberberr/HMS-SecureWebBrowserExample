package com.berkberber.hms_securewebbrowser.ui.splash

import com.berkberber.hms_securewebbrowser.R
import com.berkberber.hms_securewebbrowser.data.enums.ErrorType
import com.berkberber.hms_securewebbrowser.data.model.ErrorItem
import com.berkberber.hms_securewebbrowser.data.model.NavigationInfo
import com.berkberber.hms_securewebbrowser.interfaces.IServiceListener
import com.berkberber.hms_securewebbrowser.service.SafetyDetectService
import com.berkberber.hms_securewebbrowser.ui.base.BaseViewModel
import com.berkberber.hms_securewebbrowser.utils.HmsHelper
import com.berkberber.hms_securewebbrowser.utils.toBundle
import org.koin.core.component.inject
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.koin.core.qualifier.named

class SplashViewModel: BaseViewModel(), KoinComponent{
    private val hmsHelper: HmsHelper by inject()

    private val hmsNotAvailableItem: ErrorItem = get(named("HmsNotAvailable"))
    private val deviceNotSecureItem: ErrorItem = get(named("DeviceNotSecure"))

    fun makeSecurityControls(){
        checkIsHmsAvailable()
    }

    private fun checkIsHmsAvailable(){
        if(hmsHelper.isHmsAvailable())
            hmsAvailable()
        else
            hmsUnavailable()
    }

    private fun hmsAvailable(){
        SafetyDetectService.isDeviceSecure(object: IServiceListener<Boolean>{
            override fun onSuccess(successResult: Boolean) {
                if(successResult)
                    checkMaliciousApps()
                else
                    deviceNotSecure()
            }

            override fun onError(errorType: ErrorType) {

            }

        })
    }

    private fun checkMaliciousApps(){
        // TODO: Check malicious apps
    }

    private fun deviceNotSecure(){
        postNavigationAction(NavigationInfo(
            actionId = R.id.action_splashFragment_to_errorFragment,
            bundle = deviceNotSecureItem.toBundle())
        )
    }

    private fun hmsUnavailable(){
        postNavigationAction(NavigationInfo(
                actionId = R.id.action_splashFragment_to_errorFragment,
                bundle = hmsNotAvailableItem.toBundle())
        )
    }
}