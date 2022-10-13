package com.android.autelsdk.serialAidl

import androidx.lifecycle.MutableLiveData
import com.android.autelsdk.util.Resource
import com.autel.aidl.IBetaWIFiListListener
import com.autel.aidl.IHardwareRealTimeInterface
import com.autel.aidl.ISerialG5_8StatusListener
import com.autel.aidl.ISerialKeystrokeListener

interface SerialAidlRepository {
    fun startScan(listener: IBetaWIFiListListener): MutableLiveData<Resource<IBetaWIFiListListener>>
    fun connect(): MutableLiveData<Resource<String>>
    fun addSerialKeystrokeListener(listener: ISerialKeystrokeListener): MutableLiveData<Resource<ISerialKeystrokeListener>>
    fun addHardwareRealTimeListener(listener : IHardwareRealTimeInterface): MutableLiveData<Resource<IHardwareRealTimeInterface>>
    fun addSerialG5_8StatusListener(listener: ISerialG5_8StatusListener): MutableLiveData<Resource<ISerialG5_8StatusListener>>
    fun start5_8gPairing(): MutableLiveData<Resource<String>>
    fun removeAllListener(): MutableLiveData<Resource<String>>
}