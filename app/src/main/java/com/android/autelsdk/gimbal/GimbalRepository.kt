package com.android.autelsdk.gimbal

import androidx.lifecycle.MutableLiveData
import com.android.autelsdk.util.Resource
import com.autel.common.remotecontroller.*
import com.autel.sdk.gimbal.AutelGimbal
import com.autel.sdk.remotecontroller.AutelRemoteController

interface GimbalRepository {

    fun setController (controller : AutelGimbal)

}