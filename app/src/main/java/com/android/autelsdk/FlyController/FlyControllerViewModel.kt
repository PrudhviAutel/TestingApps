package com.android.autelsdk.FlyController

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.autelsdk.RemoteController.RemoteControllerRepository
import com.android.autelsdk.util.Resource
import com.autel.common.remotecontroller.RemoteControllerLanguage

class FlyControllerViewModel<AutelFlyController>(
    private val mController: com.autel.sdk.flycontroller.AutelFlyController,
    val flyControllerRepository : FlyControllerRepository
    ) : ViewModel(){

    public fun setBeginnerModeStateTest (enable: Boolean) : MutableLiveData<Resource<String>> {
        return flyControllerRepository.setBeginnerModeStateTest(enable)
    }

    public fun getBeginnerModeStateTest () : MutableLiveData<Resource<Boolean>> {
        return flyControllerRepository.getBeginnerModeStateTest()
    }

    public fun getMaxHeightTest (view: View) : MutableLiveData<Resource<String>> {
        return flyControllerRepository.getMaxHeightTest(view)
    }

    public fun setMaxHeightTest (view: View) : MutableLiveData<Resource<String>> {
        return flyControllerRepository.setMaxHeightTest(view)
    }

    public fun getMaxRangeTest (view: View) : MutableLiveData<Resource<String>> {
        return flyControllerRepository.getMaxRangeTest(view)
    }

    public fun setMaxRangeTest (view: View) : MutableLiveData<Resource<String>> {
        return flyControllerRepository.setMaxRangeTest(view)
    }

    public fun getReturnHeightTest (view: View) : MutableLiveData<Resource<String>> {
        return flyControllerRepository.getReturnHeightTest(view)
    }

    public fun setReturnHeightTest (view: View) : MutableLiveData<Resource<String>> {
        return flyControllerRepository.setReturnHeightTest(view)
    }
}