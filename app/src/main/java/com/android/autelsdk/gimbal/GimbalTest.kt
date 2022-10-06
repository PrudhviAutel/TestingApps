package com.android.autelsdk.gimbal

import com.autel.common.gimbal.GimbalWorkMode
import com.autel.sdk.remotecontroller.AutelRemoteController

class GimbalTest {
    lateinit var gimbalWorkMode: GimbalWorkMode

    fun setWorkMode(mode: Int) {
        if (mode == 1)
            gimbalWorkMode = GimbalWorkMode.STABILIZED
        else if (mode == 2)
            gimbalWorkMode = GimbalWorkMode.FPV
        else
            gimbalWorkMode = GimbalWorkMode.UNKNOWN
    }

    fun getWorkMode(mController : AutelRemoteController) : GimbalWorkMode? {
        return null
        //  mController.getGimbalWork
    }
}