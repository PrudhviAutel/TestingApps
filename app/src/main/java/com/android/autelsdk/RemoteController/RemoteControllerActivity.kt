package com.android.autelsdk.RemoteController

//import com.android.autelsdk.R
//import com.android.autelsdk.databinding.ActivityRemoteControllerBinding
//import com.android.autelsdk.databinding.DragonfishActivityRemoteControllerBinding
//import com.android.myapplication.R
//import com.android.myapplication.databinding.ActivityRemoteControllerBinding
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.android.autelsdk.BaseActivity
import com.android.autelsdk.R
import com.android.autelsdk.databinding.ActivityRemoteControllerBinding
import com.autel.sdk.product.BaseProduct
import com.autel.sdk.remotecontroller.AutelRemoteController

// On Activity Start First getCustomViewResId() is called and then onCreate() --- nothing else on Activity Creation
class RemoteControllerActivity : BaseActivity<AutelRemoteController>() {
    val TAG = RemoteControllerActivity::class.java.simpleName
    lateinit var binding: ActivityRemoteControllerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, customViewResId)
    }

    private fun handleListeners() {

    }

    override fun initController(product: BaseProduct?): AutelRemoteController? {
        if (product != null)
            return product!!.remoteController

        return null
    }

    override fun getCustomViewResId(): Int {
        return R.layout.activity_remote_controller
    }

    override fun initUi() {
        handleListeners()
    }


}