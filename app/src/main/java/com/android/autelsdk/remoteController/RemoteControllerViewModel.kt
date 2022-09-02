package com.android.autelsdk.remoteController

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.autelsdk.util.Resource
import com.autel.common.remotecontroller.*

class RemoteControllerViewModel : ViewModel() {

    val remoteControllerRepository: RemoteControllerRepository = RemoteControllerRepositoryImpl()

    fun setLanguageTest(language: RemoteControllerLanguage): MutableLiveData<Resource<String>> {
        return remoteControllerRepository.setLanguageTest(language)
    }

    fun getLanguageTest(): MutableLiveData<Resource<RemoteControllerLanguage>> {
        return remoteControllerRepository.getLanguageTest()
    }

    fun enterPairingTest(): MutableLiveData<Resource<String>> {
        return remoteControllerRepository.enterPairingTest()
    }

    fun setRFPowerTest(rfPower: RFPower): MutableLiveData<Resource<String>> {
        return remoteControllerRepository.setRFPowerTest(rfPower)
    }

    fun getRFPowerTest(): MutableLiveData<Resource<RFPower>> {
        return remoteControllerRepository.getRFPowerTest()
    }

    fun setTeacherStudentModeTest(teachingMode: TeachingMode): MutableLiveData<Resource<String>> {
        return remoteControllerRepository.setTeacherStudentModeTest(teachingMode)
    }

    fun getTeacherStudentModeTest(): MutableLiveData<Resource<TeachingMode>> {
        return remoteControllerRepository.getTeacherStudentModeTest()
    }

    fun setParameterUnitTest(parameterUnit: RemoteControllerParameterUnit): MutableLiveData<Resource<String>> {
        return remoteControllerRepository.setParameterUnitTest(parameterUnit)
    }

    fun getParameterUnitTest(): MutableLiveData<Resource<RemoteControllerParameterUnit>> {
        return remoteControllerRepository.getParameterUnitTest()
    }

    fun setRCCommandStickModeTest(commandStickMode: RemoteControllerCommandStickMode): MutableLiveData<Resource<String>> {
        return remoteControllerRepository.setRCCommandStickModeTest(commandStickMode)
    }

    fun getRCCommandStickModeTest(): MutableLiveData<Resource<RemoteControllerCommandStickMode>> {
        return remoteControllerRepository.getRCCommandStickModeTest()
    }

    fun setYawCoefficientTest(yawCoeff: Float): MutableLiveData<Resource<String>> {
        return remoteControllerRepository.setYawCoefficientTest(yawCoeff)
    }

    fun getYawCoefficientTest(): MutableLiveData<Resource<Float>> {
        return remoteControllerRepository.getYawCoefficientTest()
    }

    fun getVersionInfoTest(): MutableLiveData<Resource<RemoteControllerVersionInfo>> {
        return remoteControllerRepository.getVersionInfoTest()
    }

    fun getSerialNumberTest(): MutableLiveData<Resource<String>> {
        return remoteControllerRepository.getSerialNumberTest()
    }
}