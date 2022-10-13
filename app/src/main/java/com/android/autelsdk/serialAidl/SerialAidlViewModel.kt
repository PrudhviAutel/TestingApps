package com.android.autelsdk.serialAidl

import android.net.wifi.ScanResult
import androidx.lifecycle.MutableLiveData
import com.android.autelsdk.util.Resource
import com.autel.aidl.IBetaWIFiListListener
import com.autel.aidl.IHardwareManager
import com.autel.aidl.IHardwareRealTimeInterface
import com.autel.aidl.ISerialG5_8StatusListener
import com.autel.aidl.ISerialKeystrokeListener
import okhttp3.internal.http2.Http2Connection.Listener

class SerialAidlViewModel {

    var serialAidlRepository: SerialAidlRepository  = SerialAidlRepositoryImpl()

    fun startScan(listener : IBetaWIFiListListener) : MutableLiveData<Resource<IBetaWIFiListListener>> {
        return serialAidlRepository.startScan(listener)
    }

    fun connect(ing : ScanResult,pwd : String) : MutableLiveData<Resource<String>>{
        return serialAidlRepository.connect()
    }

    fun addSerialKeystrokeListener(listener : ISerialKeystrokeListener) : MutableLiveData<Resource<ISerialKeystrokeListener>>{
        return serialAidlRepository.addSerialKeystrokeListener(listener)
    }

    fun addSerialG5_8StatusListener(listener: ISerialG5_8StatusListener) : MutableLiveData<Resource<ISerialG5_8StatusListener>>{
        return serialAidlRepository.addSerialG5_8StatusListener(listener)
    }

    fun addHardwareRealTimeListener(listener: IHardwareRealTimeInterface) : MutableLiveData<Resource<IHardwareRealTimeInterface>>{
        return serialAidlRepository.addHardwareRealTimeListener(listener)
    }

    fun removeAllListener() : MutableLiveData<Resource<String>>{
        return serialAidlRepository.removeAllListener()
    }

    fun start5_8gPairing(): MutableLiveData<Resource<String>>{
        return serialAidlRepository.start5_8gPairing()
    }
}