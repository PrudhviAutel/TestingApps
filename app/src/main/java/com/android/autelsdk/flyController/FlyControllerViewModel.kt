package com.android.autelsdk.flyController

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.autelsdk.util.Resource
import com.autel.common.flycontroller.CalibrateCompassStatus
import com.autel.common.flycontroller.FlyControllerVersionInfo
import com.autel.common.flycontroller.LedPilotLamp


class FlyControllerViewModel : ViewModel() {

    val flyControllerRepository: FlyControllerRepository = FlyControllerRepositoryImpl()

    suspend fun setBeginnerModeStateTest(enable: Boolean): MutableLiveData<Resource<String>> {
        return flyControllerRepository.setBeginnerModeStateTest(enable)
    }

    suspend fun getBeginnerModeStateTest(): MutableLiveData<Resource<Boolean>> {
        return flyControllerRepository.getBeginnerModeStateTest()
    }

    suspend fun getMaxHeightTest(): MutableLiveData<Resource<Float>> {
        return flyControllerRepository.getMaxHeightTest()
    }

    suspend fun setMaxHeightTest(value: Double): MutableLiveData<Resource<String>> {
        return flyControllerRepository.setMaxHeightTest(value)
    }

    suspend fun getMaxRangeTest(): MutableLiveData<Resource<Float>> {
        return flyControllerRepository.getMaxRangeTest()
    }

    suspend fun setMaxRangeTest(value: Double): MutableLiveData<Resource<String>> {
        return flyControllerRepository.setMaxRangeTest(value)
    }

    suspend fun getReturnHeightTest(): MutableLiveData<Resource<Float>> {
        return flyControllerRepository.getReturnHeightTest()
    }

    suspend fun setReturnHeightTest(value: Double): MutableLiveData<Resource<String>> {
        return flyControllerRepository.setReturnHeightTest(value)
    }

    suspend fun setHorizontalSpeedTest(value: Double): MutableLiveData<Resource<String>> {
        return flyControllerRepository.setHorizontalSpeedTest(value)
    }

    suspend fun getHorizontalSpeedTest(): MutableLiveData<Resource<Float>> {
        return flyControllerRepository.getHorizontalSpeedTest()
    }

    suspend fun setCalibrateCompassListenerTest(): MutableLiveData<Resource<CalibrateCompassStatus>> {
        return flyControllerRepository.setCalibrateCompassListenerTest()
    }

    suspend fun getSerialNumberTest(): MutableLiveData<Resource<String>> {
        return flyControllerRepository.getSerialNumberTest()
    }

    suspend fun getVersionInfoTest(): MutableLiveData<Resource<FlyControllerVersionInfo>> {
        return flyControllerRepository.getVersionInfoTest()
    }

    suspend fun cancelLandTest(): MutableLiveData<Resource<String>> {
        return flyControllerRepository.cancelLandTest()
    }

    suspend fun cancelReturnTest(): MutableLiveData<Resource<String>> {
        return flyControllerRepository.cancelReturnTest()
    }

    suspend fun goHomeTest(): MutableLiveData<Resource<String>> {
        return flyControllerRepository.goHomeTest()
    }

    suspend fun landTest(): MutableLiveData<Resource<String>> {
        return flyControllerRepository.landTest()
    }

    suspend fun startCalibrateCompassTest(): MutableLiveData<Resource<CalibrateCompassStatus>> {
        return flyControllerRepository.startCalibrateCompassTest()
    }

    suspend fun setAircraftLocationAsHomePointTest(): MutableLiveData<Resource<String>> {
        return flyControllerRepository.setAircraftLocationAsHomePointTest()
    }

    suspend fun setLocationAsHomePointTest(
        lat: Double,
        lon: Double
    ): MutableLiveData<Resource<String>> {
        return flyControllerRepository.setLocationAsHomePointTest(lat, lon)
    }

    suspend fun setLedPilotLampTest(ledPilotLamp: LedPilotLamp): MutableLiveData<Resource<String>> {
        return flyControllerRepository.setLedPilotLampTest(ledPilotLamp)
    }

    suspend fun getLedPilotLampTest(): MutableLiveData<Resource<LedPilotLamp>> {
        return flyControllerRepository.getLedPilotLampTest()
    }

    suspend fun setAttitudeModeEnableTest(enable: Boolean): MutableLiveData<Resource<Boolean>> {
        return flyControllerRepository.setAttitudeModeEnableTest(enable)
    }

    suspend fun isAttitudeModeEnableTest(): MutableLiveData<Resource<Boolean>> {
        return flyControllerRepository.isAttitudeModeEnableTest()
    }

}