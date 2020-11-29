package com.berkberber.hms_securewebbrowser.interfaces

import com.berkberber.hms_securewebbrowser.data.enums.ErrorType

interface IServiceListener<T> {
    fun onSuccess(successResult: T)
    fun onError(errorType: ErrorType)
}