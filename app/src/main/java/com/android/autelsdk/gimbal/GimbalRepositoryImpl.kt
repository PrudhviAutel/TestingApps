package com.android.autelsdk.gimbal

import com.autel.internal.gimbal.cruiser.CruiserGimbalImpl
import com.autel.sdk.gimbal.AutelGimbal

class GimbalRepositoryImpl() : GimbalRepository {

    var mController: AutelGimbal = CruiserGimbalImpl()

    override fun setController(controller: AutelGimbal) {
       // mController = controller
    }

    //❌


}
