package com.android.autelsdk.RemoteController

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.autelsdk.util.Resource
import com.autel.common.CallbackWithNoParam
import com.autel.common.CallbackWithOneParam
import com.autel.common.error.AutelError
import com.autel.common.remotecontroller.*

class RemoteControllerRepositoryImpl<AutelRemoteController>(
    private val mController: com.autel.sdk.remotecontroller.AutelRemoteController,
) : RemoteControllerRepository{

    override fun setLanguageTest(language : RemoteControllerLanguage): MutableLiveData<Resource<String>> {
        var setLanguageTestResult : MutableLiveData<Resource<String>> = MutableLiveData()
        mController.setLanguage(language, object : CallbackWithNoParam {
            override fun onFailure(rcError: AutelError) {
                val errorMessage = "";
                setLanguageTestResult.postValue(Resource.Companion.error(errorMessage, null))
            }

            override fun onSuccess() {
                val successMessage = "";
                setLanguageTestResult.postValue(Resource.Companion.success(successMessage))
            }
        })
        return setLanguageTestResult
    }

    override fun getLanguageTest(): MutableLiveData<Resource<RemoteControllerLanguage>> {
        var getLanguageTestResult : MutableLiveData<Resource<RemoteControllerLanguage>> = MutableLiveData()
        mController.getLanguage(object : CallbackWithOneParam<RemoteControllerLanguage> {
            override fun onFailure(rcError: AutelError) {
                val errorMessage = "";
                getLanguageTestResult.postValue(Resource.Companion.error(errorMessage, null))
            }

            override fun onSuccess(language: RemoteControllerLanguage?) {
                getLanguageTestResult.postValue(Resource.Companion.success(language))
            }
        })
        return getLanguageTestResult
    }

    override fun enterPairingTest(): MutableLiveData<Resource<String>> {
        var enterPairingTestResult : MutableLiveData<Resource<String>> = MutableLiveData()
        mController.enterPairing(object : CallbackWithNoParam {
            override fun onFailure(rcError: AutelError) {
                val errorMessage = "";
                enterPairingTestResult.postValue(Resource.Companion.error(errorMessage, null))
            }

            override fun onSuccess() {
                val successMessage = "";
                enterPairingTestResult.postValue(Resource.Companion.success(successMessage))
            }
        })
        return enterPairingTestResult
    }

    override fun setRFPowerTest(rfPower: RFPower): MutableLiveData<Resource<String>> {
        TODO("Not yet implemented")
    }

    override fun getRFPowerTest(): MutableLiveData<Resource<RFPower>> {
        var getRFPowerTestResult : MutableLiveData<Resource<RFPower>> = MutableLiveData()
        mController.getRFPower(object : CallbackWithOneParam<RFPower> {
            override fun onFailure(rcError: AutelError) {
                val errorMessage = "";
                getRFPowerTestResult.postValue(Resource.Companion.error(errorMessage, null))
            }

            override fun onSuccess(rfPower: RFPower?) {
                getRFPowerTestResult.postValue(Resource.Companion.success(rfPower))
            }
        })
        return getRFPowerTestResult
    }

    override fun setTeacherStudentModeTest(teachingMode: TeachingMode): MutableLiveData<Resource<String>> {
        TODO("Not yet implemented")
    }

    override fun getTeacherStudentModeTest(): MutableLiveData<Resource<TeachingMode>> {
        var getTeacherStudentModeTestResult : MutableLiveData<Resource<TeachingMode>> = MutableLiveData()
        mController.getTeachingMode(object : CallbackWithOneParam<TeachingMode> {
            override fun onFailure(rcError: AutelError) {
                val errorMessage = "";
                getTeacherStudentModeTestResult.postValue(Resource.Companion.error(errorMessage, null))
            }

            override fun onSuccess(teachingMode: TeachingMode?) {
                getTeacherStudentModeTestResult.postValue(Resource.Companion.success(teachingMode))
            }
        })
        return getTeacherStudentModeTestResult
    }

    override fun setParameterUnitTest(parameterUnit: RemoteControllerParameterUnit): MutableLiveData<Resource<String>> {
        TODO("Not yet implemented")
    }

    override fun getParameterUnitTest(): MutableLiveData<Resource<RemoteControllerParameterUnit>> {
        var getParameterUnitTestResult : MutableLiveData<Resource<RemoteControllerParameterUnit>> = MutableLiveData()
        mController.getLengthUnit(object : CallbackWithOneParam<RemoteControllerParameterUnit> {
            override fun onFailure(rcError: AutelError) {
                val errorMessage = "";
                getParameterUnitTestResult.postValue(Resource.Companion.error(errorMessage, null))
            }

            override fun onSuccess(parameterUnit: RemoteControllerParameterUnit?) {
                getParameterUnitTestResult.postValue(Resource.Companion.success(parameterUnit))
            }
        })
        return getParameterUnitTestResult
    }

    override fun setRCCommandStickModeTest(commandStickMode: RemoteControllerCommandStickMode): MutableLiveData<Resource<String>> {
        TODO("Not yet implemented")
    }

    override fun getRCCommandStickModeTest(): MutableLiveData<Resource<RemoteControllerCommandStickMode>> {
        var getRCCommandStickModeTestResult : MutableLiveData<Resource<RemoteControllerCommandStickMode>> = MutableLiveData()
        mController.getCommandStickMode(object : CallbackWithOneParam<RemoteControllerCommandStickMode> {
            override fun onFailure(rcError: AutelError) {
                val errorMessage = "";
                getRCCommandStickModeTestResult.postValue(Resource.Companion.error(errorMessage, null))
            }

            override fun onSuccess(commandStickMode: RemoteControllerCommandStickMode?) {
                getRCCommandStickModeTestResult.postValue(Resource.Companion.success(commandStickMode))
            }
        })
        return getRCCommandStickModeTestResult
    }

    override fun setYawCoefficientTest(yawCoeff: Float): MutableLiveData<Resource<String>> {
        TODO("Not yet implemented")
    }

    override fun getYawCoefficientTest(): MutableLiveData<Resource<Float>> {
        var getYawCoefficientTestResult : MutableLiveData<Resource<Float>> = MutableLiveData()
        mController.getYawCoefficient(object : CallbackWithOneParam<Float> {
            override fun onFailure(rcError: AutelError) {
                val errorMessage = "";
                getYawCoefficientTestResult.postValue(Resource.Companion.error(errorMessage, null))
            }

            override fun onSuccess(yawCoeff: Float?) {
                getYawCoefficientTestResult.postValue(Resource.Companion.success(yawCoeff))
            }
        })
        return getYawCoefficientTestResult
    }

    override fun getVersionInfoTest(): MutableLiveData<Resource<RemoteControllerVersionInfo>> {
        var getVersionInfoTestResult : MutableLiveData<Resource<RemoteControllerVersionInfo>> = MutableLiveData()
        mController.getVersionInfo(object : CallbackWithOneParam<RemoteControllerVersionInfo> {
            override fun onFailure(rcError: AutelError) {
                val errorMessage = "";
                getVersionInfoTestResult.postValue(Resource.Companion.error(errorMessage, null))
            }

            override fun onSuccess(versionInfo: RemoteControllerVersionInfo?) {
                getVersionInfoTestResult.postValue(Resource.Companion.success(versionInfo))
            }
        })
        return getVersionInfoTestResult
    }

    override fun getSerialNumberTest(): MutableLiveData<Resource<String>> {
        var getSerialNumberTestResult : MutableLiveData<Resource<String>> = MutableLiveData()
        mController.getSerialNumber(object : CallbackWithOneParam<String> {
            override fun onFailure(rcError: AutelError) {
                val errorMessage = "";
                getSerialNumberTestResult.postValue(Resource.Companion.error(errorMessage, null))
            }

            override fun onSuccess(serialNumber: String?) {
                getSerialNumberTestResult.postValue(Resource.Companion.success(serialNumber))
            }
        })
        return getSerialNumberTestResult
    }


}