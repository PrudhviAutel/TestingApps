package com.android.autelsdk.RemoteController

import androidx.lifecycle.ViewModel
import com.autel.common.CallbackWithNoParam
import com.autel.common.error.AutelError
import com.autel.common.remotecontroller.RemoteControllerLanguage
import com.autel.sdk.remotecontroller.AutelRemoteController

class RemoteControllerViewModel<AutelRemoteController>(private val mController : com.autel.sdk.remotecontroller.AutelRemoteController) : ViewModel(){


    fun languageTest () {
        for (language in RemoteControllerLanguage.values()) {
            mController.setLanguage(language, object : CallbackWithNoParam {
                override fun onFailure(rcError: AutelError) {

                }

                override fun onSuccess() {

                }
            })
        }



    }

}