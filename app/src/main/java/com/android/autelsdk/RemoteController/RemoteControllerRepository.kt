package com.android.autelsdk.RemoteController

import androidx.lifecycle.MutableLiveData
import com.android.autelsdk.util.Resource
import com.autel.common.remotecontroller.*

interface RemoteControllerRepository {

    fun setLanguageTest(language: RemoteControllerLanguage): MutableLiveData<Resource<String>>

    fun getLanguageTest(): MutableLiveData<Resource<RemoteControllerLanguage>>

    fun enterPairingTest(): MutableLiveData<Resource<String>>

    //TODO : How to test exit Pairing method as it has no Call back element

    fun setRFPowerTest(rfPower: RFPower): MutableLiveData<Resource<String>>

    fun getRFPowerTest(): MutableLiveData<Resource<RFPower>>

    fun setTeacherStudentModeTest(teachingMode: TeachingMode): MutableLiveData<Resource<String>>

    fun getTeacherStudentModeTest(): MutableLiveData<Resource<TeachingMode>>

    //TODO : How to test startCalibration and saveCalibration

    fun setParameterUnitTest(parameterUnit: RemoteControllerParameterUnit): MutableLiveData<Resource<String>>

    fun getParameterUnitTest(): MutableLiveData<Resource<RemoteControllerParameterUnit>>

    fun setRCCommandStickModeTest(commandStickMode: RemoteControllerCommandStickMode): MutableLiveData<Resource<String>>

    fun getRCCommandStickModeTest(): MutableLiveData<Resource<RemoteControllerCommandStickMode>>

    fun setYawCoefficientTest(yawCoeff: Float): MutableLiveData<Resource<String>>

    fun getYawCoefficientTest(): MutableLiveData<Resource<Float>>

    fun getVersionInfoTest(): MutableLiveData<Resource<RemoteControllerVersionInfo>>

    fun getSerialNumberTest(): MutableLiveData<Resource<String>>
}