package com.android.autelsdk.flyController

import androidx.lifecycle.MutableLiveData
import com.android.autelsdk.util.Resource
import com.autel.common.flycontroller.*
import com.autel.sdk.flycontroller.AutelFlyController
import com.autel.sdk.remotecontroller.AutelRemoteController

interface FlyControllerRepository {

    suspend fun setBeginnerModeStateTest(enable: Boolean): MutableLiveData<Resource<String>>
    suspend fun getBeginnerModeStateTest(): MutableLiveData<Resource<Boolean>>
    suspend fun getMaxHeightTest(): MutableLiveData<Resource<Float>>
    suspend fun getMaxRangeTest(): MutableLiveData<Resource<Float>>
    suspend fun setMaxHeightTest(value: Double): MutableLiveData<Resource<String>>
    suspend fun setMaxRangeTest(value: Double): MutableLiveData<Resource<String>>
    suspend fun getReturnHeightTest(): MutableLiveData<Resource<Float>>
    suspend fun setReturnHeightTest(value: Double): MutableLiveData<Resource<String>>

    suspend fun setMaxHorizontalSpeedTest(value: Double): MutableLiveData<Resource<String>>
    suspend fun getMaxHorizontalSpeedTest(): MutableLiveData<Resource<Float>>
    suspend fun setCalibrateCompassListenerTest(): MutableLiveData<Resource<CalibrateCompassStatus>>
    suspend fun getSerialNumberTest(): MutableLiveData<Resource<String>>
    suspend fun getVersionInfoTest(): MutableLiveData<Resource<FlyControllerVersionInfo>>
    suspend fun cancelLandTest(): MutableLiveData<Resource<String>>
    suspend fun cancelReturnTest(): MutableLiveData<Resource<String>>
    suspend fun goHomeTest(): MutableLiveData<Resource<String>>
    suspend fun landTest(): MutableLiveData<Resource<String>>
    suspend fun startCalibrateCompassTest(): MutableLiveData<Resource<CalibrateCompassStatus>>
    suspend fun setAircraftLocationAsHomePointTest(): MutableLiveData<Resource<String>>
    suspend fun setLocationAsHomePointTest(lat: Double, lon: Double): MutableLiveData<Resource<String>>
    suspend fun setLedPilotLampTest(ledPilotLamp: LedPilotLamp): MutableLiveData<Resource<String>>
    suspend fun getLedPilotLampTest(): MutableLiveData<Resource<LedPilotLamp>>
    suspend fun setAttitudeModeEnableTest(enable: Boolean): MutableLiveData<Resource<Boolean>>
    suspend fun isAttitudeModeEnableTest(): MutableLiveData<Resource<Boolean>>
    fun setWarningListenerTest(): MutableLiveData<Resource<ARMWarning>>

     fun setFlyController(controller : AutelFlyController)

    fun takeOffTest(): MutableLiveData<Resource<Pair<Boolean, FlightErrorState>>>
}