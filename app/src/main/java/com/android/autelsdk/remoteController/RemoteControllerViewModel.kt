package com.android.autelsdk.remoteController

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.autelsdk.util.Resource
import com.autel.common.product.AutelProductType
import com.autel.common.remotecontroller.*
import com.autel.internal.remotecontroller.RemoteController10
import com.autel.sdk.product.BaseProduct
import com.autel.sdk.remotecontroller.AutelRemoteController

class RemoteControllerViewModel() : ViewModel() {

    private var mController: AutelRemoteController = RemoteController10()
    private var currentProduct: MutableLiveData<BaseProduct?> = MutableLiveData()
    private var currentProductType: MutableLiveData<AutelProductType> = MutableLiveData(AutelProductType.UNKNOWN)
    private val remoteControllerRepository: RemoteControllerRepository = RemoteControllerRepositoryImpl()

    suspend fun setLanguageTest(language: RemoteControllerLanguage): MutableLiveData<Resource<String>> {
        return remoteControllerRepository.setLanguageTest(language)
    }

    suspend fun getLanguageTest(): MutableLiveData<Resource<RemoteControllerLanguage>> {
        return remoteControllerRepository.getLanguageTest()
    }

    suspend fun enterPairingTest(): MutableLiveData<Resource<String>> {
        return remoteControllerRepository.enterPairingTest()
    }

    fun exitPairing() {
        return remoteControllerRepository.exitPairing()
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

    fun getParameterRangeManager(): RemoteControllerParameterRangeManager {
        return remoteControllerRepository.getParameterRangeManager()
    }

    suspend fun setStickCalibrationTest(calibration: RemoteControllerStickCalibration) : MutableLiveData<Resource<RemoteControllerStickCalibration>> {
        return remoteControllerRepository.setStickCalibrationTest(calibration)
    }

    fun setCurrentProduct(product : BaseProduct?) {
        setCurrentProductType(product?.type)
        currentProduct.postValue(product)
    }

    fun getCurrentProduct(): MutableLiveData<BaseProduct?> {
        return currentProduct
    }

    fun setCurrentProductType(productType : AutelProductType?) {
        currentProductType.postValue(productType ?: AutelProductType.UNKNOWN)
    }

    fun getCurrentProductType(): MutableLiveData<AutelProductType> {
        return currentProductType
    }

    fun setRemoteController(controller : AutelRemoteController) {
        mController = controller
        remoteControllerRepository.setRemoteController(mController)
    }

    fun getRemoteController() : AutelRemoteController {
        return mController
    }

    suspend fun setRemoteButtonControllerListenerTest(): MutableLiveData<Resource<RemoteControllerNavigateButtonEvent>> {
        return remoteControllerRepository.setRemoteButtonControllerListenerTest()
    }

    suspend fun setInfoDataListenerTest(): MutableLiveData<Resource<RemoteControllerInfo>> {
        return remoteControllerRepository.setInfoDataListenerTest()
    }

    suspend fun setConnectStateListenerTest(): MutableLiveData<Resource<RemoteControllerConnectState>> {
        return remoteControllerRepository.setConnectStateListenerTest()
    }

    suspend fun setControlMenuListenerTest(): MutableLiveData<Resource<IntArray>> {
        return remoteControllerRepository.setControlMenuListenerTest()
    }

    suspend fun setGimbalDialAdjustSpeedTest(speed: Int): MutableLiveData<Resource<Int>> {
        return remoteControllerRepository.setGimbalDialAdjustSpeedTest(speed)
    }

    suspend fun getGimbalDialAdjustSpeedTest(): MutableLiveData<Resource<Int>> {
        return remoteControllerRepository.getGimbalDialAdjustSpeedTest()
    }

    fun resetRemoteButtonControllerListenerTest() {
        return remoteControllerRepository.resetRemoteButtonControllerListenerTest()
    }

    fun resetInfoDataListenerTest() {
        return remoteControllerRepository.resetInfoDataListenerTest()
    }

    fun resetConnectStateListenerTest() {
        return remoteControllerRepository.resetConnectStateListenerTest()
    }

    fun resetControlMenuListenerTest() {
        return remoteControllerRepository.resetControlMenuListenerTest()
    }

//    // When we will have to Pass the Values to ViewModel, we can use this code
//    class Factory(private val mController: AutelRemoteController?) : ViewModelProvider.Factory {
//        override fun <T : ViewModel> create(modelClass: Class<T>): T {
//            return RemoteControllerViewModel(mController) as T
//        }
//    }
}