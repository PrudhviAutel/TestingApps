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
                    Utils.getFailureShowText("on Language = ${language.name}.\nReason - ${rcError.description}");
                setLanguageTestResult.postValue(
                    Resource.Companion.error(
                        errorMessage,
                        null
                    )
                )
            }

            override fun onSuccess() {
                val successMessage = Utils.getSuccessShowText("\nFor language = ${language.name} ");
                setLanguageTestResult.postValue(Resource.Companion.success(successMessage, successMessage))
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
                    val successMessage = Utils.getSuccessShowText(".\nRemote Controller Language = ${language.name} ");
                    getLanguageTestResult.postValue(Resource.Companion.success(language, successMessage))
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
                enterPairingTestResult.postValue(Resource.Companion.success(successMessage, successMessage))
            }
        })
        return enterPairingTestResult
    }

    override suspend fun setRFPowerTest(rfPower: RFPower): MutableLiveData<Resource<String>> {
        var setLanguageTestResult: MutableLiveData<Resource<String>> = MutableLiveData()
        mController.setRFPower(rfPower, object : CallbackWithNoParam {
            override fun onFailure(rcError: AutelError) {
                val errorMessage =
                    Utils.getFailureShowText("on RFPower = ${rfPower.name}.\nReason - ${rcError.description}");
                setLanguageTestResult.postValue(Resource.Companion.error(errorMessage, null))
            }

            override fun onSuccess() {
                val successMessage = Utils.getSuccessShowText(
                    "\nFor RFPower = ${rfPower.name} "
                );
                setLanguageTestResult.postValue(Resource.Companion.success(successMessage, successMessage))
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
                val successMessage = Utils.getSuccessShowText(
                    "\nRFPower = ${rfPower?.name} "
                );
                getRFPowerTestResult.postValue(Resource.Companion.success(rfPower, successMessage))
            }
        })
        return getRFPowerTestResult
    }

    override suspend fun setTeacherStudentModeTest(teachingMode: TeachingMode): MutableLiveData<Resource<String>> {
        var setLanguageTestResult: MutableLiveData<Resource<String>> = MutableLiveData()
        mController.setTeachingMode(teachingMode, object : CallbackWithNoParam {
            override fun onFailure(rcError: AutelError) {
                val errorMessage =
                    Utils.getFailureShowText("on Teaching Mode = ${teachingMode.name}.\nReason - ${rcError.description}");
                setLanguageTestResult.postValue(Resource.Companion.error(errorMessage, null))
            }

            override fun onSuccess() {
                val successMessage =
                    Utils.getSuccessShowText("\nFor Teaching Mode = ${teachingMode.name} ");
                setLanguageTestResult.postValue(Resource.Companion.success(successMessage, successMessage))
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
                val successMessage = Utils.getSuccessShowText(
                    "\nTeaching Mode = ${teachingMode?.name} "
                );
                getTeacherStudentModeTestResult.postValue(Resource.Companion.success(teachingMode, successMessage))
            }
        })
        return getTeacherStudentModeTestResult
    }

    override suspend fun setParameterUnitTest(parameterUnit: RemoteControllerParameterUnit): MutableLiveData<Resource<String>> {
        var setLanguageTestResult: MutableLiveData<Resource<String>> = MutableLiveData()
        mController.setParameterUnit(parameterUnit, object : CallbackWithNoParam {
            override fun onFailure(rcError: AutelError) {
                val errorMessage =
                    Utils.getFailureShowText("on Parameter unit = ${parameterUnit.name}.\nReason - ${rcError.description}");
                setLanguageTestResult.postValue(Resource.Companion.error(errorMessage, null))
            }

            override fun onSuccess() {
                val successMessage =
                    Utils.getSuccessShowText("\nFor Remote Controller Parameter Unit = ${parameterUnit.name} ");
                setLanguageTestResult.postValue(Resource.Companion.success(successMessage, successMessage))
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
                val successMessage = Utils.getSuccessShowText(
                    "\nParameter Unit = ${parameterUnit?.name} "
                );
                getParameterUnitTestResult.postValue(Resource.Companion.success(parameterUnit, successMessage))
            }
        })
        return getParameterUnitTestResult
    }

    override suspend fun setRCCommandStickModeTest(commandStickMode: RemoteControllerCommandStickMode): MutableLiveData<Resource<String>> {
        var setLanguageTestResult: MutableLiveData<Resource<String>> = MutableLiveData()
        mController.setCommandStickMode(commandStickMode, object : CallbackWithNoParam {
            override fun onFailure(rcError: AutelError) {
                val errorMessage =
                    Utils.getFailureShowText("on Command Stick Mode = ${commandStickMode.name}.\nReason - ${rcError.description}");
                setLanguageTestResult.postValue(Resource.Companion.error(errorMessage, null))
            }

            override fun onSuccess() {
                val successMessage =
                    Utils.getSuccessShowText("\nFor Remote Controller Command Stick Mode = ${commandStickMode.name} ");
                setLanguageTestResult.postValue(Resource.Companion.success(successMessage, successMessage))
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
                val successMessage = Utils.getSuccessShowText("\n Remote Controller CommandStick Mode = ${commandStickMode?.name}",
                    methodName = "getMaxHorizontalSpeed");
                getRCCommandStickModeTestResult.postValue(
                    Resource.Companion.success(
                        commandStickMode, successMessage
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
                setYawCoefficientTestResult.postValue(Resource.Companion.success(successMessage, successMessage))
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
                val successMessage = Utils.getSuccessShowText("\nYaw Coefficient = ${yawCoeff}",
                    methodName = "getYawCoefficient");
                getYawCoefficientTestResult.postValue(Resource.Companion.success(yawCoeff, successMessage))
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

            override fun onSuccess(versionInfo: RemoteControllerVersionInfo) {
                val successMessage =
                    Utils.getSuccessShowText("\nRFRX Version = ${versionInfo.rfrxVersion} , " +
                            "RFTX Version = ${versionInfo.rftxVersion}",
                        methodName = "getVersionInfo");
                getVersionInfoTestResult.postValue(Resource.Companion.success(versionInfo,successMessage))
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
                val successMessage = Utils.getSuccessShowText("\nSerial Number = ${serialNumber}",
                    methodName = "getSerialNumber");
                getSerialNumberTestResult.postValue(Resource.Companion.success(serialNumber,successMessage))
            }
        })
        return getSerialNumberTestResult
    }

}
