package com.android.autelsdk.gimbal

import androidx.lifecycle.MutableLiveData
import com.android.autelsdk.util.Resource
import com.autel.AutelNet2.aircraft.gimbal.engine.LeaserRadarInfo
import com.autel.common.RangePair
import com.autel.common.gimbal.*
import com.autel.common.gimbal.evo.EvoAngleInfo
import com.autel.common.gimbal.evo.EvoGimbalParameterRangeManager
import com.autel.common.gimbal.evo.GimbalAngleRange
import com.autel.common.gimbal.evo.GimbalAngleSpeed
import com.autel.common.remotecontroller.*
import com.autel.sdk.gimbal.AutelGimbal
import com.autel.sdk.remotecontroller.AutelRemoteController

interface GimbalRepository {

    fun <T> setController(controller : T)

    //Functions related to AutelGimbal Interface
    suspend fun setGimbalWorkModeTest(mode: GimbalWorkMode) : MutableLiveData<Resource<String>>

    suspend fun getGimbalWorkModeTest() : MutableLiveData<Resource<GimbalWorkMode>>

    suspend fun getVersionInfoTest() : MutableLiveData<Resource<GimbalVersionInfo>>

    fun getGimbalParameterRangeManager() : GimbalParameterRangeManager

    //Functions related to CruiserGimbal Interface
    suspend fun setAngleListenerTest() : MutableLiveData<Resource<EvoAngleInfo>>

    suspend fun getAngleRangeTest() : MutableLiveData<Resource<GimbalAngleRange>>

    suspend fun getAngleSpeedRangeTest() : MutableLiveData<Resource<RangePair<Int>>>

    suspend fun setGimbalAngleTest(pitch: Float, roll: Float, yaw: Float)

    suspend fun setGimbalAngleSpeedTest(pitch: Int, yaw: Int)

    suspend fun resetGimbalAngleTest(gimbalAxisType: GimbalAxisType) : MutableLiveData<Resource<String>>

    suspend fun setRollAdjustDataTest(roll: Float)

    suspend fun getRollAdjustDataTest() : MutableLiveData<Resource<Double>>

    //Extra Methods not given in Dragon Fish Sdk Sample Taken from CruiserGimbalImpl
    suspend fun setLeaserRadarListenerTest() : MutableLiveData<Resource<LeaserRadar>>

    suspend fun resetLeaserRadarListenerTest()

    suspend fun adjustGimbalDirectionTest(x: Float, y: Float, pitch: Float, roll: Float, yaw: Float) : MutableLiveData<Resource<String>>

    fun getCruiserGimbalParameterRangeManager() : EvoGimbalParameterRangeManager

    suspend fun setYawAdjustDataTest(yaw: Float) : MutableLiveData<Resource<String>>

    suspend fun getYawAdjustDataTest() : MutableLiveData<Resource<Double>>

    suspend fun setPitchAdjustDataTest(pitch: Float) : MutableLiveData<Resource<String>>

    suspend fun getPitchAdjustDataTest() : MutableLiveData<Resource<Double>>

    suspend fun setSaveParamsTest() : MutableLiveData<Resource<String>>

    suspend fun setGimbalCalibrationTest() : MutableLiveData<Resource<String>>

    suspend fun stopGimbalCalibrationTest() : MutableLiveData<Resource<String>>

    suspend fun getAdjustGimbalAngelDataTest() : MutableLiveData<Resource<GimbalAdjustmentAngle>>


}