package com.berkberber.hms_securewebbrowser.ui.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.berkberber.hms_securewebbrowser.data.model.NavigationInfo

open class BaseViewModel: ViewModel() {
    val navigationAction = MutableLiveData<NavigationInfo>()

    fun postNavigationAction(navigationInfo: NavigationInfo){
        navigationAction.postValue(navigationInfo)
    }
}