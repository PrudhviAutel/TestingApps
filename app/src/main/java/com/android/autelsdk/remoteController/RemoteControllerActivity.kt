package com.android.autelsdk.remoteController

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.android.autelsdk.BaseActivity
import com.android.autelsdk.R
import com.android.autelsdk.databinding.ActivityRemoteControllerBinding
import com.autel.common.remotecontroller.RemoteControllerLanguage
import com.autel.sdk.product.BaseProduct
import com.autel.sdk.remotecontroller.AutelRemoteController


// On Activity Start First getCustomViewResId() is called and then onCreate() --- nothing else on Activity Creation
class RemoteControllerActivity : BaseActivity<AutelRemoteController>() {
    val TAG = RemoteControllerActivity::class.java.simpleName

    lateinit var viewModel:RemoteControllerViewModel
    lateinit var binding: ActivityRemoteControllerBinding



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, customViewResId)

        viewModel = ViewModelProvider(this@RemoteControllerActivity).get(RemoteControllerViewModel::class.java)

        initUi()
        runTests()

    }

    private fun handleListeners() {

    }

    override fun initController(product: BaseProduct?): AutelRemoteController? {
        if (product != null)
            return product.remoteController

        return null
    }

    override fun getCustomViewResId(): Int {
        return R.layout.activity_remote_controller
    }

    override fun initUi() {
        handleListeners()
    }

    fun runTests() {

        viewModel.setLanguageTest(RemoteControllerLanguage.ENGLISH).observe(this, Observer { msg ->
            binding.testResults.append(msg.data)
        })
    }

}