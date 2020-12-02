package com.berkberber.hms_securewebbrowser.utils

import android.os.Bundle
import com.berkberber.hms_securewebbrowser.data.model.ErrorItem

fun ErrorItem.toBundle(): Bundle{
    var errorItemBundle = Bundle()
    errorItemBundle.putSerializable(Constants.ERROR_ITEM, this)
    return errorItemBundle
}