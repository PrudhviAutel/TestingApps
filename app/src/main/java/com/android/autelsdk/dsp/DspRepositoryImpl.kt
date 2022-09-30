package com.android.autelsdk.dsp

import androidx.lifecycle.MutableLiveData
import com.android.autelsdk.util.Resource
import com.android.autelsdk.util.Utils
import com.autel.common.CallbackWithNoParam
import com.autel.common.CallbackWithOneParam
import com.autel.common.CallbackWithTwoParams
import com.autel.common.dsp.*
import com.autel.common.dsp.evo.*
import com.autel.common.error.AutelError
import com.autel.internal.dsp.cruiser.CruiserDspImpl
import com.autel.sdk.dsp.AutelDsp
import com.autel.sdk.dsp.CruiserDsp

class DspRepositoryImpl() : DspRepository {

    var cruiserDspController : CruiserDsp = CruiserDspImpl()
    var autelDspController : AutelDsp = CruiserDspImpl()

    override fun <T> setController(controller: T) {
        if (controller is CruiserDsp) {
            cruiserDspController = controller
        } else if (controller is AutelDsp) {
            autelDspController = controller
        }
    }

    override suspend fun getRFDataListTest(maxRetryCount: Int): MutableLiveData<Resource<List<RFData>>> {
        var getRFDataListTestResult: MutableLiveData<Resource<List<RFData>>> = MutableLiveData()
        autelDspController.getRFDataList(maxRetryCount, object : CallbackWithOneParam<List<RFData>> {
            override fun onFailure(rcError: AutelError) {
                val errorMessage = Utils.getFailureShowText("\nReason - ${rcError.description}",
                    methodName = "getRFDataList");
                getRFDataListTestResult.postValue(Resource.Companion.error(errorMessage, null))
            }

            override fun onSuccess(data: List<RFData>) {
                val successMessage = Utils.getSuccessShowText(
                    "\nRF Data List : ${data}",
                    methodName = "getRFDataList"
                );
                getRFDataListTestResult.postValue(Resource.Companion.success(data, successMessage))
            }
        })
        return getRFDataListTestResult
    }

    override suspend fun getCurrentRFDataTest(maxRetryCount: Int): MutableLiveData<Resource<RFData>> {
        var getCurrentRFDataTestResult: MutableLiveData<Resource<RFData>> = MutableLiveData()
        autelDspController.getCurrentRFData(maxRetryCount, object : CallbackWithOneParam<RFData> {
            override fun onFailure(rcError: AutelError) {
                val errorMessage = Utils.getFailureShowText("\nReason - ${rcError.description}",
                    methodName = "getCurrentRFData");
                getCurrentRFDataTestResult.postValue(Resource.Companion.error(errorMessage, null))
            }

            override fun onSuccess(data: RFData) {
                val successMessage = Utils.getSuccessShowText(
                    "\nRF Data : ${data}",
                    methodName = "getCurrentRFData"
                );
                getCurrentRFDataTestResult.postValue(Resource.Companion.success(data, successMessage))
            }
        })
        return getCurrentRFDataTestResult
    }

    override suspend fun setCurrentRFDataTest(selectedRFHz: Int, maxRetryCount: Int): MutableLiveData<Resource<String>> {
        var setCurrentRFDataTestResult: MutableLiveData<Resource<String>> = MutableLiveData()
        autelDspController.setCurrentRFData(selectedRFHz, maxRetryCount, object : CallbackWithNoParam {
            override fun onFailure(rcError: AutelError) {
                val errorMessage = Utils.getFailureShowText("\nReason - ${rcError.description}",
                    methodName = "setCurrentRFData");
                setCurrentRFDataTestResult.postValue(Resource.Companion.error(errorMessage, null))
            }

            override fun onSuccess() {
                val successMessage = Utils.getSuccessShowText(
                    "\non Selected RF Hz = ${selectedRFHz} and Max Retry Count = ${maxRetryCount}",
                    methodName = "setCurrentRFData"
                );
                setCurrentRFDataTestResult.postValue(Resource.Companion.success(successMessage, successMessage))
            }
        })
        return setCurrentRFDataTestResult
    }

    override suspend fun getVersionInfoTest(): MutableLiveData<Resource<DspVersionInfo>> {
        var getVersionInfoTestResult: MutableLiveData<Resource<DspVersionInfo>> = MutableLiveData()
        autelDspController.getVersionInfo(object : CallbackWithOneParam<DspVersionInfo> {
            override fun onFailure(rcError: AutelError) {
                val errorMessage = Utils.getFailureShowText("\nReason - ${rcError.description}",
                    methodName = "getVersionInfo");
                getVersionInfoTestResult.postValue(Resource.Companion.error(errorMessage, null))
            }

            override fun onSuccess(info: DspVersionInfo) {
                val successMessage = Utils.getSuccessShowText(
                    "\nVersion Info = ${info} ",
                    methodName = "getVersionInfo"
                );
                getVersionInfoTestResult.postValue(Resource.Companion.success(info, successMessage))
            }
        })
        return getVersionInfoTestResult
    }

    override suspend fun setSynMsgBroadcastTest(appAction: AppAction, appActionParam: AppActionParam, ): MutableLiveData<Resource<String>> {
        var setSynMsgBroadcastTestResult: MutableLiveData<Resource<String>> = MutableLiveData()
        cruiserDspController.setSynMsgBroadcast(appAction, appActionParam)
        val successMessage = Utils.getSuccessShowText(
            "on App action : ${appAction},\n\n App Action param : ${appActionParam}",
            methodName = "setSynMsgBroadcast"
        )
        setSynMsgBroadcastTestResult.postValue(Resource.Companion.success(successMessage, successMessage))
        return setSynMsgBroadcastTestResult
    }

    override suspend fun setSynMsgBroadcastListenerTest(): MutableLiveData<Resource<Pair<AppAction, AppActionParam>>> {
        var setCurrentRFDataTestResult: MutableLiveData<Resource<Pair<AppAction, AppActionParam>>> = MutableLiveData()
        cruiserDspController.setSynMsgBroadcastListener(object : CallbackWithTwoParams<AppAction, AppActionParam> {
            override fun onFailure(rcError: AutelError) {
                val errorMessage = Utils.getFailureShowText("\nReason - ${rcError.description}",
                    methodName = "setSynMsgBroadcastListener");
                setCurrentRFDataTestResult.postValue(Resource.Companion.error(errorMessage, null))
            }

            override fun onSuccess(appAction: AppAction, appActionParam: AppActionParam) {
                val successMessage = Utils.getSuccessShowText(
                    "App action : ${appAction},\n\nApp Action param : ${appActionParam}",
                    methodName = "setSynMsgBroadcastListener"
                );
                val data = Pair(appAction, appActionParam)
                setCurrentRFDataTestResult.postValue(Resource.Companion.success(data, successMessage))
            }
        })
        return setCurrentRFDataTestResult
    }

    override suspend fun setDspInfoListenerTest(): MutableLiveData<Resource<EvoDspInfo>> {
        var setDspInfoListenerTestResult: MutableLiveData<Resource<EvoDspInfo>> = MutableLiveData()
        cruiserDspController.setDspInfoListener(object : CallbackWithOneParam<EvoDspInfo> {
            override fun onFailure(rcError: AutelError) {
                val errorMessage = Utils.getFailureShowText("\nReason - ${rcError.description}",
                    methodName = "setDspInfoListener");
                setDspInfoListenerTestResult.postValue(Resource.Companion.error(errorMessage, null))
            }

            override fun onSuccess(info: EvoDspInfo?) {
                val successMessage = Utils.getSuccessShowText(
                    "\non EvoDspInfo : ${info}",
                    methodName = "setDspInfoListener"
                );
                setDspInfoListenerTestResult.postValue(Resource.Companion.success(info, successMessage))
            }
        })
        return setDspInfoListenerTestResult
    }

    override suspend fun setBandwidthInfoTest(bandMode: BandMode, bandwidth: Bandwidth): MutableLiveData<Resource<String>> {
        var setBandwidthInfoTestResult: MutableLiveData<Resource<String>> = MutableLiveData()
        cruiserDspController.setBandwidthInfo(bandMode, bandwidth)
        val successMessage = Utils.getSuccessShowText(
            "on Bandmode = ${bandMode.name}, Bandwidth = ${bandwidth.name}",
            methodName = "setBandwidthInfo"
        )
        setBandwidthInfoTestResult.postValue(Resource.Companion.success(successMessage, successMessage))
        return setBandwidthInfoTestResult
    }

    override suspend fun setTransferModeTest(transferMode: TransferMode): MutableLiveData<Resource<String>> {
        var setTransferModeTestResult: MutableLiveData<Resource<String>> = MutableLiveData()
        cruiserDspController.setTransferMode(transferMode, object : CallbackWithNoParam {
            override fun onFailure(rcError: AutelError) {
                val errorMessage = Utils.getFailureShowText("\nReason - ${rcError.description}",
                    methodName = "setTransferMode");
                setTransferModeTestResult.postValue(Resource.Companion.error(errorMessage, null))
            }

            override fun onSuccess() {
                val successMessage = Utils.getSuccessShowText(
                    "\non Transfer Mode = ${transferMode.name}",
                    methodName = "setTransferMode"
                );
                setTransferModeTestResult.postValue(Resource.Companion.success(successMessage, successMessage))
            }
        })
        return setTransferModeTestResult
    }

    override suspend fun getTransferModeTest(): MutableLiveData<Resource<TransferMode>> {
        var getTransferModeTestResult: MutableLiveData<Resource<TransferMode>> = MutableLiveData()
        cruiserDspController.getTransferMode(object : CallbackWithOneParam<TransferMode> {
            override fun onFailure(rcError: AutelError) {
                val errorMessage = Utils.getFailureShowText("\nReason - ${rcError.description}",
                    methodName = "getTransferMode");
                getTransferModeTestResult.postValue(Resource.Companion.error(errorMessage, null))
            }

            override fun onSuccess(mode: TransferMode) {
                val successMessage = Utils.getSuccessShowText(
                    "\nTransfer mode = ${mode.name} ",
                    methodName = "getTransferMode"
                );
                getTransferModeTestResult.postValue(Resource.Companion.success(mode, successMessage))
            }
        })
        return getTransferModeTestResult
    }

    override suspend fun setVideoLinkStateTest(state: Boolean): MutableLiveData<Resource<String>> {
        var setVideoLinkStateTestResult: MutableLiveData<Resource<String>> = MutableLiveData()
        cruiserDspController.setVideoLinkState(state, object : CallbackWithNoParam {
            override fun onFailure(rcError: AutelError) {
                val errorMessage = Utils.getFailureShowText("on state = ${state}.\nReason - ${rcError.description}",
                    methodName = "setVideoLinkState");
                setVideoLinkStateTestResult.postValue(Resource.Companion.error(errorMessage, null))
            }

            override fun onSuccess() {
                val successMessage = Utils.getSuccessShowText(
                    "\non Video Link State = ${state.toString()}",
                    methodName = "setVideoLinkState"
                );
                setVideoLinkStateTestResult.postValue(Resource.Companion.success(successMessage, successMessage))
            }
        })
        return setVideoLinkStateTestResult
    }

    override suspend fun getDeviceVersionInfoTest(): MutableLiveData<Resource<List<DeviceVersionInfo>>> {
        var getDeviceVersionInfoTestResult: MutableLiveData<Resource<List<DeviceVersionInfo>>> = MutableLiveData()
        cruiserDspController.getDeviceVersionInfo(object : CallbackWithOneParam<List<DeviceVersionInfo>> {
            override fun onFailure(rcError: AutelError) {
                val errorMessage = Utils.getFailureShowText("\nReason - ${rcError.description}",
                    methodName = "getDeviceVersionInfo");
                getDeviceVersionInfoTestResult.postValue(Resource.Companion.error(errorMessage, null))
            }

            override fun onSuccess(infoList: List<DeviceVersionInfo>) {
                val successMessage = Utils.getSuccessShowText(
                    "\nDevice Version Info List = ${infoList} ",
                    methodName = "getDeviceVersionInfo"
                );
                getDeviceVersionInfoTestResult.postValue(Resource.Companion.success(infoList, successMessage))
            }
        })
        return getDeviceVersionInfoTestResult
    }

    override suspend fun setBaseStationEnableTest(state: Boolean): MutableLiveData<Resource<String>> {
        var setBaseStationEnableTestResult: MutableLiveData<Resource<String>> = MutableLiveData()
        cruiserDspController.setBaseStationEnable(state, object : CallbackWithNoParam {
            override fun onFailure(rcError: AutelError) {
                val errorMessage = Utils.getFailureShowText("on state = ${state}.\nReason - ${rcError.description}",
                    methodName = "setBaseStationEnable");
                setBaseStationEnableTestResult.postValue(Resource.Companion.error(errorMessage, null))
            }

            override fun onSuccess() {
                val successMessage = Utils.getSuccessShowText(
                    "\non Base Station Enabled = ${state}",
                    methodName = "setBaseStationEnable"
                );
                setBaseStationEnableTestResult.postValue(Resource.Companion.success(successMessage, successMessage))
            }
        })
        return setBaseStationEnableTestResult
    }

    override suspend fun isBaseStationEnableTest(): MutableLiveData<Resource<Boolean>> {
        var isBaseStationEnableTestResult: MutableLiveData<Resource<Boolean>> = MutableLiveData()
        cruiserDspController.isBaseStationEnable(object : CallbackWithOneParam<Boolean> {
            override fun onFailure(rcError: AutelError) {
                val errorMessage = Utils.getFailureShowText("\nReason - ${rcError.description}",
                    methodName = "isBaseStationEnable");
                isBaseStationEnableTestResult.postValue(Resource.Companion.error(errorMessage, null))
            }

            override fun onSuccess(state: Boolean) {
                val successMessage = Utils.getSuccessShowText(
                    "\nBase Station Enabled = ${state} ",
                    methodName = "isBaseStationEnable"
                );
                isBaseStationEnableTestResult.postValue(Resource.Companion.success(state, successMessage))
            }
        })
        return isBaseStationEnableTestResult
    }

    override suspend fun setRouteWifiConfigTest(wiFiInfo: WiFiInfo): MutableLiveData<Resource<String>> {
        var setRouteWifiConfigTestResult: MutableLiveData<Resource<String>> = MutableLiveData()
        cruiserDspController.setRouteWifConfig(wiFiInfo, object : CallbackWithNoParam {
            override fun onFailure(rcError: AutelError) {
                val errorMessage = Utils.getFailureShowText("on Wifi Info : ${wiFiInfo}\nReason - ${rcError.description}",
                    methodName = "setRouteWifConfig");
                setRouteWifiConfigTestResult.postValue(Resource.Companion.error(errorMessage, null))
            }

            override fun onSuccess() {
                val successMessage = Utils.getSuccessShowText(
                    "\non Wifi Info : ${wiFiInfo}",
                    methodName = "setRouteWifConfig"
                );
                setRouteWifiConfigTestResult.postValue(Resource.Companion.success(successMessage, successMessage))
            }
        })
        return setRouteWifiConfigTestResult
    }


}
