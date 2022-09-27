package com.android.autelsdk.dsp

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.autelsdk.util.Resource
import com.autel.common.RangePair
import com.autel.common.dsp.*
import com.autel.common.dsp.evo.*
import com.autel.common.gimbal.*
import com.autel.common.gimbal.evo.EvoAngleInfo
import com.autel.common.gimbal.evo.EvoGimbalParameterRangeManager
import com.autel.common.gimbal.evo.GimbalAngleRange
import com.autel.common.product.AutelProductType
import com.autel.internal.dsp.cruiser.CruiserDspImpl
import com.autel.internal.gimbal.cruiser.CruiserGimbalImpl
import com.autel.sdk.dsp.AutelDsp
import com.autel.sdk.dsp.CruiserDsp
import com.autel.sdk.gimbal.AutelGimbal
import com.autel.sdk.gimbal.CruiserGimbal
import com.autel.sdk.product.BaseProduct
import com.autel.sdk.product.CruiserAircraft

class DspViewModel() : ViewModel() {

    private var autelDspController: AutelDsp = CruiserDspImpl()
    private var cruiserDspController: CruiserDsp = CruiserDspImpl()
    private var currentProduct: MutableLiveData<BaseProduct?> = MutableLiveData()
    private var currentProductType: MutableLiveData<AutelProductType> = MutableLiveData(AutelProductType.UNKNOWN)
    private val dspRepository: DspRepository = DspRepositoryImpl()

    fun setCurrentProduct(product : BaseProduct?) {
        setCurrentProductType(product?.type)
        currentProduct.postValue(product)
        setController(product?.dsp)
        val cruiserDspController = (product as CruiserAircraft).dsp
        setController(cruiserDspController)
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

    fun <T> setController(controller : T) {
        if (controller is CruiserDsp) {
            cruiserDspController = controller
        } else if (controller is AutelDsp) {
            autelDspController = controller
        }

        dspRepository.setController(controller)
    }

    //Functions related to AutelDsp Interface
    suspend fun getRFDataListTest(maxRetryCount: Int) : MutableLiveData<Resource<List<RFData>>> {
        return dspRepository.getRFDataListTest(maxRetryCount)
    }

    suspend fun getCurrentRFDataTest(maxRetryCount: Int) : MutableLiveData<Resource<RFData>> {
        return dspRepository.getCurrentRFDataTest(maxRetryCount)
    }

    suspend fun setCurrentRFDataTest(selectedRFHz: Int, maxRetryCount: Int) : MutableLiveData<Resource<String>> {
        return dspRepository.setCurrentRFDataTest(selectedRFHz, maxRetryCount)
    }

    suspend fun getVersionInfoTest() : MutableLiveData<Resource<DspVersionInfo>> {
        return dspRepository.getVersionInfoTest()
    }

    //Functions related to CruiserDsp Interface
    suspend fun setSynMsgBroadcastTest(appAction: AppAction, appActionParam: AppActionParam) : MutableLiveData<Resource<String>> {
        return dspRepository.setSynMsgBroadcastTest(appAction, appActionParam)
    }

    suspend fun setSynMsgBroadcastListenerTest() : MutableLiveData<Resource<Pair<AppAction, AppActionParam>>> {
        return dspRepository.setSynMsgBroadcastListenerTest()
    }

    suspend fun setDspInfoListenerTest() : MutableLiveData<Resource<EvoDspInfo>> {
        return dspRepository.setDspInfoListenerTest()
    }

    suspend fun setBandwidthInfoTest(bandMode: BandMode, bandwidth: Bandwidth) : MutableLiveData<Resource<String>> {
        return dspRepository.setBandwidthInfoTest(bandMode, bandwidth)
    }

    suspend fun setTransferModeTest(transferMode: TransferMode) : MutableLiveData<Resource<String>> {
        return dspRepository.setTransferModeTest(transferMode)
    }

    suspend fun getTransferModeTest() : MutableLiveData<Resource<TransferMode>> {
        return dspRepository.getTransferModeTest()
    }

    suspend fun setVideoLinkStateTest(state: Boolean) : MutableLiveData<Resource<String>> {
        return dspRepository.setVideoLinkStateTest(state)
    }

    suspend fun getDeviceVersionInfoTest() : MutableLiveData<Resource<List<DeviceVersionInfo>>> {
        return dspRepository.getDeviceVersionInfoTest()
    }

    suspend fun setBaseStationEnableTest(state: Boolean) : MutableLiveData<Resource<String>> {
        return dspRepository.setBaseStationEnableTest(state)
    }

    suspend fun isBaseStationEnableTest() : MutableLiveData<Resource<Boolean>> {
        return dspRepository.isBaseStationEnableTest()
    }

    suspend fun setRouteWifiConfigTest(wiFiInfo: WiFiInfo) : MutableLiveData<Resource<String>> {
        return dspRepository.setRouteWifiConfigTest(wiFiInfo)
    }


//    // When we will have to Pass the Values to ViewModel, we can use this code
//    class Factory(private val mController: AutelRemoteController?) : ViewModelProvider.Factory {
//        override fun <T : ViewModel> create(modelClass: Class<T>): T {
//            return RemoteControllerViewModel(mController) as T
//        }
//    }
}