package com.android.autelsdk.codec

import com.android.autelsdk.util.Constants.Codec
import com.autel.internal.video.AutelCodec_Ranger
import com.autel.sdk.flycontroller.AutelFlyController
import com.autel.sdk.video.AutelCodec
import com.autel.sdk.video.AutelCodecListener

class CodecRepositoryImpl : CodecRepository {

    val mcontroller : AutelCodec = AutelCodec_Ranger()




}