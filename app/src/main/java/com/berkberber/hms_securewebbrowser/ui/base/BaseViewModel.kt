package com.berkberber.hms_securewebbrowser.ui.base

import androidx.lifecycle.ViewModel
import com.berkberber.hms_securewebbrowser.data.model.NavigationInfo
import com.berkberber.hms_securewebbrowser.utils.SingleLiveEvent

open class BaseViewModel: ViewModel() {
    val navigationAction = SingleLiveEvent<NavigationInfo>()

    fun postNavigationAction(navigationInfo: NavigationInfo){
        navigationAction.postValue(navigationInfo)
    }
}