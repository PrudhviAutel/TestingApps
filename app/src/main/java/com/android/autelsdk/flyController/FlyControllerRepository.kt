package com.android.autelsdk.flyController

import android.view.View
import androidx.lifecycle.MutableLiveData
import com.android.autelsdk.util.Resource
import com.autel.common.flycontroller.CalibrateCompassStatus
import com.autel.common.flycontroller.FlyControllerVersionInfo
import com.autel.common.flycontroller.LedPilotLamp

interface FlyControllerRepository {

    fun setBeginnerModeStateTest(enable: Boolean): MutableLiveData<Resource<String>>
    fun getBeginnerModeStateTest(): MutableLiveData<Resource<Boolean>>
    fun getMaxHeightTest(): MutableLiveData<Resource<Float>>
    fun getMaxRangeTest(): MutableLiveData<Resource<Float>>
    fun setMaxHeightTest(): MutableLiveData<Resource<String>>
    fun setMaxRangeTest(): MutableLiveData<Resource<String>>
    fun getReturnHeightTest(): MutableLiveData<Resource<Float>>
    fun setReturnHeightTest(): MutableLiveData<Resource<String>>

    fun setHorizontalSpeedTest(): MutableLiveData<Resource<String>>
    fun getHorizontalSpeedTest(): MutableLiveData<Resource<Float>>
    fun setCalibrateCompassListenerTest(): MutableLiveData<Resource<CalibrateCompassStatus>>
    fun getSerialNumberTest(): MutableLiveData<Resource<String>>
    fun getVersionInfoTest(): MutableLiveData<Resource<FlyControllerVersionInfo>>
    fun cancelLandTest(): MutableLiveData<Resource<String>>
    fun cancelReturnTest(): MutableLiveData<Resource<String>>
    fun goHome(): MutableLiveData<Resource<String>>
    fun landTest(): MutableLiveData<Resource<String>>
    fun startCalibrateCompassTest(): MutableLiveData<Resource<CalibrateCompassStatus>>
    fun setAircraftLocationAsHomePointTest(): MutableLiveData<Resource<String>>
    fun setLocationAsHomePointTest(): MutableLiveData<Resource<String>>
    fun setLedPilotLampTest(): MutableLiveData<Resource<String>>
    fun getLedPilotLampTest(): MutableLiveData<Resource<LedPilotLamp>>
    fun setAttiModeEnableTest(enable: Boolean): MutableLiveData<Resource<Boolean>>
    fun isAttiModeEnableTest(): MutableLiveData<Resource<Boolean>>
}