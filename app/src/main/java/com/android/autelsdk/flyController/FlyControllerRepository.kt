package com.android.autelsdk.flyController

import android.view.View
import androidx.lifecycle.MutableLiveData
import com.android.autelsdk.util.Resource

interface FlyControllerRepository {

    fun setBeginnerModeStateTest(enable: Boolean): MutableLiveData<Resource<String>>
    fun getBeginnerModeStateTest(): MutableLiveData<Resource<Boolean>>
    fun getMaxHeightTest(view: View): MutableLiveData<Resource<String>>
    fun getMaxRangeTest(view: View): MutableLiveData<Resource<String>>
    fun setMaxHeightTest(view: View): MutableLiveData<Resource<String>>
    fun setMaxRangeTest(view: View): MutableLiveData<Resource<String>>
    fun getReturnHeightTest(view: View): MutableLiveData<Resource<String>>
    fun setReturnHeightTest(view: View): MutableLiveData<Resource<String>>
}