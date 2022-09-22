package com.android.autelsdk.gimbal

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.autelsdk.util.Resource
import com.autel.common.RangePair
import com.autel.common.gimbal.*
import com.autel.common.gimbal.evo.EvoAngleInfo
import com.autel.common.gimbal.evo.EvoGimbalParameterRangeManager
import com.autel.common.gimbal.evo.GimbalAngleRange
import com.autel.common.product.AutelProductType
import com.autel.internal.gimbal.cruiser.CruiserGimbalImpl
import com.autel.sdk.gimbal.AutelGimbal
import com.autel.sdk.product.BaseProduct

class GimbalViewModel() : ViewModel() {

    private var mController: AutelGimbal = CruiserGimbalImpl()
    private var currentProduct: MutableLiveData<BaseProduct?> = MutableLiveData()
    private var currentProductType: MutableLiveData<AutelProductType> = MutableLiveData(AutelProductType.UNKNOWN)
    private val gimbalRepository: GimbalRepository = GimbalRepositoryImpl()



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

    fun setController(controller : AutelGimbal) {
        mController = controller
        gimbalRepository.setController(mController)
    }

    fun getController() : AutelGimbal {
        return mController
    }


    //Functions related to AutelGimbal Interface
    suspend fun setGimbalWorkModeTest(mode: GimbalWorkMode) : MutableLiveData<Resource<String>> {
        return gimbalRepository.setGimbalWorkModeTest(mode)
    }

    suspend fun getGimbalWorkModeTest() : MutableLiveData<Resource<GimbalWorkMode>> {
        return gimbalRepository.getGimbalWorkModeTest()
    }

    suspend fun getVersionInfoTest() : MutableLiveData<Resource<GimbalVersionInfo>> {
        return gimbalRepository.getVersionInfoTest()
    }

    fun getGimbalParameterRangeManager() : GimbalParameterRangeManager {
        return gimbalRepository.getGimbalParameterRangeManager()
    }

    //Functions related to CruiserGimbal Interface
    suspend fun setAngleListenerTest() : MutableLiveData<Resource<EvoAngleInfo>> {
        return gimbalRepository.setAngleListenerTest()
    }

    suspend fun getAngleRangeTest() : MutableLiveData<Resource<GimbalAngleRange>> {
        return gimbalRepository.getAngleRangeTest()
    }

    suspend fun getAngleSpeedRangeTest() : MutableLiveData<Resource<RangePair<Int>>> {
        return gimbalRepository.getAngleSpeedRangeTest()
    }

    suspend fun setGimbalAngleTest(pitch: Float, roll: Float, yaw: Float) {
        return gimbalRepository.setGimbalAngleTest(pitch, roll, yaw)
    }

    suspend fun setGimbalAngleSpeedTest(pitch: Int, yaw: Int) {
        return gimbalRepository.setGimbalAngleSpeedTest(pitch, yaw)
    }

    suspend fun resetGimbalAngleTest(gimbalAxisType: GimbalAxisType) : MutableLiveData<Resource<String>> {
        return gimbalRepository.resetGimbalAngleTest(gimbalAxisType)
    }

    suspend fun setRollAdjustDataTest(roll: Float) {
        return gimbalRepository.setRollAdjustDataTest(roll)
    }

    suspend fun getRollAdjustDataTest() : MutableLiveData<Resource<Double>> {
        return gimbalRepository.getRollAdjustDataTest()
    }

    //Extra Methods not given in Dragon Fish Sdk Sample Taken from CruiserGimbalImpl
    suspend fun setLeaserRadarListenerTest() : MutableLiveData<Resource<LeaserRadar>> {
        return gimbalRepository.setLeaserRadarListenerTest()
    }

    suspend fun resetLeaserRadarListenerTest() {
        return gimbalRepository.resetLeaserRadarListenerTest()
    }

    suspend fun adjustGimbalDirectionTest(x: Float, y: Float, pitch: Float, roll: Float, yaw: Float) : MutableLiveData<Resource<String>> {
        return gimbalRepository.adjustGimbalDirectionTest(x, y, pitch, roll, yaw)
    }

    fun getCruiserGimbalParameterRangeManager() : EvoGimbalParameterRangeManager {
        return gimbalRepository.getCruiserGimbalParameterRangeManager()
    }

    suspend fun setYawAdjustDataTest(yaw: Float) : MutableLiveData<Resource<String>> {
        return gimbalRepository.setYawAdjustDataTest(yaw)
    }

    suspend fun getYawAdjustDataTest() : MutableLiveData<Resource<Double>> {
        return gimbalRepository.getYawAdjustDataTest()
    }

    suspend fun setPitchAdjustDataTest(pitch: Float) : MutableLiveData<Resource<String>> {
        return gimbalRepository.setPitchAdjustDataTest(pitch)
    }

    suspend fun getPitchAdjustDataTest() : MutableLiveData<Resource<Double>> {
        return gimbalRepository.getPitchAdjustDataTest()
    }

    suspend fun setSaveParamsTest() : MutableLiveData<Resource<String>> {
        return gimbalRepository.setSaveParamsTest()
    }

    suspend fun setGimbalCalibrationTest() : MutableLiveData<Resource<String>> {
        return gimbalRepository.setGimbalCalibrationTest()
    }

    suspend fun stopGimbalCalibrationTest() : MutableLiveData<Resource<String>> {
        return gimbalRepository.stopGimbalCalibrationTest()
    }

    suspend fun getAdjustGimbalAngelDataTest() : MutableLiveData<Resource<GimbalAdjustmentAngle>> {
        return gimbalRepository.getAdjustGimbalAngelDataTest()
    }



//    // When we will have to Pass the Values to ViewModel, we can use this code
//    class Factory(private val mController: AutelRemoteController?) : ViewModelProvider.Factory {
//        override fun <T : ViewModel> create(modelClass: Class<T>): T {
//            return RemoteControllerViewModel(mController) as T
//        }
//    }
}