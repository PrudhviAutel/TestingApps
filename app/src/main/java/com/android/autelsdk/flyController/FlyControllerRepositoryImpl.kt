package com.android.autelsdk.flyController

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.android.autelsdk.util.Resource
import com.android.autelsdk.util.Utils
import com.autel.common.CallbackWithNoParam
import com.autel.common.CallbackWithOneParam
import com.autel.common.error.AutelError
import com.autel.common.flycontroller.CalibrateCompassStatus
import com.autel.common.flycontroller.FlyControllerVersionInfo
import com.autel.common.flycontroller.LedPilotLamp
import com.autel.internal.flycontroller.xstar.XStarFlyController
import com.autel.sdk.flycontroller.AutelFlyController


class FlyControllerRepositoryImpl : FlyControllerRepository {

    val mController: AutelFlyController =
        XStarFlyController()      //TODO(Sreyans) : Need to look why CruiserFlyController() is not there

    override suspend fun setBeginnerModeStateTest(enable: Boolean): MutableLiveData<Resource<String>> {
        var setBeginnerModeStateTestResult: MutableLiveData<Resource<String>> = MutableLiveData()
        mController.setBeginnerModeEnable(enable, object : CallbackWithNoParam {
            override fun onFailure(rcError: AutelError) {
                val m = Utils.getAllStackTraceFunctions()
                for (i in m ) {
                    Log.i("KLKLKL", i.methodName);
                }
                val errorMessage =
                    Utils.getFailureShowText("on Set Enable State = ${if (enable) "true" else "false"}.\nReason - ${rcError.description}",
                    methodName = "setBeginnerModeEnable");
                setBeginnerModeStateTestResult.postValue(
                    Resource.Companion.error(
                        errorMessage,
                        null
                    )
                )
            }

            override fun onSuccess() {
                val successMessage =
                    Utils.getSuccessShowText("\nFor Beginner Mode State = ${if (enable) "true" else "false"}",
                        methodName = "setBeginnerModeEnable");
                setBeginnerModeStateTestResult.postValue(Resource.Companion.success(successMessage))
            }
        })
        return setBeginnerModeStateTestResult
    }

    override suspend fun getBeginnerModeStateTest(): MutableLiveData<Resource<Boolean>> {
        var getBeginnerModeStateTestResult: MutableLiveData<Resource<Boolean>> = MutableLiveData()
        mController.isBeginnerModeEnable(object : CallbackWithOneParam<Boolean> {
            override fun onFailure(rcError: AutelError) {
                val errorMessage = Utils.getFailureShowText("\nReason - ${rcError.description}",
                    methodName = "getBeginnerModeEnable");
                getBeginnerModeStateTestResult.postValue(
                    Resource.Companion.error(
                        errorMessage,
                        null
                    )
                )
            }

            override fun onSuccess(mode: Boolean?) {
                getBeginnerModeStateTestResult.postValue(Resource.Companion.success(mode))
            }
        })
        return getBeginnerModeStateTestResult
    }


    override suspend fun getMaxHeightTest(): MutableLiveData<Resource<Float>> {
        var getMaxHeightTestResult: MutableLiveData<Resource<Float>> = MutableLiveData()
        mController.getMaxHeight(object : CallbackWithOneParam<Float> {
            override fun onFailure(rcError: AutelError) {
                val errorMessage = Utils.getFailureShowText("\nReason - ${rcError.description}",
                    methodName = "getMaxHeight");
                getMaxHeightTestResult.postValue(Resource.Companion.error(errorMessage, null))
            }

            override fun onSuccess(value: Float?) {
                val successMessage = "";
                getMaxHeightTestResult.postValue(Resource.Companion.success(value))
            }
        })
        return getMaxHeightTestResult
    }

    override suspend fun setMaxHeightTest(value: Double): MutableLiveData<Resource<String>> {
        var setMaxHeightTestResult: MutableLiveData<Resource<String>> = MutableLiveData()
        mController.setMaxHeight(value, object : CallbackWithNoParam {
            override fun onFailure(rcError: AutelError) {
                val errorMessage =
                    Utils.getFailureShowText("for max height = ${value}.\nReason - ${rcError.description}",
                        methodName = "setMaxHeight");
                setMaxHeightTestResult.postValue(Resource.Companion.error(errorMessage, null))
            }

            override fun onSuccess() {
                val successMessage = Utils.getSuccessShowText("\nFor Max Height = ${value}",
                    methodName = "setMaxHeight");
                setMaxHeightTestResult.postValue(Resource.Companion.success(successMessage))
            }
        })
        return setMaxHeightTestResult
    }

    override suspend fun getMaxRangeTest(): MutableLiveData<Resource<Float>> {
        var getMaxRangeTestResult: MutableLiveData<Resource<Float>> = MutableLiveData()
        mController.getMaxRange(object : CallbackWithOneParam<Float> {
            override fun onFailure(rcError: AutelError) {
                val errorMessage = Utils.getFailureShowText("\nReason - ${rcError.description}",
                    methodName = "getMaxRange");
                getMaxRangeTestResult.postValue(Resource.Companion.error(errorMessage, null))
            }

            override fun onSuccess(maxRange: Float?) {
                val successMessage = "";
                getMaxRangeTestResult.postValue(Resource.Companion.success(maxRange))
            }
        })
        return getMaxRangeTestResult
    }

    override suspend fun setMaxRangeTest(value: Double): MutableLiveData<Resource<String>> {
        var setMaxRangeTestResult: MutableLiveData<Resource<String>> = MutableLiveData()
        mController.setMaxRange(value, object : CallbackWithNoParam {
            override fun onFailure(rcError: AutelError) {
                val errorMessage =
                    Utils.getFailureShowText("for Max Range = ${value}.\nReason - ${rcError.description}",
                        methodName = "setMaxRange");
                setMaxRangeTestResult.postValue(Resource.Companion.error(errorMessage, null))
            }

            override fun onSuccess() {
                val successMessage = Utils.getSuccessShowText("\nFor Max Range = ${value}",
                    methodName = "setMaxRange");
                setMaxRangeTestResult.postValue(Resource.Companion.success(successMessage))
            }
        })
        return setMaxRangeTestResult
    }

    override suspend fun getReturnHeightTest(): MutableLiveData<Resource<Float>> {
        var getReturnHeightTestResult: MutableLiveData<Resource<Float>> = MutableLiveData()

        mController.getReturnHeight(object : CallbackWithOneParam<Float> {
            override fun onFailure(rcError: AutelError) {
                val errorMessage = Utils.getFailureShowText("\nReason - ${rcError.description}",
                    methodName = "getReturnHeight");
                getReturnHeightTestResult.postValue(Resource.Companion.error(errorMessage, null))
            }

            override fun onSuccess(returnHeight: Float?) {
                val successMessage = "";
                getReturnHeightTestResult.postValue(Resource.Companion.success(returnHeight))
            }
        })
        return getReturnHeightTestResult
    }

    override suspend fun setReturnHeightTest(value: Double): MutableLiveData<Resource<String>> {
        var setReturnHeightTestResult: MutableLiveData<Resource<String>> = MutableLiveData()
        mController.setReturnHeight(value, object : CallbackWithNoParam {
            override fun onFailure(rcError: AutelError) {
                val errorMessage =
                    Utils.getFailureShowText("for Return Height = ${value}.\nReason - ${rcError.description}",
                        methodName = "setReturnHeight");
                setReturnHeightTestResult.postValue(Resource.Companion.error(errorMessage, null))
            }

            override fun onSuccess() {
                val successMessage = Utils.getSuccessShowText("\nFor Return Height = ${value}",
                    methodName = "setReturnHeight");
                setReturnHeightTestResult.postValue(Resource.Companion.success(successMessage))
            }
        })
        return setReturnHeightTestResult
    }

    override suspend fun getHorizontalSpeedTest(): MutableLiveData<Resource<Float>> {
        var getHorizontalSpeedTestResult: MutableLiveData<Resource<Float>> = MutableLiveData()

        mController.getMaxHorizontalSpeed(object : CallbackWithOneParam<Float> {
            override fun onFailure(rcError: AutelError) {
                val errorMessage = Utils.getFailureShowText("\nReason - ${rcError.description}",
                    methodName = "getMaxHorizontalSpeed");
                getHorizontalSpeedTestResult.postValue(Resource.Companion.error(errorMessage, null))
            }

            override fun onSuccess(horizontalSpeed: Float?) {
                val successMessage = "";
                getHorizontalSpeedTestResult.postValue(Resource.Companion.success(horizontalSpeed))
            }
        })
        return getHorizontalSpeedTestResult
    }

    override suspend fun setHorizontalSpeedTest(value: Double): MutableLiveData<Resource<String>> {
        var setHorizontalSpeedTestResult: MutableLiveData<Resource<String>> = MutableLiveData()
        mController.setMaxHorizontalSpeed(value, object : CallbackWithNoParam {
            override fun onFailure(rcError: AutelError) {
                val errorMessage =
                    Utils.getFailureShowText("for Horizontal Speed = ${value}.\nReason - ${rcError.description}",
                        methodName = "setMaxHorizontalSpeed");
                setHorizontalSpeedTestResult.postValue(Resource.Companion.error(errorMessage, null))
            }

            override fun onSuccess() {
                val successMessage = Utils.getSuccessShowText("\nFor Horizontal Speed = ${value}",
                    methodName = "setMaxHorizontalSpeed");
                setHorizontalSpeedTestResult.postValue(Resource.Companion.success(successMessage))
            }
        })
        return setHorizontalSpeedTestResult
    }

    override suspend fun isAttitudeModeEnableTest(): MutableLiveData<Resource<Boolean>> {
        var isAttitudeModeEnableTestResult: MutableLiveData<Resource<Boolean>> = MutableLiveData()
        mController.isAttitudeModeEnable(object : CallbackWithOneParam<Boolean> {
            override fun onFailure(rcError: AutelError) {
                val errorMessage = Utils.getFailureShowText("\nReason - ${rcError.description}",
                    methodName = "getAttitudeModeEnable");
                isAttitudeModeEnableTestResult.postValue(
                    Resource.Companion.error(
                        errorMessage,
                        null
                    )
                )
            }

            override fun onSuccess(result: Boolean?) {
                val successMessage = "";
                isAttitudeModeEnableTestResult.postValue(Resource.Companion.success(result))
            }
        })
        return isAttitudeModeEnableTestResult
    }

    override suspend fun setAttitudeModeEnableTest(enable: Boolean): MutableLiveData<Resource<Boolean>> {
        var setAttitudeModeEnableTestResult: MutableLiveData<Resource<Boolean>> = MutableLiveData()
        mController.setAttitudeModeEnable(enable, object : CallbackWithNoParam {
            override fun onFailure(rcError: AutelError) {
                val errorMessage =
                    Utils.getFailureShowText("on Set Enable State = ${if (enable) "true" else "false"}.\nReason - ${rcError.description}",
                        methodName = "setAttitudeModeEnable");
                setAttitudeModeEnableTestResult.postValue(
                    Resource.Companion.error(
                        errorMessage,
                        null
                    )
                )
            }

            override fun onSuccess() {
                val successMessage =
                    Utils.getSuccessShowText("\nFor Attitude Mode State = ${if (enable) "true" else "false"}",
                        methodName = "setAttitudeModeEnable");
                setAttitudeModeEnableTestResult.postValue(Resource.Companion.success(enable))
            }
        })
        return setAttitudeModeEnableTestResult
    }

    override suspend fun getLedPilotLampTest(): MutableLiveData<Resource<LedPilotLamp>> {
        var getLedPilotLampTestResult: MutableLiveData<Resource<LedPilotLamp>> = MutableLiveData()

        mController.getLedPilotLamp(object : CallbackWithOneParam<LedPilotLamp> {
            override fun onFailure(rcError: AutelError) {
                val errorMessage = Utils.getFailureShowText("\nReason - ${rcError.description}",
                    methodName = "getLedPilotLamp");
                getLedPilotLampTestResult.postValue(Resource.Companion.error(errorMessage, null))
            }

            override fun onSuccess(ledpilotlamp: LedPilotLamp?) {
                val successMessage = "";
                getLedPilotLampTestResult.postValue(Resource.Companion.success(ledpilotlamp))
            }
        })
        return getLedPilotLampTestResult
    }

    override suspend fun setLedPilotLampTest(ledPilotLamp: LedPilotLamp): MutableLiveData<Resource<String>> {
        var setLedPilotLampTestResult: MutableLiveData<Resource<String>> = MutableLiveData()
        mController.setLedPilotLamp(ledPilotLamp, object : CallbackWithNoParam {
            override fun onFailure(rcError: AutelError) {
                val errorMessage =
                    Utils.getFailureShowText("for Led Pilot Lamp = ${ledPilotLamp.value}.\nReason - ${rcError.description}",
                        methodName = "setLedPilotLamp");
                setLedPilotLampTestResult.postValue(Resource.Companion.error(errorMessage, null))
            }

            override fun onSuccess() {
                val successMessage =
                    Utils.getSuccessShowText("\nFor Set Pilot Lamp = ${ledPilotLamp.value}",
                        methodName = "setLedPilotLamp");
                setLedPilotLampTestResult.postValue(Resource.Companion.success(successMessage))
            }
        })
        return setLedPilotLampTestResult
    }

    override suspend fun setLocationAsHomePointTest(
        lat: Double,
        lon: Double
    ): MutableLiveData<Resource<String>> {
        var setLocationAsHomePointTestResult: MutableLiveData<Resource<String>> = MutableLiveData()
        mController.setLocationAsHomePoint(lat, lon, object : CallbackWithNoParam {
            override fun onFailure(rcError: AutelError) {
                val errorMessage =
                    Utils.getFailureShowText("for Latitude = ${lat}, Longitude = ${lon}.\nReason - ${rcError.description}",
                        methodName = "setLocationAsHomePoint");
                setLocationAsHomePointTestResult.postValue(
                    Resource.Companion.error(
                        errorMessage,
                        null
                    )
                )
            }

            override fun onSuccess() {
                val successMessage =
                    Utils.getSuccessShowText("\nFor Latitude = ${lat}, Longitude = ${lon}",
                        methodName = "setLocationAsHomePoint");
                setLocationAsHomePointTestResult.postValue(Resource.Companion.success(successMessage))
            }
        })
        return setLocationAsHomePointTestResult
    }

    override suspend fun setAircraftLocationAsHomePointTest(): MutableLiveData<Resource<String>> {

        var setAircraftLocationAsHomePointTestResult: MutableLiveData<Resource<String>> =
            MutableLiveData()
        mController.setAircraftLocationAsHomePoint(object : CallbackWithNoParam {
            override fun onFailure(rcError: AutelError) {
                val errorMessage = Utils.getFailureShowText("\nReason - ${rcError.description}",
                    methodName = "setAircraftLocationAsHomePoint");
                setAircraftLocationAsHomePointTestResult.postValue(
                    Resource.Companion.error(
                        errorMessage,
                        null
                    )
                )
            }

            override fun onSuccess() {
                val successMessage = Utils.getSuccessShowText("",
                    methodName = "setAircraftLocationAsHomePoint");
                setAircraftLocationAsHomePointTestResult.postValue(Resource.Companion.success(successMessage))
            }
        })
        return setAircraftLocationAsHomePointTestResult
    }

    override suspend fun startCalibrateCompassTest(): MutableLiveData<Resource<CalibrateCompassStatus>> {
        var startCalibrateCompassTestResult: MutableLiveData<Resource<CalibrateCompassStatus>> =
            MutableLiveData()
        mController.startCalibrateCompass(object : CallbackWithOneParam<CalibrateCompassStatus> {
            override fun onFailure(rcError: AutelError) {
                val errorMessage = Utils.getFailureShowText("\nReason - ${rcError.description}",
                    methodName = "startCalibrateCompass");
                startCalibrateCompassTestResult.postValue(
                    Resource.Companion.error(
                        errorMessage,
                        null
                    )
                )
            }

            override fun onSuccess(calibrateCompassStatus: CalibrateCompassStatus) {
                val successMessage =
                    Utils.getSuccessShowText("\nFor Calibrate Compass Status = ${calibrateCompassStatus.value}",
                        methodName = "startCalibrateCompass");
                startCalibrateCompassTestResult.postValue(
                    Resource.Companion.success(
                        calibrateCompassStatus
                    )
                )
            }
        })
        return startCalibrateCompassTestResult
    }

    /**override fun takeOffTest(): MutableLiveData<Resource<CalibrateCompassStatus>> {
    val lat = 22.0
    val lon = 22.0

    var takeOffTestResult : MutableLiveData<Resource<CalibrateCompassStatus>> = MutableLiveData()
    var value : Double
    value = 200.0
    mController.takeOff( object : CallbackWithOneParam<Pair<Boolean>>{
    override fun onFailure(rcError: AutelError) {
    val errorMessage = "";
    takeOffTestResult.postValue(Resource.Companion.error(errorMessage, null))
    }

    override fun onSuccess(calibrateCompassStatus : CalibrateCompassStatus) {
    val successMessage = "";
    takeOffTestResult.postValue(Resource.Companion.success(calibrateCompassStatus))
    }
    })
    return takeOffTestResult
    }*/

    override suspend fun landTest(): MutableLiveData<Resource<String>> {
        var landTestResult: MutableLiveData<Resource<String>> = MutableLiveData()
        mController.land(object : CallbackWithNoParam {
            override fun onFailure(rcError: AutelError) {
                val errorMessage = Utils.getFailureShowText("\nReason - ${rcError.description}",
                    methodName = "land");
                landTestResult.postValue(Resource.Companion.error(errorMessage, null))
            }

            override fun onSuccess() {
                val successMessage = Utils.getSuccessShowText("",
                    methodName = "land");
                landTestResult.postValue(Resource.Companion.success(successMessage))
            }
        })
        return landTestResult
    }

    override suspend fun goHomeTest(): MutableLiveData<Resource<String>> {
        var goHomeTestResult: MutableLiveData<Resource<String>> = MutableLiveData()
        mController.goHome(object : CallbackWithNoParam {
            override fun onFailure(rcError: AutelError) {
                val errorMessage = Utils.getFailureShowText("\nReason - ${rcError.description}",
                    methodName = "goHome");
                goHomeTestResult.postValue(Resource.Companion.error(errorMessage, null))
            }

            override fun onSuccess() {
                val successMessage = Utils.getSuccessShowText("",
                    methodName = "goHome");
                goHomeTestResult.postValue(Resource.Companion.success(successMessage))
            }
        })
        return goHomeTestResult
    }

    override suspend fun cancelReturnTest(): MutableLiveData<Resource<String>> {
        var cancelReturnTestResult: MutableLiveData<Resource<String>> = MutableLiveData()
        mController.cancelReturn(object : CallbackWithNoParam {
            override fun onFailure(rcError: AutelError) {
                val errorMessage = Utils.getFailureShowText("\nReason - ${rcError.description}",
                    methodName = "cancelReturn");
                cancelReturnTestResult.postValue(Resource.Companion.error(errorMessage, null))
            }

            override fun onSuccess() {
                val successMessage = Utils.getSuccessShowText("",
                    methodName = "cancelReturn");
                cancelReturnTestResult.postValue(Resource.Companion.success(successMessage))
            }
        })
        return cancelReturnTestResult
    }

    override suspend fun cancelLandTest(): MutableLiveData<Resource<String>> {
        var cancelLandTestResult: MutableLiveData<Resource<String>> = MutableLiveData()
        mController.cancelLand(object : CallbackWithNoParam {
            override fun onFailure(rcError: AutelError) {
                val errorMessage = Utils.getFailureShowText("\nReason - ${rcError.description}",
                    methodName = "cancelLand");
                cancelLandTestResult.postValue(Resource.Companion.error(errorMessage, null))
            }

            override fun onSuccess() {
                val successMessage = Utils.getSuccessShowText("",
                    methodName = "cancelLand");
                cancelLandTestResult.postValue(Resource.Companion.success(successMessage))
            }
        })
        return cancelLandTestResult
    }

    override suspend fun getVersionInfoTest(): MutableLiveData<Resource<FlyControllerVersionInfo>> {
        var getVersionInfoTestResult: MutableLiveData<Resource<FlyControllerVersionInfo>> =
            MutableLiveData()
        mController.getVersionInfo(object : CallbackWithOneParam<FlyControllerVersionInfo> {
            override fun onFailure(rcError: AutelError) {
                val errorMessage = Utils.getFailureShowText(
                    "\nReason - ${rcError.description}",
                    methodName = "getVersionInfo"
                );
                getVersionInfoTestResult.postValue(Resource.Companion.error(errorMessage, null))
            }

            override fun onSuccess(flyresult: FlyControllerVersionInfo) {
                val successMessage = "";
                getVersionInfoTestResult.postValue(Resource.Companion.success(flyresult))
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

            override fun onSuccess(result: String) {
                val successMessage = "";
                getSerialNumberTestResult.postValue(Resource.Companion.success(result))
            }
        })
        return getSerialNumberTestResult
    }

    override suspend fun setCalibrateCompassListenerTest(): MutableLiveData<Resource<CalibrateCompassStatus>> {
        var setCalibrateCompassListenerTestResult: MutableLiveData<Resource<CalibrateCompassStatus>> =
            MutableLiveData()
        mController.setCalibrateCompassListener(object :
            CallbackWithOneParam<CalibrateCompassStatus> {
            override fun onFailure(rcError: AutelError) {
                val errorMessage = Utils.getFailureShowText("\nReason - ${rcError.description}",
                    methodName = "setCalibrateCompassListener");
                setCalibrateCompassListenerTestResult.postValue(
                    Resource.Companion.error(
                        errorMessage,
                        null
                    )
                )
            }

            override fun onSuccess(result: CalibrateCompassStatus) {
                val successMessage = "";
                setCalibrateCompassListenerTestResult.postValue(Resource.Companion.success(result))
            }
        })
        return setCalibrateCompassListenerTestResult
    }

    /**override fun setWarningListenerTest(): MutableLiveData<Resource<CalibrateCompassStatus>> {
    val lat = 22.0
    val lon = 22.0

    var setWarningListenerTestResult : MutableLiveData<Resource<CalibrateCompassStatus>> = MutableLiveData()
    var value : Double
    value = 200.0
    mController.setWarningListener( object : CallbackWithOneParam<ARMWarning, MagnetometerState>,
    CallbackWithTwoParams<ARMWarning, MagnetometerState> {
    override fun onFailure(rcError: AutelError) {
    val errorMessage = "";
    setWarningListenerTestResult.postValue(Resource.Companion.error(errorMessage, null))
    }

    override fun onSuccess(ARMWarning data1, MagnetometerState data2) {
    val successMessage = "";
    setWarningListenerTestResult.postValue(Resource.Companion.success(data2+data1))
    }
    })
    return setWarningListenerTestResult
    }*/

}