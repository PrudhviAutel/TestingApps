package com.android.autelsdk.codec

import android.graphics.SurfaceTexture
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.autelsdk.flyController.FlyControllerRepository
import com.android.autelsdk.flyController.FlyControllerRepositoryImpl
import com.android.autelsdk.util.Resource
import com.autel.common.product.AutelProductType
import com.autel.common.video.OnRenderFrameInfoListener

class CodecViewModel : ViewModel() {
    val codecRepository: CodecRepository = CodecRepositoryImpl()
    private var currentProductType: MutableLiveData<AutelProductType> = MutableLiveData(AutelProductType.UNKNOWN)


    fun isOverExposureEnabled(): MutableLiveData<Resource<Boolean>> {
        return codecRepository.isOverExposureEnabled()
    }

    fun pause() : MutableLiveData<Resource<String>> {
        return codecRepository.pause()
    }

    fun resume() : MutableLiveData<Resource<String>> {
        return codecRepository.resume()
    }

    fun setOnRenderFrameInfoListener() : MutableLiveData<Resource<String>> {
        return codecRepository.setOnRenderFrameInfoListener()
    }
    fun startDecode(surfaceTexture: SurfaceTexture?,
                    mSurfaceWidth: Int,
                    mSurfaceHeight: Int,
                    useOpenGL: Boolean) : MutableLiveData<Resource<String>> {
        return codecRepository.startDecode(surfaceTexture,mSurfaceWidth,mSurfaceHeight,useOpenGL)

    }

    fun setOverExposure(enabled: Boolean, resId: Int) : MutableLiveData<Resource<String>>{
        return codecRepository.setOverExposure(enabled,resId)
    }


    fun surfaceSizeChanged(surfaceWidth: Int, surfaceHeight: Int) : MutableLiveData<Resource<String>>{
        return codecRepository.surfaceSizeChanged(surfaceWidth,surfaceHeight)
    }

    fun getCurrentProductType(): MutableLiveData<AutelProductType> {
        return currentProductType
    }

    fun stopCodec() : MutableLiveData<Resource<String>>{
        return codecRepository.stopCodec()
    }



}