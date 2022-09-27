package com.android.autelsdk.dsp

import androidx.lifecycle.MutableLiveData
import com.android.autelsdk.util.Resource
import com.autel.common.CallbackWithNoParam
import com.autel.common.CallbackWithOneParam
import com.autel.common.CallbackWithTwoParams
import com.autel.common.dsp.*
import com.autel.common.dsp.evo.*

interface DspRepository {

    fun <T> setController(controller : T)

    //Functions related to AutelDsp Interface
    suspend fun getRFDataListTest(maxRetryCount: Int) : MutableLiveData<Resource<List<RFData>>>

    suspend fun getCurrentRFDataTest(maxRetryCount: Int) : MutableLiveData<Resource<RFData>>

    suspend fun setCurrentRFDataTest(selectedRFHz: Int, maxRetryCount: Int) : MutableLiveData<Resource<String>>

    suspend fun getVersionInfoTest() : MutableLiveData<Resource<DspVersionInfo>>

    //Functions related to CruiserDsp Interface
    suspend fun setSynMsgBroadcastTest(appAction: AppAction, appActionParam: AppActionParam) : MutableLiveData<Resource<String>>

    suspend fun setSynMsgBroadcastListenerTest() : MutableLiveData<Resource<Pair<AppAction, AppActionParam>>>

    suspend fun setDspInfoListenerTest() : MutableLiveData<Resource<EvoDspInfo>>

    suspend fun setBandwidthInfoTest(bandMode: BandMode, bandwidth: Bandwidth) : MutableLiveData<Resource<String>>

    suspend fun setTransferModeTest(transferMode: TransferMode) : MutableLiveData<Resource<String>>

    suspend fun getTransferModeTest() : MutableLiveData<Resource<TransferMode>>

    suspend fun setVideoLinkStateTest(state: Boolean) : MutableLiveData<Resource<String>>

    suspend fun getDeviceVersionInfoTest() : MutableLiveData<Resource<List<DeviceVersionInfo>>>

    suspend fun setBaseStationEnableTest(state: Boolean) : MutableLiveData<Resource<String>>

    suspend fun isBaseStationEnableTest() : MutableLiveData<Resource<Boolean>>

    suspend fun setRouteWifiConfigTest(wiFiInfo: WiFiInfo) : MutableLiveData<Resource<String>>

}