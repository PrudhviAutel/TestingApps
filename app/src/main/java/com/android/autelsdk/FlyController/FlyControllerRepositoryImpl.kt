package com.android.autelsdk.FlyController

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.autelsdk.util.Resource
import com.autel.common.CallbackWithNoParam
import com.autel.common.CallbackWithOneParam
import com.autel.common.error.AutelError
import com.autel.common.remotecontroller.RemoteControllerLanguage

class FlyControllerRepositoryImpl<AutelFlyController>(
    private val mController: com.autel.sdk.flycontroller.AutelFlyController,
) : FlyControllerRepository{

    override fun setBeginnerModeStateTest(enable : Boolean): MutableLiveData<Resource<String>> {
        var setBeginnerModeStateTestResult : MutableLiveData<Resource<String>> = MutableLiveData()
        mController.setBeginnerModeEnable(enable, object : CallbackWithNoParam {
            override fun onFailure(rcError: AutelError) {
                val errorMessage = "";
                setBeginnerModeStateTestResult.postValue(Resource.Companion.error(errorMessage, null))
            }

            override fun onSuccess() {
                val successMessage = "";
                setBeginnerModeStateTestResult.postValue(Resource.Companion.success(successMessage))
            }
        })
        return setBeginnerModeStateTestResult
    }

    override fun getBeginnerModeStateTest(view : View): MutableLiveData<Resource<View>> {
        var getBeginnerModeStateTestResult : MutableLiveData<Resource<View>> = MutableLiveData()
        mController.isBeginnerModeEnable( object : CallbackWithOneParam<Boolean> {
            override fun onFailure(rcError: AutelError) {
                val errorMessage = "";
                getBeginnerModeStateTestResult.postValue(Resource.Companion.error(errorMessage, null))
            }

            override fun onSuccess(p0: Boolean?) {
                val successMessage = "";
                getBeginnerModeStateTestResult.postValue(Resource.Companion.success(view))
            }
        })
        return getBeginnerModeStateTestResult
    }

    override fun getMaxHeightTest(view : View): MutableLiveData<Resource<String>> {
        var getMaxHeightTestResult : MutableLiveData<Resource<String>> = MutableLiveData()
        mController.getMaxHeight( object : CallbackWithOneParam<Float> {
            override fun onFailure(rcError: AutelError) {
                val errorMessage = "";
                getMaxHeightTestResult.postValue(Resource.Companion.error(errorMessage, null))
            }

            override fun onSuccess(p0: Float?) {
                val successMessage = "";
                getMaxHeightTestResult.postValue(Resource.Companion.success(successMessage))
            }
        })
        return getMaxHeightTestResult
    }

    override fun setMaxHeightTest(view : View): MutableLiveData<Resource<String>> {
        var setMaxHeightTestResult : MutableLiveData<Resource<String>> = MutableLiveData()
        var value : Double
        value = 200.0
        mController.setMaxHeight( value,object : CallbackWithNoParam {
            override fun onFailure(rcError: AutelError) {
                val errorMessage = "";
                setMaxHeightTestResult.postValue(Resource.Companion.error(errorMessage, null))
            }

            override fun onSuccess() {
                val successMessage = "";
                setMaxHeightTestResult.postValue(Resource.Companion.success(successMessage))
            }
        })
        return setMaxHeightTestResult
    }

    override fun getMaxRangeTest(view : View): MutableLiveData<Resource<String>> {
        var getMaxRangeTestResult : MutableLiveData<Resource<String>> = MutableLiveData()
        mController.getMaxRange( object : CallbackWithOneParam<Float> {
            override fun onFailure(rcError: AutelError) {
                val errorMessage = "";
                getMaxRangeTestResult.postValue(Resource.Companion.error(errorMessage, null))
            }

            override fun onSuccess(p0 : Float?) {
                val successMessage = "";
                getMaxRangeTestResult.postValue(Resource.Companion.success(successMessage))
            }
        })
        return getMaxRangeTestResult
    }

    override fun setMaxRangeTest(view : View): MutableLiveData<Resource<String>> {
        var setMaxRangeTestResult : MutableLiveData<Resource<String>> = MutableLiveData()
        var value : Double
        value = 200.0
        mController.setMaxRange( value,object : CallbackWithNoParam{
            override fun onFailure(rcError: AutelError) {
                val errorMessage = "";
                setMaxRangeTestResult.postValue(Resource.Companion.error(errorMessage, null))
            }

            override fun onSuccess() {
                val successMessage = "";
                setMaxRangeTestResult.postValue(Resource.Companion.success(successMessage))
            }
        })
        return setMaxRangeTestResult
    }

    override fun getReturnHeightTest(view : View): MutableLiveData<Resource<String>> {
        var getReturnHeightTestResult : MutableLiveData<Resource<String>> = MutableLiveData()

        mController.getReturnHeight(object : CallbackWithOneParam<Float>{
            override fun onFailure(rcError: AutelError) {
                val errorMessage = "";
                getReturnHeightTestResult.postValue(Resource.Companion.error(errorMessage, null))
            }

            override fun onSuccess(p0 : Float?) {
                val successMessage = "";
                getReturnHeightTestResult.postValue(Resource.Companion.success(successMessage))
            }
        })
        return getReturnHeightTestResult
    }

    override fun setReturnHeightTest(view : View): MutableLiveData<Resource<String>> {
        var setReturnHeightTestResult : MutableLiveData<Resource<String>> = MutableLiveData()
        var value : Double
        value = 200.0
        mController.setReturnHeight( value,object : CallbackWithNoParam{
            override fun onFailure(rcError: AutelError) {
                val errorMessage = "";
                setReturnHeightTestResult.postValue(Resource.Companion.error(errorMessage, null))
            }

            override fun onSuccess() {
                val successMessage = "";
                setReturnHeightTestResult.postValue(Resource.Companion.success(successMessage))
            }
        })
        return setReturnHeightTestResult
    }

//    override fun setLanguageTest(language : RemoteControllerLanguage): MutableLiveData<Resource<String>> {
//        var setLanguageTestResult : MutableLiveData<Resource<String>> = MutableLiveData()
//        mController.setLanguage(language, object : CallbackWithNoParam {
//            override fun onFailure(rcError: AutelError) {
//                val errorMessage = "";
//                setLanguageTestResult.postValue(Resource.Companion.error(errorMessage, null))
//            }
//
//            override fun onSuccess() {
//                val successMessage = "";
//                setLanguageTestResult.postValue(Resource.Companion.success(successMessage))
//            }
//        })
//        return setLanguageTestResult
//    }


}