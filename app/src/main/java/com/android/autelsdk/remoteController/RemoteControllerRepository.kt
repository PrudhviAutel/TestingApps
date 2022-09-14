package com.android.autelsdk.remoteController

import androidx.lifecycle.MutableLiveData
import com.android.autelsdk.util.Resource
import com.autel.common.remotecontroller.*

interface RemoteControllerRepository {

    suspend fun setLanguageTest(language: RemoteControllerLanguage): MutableLiveData<Resource<String>>

    suspend fun getLanguageTest(): MutableLiveData<Resource<RemoteControllerLanguage>>

    suspend fun enterPairingTest(): MutableLiveData<Resource<String>>

    //TODO : How to test exit Pairing method as it has no Call back element

    suspend fun setRFPowerTest(rfPower: RFPower): MutableLiveData<Resource<String>>

    suspend fun getRFPowerTest(): MutableLiveData<Resource<RFPower>>

    suspend fun setTeacherStudentModeTest(teachingMode: TeachingMode): MutableLiveData<Resource<String>>

    suspend fun getTeacherStudentModeTest(): MutableLiveData<Resource<TeachingMode>>

    //TODO : How to test startCalibration and saveCalibration

    suspend fun setParameterUnitTest(parameterUnit: RemoteControllerParameterUnit): MutableLiveData<Resource<String>>

    suspend fun getParameterUnitTest(): MutableLiveData<Resource<RemoteControllerParameterUnit>>

    suspend fun setRCCommandStickModeTest(commandStickMode: RemoteControllerCommandStickMode): MutableLiveData<Resource<String>>

    suspend fun getRCCommandStickModeTest(): MutableLiveData<Resource<RemoteControllerCommandStickMode>>

    suspend fun setYawCoefficientTest(yawCoeff: Float): MutableLiveData<Resource<String>>

    suspend fun getYawCoefficientTest(): MutableLiveData<Resource<Float>>

    suspend fun getVersionInfoTest(): MutableLiveData<Resource<RemoteControllerVersionInfo>>

    suspend fun getSerialNumberTest(): MutableLiveData<Resource<String>>
}