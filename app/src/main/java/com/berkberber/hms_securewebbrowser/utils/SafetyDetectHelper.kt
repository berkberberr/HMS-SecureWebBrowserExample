package com.berkberber.hms_securewebbrowser.utils

import android.util.Base64
import com.huawei.hms.support.api.entity.safetydetect.SysIntegrityResp
import org.json.JSONException
import org.json.JSONObject
import java.nio.charset.StandardCharsets

object SafetyDetectHelper {
    fun getPayloadDetailAsJson(sysIntegrityResp: SysIntegrityResp): JSONObject? {
        val jwsStr = sysIntegrityResp.result
        val jwsSplit = jwsStr.split(".").toTypedArray()
        val jwsPayloadStr = jwsSplit[1]
        val payloadDetail = String(Base64.decode(
            jwsPayloadStr.toByteArray(StandardCharsets.UTF_8), Base64.URL_SAFE),
            StandardCharsets.UTF_8)

        return try {
            JSONObject(payloadDetail)
        }catch (jsonError: JSONException){
            null
        }
    }
}