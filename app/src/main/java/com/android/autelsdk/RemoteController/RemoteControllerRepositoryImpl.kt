package com.android.autelsdk.RemoteController

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.autelsdk.util.Resource
import com.autel.common.CallbackWithNoParam
import com.autel.common.error.AutelError
import com.autel.common.remotecontroller.RemoteControllerLanguage

class RemoteControllerRepositoryImpl<AutelRemoteController>(
    private val mController: com.autel.sdk.remotecontroller.AutelRemoteController,
) : RemoteControllerRepository{

    override fun setLanguageTest(language : RemoteControllerLanguage): MutableLiveData<Resource<String>> {
        var setLanguageTestResult : MutableLiveData<Resource<String>> = MutableLiveData()
        mController.setLanguage(language, object : CallbackWithNoParam {
            override fun onFailure(rcError: AutelError) {
                val errorMessage = "";
                setLanguageTestResult.postValue(Resource.Companion.error(errorMessage, null))
            }

            override fun onSuccess() {
                val successMessage = "";
                setLanguageTestResult.postValue(Resource.Companion.success(successMessage))
            }
        })
        return setLanguageTestResult
    }


}