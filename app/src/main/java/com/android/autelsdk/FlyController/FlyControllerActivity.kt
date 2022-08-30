package com.android.autelsdk.FlyController

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.android.autelsdk.BaseActivity
import com.android.autelsdk.R
import com.android.autelsdk.RemoteController.RemoteControllerActivity
import com.android.autelsdk.databinding.ActivityFlyControllerBinding
import com.android.autelsdk.databinding.ActivityRemoteControllerBinding
import com.autel.sdk.flycontroller.AutelFlyController
import com.autel.sdk.product.BaseProduct
import com.autel.sdk.remotecontroller.AutelRemoteController

//import com.android.autelsdk.R
//import com.android.autelsdk.databinding.ActivityRemoteControllerBinding
//import com.android.myapplication.R
//import com.android.myapplication.databinding.ActivityFlyControllerBinding

class FlyControllerActivity :  BaseActivity<AutelFlyController>() {
    val TAG = RemoteControllerActivity::class.java.simpleName
    lateinit var binding : ActivityFlyControllerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this , customViewResId)

    }

    private fun handleListeners() {
    }

    override fun initController(product: BaseProduct?): AutelFlyController? {
        if (product != null)
            return product!!.flyController

        return null
    }

    override fun getCustomViewResId(): Int {
        return R.layout.activity_fly_controller
    }

    override fun initUi() {
        handleListeners()
    }
}