package com.android.autelsdk.RemoteController

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.autelsdk.util.Resource
import com.autel.common.CallbackWithNoParam
import com.autel.common.error.AutelError
import com.autel.common.remotecontroller.RemoteControllerLanguage
import com.autel.sdk.remotecontroller.AutelRemoteController

class RemoteControllerViewModel<AutelRemoteController>(
    private val mController: com.autel.sdk.remotecontroller.AutelRemoteController,
    val remoteControllerRepository : RemoteControllerRepository
) : ViewModel(){

    fun setLanguageTest (language: RemoteControllerLanguage) : MutableLiveData<Resource<String>> {
        return remoteControllerRepository.setLanguageTest(language)
    }

}