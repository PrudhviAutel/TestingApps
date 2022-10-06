package com.android.autelsdk.codec

import android.graphics.SurfaceTexture
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.android.autelsdk.util.Resource
import com.android.autelsdk.util.Utils
import com.autel.common.video.OnRenderFrameInfoListener
import com.autel.internal.video.AutelCodec_Ranger
import com.autel.internal.video.core.decoder2.CodecManager
import com.autel.sdk.video.AutelCodec

class CodecRepositoryImpl : CodecRepository {
    var mController : AutelCodec = AutelCodec_Ranger()
    val codecManager = CodecManager.getInstance()

    override fun isOverExposureEnabled(): MutableLiveData<Resource<Boolean>> {
        var isOverExposureEnabledResult: MutableLiveData<Resource<Boolean>> = MutableLiveData()
        val successMessage = "Over Exposure is ${if(codecManager.isOverExposureEnabled) "Enabled" else "Disabled"}"
        isOverExposureEnabledResult.postValue(Resource.Companion.success(codecManager.isOverExposureEnabled, successMessage))
        return isOverExposureEnabledResult
    }

    override fun pause() : MutableLiveData<Resource<String>>{

        var pauseResult: MutableLiveData<Resource<String>> = MutableLiveData()
        val successMessage = Utils.getSuccessShowText("", methodName = "pauseRender")
        pauseResult.postValue(Resource.Companion.success(successMessage, successMessage))
        return pauseResult
    }

    override fun resume() : MutableLiveData<Resource<String>>{
        var resumeResult: MutableLiveData<Resource<String>> = MutableLiveData()
        val successMessage = Utils.getSuccessShowText("", methodName = "resume")
        resumeResult.postValue(Resource.Companion.success(successMessage, successMessage))
        return resumeResult
    }

    override fun setOnRenderFrameInfoListener() :  MutableLiveData<Resource<String>> {
        var setOnRenderFrameInfoListenerResult: MutableLiveData<Resource<String>> = MutableLiveData()
        var successMessage = Utils.getSuccessShowText("", methodName = "setOnRenderFrameInfoListener")
        codecManager.setOnRenderFrameInfoListener(object : OnRenderFrameInfoListener {
            override fun onRenderFrameTimestamp(l: Long) {
                successMessage = Utils.getSuccessShowText("For Render Frame Time Stamp = ${l}", methodName = "onRenderFrameTimestamp")
                setOnRenderFrameInfoListenerResult.postValue(Resource.Companion.success(successMessage, successMessage))
            }
            override fun onRenderFrameSizeChanged(width: Int, height: Int) {
                successMessage = Utils.getSuccessShowText("For Render Frame Size Width = ${width}  , Height = ${height} , Unit = Pixels ", methodName = "onRenderFrameSizeChanged")
                setOnRenderFrameInfoListenerResult.postValue(Resource.Companion.success(successMessage, successMessage))
            }

            override fun onFrameStream(
                videoBuffer: ByteArray,
                isIFrame: Boolean,
                size: Int,
                pts: Long
            ) {
                Log.d("onFrameStream", " onFrameStream size $size")
                if (null == videoBuffer) return
                successMessage = Utils.getSuccessShowText("For Frame VideoBuffer = ${videoBuffer} , isIFrame = ${isIFrame}, size = ${size} , pts = ${pts}", methodName = "onFrameStream")
                setOnRenderFrameInfoListenerResult.postValue(Resource.Companion.success(successMessage, successMessage))
            }
        })
        return setOnRenderFrameInfoListenerResult
    }

    override  fun startDecode(
        surfaceTexture: SurfaceTexture?,
        mSurfaceWidth: Int,
        mSurfaceHeight: Int,
        useOpenGL: Boolean
    ) : MutableLiveData<Resource<String>>{
        var startDecodeResult: MutableLiveData<Resource<String>> = MutableLiveData()
        val successMessage = Utils.getSuccessShowText("For Start Decode surfaceTexture = ${surfaceTexture} , msurfaceWidth = ${mSurfaceWidth}, msurfaceHeight = ${mSurfaceHeight} useOpenGL = ${useOpenGL}", methodName = "startDecodeResult")
        startDecodeResult.postValue(Resource.Companion.success(successMessage, successMessage))
        return startDecodeResult
    }

    override fun stopCodec() : MutableLiveData<Resource<String>>{
        var stopCodecResult : MutableLiveData<Resource<String>> = MutableLiveData()
        val successMessage = Utils.getSuccessShowText("", methodName = "stopDecodeResult")
        stopCodecResult.postValue(Resource.Companion.success(successMessage, successMessage))
        return stopCodecResult
    }

    override fun setOverExposure(enabled: Boolean, resId: Int) : MutableLiveData<Resource<String>> {
        var setOverExposureResult : MutableLiveData<Resource<String>> = MutableLiveData()
        val successMessage = Utils.getSuccessShowText("For Over Exposure Status = ${enabled}, resId=${resId}", methodName = "setOverExposure")
        setOverExposureResult.postValue(Resource.Companion.success(successMessage, successMessage))
        return setOverExposureResult
    }

    override fun surfaceSizeChanged(surfaceWidth: Int, surfaceHeight: Int) : MutableLiveData<Resource<String>> {
        var surfaceSizeChangedResult : MutableLiveData<Resource<String>> = MutableLiveData()
        val successMessage = Utils.getSuccessShowText("For Surface Size Changed surfaceWidth = ${surfaceWidth}, surfaceHeight=${surfaceHeight}", methodName = "surfaceSizeChanged")
        surfaceSizeChangedResult.postValue(Resource.Companion.success(successMessage, successMessage))
        return surfaceSizeChangedResult
    }

    override fun setCodec(controller: AutelCodec) {
        mController = controller
    }






}