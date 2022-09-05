package com.android.autelsdk.remoteController

import androidx.lifecycle.MutableLiveData
import com.android.autelsdk.util.Resource
import com.android.autelsdk.util.Utils
import com.autel.common.CallbackWithNoParam
import com.autel.common.CallbackWithOneParam
import com.autel.common.error.AutelError
import com.autel.common.remotecontroller.*
import com.autel.internal.remotecontroller.RemoteController10
import com.autel.sdk.remotecontroller.AutelRemoteController

class RemoteControllerRepositoryImpl : RemoteControllerRepository {

    val mController: AutelRemoteController = RemoteController10()

    //❌
    override suspend fun setLanguageTest(language: RemoteControllerLanguage): MutableLiveData<Resource<String>> {

        var setLanguageTestResult: MutableLiveData<Resource<String>> = MutableLiveData()
        mController.setLanguage(language, object : CallbackWithNoParam {
            override fun onFailure(rcError: AutelError) {
                val errorMessage =
                    Utils.getFailureShowText("on Language = ${language.value}.\nReason - ${rcError.description}");
                setLanguageTestResult.postValue(
                    Resource.Companion.error(
                        errorMessage,
                        null
                    )
                )
            }

            override fun onSuccess() {
                val successMessage =
                    Utils.getSuccessShowText("\nFor language = ${language.value} ");
                setLanguageTestResult.postValue(Resource.Companion.success(successMessage))
            }
        })
        return setLanguageTestResult
    }

    override suspend fun getLanguageTest(): MutableLiveData<Resource<RemoteControllerLanguage>> {
        var getLanguageTestResult: MutableLiveData<Resource<RemoteControllerLanguage>> =
            MutableLiveData()
        mController.getLanguage(object : CallbackWithOneParam<RemoteControllerLanguage> {
            override fun onFailure(rcError: AutelError) {
                val errorMessage = Utils.getFailureShowText("\nReason - ${rcError.description}");
                getLanguageTestResult.postValue(Resource.Companion.error(errorMessage, null))
            }

            override fun onSuccess(language: RemoteControllerLanguage?) {
                language?.let {
                    val successMessage =
                        Utils.getSuccessShowText(".\nRemote Controller Language = ${language.value} ");
                    getLanguageTestResult.postValue(Resource.Companion.success(language))
                }
            }
        })
        return getLanguageTestResult
    }

    override suspend fun enterPairingTest(): MutableLiveData<Resource<String>> {
        var enterPairingTestResult: MutableLiveData<Resource<String>> = MutableLiveData()
        mController.enterPairing(object : CallbackWithNoParam {
            override fun onFailure(rcError: AutelError) {
                val errorMessage = Utils.getFailureShowText("\nReason - ${rcError.description}");
                enterPairingTestResult.postValue(Resource.Companion.error(errorMessage, null))
            }

            override fun onSuccess() {
                val successMessage = Utils.getSuccessShowText();
                enterPairingTestResult.postValue(Resource.Companion.success(successMessage))
            }
        })
        return enterPairingTestResult
    }

    override suspend fun setRFPowerTest(rfPower: RFPower): MutableLiveData<Resource<String>> {
        var setLanguageTestResult: MutableLiveData<Resource<String>> = MutableLiveData()
        mController.setRFPower(rfPower, object : CallbackWithNoParam {
            override fun onFailure(rcError: AutelError) {
                val errorMessage =
                    Utils.getFailureShowText("on RFPower = ${rfPower.value}.\nReason - ${rcError.description}");
                setLanguageTestResult.postValue(Resource.Companion.error(errorMessage, null))
            }

            override fun onSuccess() {
                val successMessage = Utils.getSuccessShowText(
                    "\nFor RFPower = ${rfPower.value} "
                );
                setLanguageTestResult.postValue(Resource.Companion.success(successMessage))
            }
        })
        return setLanguageTestResult
    }

    override suspend fun getRFPowerTest(): MutableLiveData<Resource<RFPower>> {
        var getRFPowerTestResult: MutableLiveData<Resource<RFPower>> = MutableLiveData()
        mController.getRFPower(object : CallbackWithOneParam<RFPower> {
            override fun onFailure(rcError: AutelError) {
                val errorMessage = Utils.getFailureShowText("\nReason - ${rcError.description}");
                getRFPowerTestResult.postValue(Resource.Companion.error(errorMessage, null))
            }

            override fun onSuccess(rfPower: RFPower?) {
                getRFPowerTestResult.postValue(Resource.Companion.success(rfPower))
            }
        })
        return getRFPowerTestResult
    }

    override suspend fun setTeacherStudentModeTest(teachingMode: TeachingMode): MutableLiveData<Resource<String>> {
        var setLanguageTestResult: MutableLiveData<Resource<String>> = MutableLiveData()
        mController.setTeachingMode(teachingMode, object : CallbackWithNoParam {
            override fun onFailure(rcError: AutelError) {
                val errorMessage =
                    Utils.getFailureShowText("on Teaching Mode = ${teachingMode.value}.\nReason - ${rcError.description}");
                setLanguageTestResult.postValue(Resource.Companion.error(errorMessage, null))
            }

            override fun onSuccess() {
                val successMessage =
                    Utils.getSuccessShowText("\nFor Teaching Mode = ${teachingMode.value} ");
                setLanguageTestResult.postValue(Resource.Companion.success(successMessage))
            }
        })
        return setLanguageTestResult
    }

    override suspend fun getTeacherStudentModeTest(): MutableLiveData<Resource<TeachingMode>> {
        var getTeacherStudentModeTestResult: MutableLiveData<Resource<TeachingMode>> =
            MutableLiveData()
        mController.getTeachingMode(object : CallbackWithOneParam<TeachingMode> {
            override fun onFailure(rcError: AutelError) {
                val errorMessage = Utils.getFailureShowText("\nReason - ${rcError.description}");
                getTeacherStudentModeTestResult.postValue(
                    Resource.Companion.error(
                        errorMessage,
                        null
                    )
                )
            }

            override fun onSuccess(teachingMode: TeachingMode?) {
                getTeacherStudentModeTestResult.postValue(Resource.Companion.success(teachingMode))
            }
        })
        return getTeacherStudentModeTestResult
    }

    override suspend fun setParameterUnitTest(parameterUnit: RemoteControllerParameterUnit): MutableLiveData<Resource<String>> {
        var setLanguageTestResult: MutableLiveData<Resource<String>> = MutableLiveData()
        mController.setParameterUnit(parameterUnit, object : CallbackWithNoParam {
            override fun onFailure(rcError: AutelError) {
                val errorMessage =
                    Utils.getFailureShowText("on Parameter unit = ${parameterUnit.value}.\nReason - ${rcError.description}");
                setLanguageTestResult.postValue(Resource.Companion.error(errorMessage, null))
            }

            override fun onSuccess() {
                val successMessage =
                    Utils.getSuccessShowText("\nFor Remote Controller Parameter Unit = ${parameterUnit.value} ");
                setLanguageTestResult.postValue(Resource.Companion.success(successMessage))
            }
        })
        return setLanguageTestResult
    }

    override suspend fun getParameterUnitTest(): MutableLiveData<Resource<RemoteControllerParameterUnit>> {
        var getParameterUnitTestResult: MutableLiveData<Resource<RemoteControllerParameterUnit>> =
            MutableLiveData()
        mController.getLengthUnit(object : CallbackWithOneParam<RemoteControllerParameterUnit> {
            override fun onFailure(rcError: AutelError) {
                val errorMessage = Utils.getFailureShowText("\nReason - ${rcError.description}");
                getParameterUnitTestResult.postValue(Resource.Companion.error(errorMessage, null))
            }

            override fun onSuccess(parameterUnit: RemoteControllerParameterUnit?) {
                getParameterUnitTestResult.postValue(Resource.Companion.success(parameterUnit))
            }
        })
        return getParameterUnitTestResult
    }

    override suspend fun setRCCommandStickModeTest(commandStickMode: RemoteControllerCommandStickMode): MutableLiveData<Resource<String>> {
        var setLanguageTestResult: MutableLiveData<Resource<String>> = MutableLiveData()
        mController.setCommandStickMode(commandStickMode, object : CallbackWithNoParam {
            override fun onFailure(rcError: AutelError) {
                val errorMessage =
                    Utils.getFailureShowText("on Command Stick Mode = ${commandStickMode.value}.\nReason - ${rcError.description}");
                setLanguageTestResult.postValue(Resource.Companion.error(errorMessage, null))
            }

            override fun onSuccess() {
                val successMessage =
                    Utils.getSuccessShowText("\nFor Remote Controller Command Stick Mode = ${commandStickMode.value} ");
                setLanguageTestResult.postValue(Resource.Companion.success(successMessage))
            }
        })
        return setLanguageTestResult
    }

    override suspend fun getRCCommandStickModeTest(): MutableLiveData<Resource<RemoteControllerCommandStickMode>> {
        var getRCCommandStickModeTestResult: MutableLiveData<Resource<RemoteControllerCommandStickMode>> =
            MutableLiveData()
        mController.getCommandStickMode(object :
            CallbackWithOneParam<RemoteControllerCommandStickMode> {
            override fun onFailure(rcError: AutelError) {
                val errorMessage = Utils.getFailureShowText("\nReason - ${rcError.description}");
                getRCCommandStickModeTestResult.postValue(
                    Resource.Companion.error(
                        errorMessage,
                        null
                    )
                )
            }

            override fun onSuccess(commandStickMode: RemoteControllerCommandStickMode?) {
                getRCCommandStickModeTestResult.postValue(
                    Resource.Companion.success(
                        commandStickMode
                    )
                )
            }
        })
        return getRCCommandStickModeTestResult
    }

    override suspend fun setYawCoefficientTest(yawCoeff: Float): MutableLiveData<Resource<String>> {
        var setYawCoefficientTestResult: MutableLiveData<Resource<String>> = MutableLiveData()
        mController.setYawCoefficient(yawCoeff, object : CallbackWithNoParam {
            override fun onFailure(rcError: AutelError) {
                val errorMessage = Utils.getFailureShowText(
                    "on Yaw Coefficient = ${yawCoeff}.\nReason - ${rcError.description}",
                    methodName = "setYawCoefficient()"
                );
                setYawCoefficientTestResult.postValue(Resource.Companion.error(errorMessage, null))
            }

            override fun onSuccess() {
                val successMessage =
                    Utils.getSuccessShowText(
                        "\nFor Yaw Coefficient = ${yawCoeff} ",
                        methodName = "setYawCoefficient"
                    );
                setYawCoefficientTestResult.postValue(Resource.Companion.success(successMessage))
            }
        })
        return setYawCoefficientTestResult
    }

    override suspend fun getYawCoefficientTest(): MutableLiveData<Resource<Float>> {
        var getYawCoefficientTestResult: MutableLiveData<Resource<Float>> = MutableLiveData()
        mController.getYawCoefficient(object : CallbackWithOneParam<Float> {
            override fun onFailure(rcError: AutelError) {
                val errorMessage = Utils.getFailureShowText(
                    "\nReason - ${rcError.description}",
                    methodName = "getYawCoefficient"
                );
                getYawCoefficientTestResult.postValue(Resource.Companion.error(errorMessage, null))
            }

            override fun onSuccess(yawCoeff: Float?) {
                getYawCoefficientTestResult.postValue(Resource.Companion.success(yawCoeff))
            }
        })
        return getYawCoefficientTestResult
    }

    override suspend fun getVersionInfoTest(): MutableLiveData<Resource<RemoteControllerVersionInfo>> {
        var getVersionInfoTestResult: MutableLiveData<Resource<RemoteControllerVersionInfo>> =
            MutableLiveData()
        mController.getVersionInfo(object : CallbackWithOneParam<RemoteControllerVersionInfo> {
            override fun onFailure(rcError: AutelError) {
                val errorMessage = Utils.getFailureShowText(
                    "\nReason - ${rcError.description}",
                    methodName = "getVersionInfo"
                );
                getVersionInfoTestResult.postValue(Resource.Companion.error(errorMessage, null))
            }

            override fun onSuccess(versionInfo: RemoteControllerVersionInfo?) {
                getVersionInfoTestResult.postValue(Resource.Companion.success(versionInfo))
            }
        })
        return getVersionInfoTestResult
    }

    override suspend fun getSerialNumberTest(): MutableLiveData<Resource<String>> {
        var getSerialNumberTestResult: MutableLiveData<Resource<String>> = MutableLiveData()
        mController.getSerialNumber(object : CallbackWithOneParam<String> {
            override fun onFailure(rcError: AutelError) {
                val errorMessage = Utils.getFailureShowText(
                    "\nReason - ${rcError.description}",
                    methodName = "getSerialNumber"
                );
                getSerialNumberTestResult.postValue(Resource.Companion.error(errorMessage, null))
            }

            override fun onSuccess(serialNumber: String?) {
                getSerialNumberTestResult.postValue(Resource.Companion.success(serialNumber))
            }
        })
        return getSerialNumberTestResult
    }

}
