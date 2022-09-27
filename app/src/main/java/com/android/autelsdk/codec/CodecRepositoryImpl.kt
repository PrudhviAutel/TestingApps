package com.android.autelsdk.codec

import com.autel.internal.video.AutelCodec_Ranger
import com.autel.internal.video.core.decoder2.CodecManager
import com.autel.sdk.video.AutelCodec

class CodecRepositoryImpl : CodecRepository {

    val mcontroller : AutelCodec = AutelCodec_Ranger()

    fun isOverExposureEnabled(): Boolean {
        return CodecManager.getInstance().isOverExposureEnabled
    }




}