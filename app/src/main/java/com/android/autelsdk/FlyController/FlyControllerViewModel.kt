package com.android.autelsdk.FlyController

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.autelsdk.RemoteController.RemoteControllerRepository
import com.android.autelsdk.util.Resource
import com.autel.common.remotecontroller.RemoteControllerLanguage

class FlyControllerViewModel<AutelFlyController>(
    private val mController: com.autel.sdk.flycontroller.AutelFlyController,
    val flyControllerRepository : FlyControllerRepository
    ) : ViewModel(){
}