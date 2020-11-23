package com.berkberber.hms_securewebbrowser.ui.splash

import com.berkberber.hms_securewebbrowser.R
import com.berkberber.hms_securewebbrowser.data.model.ErrorItem
import com.berkberber.hms_securewebbrowser.data.model.NavigationInfo
import com.berkberber.hms_securewebbrowser.ui.base.BaseViewModel
import com.berkberber.hms_securewebbrowser.utils.HmsHelper
import com.berkberber.hms_securewebbrowser.utils.toBundle
import org.koin.core.component.inject
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.koin.core.qualifier.named

class SplashViewModel: BaseViewModel(), KoinComponent{
    private val hmsHelper: HmsHelper by inject()

    var errorItem: ErrorItem = get(named("HmsNotAvailable"))

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
        postNavigationAction(NavigationInfo(R.id.action_splashFragment_to_browserFragment))
    }

    private fun hmsUnavailable(){
        postNavigationAction(NavigationInfo(
                actionId = R.id.action_splashFragment_to_errorFragment,
                bundle = errorItem.toBundle())
        )
    }
}