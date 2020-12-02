package com.berkberber.hms_securewebbrowser.data.model

import android.graphics.drawable.Drawable
import java.io.Serializable

data class ErrorItem(
    val icon: Drawable,
    val title: String,
    val message: String = ""
) : Serializable