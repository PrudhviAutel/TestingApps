package com.android.autelsdk.serialAidl

import android.os.IBinder
import androidx.lifecycle.MutableLiveData
import com.android.autelsdk.util.Resource
import com.android.autelsdk.util.Utils
import com.autel.aidl.IBetaWIFiListListener
import com.autel.aidl.IHardwareManager
import com.autel.aidl.IHardwareRealTimeInterface
import com.autel.aidl.ISerialG5_8StatusListener
import com.autel.aidl.ISerialKeystrokeListener
import com.autel.common.CallbackWithOneParam
import com.autel.common.error.AutelError

class SerialAidlRepositoryImpl : SerialAidlRepository  {

    lateinit var mService : IHardwareManager
    lateinit var mListener : IBetaWIFiListListener

    override fun startScan(listener : IBetaWIFiListListener) : MutableLiveData<Resource<IBetaWIFiListListener>>{
        var startScanResult : MutableLiveData<Resource<IBetaWIFiListListener>> = MutableLiveData()
        mService.startScan( object : CallbackWithOneParam<IBetaWIFiListListener>, IBetaWIFiListListener {
            override fun onFailure(rcError: AutelError) {
                val errorMessage = Utils.getFailureShowText("\nReason - ${rcError.description}",
                        methodName = "startScan");
                startScanResult.postValue(Resource.Companion.error(errorMessage, null))
            }

            override fun onSuccess(data1: IBetaWIFiListListener?) {
                val successMessage = Utils.getSuccessShowText("",
                        methodName = "startScan");
                startScanResult.postValue(Resource.Companion.success(data1,successMessage))
            }

            override fun asBinder(): IBinder {
                TODO("Not yet implemented")
            }

            override fun onScanLists(list: MutableList<com.autel.aidl.WIFiScanResult>?) {
                TODO("Not yet implemented")
            }

            override fun isConnected(isConnect: Boolean) {
                TODO("Not yet implemented")
            }

        })
        return startScanResult
    }

    override fun connect() : MutableLiveData<Resource<String>>{
        var connectResult : MutableLiveData<Resource<String>> = MutableLiveData()
//        mService.startScan( object : WIF{
//
//        })
                return connectResult
    }

    override fun addSerialKeystrokeListener(listener : ISerialKeystrokeListener) : MutableLiveData<Resource<ISerialKeystrokeListener>>{
        var addSerialKeystrokeListenerResult : MutableLiveData<Resource<ISerialKeystrokeListener>> = MutableLiveData()
        mService.addSerialKeystrokeListener(object : CallbackWithOneParam<ISerialKeystrokeListener>, ISerialKeystrokeListener {
            override fun onFailure(p0: AutelError?) {
                TODO("Not yet implemented")
            }

            override fun onSuccess(p0: ISerialKeystrokeListener?) {
                TODO("Not yet implemented")
            }

            override fun asBinder(): IBinder {
                TODO("Not yet implemented")
            }

            override fun onResponse(event: String?) {
                TODO("Not yet implemented")
            }

        })
        return addSerialKeystrokeListenerResult
    }

    override fun addHardwareRealTimeListener(listener : IHardwareRealTimeInterface) : MutableLiveData<Resource<IHardwareRealTimeInterface>>{
        var addHardwareRealTimeListenerResult : MutableLiveData<Resource<IHardwareRealTimeInterface>> = MutableLiveData()
        mService.addHardwareRealTimeListener(object : CallbackWithOneParam<IHardwareRealTimeInterface>, IHardwareRealTimeInterface {
            override fun onFailure(p0: AutelError?) {
                TODO("Not yet implemented")
            }

            override fun onSuccess(p0: IHardwareRealTimeInterface?) {
                TODO("Not yet implemented")
            }

            override fun asBinder(): IBinder {
                TODO("Not yet implemented")
            }

            override fun onRealTimeListener(data: String?) {
                TODO("Not yet implemented")
            }

        })

        return addHardwareRealTimeListenerResult
    }


    override fun addSerialG5_8StatusListener(listener: ISerialG5_8StatusListener) : MutableLiveData<Resource<ISerialG5_8StatusListener>>{
        var addSerialG5_8StatusListenerResult : MutableLiveData<Resource<ISerialG5_8StatusListener>> = MutableLiveData()
        mService.addSerialG5_8StatusListener(object : CallbackWithOneParam<ISerialG5_8StatusListener>, ISerialG5_8StatusListener {
            override fun onFailure(p0: AutelError?) {
                TODO("Not yet implemented")
            }

            override fun onSuccess(p0: ISerialG5_8StatusListener?) {
                TODO("Not yet implemented")
            }

            override fun asBinder(): IBinder {
                TODO("Not yet implemented")
            }

            override fun onConnect(isConnect: Boolean) {
                TODO("Not yet implemented")
            }

        })
        return addSerialG5_8StatusListenerResult
    }

    override fun start5_8gPairing(): MutableLiveData<Resource<String>>{
        var start5_8gPairingResult : MutableLiveData<Resource<String>> = MutableLiveData()
        val successMessage = Utils.getSuccessShowText("", methodName = "start5_8gPairing")
        start5_8gPairingResult.postValue(Resource.Companion.success(mService.removeAllListener().toString(), successMessage))
        return start5_8gPairingResult
    }

    override fun removeAllListener():MutableLiveData<Resource<String>>{
        var removeAllListenerResult : MutableLiveData<Resource<String>> = MutableLiveData()
        val successMessage = Utils.getSuccessShowText("", methodName = "removeAllListener")
        removeAllListenerResult.postValue(Resource.Companion.success(mService.removeAllListener().toString(), successMessage))
        return removeAllListenerResult

    }

}






