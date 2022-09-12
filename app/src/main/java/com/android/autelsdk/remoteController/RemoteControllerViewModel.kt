package com.android.autelsdk.remoteController

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.autelsdk.util.Resource
import com.autel.common.remotecontroller.*

class RemoteControllerViewModel() : ViewModel() {

    val remoteControllerRepository: RemoteControllerRepository = RemoteControllerRepositoryImpl()

    suspend fun setLanguageTest(language: RemoteControllerLanguage): MutableLiveData<Resource<String>> {
        return remoteControllerRepository.setLanguageTest(language)
    }

    suspend fun getLanguageTest(): MutableLiveData<Resource<RemoteControllerLanguage>> {
        return remoteControllerRepository.getLanguageTest()
    }

    suspend fun enterPairingTest(): MutableLiveData<Resource<String>> {
        return remoteControllerRepository.enterPairingTest()
    }

    suspend fun setRFPowerTest(rfPower: RFPower): MutableLiveData<Resource<String>> {
        return remoteControllerRepository.setRFPowerTest(rfPower)
    }

    suspend fun getRFPowerTest(): MutableLiveData<Resource<RFPower>> {
        return remoteControllerRepository.getRFPowerTest()
    }

    suspend fun setTeacherStudentModeTest(teachingMode: TeachingMode): MutableLiveData<Resource<String>> {
        return remoteControllerRepository.setTeacherStudentModeTest(teachingMode)
    }

    suspend fun getTeacherStudentModeTest(): MutableLiveData<Resource<TeachingMode>> {
        return remoteControllerRepository.getTeacherStudentModeTest()
    }

    suspend fun setParameterUnitTest(parameterUnit: RemoteControllerParameterUnit): MutableLiveData<Resource<String>> {
        return remoteControllerRepository.setParameterUnitTest(parameterUnit)
    }

    suspend fun getParameterUnitTest(): MutableLiveData<Resource<RemoteControllerParameterUnit>> {
        return remoteControllerRepository.getParameterUnitTest()
    }

    suspend fun setRCCommandStickModeTest(commandStickMode: RemoteControllerCommandStickMode): MutableLiveData<Resource<String>> {
        return remoteControllerRepository.setRCCommandStickModeTest(commandStickMode)
    }

    suspend fun getRCCommandStickModeTest(): MutableLiveData<Resource<RemoteControllerCommandStickMode>> {
        return remoteControllerRepository.getRCCommandStickModeTest()
    }

    suspend fun setYawCoefficientTest(yawCoeff: Float): MutableLiveData<Resource<String>> {
        return remoteControllerRepository.setYawCoefficientTest(yawCoeff)
    }

    suspend fun getYawCoefficientTest(): MutableLiveData<Resource<Float>> {
        return remoteControllerRepository.getYawCoefficientTest()
    }

    suspend fun getVersionInfoTest(): MutableLiveData<Resource<RemoteControllerVersionInfo>> {
        return remoteControllerRepository.getVersionInfoTest()
    }

    suspend fun getSerialNumberTest(): MutableLiveData<Resource<String>> {
        return remoteControllerRepository.getSerialNumberTest()
    }

//    // When we will have to Pass the Values to ViewModel, we can use this code
//    class Factory(private val mController: AutelRemoteController?) : ViewModelProvider.Factory {
//        override fun <T : ViewModel> create(modelClass: Class<T>): T {
//            return RemoteControllerViewModel(mController) as T
//        }
//    }
}