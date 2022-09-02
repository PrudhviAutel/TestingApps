package com.android.autelsdk.FlyController

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.autelsdk.util.Resource
import com.autel.common.flycontroller.CalibrateCompassStatus
import com.autel.common.flycontroller.FlyControllerVersionInfo
import com.autel.common.flycontroller.LedPilotLamp
import com.autel.common.remotecontroller.RemoteControllerLanguage


class FlyControllerViewModel<AutelFlyController>(
    private val mController: com.autel.sdk.flycontroller.AutelFlyController,
    val flyControllerRepository : FlyControllerRepository
) : ViewModel(){

    public fun setBeginnerModeStateTest (enable: Boolean) : MutableLiveData<Resource<String>> {
        return flyControllerRepository.setBeginnerModeStateTest(enable)
    }

    public fun getBeginnerModeStateTest () : MutableLiveData<Resource<Boolean>> {
        return flyControllerRepository.getBeginnerModeStateTest()
    }

    public fun getMaxHeightTest () : MutableLiveData<Resource<Float>> {
        return flyControllerRepository.getMaxHeightTest()
    }

    public fun setMaxHeightTest () : MutableLiveData<Resource<String>> {
        return flyControllerRepository.setMaxHeightTest()
    }

    public fun getMaxRangeTest () : MutableLiveData<Resource<Float>> {
        return flyControllerRepository.getMaxRangeTest()
    }

    public fun setMaxRangeTest () : MutableLiveData<Resource<String>> {
        return flyControllerRepository.setMaxRangeTest()
    }

    public fun getReturnHeightTest () : MutableLiveData<Resource<Float>> {
        return flyControllerRepository.getReturnHeightTest()
    }

    public fun setReturnHeightTest () : MutableLiveData<Resource<String>> {
        return flyControllerRepository.setReturnHeightTest()
    }

    public fun setHorizontalSpeedTest () : MutableLiveData<Resource<String>> {
        return flyControllerRepository.setHorizontalSpeedTest()
    }

    public fun getHorizontalSpeedTest () : MutableLiveData<Resource<Float>> {
        return flyControllerRepository.getHorizontalSpeedTest()
    }

    public fun setCalibrateCompassListenerTest () : MutableLiveData<Resource<CalibrateCompassStatus>> {
        return flyControllerRepository.setCalibrateCompassListenerTest()
    }

    public fun getSerialNumberTest () : MutableLiveData<Resource<String>> {
        return flyControllerRepository.getSerialNumberTest()
    }

    public fun getVersionInfoTest () : MutableLiveData<Resource<FlyControllerVersionInfo>> {
        return flyControllerRepository.getVersionInfoTest()
    }

    public fun cancelLandTest () : MutableLiveData<Resource<String>> {
        return flyControllerRepository.cancelLandTest()
    }

    public fun cancelReturnTest () : MutableLiveData<Resource<String>> {
        return flyControllerRepository.cancelReturnTest()
    }

    public fun goHome () : MutableLiveData<Resource<String>> {
        return flyControllerRepository.goHome()
    }

    public fun landTest () : MutableLiveData<Resource<String>> {
        return flyControllerRepository.landTest()
    }

    public fun startCalibrateCompassTest () : MutableLiveData<Resource<CalibrateCompassStatus>> {
        return flyControllerRepository.startCalibrateCompassTest()
    }

    public fun setAircraftLocationAsHomePointTest () : MutableLiveData<Resource<String>> {
        return flyControllerRepository.setAircraftLocationAsHomePointTest()
    }

    public fun setLocationAsHomePointTest () : MutableLiveData<Resource<String>> {
        return flyControllerRepository.setLocationAsHomePointTest()
    }

    public fun setLedPilotLampTest () : MutableLiveData<Resource<String>> {
        return flyControllerRepository.setLedPilotLampTest()
    }

    public fun getLedPilotLampTest () : MutableLiveData<Resource<LedPilotLamp>> {
        return flyControllerRepository.getLedPilotLampTest()
    }

    public fun setAttiModeEnableTest (enable: Boolean) : MutableLiveData<Resource<Boolean>> {
        return flyControllerRepository.setAttiModeEnableTest(enable)
    }

    public fun isAttiModeEnableTest () : MutableLiveData<Resource<Boolean>> {
        return flyControllerRepository.isAttiModeEnableTest()
    }

}