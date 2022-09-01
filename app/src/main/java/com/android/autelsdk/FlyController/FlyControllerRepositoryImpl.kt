package com.android.autelsdk.FlyController

import android.text.TextUtils.isEmpty
import android.util.Pair
import androidx.lifecycle.MutableLiveData
import com.android.autelsdk.util.Resource
import com.autel.common.CallbackWithNoParam
import com.autel.common.CallbackWithOneParam
import com.autel.common.CallbackWithTwoParams
import com.autel.common.error.AutelError
import com.autel.common.flycontroller.*

class FlyControllerRepositoryImpl<AutelFlyController>(
    private val mController: com.autel.sdk.flycontroller.AutelFlyController,
) : FlyControllerRepository{

    protected var ledPilotLamp = LedPilotLamp.ALL_OFF

    override fun setBeginnerModeStateTest(enable : Boolean): MutableLiveData<Resource<String>> {
        var setBeginnerModeStateTestResult : MutableLiveData<Resource<String>> = MutableLiveData()
        mController.setBeginnerModeEnable(enable, object : CallbackWithNoParam {
            override fun onFailure(rcError: AutelError) {
                val errorMessage = "";
                setBeginnerModeStateTestResult.postValue(Resource.Companion.error(errorMessage, null))
            }

            override fun onSuccess() {
                val successMessage = "";
                setBeginnerModeStateTestResult.postValue(Resource.Companion.success(successMessage))
            }
        })
        return setBeginnerModeStateTestResult
    }

    override fun getBeginnerModeStateTest(): MutableLiveData<Resource<Boolean>> {
        var getBeginnerModeStateTestResult : MutableLiveData<Resource<Boolean>> = MutableLiveData()
        mController.isBeginnerModeEnable( object : CallbackWithOneParam<Boolean> {
            override fun onFailure(rcError: AutelError) {
                val errorMessage = "";
                getBeginnerModeStateTestResult.postValue(Resource.Companion.error(errorMessage, null))
            }

            override fun onSuccess(mode: Boolean?) {
                getBeginnerModeStateTestResult.postValue(Resource.Companion.success(mode))
            }
        })
        return getBeginnerModeStateTestResult
    }



    override fun getMaxHeightTest(): MutableLiveData<Resource<Float>> {
        var getMaxHeightTestResult : MutableLiveData<Resource<Float>> = MutableLiveData()
        mController.getMaxHeight( object : CallbackWithOneParam<Float> {
            override fun onFailure(rcError: AutelError) {
                val errorMessage = "";
                getMaxHeightTestResult.postValue(Resource.Companion.error(errorMessage, null))
            }

            override fun onSuccess(value: Float?) {
                val successMessage = "";
                getMaxHeightTestResult.postValue(Resource.Companion.success(value))
            }
        })
        return getMaxHeightTestResult
    }

    override fun setMaxHeightTest(): MutableLiveData<Resource<String>> {
        var setMaxHeightTestResult : MutableLiveData<Resource<String>> = MutableLiveData()
        var value : Double
        value = 200.0
        mController.setMaxHeight( value,object : CallbackWithNoParam {
            override fun onFailure(rcError: AutelError) {
                val errorMessage = "";
                setMaxHeightTestResult.postValue(Resource.Companion.error(errorMessage, null))
            }

            override fun onSuccess() {
                val successMessage = "";
                setMaxHeightTestResult.postValue(Resource.Companion.success(successMessage))
            }
        })
        return setMaxHeightTestResult
    }

    override fun getMaxRangeTest(): MutableLiveData<Resource<Float>> {
        var getMaxRangeTestResult : MutableLiveData<Resource<Float>> = MutableLiveData()
        mController.getMaxRange( object : CallbackWithOneParam<Float> {
            override fun onFailure(rcError: AutelError) {
                val errorMessage = "";
                getMaxRangeTestResult.postValue(Resource.Companion.error(errorMessage, null))
            }

            override fun onSuccess(maxRange : Float?) {
                val successMessage = "";
                getMaxRangeTestResult.postValue(Resource.Companion.success(maxRange))
            }
        })
        return getMaxRangeTestResult
    }

    override fun setMaxRangeTest(): MutableLiveData<Resource<String>> {
        var setMaxRangeTestResult : MutableLiveData<Resource<String>> = MutableLiveData()
        var value : Double
        value = 200.0
        mController.setMaxRange( value,object : CallbackWithNoParam{
            override fun onFailure(rcError: AutelError) {
                val errorMessage = "";
                setMaxRangeTestResult.postValue(Resource.Companion.error(errorMessage, null))
            }

            override fun onSuccess() {
                val successMessage = "";
                setMaxRangeTestResult.postValue(Resource.Companion.success(successMessage))
            }
        })
        return setMaxRangeTestResult
    }

    override fun getReturnHeightTest(): MutableLiveData<Resource<Float>> {
        var getReturnHeightTestResult : MutableLiveData<Resource<Float>> = MutableLiveData()

        mController.getReturnHeight(object : CallbackWithOneParam<Float>{
            override fun onFailure(rcError: AutelError) {
                val errorMessage = "";
                getReturnHeightTestResult.postValue(Resource.Companion.error(errorMessage, null))
            }

            override fun onSuccess(returnHeight : Float?) {
                val successMessage = "";
                getReturnHeightTestResult.postValue(Resource.Companion.success(returnHeight))
            }
        })
        return getReturnHeightTestResult
    }

    override fun setReturnHeightTest(): MutableLiveData<Resource<String>> {
        var setReturnHeightTestResult : MutableLiveData<Resource<String>> = MutableLiveData()
        var value : Double
        value = 200.0
        mController.setReturnHeight( value,object : CallbackWithNoParam{
            override fun onFailure(rcError: AutelError) {
                val errorMessage = "";
                setReturnHeightTestResult.postValue(Resource.Companion.error(errorMessage, null))
            }

            override fun onSuccess() {
                val successMessage = "";
                setReturnHeightTestResult.postValue(Resource.Companion.success(successMessage))
            }
        })
        return setReturnHeightTestResult
    }

    override fun getHorizontalSpeedTest(): MutableLiveData<Resource<Float>> {
        var getHorizontalSpeedTestResult : MutableLiveData<Resource<Float>> = MutableLiveData()

        mController.getMaxHorizontalSpeed(object : CallbackWithOneParam<Float>{
            override fun onFailure(rcError: AutelError) {
                val errorMessage = "";
                getHorizontalSpeedTestResult.postValue(Resource.Companion.error(errorMessage, null))
            }

            override fun onSuccess(horizontalSpeed : Float?) {
                val successMessage = "";
                getHorizontalSpeedTestResult.postValue(Resource.Companion.success(horizontalSpeed))
            }
        })
        return getHorizontalSpeedTestResult
    }

    override fun setHorizontalSpeedTest(): MutableLiveData<Resource<String>> {
        var setHorizontalSpeedTestResult : MutableLiveData<Resource<String>> = MutableLiveData()
        var value : Double
        value = 200.0
        mController.setMaxHorizontalSpeed( value,object : CallbackWithNoParam{
            override fun onFailure(rcError: AutelError) {
                val errorMessage = "";
                setHorizontalSpeedTestResult.postValue(Resource.Companion.error(errorMessage, null))
            }

            override fun onSuccess() {
                val successMessage = "";
                setHorizontalSpeedTestResult.postValue(Resource.Companion.success(successMessage))
            }
        })
        return setHorizontalSpeedTestResult
    }

    override fun isAttiModeEnableTest(): MutableLiveData<Resource<Boolean>> {
        var isAttiModeEnableTestResult : MutableLiveData<Resource<Boolean>> = MutableLiveData()

        mController.isAttitudeModeEnable(object : CallbackWithOneParam<Boolean>{
            override fun onFailure(rcError: AutelError) {
                val errorMessage = "";
                isAttiModeEnableTestResult.postValue(Resource.Companion.error(errorMessage, null))
            }

            override fun onSuccess(result : Boolean?) {
                val successMessage = "";
                isAttiModeEnableTestResult.postValue(Resource.Companion.success(result))
            }
        })
        return isAttiModeEnableTestResult
    }

    override fun setAttiModeEnableTest(enable: Boolean): MutableLiveData<Resource<Boolean>> {
        var setAttiModeEnableTestResult : MutableLiveData<Resource<Boolean>> = MutableLiveData()
        var value : Double
        value = 200.0
        mController.setAttitudeModeEnable( enable,object : CallbackWithNoParam{
            override fun onFailure(rcError: AutelError) {
                val errorMessage = "";
                setAttiModeEnableTestResult.postValue(Resource.Companion.error(errorMessage, null))
            }

            override fun onSuccess() {
                val successMessage = "";
                setAttiModeEnableTestResult.postValue(Resource.Companion.success(enable))
            }
        })
        return setAttiModeEnableTestResult
    }

    override fun getLedPilotLampTest(): MutableLiveData<Resource<LedPilotLamp>> {
        var getLedPilotLampTestResult : MutableLiveData<Resource<LedPilotLamp>> = MutableLiveData()

        mController.getLedPilotLamp(object : CallbackWithOneParam<LedPilotLamp>{
            override fun onFailure(rcError: AutelError) {
                val errorMessage = "";
                getLedPilotLampTestResult.postValue(Resource.Companion.error(errorMessage, null))
            }

            override fun onSuccess(ledpilotlamp : LedPilotLamp?) {
                val successMessage = "";
                getLedPilotLampTestResult.postValue(Resource.Companion.success(ledpilotlamp))
            }
        })
        return getLedPilotLampTestResult
    }

    override fun setLedPilotLampTest(): MutableLiveData<Resource<String>> {
        var setLedPilotLampTestResult : MutableLiveData<Resource<String>> = MutableLiveData()
        var value : Double
        value = 200.0
        mController.setLedPilotLamp( ledPilotLamp,object : CallbackWithNoParam{
            override fun onFailure(rcError: AutelError) {
                val errorMessage = "";
                setLedPilotLampTestResult.postValue(Resource.Companion.error(errorMessage, null))
            }

            override fun onSuccess() {
                val successMessage = "";
                setLedPilotLampTestResult.postValue(Resource.Companion.success(successMessage))
            }
        })
        return setLedPilotLampTestResult
    }

    override fun setLocationAsHomePointTest(): MutableLiveData<Resource<String>> {
        val lat = 22.0
        val lon = 22.0

        var setLocationAsHomePointTestResult : MutableLiveData<Resource<String>> = MutableLiveData()
        var value : Double
        value = 200.0
        mController.setLocationAsHomePoint(lat,lon, object : CallbackWithNoParam{
            override fun onFailure(rcError: AutelError) {
                val errorMessage = "";
                setLocationAsHomePointTestResult.postValue(Resource.Companion.error(errorMessage, null))
            }

            override fun onSuccess() {
                val successMessage = "";
                setLocationAsHomePointTestResult.postValue(Resource.Companion.success(successMessage))
            }
        })
        return setLocationAsHomePointTestResult
    }

    override fun setAircraftLocationAsHomePointTest(): MutableLiveData<Resource<String>> {
        val lat = 22.0
        val lon = 22.0

        var setAircraftLocationAsHomePointTestResult : MutableLiveData<Resource<String>> = MutableLiveData()
        var value : Double
        value = 200.0
        mController.setAircraftLocationAsHomePoint( object : CallbackWithNoParam{
            override fun onFailure(rcError: AutelError) {
                val errorMessage = "";
                setAircraftLocationAsHomePointTestResult.postValue(Resource.Companion.error(errorMessage, null))
            }

            override fun onSuccess() {
                val successMessage = "";
                setAircraftLocationAsHomePointTestResult.postValue(Resource.Companion.success(successMessage))
            }
        })
        return setAircraftLocationAsHomePointTestResult
    }

    override fun startCalibrateCompassTest(): MutableLiveData<Resource<CalibrateCompassStatus>> {
        val lat = 22.0
        val lon = 22.0

        var startCalibrateCompassTestResult : MutableLiveData<Resource<CalibrateCompassStatus>> = MutableLiveData()
        var value : Double
        value = 200.0
        mController.startCalibrateCompass( object : CallbackWithOneParam<CalibrateCompassStatus>{
            override fun onFailure(rcError: AutelError) {
                val errorMessage = "";
                startCalibrateCompassTestResult.postValue(Resource.Companion.error(errorMessage, null))
            }

            override fun onSuccess(calibrateCompassStatus : CalibrateCompassStatus) {
                val successMessage = "";
                startCalibrateCompassTestResult.postValue(Resource.Companion.success(calibrateCompassStatus))
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

    override fun landTest(): MutableLiveData<Resource<String>> {
        val lat = 22.0
        val lon = 22.0

        var landTestResult : MutableLiveData<Resource<String>> = MutableLiveData()
        var value : Double
        value = 200.0
        mController.land( object : CallbackWithNoParam{
            override fun onFailure(rcError: AutelError) {
                val errorMessage = "";
                landTestResult.postValue(Resource.Companion.error(errorMessage, null))
            }

            override fun onSuccess() {
                val successMessage = "";
                landTestResult.postValue(Resource.Companion.success(successMessage))
            }
        })
        return landTestResult
    }

    override fun goHome(): MutableLiveData<Resource<String>> {
        val lat = 22.0
        val lon = 22.0

        var goHomeTestResult : MutableLiveData<Resource<String>> = MutableLiveData()
        var value : Double
        value = 200.0
        mController.goHome( object : CallbackWithNoParam{
            override fun onFailure(rcError: AutelError) {
                val errorMessage = "";
                goHomeTestResult.postValue(Resource.Companion.error(errorMessage, null))
            }

            override fun onSuccess() {
                val successMessage = "";
                goHomeTestResult.postValue(Resource.Companion.success(successMessage))
            }
        })
        return goHomeTestResult
    }

    override fun cancelReturnTest(): MutableLiveData<Resource<String>> {
        val lat = 22.0
        val lon = 22.0

        var cancelReturnTestResult : MutableLiveData<Resource<String>> = MutableLiveData()
        var value : Double
        value = 200.0
        mController.cancelReturn( object : CallbackWithNoParam{
            override fun onFailure(rcError: AutelError) {
                val errorMessage = "";
                cancelReturnTestResult.postValue(Resource.Companion.error(errorMessage, null))
            }

            override fun onSuccess() {
                val successMessage = "";
                cancelReturnTestResult.postValue(Resource.Companion.success(successMessage))
            }
        })
        return cancelReturnTestResult
    }

    override fun cancelLandTest(): MutableLiveData<Resource<String>> {
        val lat = 22.0
        val lon = 22.0

        var cancelLandTestResult : MutableLiveData<Resource<String>> = MutableLiveData()
        var value : Double
        value = 200.0
        mController.cancelLand( object : CallbackWithNoParam{
            override fun onFailure(rcError: AutelError) {
                val errorMessage = "";
                cancelLandTestResult.postValue(Resource.Companion.error(errorMessage, null))
            }

            override fun onSuccess() {
                val successMessage = "";
                cancelLandTestResult.postValue(Resource.Companion.success(successMessage))
            }
        })
        return cancelLandTestResult
    }

    override fun getVersionInfoTest(): MutableLiveData<Resource<FlyControllerVersionInfo>> {
        val lat = 22.0
        val lon = 22.0

        var getVersionInfoTestResult : MutableLiveData<Resource<FlyControllerVersionInfo>> = MutableLiveData()
        var value : Double
        value = 200.0
        mController.getVersionInfo( object : CallbackWithOneParam<FlyControllerVersionInfo>{
            override fun onFailure(rcError: AutelError) {
                val errorMessage = "";
                getVersionInfoTestResult.postValue(Resource.Companion.error(errorMessage, null))
            }

            override fun onSuccess(flyresult : FlyControllerVersionInfo) {
                val successMessage = "";
                getVersionInfoTestResult.postValue(Resource.Companion.success(flyresult))
            }
        })
        return getVersionInfoTestResult
    }

    override fun getSerialNumberTest(): MutableLiveData<Resource<String>> {
        val lat = 22.0
        val lon = 22.0

        var getSerialNumberTestResult : MutableLiveData<Resource<String>> = MutableLiveData()
        var value : Double
        value = 200.0
        mController.getSerialNumber( object : CallbackWithOneParam<String>{
            override fun onFailure(rcError: AutelError) {
                val errorMessage = "";
                getSerialNumberTestResult.postValue(Resource.Companion.error(errorMessage, null))
            }

            override fun onSuccess(result : String) {
                val successMessage = "";
                getSerialNumberTestResult.postValue(Resource.Companion.success(result))
            }
        })
        return getSerialNumberTestResult
    }

    override fun setCalibrateCompassListenerTest(): MutableLiveData<Resource<CalibrateCompassStatus>> {
        val lat = 22.0
        val lon = 22.0

        var setCalibrateCompassListenerTestResult : MutableLiveData<Resource<CalibrateCompassStatus>> = MutableLiveData()
        var value : Double
        value = 200.0
        mController.setCalibrateCompassListener( object : CallbackWithOneParam<CalibrateCompassStatus>{
            override fun onFailure(rcError: AutelError) {
                val errorMessage = "";
                setCalibrateCompassListenerTestResult.postValue(Resource.Companion.error(errorMessage, null))
            }

            override fun onSuccess(result : CalibrateCompassStatus) {
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









//    override fun setLanguageTest(language : RemoteControllerLanguage): MutableLiveData<Resource<String>> {
//        var setLanguageTestResult : MutableLiveData<Resource<String>> = MutableLiveData()
//        mController.setLanguage(language, object : CallbackWithNoParam {
//            override fun onFailure(rcError: AutelError) {
//                val errorMessage = "";
//                setLanguageTestResult.postValue(Resource.Companion.error(errorMessage, null))
//            }
//
//            override fun onSuccess() {
//                val successMessage = "";
//                setLanguageTestResult.postValue(Resource.Companion.success(successMessage))
//            }
//        })
//        return setLanguageTestResult
//    }


}