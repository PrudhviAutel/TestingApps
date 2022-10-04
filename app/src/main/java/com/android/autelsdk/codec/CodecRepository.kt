package com.android.autelsdk.codec

import android.graphics.SurfaceTexture
import androidx.lifecycle.MutableLiveData
import com.android.autelsdk.util.Resource
import com.autel.common.video.OnRenderFrameInfoListener

interface CodecRepository {
    fun isOverExposureEnabled(): MutableLiveData<Resource<Boolean>>
    fun pause() : MutableLiveData<Resource<String>>
    fun resume() : MutableLiveData<Resource<String>>
    fun setOnRenderFrameInfoListener() : MutableLiveData<Resource<String>>
    fun startDecode(
        surfaceTexture: SurfaceTexture?,
        mSurfaceWidth: Int,
        mSurfaceHeight: Int,
        useOpenGL: Boolean
    ) : MutableLiveData<Resource<String>>

    fun setOverExposure(enabled: Boolean, resId: Int) :MutableLiveData<Resource<String>>
    fun surfaceSizeChanged(surfaceWidth: Int, surfaceHeight: Int) :MutableLiveData<Resource<String>>
    fun stopCodec(): MutableLiveData<Resource<String>>
}