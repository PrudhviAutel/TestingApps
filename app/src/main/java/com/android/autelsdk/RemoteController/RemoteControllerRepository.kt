package com.android.autelsdk.RemoteController

import androidx.lifecycle.MutableLiveData
import com.android.autelsdk.util.Resource
import com.autel.common.remotecontroller.RemoteControllerLanguage

interface RemoteControllerRepository {

    fun setLanguageTest(language: RemoteControllerLanguage) : MutableLiveData<Resource<String>>

    fun getLanguageTest() : MutableLiveData<Resource<RemoteControllerLanguage>>



}