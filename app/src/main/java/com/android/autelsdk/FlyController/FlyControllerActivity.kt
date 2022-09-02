package com.android.autelsdk.flyController

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.android.autelsdk.BaseActivity
import com.android.autelsdk.R
//import com.android.autelsdk.remoteController.RemoteControllerActivity
import com.android.autelsdk.databinding.ActivityFlyControllerBinding
import com.autel.sdk.flycontroller.AutelFlyController
import com.autel.sdk.product.BaseProduct
import com.autel.sdk.remotecontroller.AutelRemoteController
import java.util.EnumSet.of
import java.util.List.of
import java.util.Set.of


//import com.android.autelsdk.R
//import com.android.autelsdk.databinding.ActivityRemoteControllerBinding
//import com.android.myapplication.R
//import com.android.myapplication.databinding.ActivityFlyControllerBinding

class FlyControllerActivity :  BaseActivity<AutelFlyController>() {
    val TAG = FlyControllerActivity::class.java.simpleName
    lateinit var binding : ActivityFlyControllerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this , customViewResId)
        //val viewModel : FlyControllerViewModel<AutelFlyController> = ViewModelProvider.

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




