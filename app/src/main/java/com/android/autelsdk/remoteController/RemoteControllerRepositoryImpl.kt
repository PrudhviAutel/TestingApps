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

class RemoteControllerRepositoryImpl() : RemoteControllerRepository {

    var mController: AutelRemoteController = RemoteController10()

    override fun setRemoteController(controller: AutelRemoteController) {
        mController = controller
    }

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

    override fun exitPairing() {
        mController.exitPairing()
    }


    override suspend fun setRFPowerTest(rfPower: RFPower): MutableLiveData<Resource<String>> {
        var setRFPowerTestResult: MutableLiveData<Resource<String>> = MutableLiveData()
        mController.setRFPower(rfPower, object : CallbackWithNoParam {
            override fun onFailure(rcError: AutelError) {
                val errorMessage =
                    Utils.getFailureShowText("on RFPower = ${rfPower.name}.\nReason - ${rcError.description}");
                setRFPowerTestResult.postValue(Resource.Companion.error(errorMessage, null))
            }

            override fun onSuccess() {
                val successMessage = Utils.getSuccessShowText(
                    "\nFor RFPower = ${rfPower.name} "
                );
                setRFPowerTestResult.postValue(Resource.Companion.success(successMessage, successMessage))
            }
        })
        return setRFPowerTestResult
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
        var setTeacherStudentModeTestResult: MutableLiveData<Resource<String>> = MutableLiveData()
        mController.setTeachingMode(teachingMode, object : CallbackWithNoParam {
            override fun onFailure(rcError: AutelError) {
                val errorMessage =
                    Utils.getFailureShowText("on Teaching Mode = ${teachingMode.name}.\nReason - ${rcError.description}");
                setTeacherStudentModeTestResult.postValue(Resource.Companion.error(errorMessage, null))
            }

            override fun onSuccess() {
                val successMessage =
                    Utils.getSuccessShowText("\nFor Teaching Mode = ${teachingMode.name} ");
                setTeacherStudentModeTestResult.postValue(Resource.Companion.success(successMessage, successMessage))
            }
        })
        return setTeacherStudentModeTestResult
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
        var setParameterUnitTestResult: MutableLiveData<Resource<String>> = MutableLiveData()
        mController.setParameterUnit(parameterUnit, object : CallbackWithNoParam {
            override fun onFailure(rcError: AutelError) {
                val errorMessage =
                    Utils.getFailureShowText("on Parameter unit = ${parameterUnit.name}.\nReason - ${rcError.description}");
                setParameterUnitTestResult.postValue(Resource.Companion.error(errorMessage, null))
            }

            override fun onSuccess() {
                val successMessage =
                    Utils.getSuccessShowText("\nFor Remote Controller Parameter Unit = ${parameterUnit.name} ");
                setParameterUnitTestResult.postValue(Resource.Companion.success(successMessage, successMessage))
            }
        })
        return setParameterUnitTestResult
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
        var setRCCommandStickModeTestResult: MutableLiveData<Resource<String>> = MutableLiveData()
        mController.setCommandStickMode(commandStickMode, object : CallbackWithNoParam {
            override fun onFailure(rcError: AutelError) {
                val errorMessage =
                    Utils.getFailureShowText("on Command Stick Mode = ${commandStickMode.name}.\nReason - ${rcError.description}");
                setRCCommandStickModeTestResult.postValue(Resource.Companion.error(errorMessage, null))
            }

            override fun onSuccess() {
                val successMessage =
                    Utils.getSuccessShowText("\nFor Remote Controller Command Stick Mode = ${commandStickMode.name} ");
                setRCCommandStickModeTestResult.postValue(Resource.Companion.success(successMessage, successMessage))
            }
        })
        return setRCCommandStickModeTestResult
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

    override fun getParameterRangeManager(): RemoteControllerParameterRangeManager {
        return mController.parameterRangeManager
    }

    override suspend fun setStickCalibrationTest(calibration: RemoteControllerStickCalibration) : MutableLiveData<Resource<RemoteControllerStickCalibration>> {
        var setStickCalibrationTestResult: MutableLiveData<Resource<RemoteControllerStickCalibration>> = MutableLiveData()
        mController.setStickCalibration(calibration, object : CallbackWithNoParam {
            override fun onFailure(rcError: AutelError) {
                val errorMessage =
                    Utils.getFailureShowText("on Calibration = ${calibration.name}.\nReason - ${rcError.description}");
                setStickCalibrationTestResult.postValue(Resource.Companion.error(errorMessage, null))
            }

            override fun onSuccess() {
                val successMessage = Utils.getSuccessShowText("\nFor Calibration = ${calibration.name} ");
                setStickCalibrationTestResult.postValue(Resource.Companion.success(calibration, successMessage))
            }
        })
        return setStickCalibrationTestResult
    }

    override suspend fun setRemoteButtonControllerListenerTest(): MutableLiveData<Resource<RemoteControllerNavigateButtonEvent>> {
        var setRemoteButtonControllerListenerTest: MutableLiveData<Resource<RemoteControllerNavigateButtonEvent>> = MutableLiveData()
        mController.setRemoteButtonControllerListener(object : CallbackWithOneParam<RemoteControllerNavigateButtonEvent> {
            override fun onFailure(rcError: AutelError) {
                val errorMessage =
                    Utils.getFailureShowText(".\nReason - ${rcError.description}");
                setRemoteButtonControllerListenerTest.postValue(Resource.Companion.error(errorMessage, null))
            }

            override fun onSuccess(rcControlBtnEvent: RemoteControllerNavigateButtonEvent) {
                val successMessage = Utils.getSuccessShowText("\nRemote Controller Navigate Button Event = ${rcControlBtnEvent.name} ");
                setRemoteButtonControllerListenerTest.postValue(Resource.Companion.success(rcControlBtnEvent, successMessage))
            }

        })
        return setRemoteButtonControllerListenerTest
    }

    override suspend fun setInfoDataListenerTest(): MutableLiveData<Resource<RemoteControllerInfo>> {
        var setInfoDataListenerTestResult: MutableLiveData<Resource<RemoteControllerInfo>> = MutableLiveData()
        mController.setInfoDataListener(object : CallbackWithOneParam<RemoteControllerInfo> {
            override fun onFailure(rcError: AutelError) {
                val errorMessage =
                    Utils.getFailureShowText(".\nReason - ${rcError.description}");
                setInfoDataListenerTestResult.postValue(Resource.Companion.error(errorMessage, null))
            }

            override fun onSuccess(remoteControllerInfo: RemoteControllerInfo) {
                val successMessage = Utils.getSuccessShowText("\nBattery capacity percentage = ${remoteControllerInfo.batteryCapacityPercentage},  DSP percentage = ${remoteControllerInfo.dspPercentage}, Controller Signal percentage = ${remoteControllerInfo.controllerSignalPercentage}");
                setInfoDataListenerTestResult.postValue(Resource.Companion.success(remoteControllerInfo, successMessage))
            }

        })
        return setInfoDataListenerTestResult
    }

    override suspend fun setConnectStateListenerTest(): MutableLiveData<Resource<RemoteControllerConnectState>> {
        var setConnectStateListenerTestResult: MutableLiveData<Resource<RemoteControllerConnectState>> = MutableLiveData()
        mController.setConnectStateListener(object : CallbackWithOneParam<RemoteControllerConnectState> {
            override fun onFailure(rcError: AutelError) {
                val errorMessage =
                    Utils.getFailureShowText(".\nReason - ${rcError.description}");
                setConnectStateListenerTestResult.postValue(Resource.Companion.error(errorMessage, null))
            }

            override fun onSuccess(state: RemoteControllerConnectState) {
                val successMessage = Utils.getSuccessShowText("\nRemote Controller Connect State = ${state.name} ");
                setConnectStateListenerTestResult.postValue(Resource.Companion.success(state, successMessage))
            }

        })
        return setConnectStateListenerTestResult
    }

    override suspend fun setControlMenuListenerTest(): MutableLiveData<Resource<IntArray>> {
        var setControlMenuListenerTestResult: MutableLiveData<Resource<IntArray>> = MutableLiveData()
        mController.setControlMenuListener(object : CallbackWithOneParam<IntArray> {
            override fun onFailure(rcError: AutelError) {
                val errorMessage =
                    Utils.getFailureShowText(".\nReason - ${rcError.description}");
                setControlMenuListenerTestResult.postValue(Resource.Companion.error(errorMessage, null))
            }

            override fun onSuccess(data: IntArray) {
                val successMessage = Utils.getSuccessShowText("\nData = ${data}");
                setControlMenuListenerTestResult.postValue(Resource.Companion.success(data, successMessage))
            }

        })
        return setControlMenuListenerTestResult
    }

    override fun resetRemoteButtonControllerListenerTest() {
        mController.setRemoteButtonControllerListener(null)
    }

    override fun resetInfoDataListenerTest() {
        mController.setInfoDataListener(null)
    }

    override fun resetConnectStateListenerTest() {
        mController.setConnectStateListener(null)
    }

    override fun resetControlMenuListenerTest() {
        mController.setControlMenuListener(null)
    }

    override suspend fun setGimbalDialAdjustSpeedTest(speed: Int): MutableLiveData<Resource<Int>> {
        var setGimbalDialAdjustSpeedTestResult: MutableLiveData<Resource<Int>> = MutableLiveData()
        mController.setGimbalDialAdjustSpeed(speed, object : CallbackWithNoParam {
            override fun onFailure(rcError: AutelError) {
                val errorMessage =
                    Utils.getFailureShowText(".\nReason - ${rcError.description}");
                setGimbalDialAdjustSpeedTestResult.postValue(Resource.Companion.error(errorMessage, null))
            }

            override fun onSuccess() {
                val successMessage = Utils.getSuccessShowText("\nFor Gimbal Dial Adjust Speed = ${speed} ");
                setGimbalDialAdjustSpeedTestResult.postValue(Resource.Companion.success(speed, successMessage))
            }
        })
        return setGimbalDialAdjustSpeedTestResult
    }

    override suspend fun getGimbalDialAdjustSpeedTest(): MutableLiveData<Resource<Int>> {
        var getGimbalDialAdjustSpeedTestResult: MutableLiveData<Resource<Int>> = MutableLiveData()
        mController.getGimbalDialAdjustSpeed(object : CallbackWithOneParam<Int> {
            override fun onFailure(rcError: AutelError) {
                val errorMessage = Utils.getFailureShowText(
                    "\nReason - ${rcError.description}",
                    methodName = "getGimbalDialAdjustSpeed"
                );
                getGimbalDialAdjustSpeedTestResult.postValue(Resource.Companion.error(errorMessage, null))
            }

            override fun onSuccess(dialAdjustSpeed: Int?) {
                val successMessage = Utils.getSuccessShowText("\nGimbal Dial Adjust Speed = ${dialAdjustSpeed}",
                    methodName = "getGimbalDialAdjustSpeed");
                getGimbalDialAdjustSpeedTestResult.postValue(Resource.Companion.success(dialAdjustSpeed, successMessage))
            }
        })
        return getGimbalDialAdjustSpeedTestResult
    }

}
